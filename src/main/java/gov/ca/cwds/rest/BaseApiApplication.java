package gov.ca.cwds.rest;

import java.util.EnumSet;
import java.util.List;

import javax.servlet.DispatcherType;
import javax.servlet.FilterRegistration;

import org.eclipse.jetty.server.session.SessionHandler;
import org.eclipse.jetty.servlets.CrossOriginFilter;
import org.flywaydb.core.Flyway;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.Version;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.google.inject.Injector;
import com.google.inject.Module;
import com.hubspot.dropwizard.guice.GuiceBundle;

import gov.ca.cwds.data.CmsSystemCodeSerializer;
import gov.ca.cwds.data.persistence.cms.CmsSystemCodeCacheService;
import gov.ca.cwds.data.persistence.cms.SystemCodeDaoFileImpl;
import gov.ca.cwds.rest.filters.RequestResponseLoggingFilter;
import gov.ca.cwds.rest.resources.SwaggerResource;
import io.dropwizard.Application;
import io.dropwizard.assets.AssetsBundle;
import io.dropwizard.configuration.EnvironmentVariableSubstitutor;
import io.dropwizard.configuration.SubstitutingSourceProvider;
import io.dropwizard.db.DataSourceFactory;
import io.dropwizard.flyway.FlywayBundle;
import io.dropwizard.flyway.FlywayFactory;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import io.dropwizard.views.ViewBundle;
import io.swagger.jaxrs.config.BeanConfig;
import io.swagger.jaxrs.listing.ApiListingResource;

/**
 * Base execution class for CWDS REST API applications.
 * 
 * @author CWDS API Team
 * @param <T> type of configuration
 */
public abstract class BaseApiApplication<T extends BaseApiConfiguration> extends Application<T> {

  private static final Logger LOGGER = LoggerFactory.getLogger(BaseApiApplication.class);

  private GuiceBundle<T> guiceBundle;

  private final FlywayBundle<T> flywayBundle = new FlywayBundle<T>() {
    @Override
    public DataSourceFactory getDataSourceFactory(T configuration) {
      return configuration.getNsDataSourceFactory();
    }

    @Override
    public FlywayFactory getFlywayFactory(T configuration) {
      return configuration.getFlywayFactory();
    }
  };

  /**
   * Extending classes must provide the Guice module for their application.
   * 
   * @param bootstrap The bootstrap
   * @return The main Guice module for this application.
   */
  public abstract Module applicationModule(Bootstrap<T> bootstrap);

  /**
   * Extending classes can do any initialization specific to their applications here.
   * 
   * @param bootstrap The bootstrap for this application
   */
  public void initializeInternal(Bootstrap<T> bootstrap) {}

  /**
   * Extending cannot implement the run method, this is their chance to complete tasks which would
   * normally go in run.
   * 
   * @param configuration The configuration
   * @param environment The environment
   */
  public void runInternal(final T configuration, final Environment environment) {}

  @Override
  public final void initialize(Bootstrap<T> bootstrap) {

    // DropWizard/Guice integration error. Call in run() instead. :-(
    // Occurs in GuiceServiceLocatorGeneratorStub.create(). The "throw" is unnecessary.
    // https://github.com/HubSpot/dropwizard-guice/issues/95
    // https://github.com/Squarespace/jersey2-guice/pull/39/files#diff-a1f0825aeeb627cc56eb829c34394860R50

    // #136994539: GOAL: Deserialize enums without resorting to hacks.
    // bootstrap.getObjectMapper().enable(DeserializationFeature.READ_ENUMS_USING_TO_STRING);

    bootstrap.setConfigurationSourceProvider(new SubstitutingSourceProvider(
        bootstrap.getConfigurationSourceProvider(), new EnvironmentVariableSubstitutor(false)));

    bootstrap.addBundle(new ViewBundle<T>());
    guiceBundle = GuiceBundle.<T>newBuilder().addModule(applicationModule(bootstrap))
        .setConfigClass(bootstrap.getApplication().getConfigurationClass())
        .enableAutoConfig(getClass().getPackage().getName()).build();

    bootstrap.addBundle(guiceBundle);
    bootstrap.addBundle(flywayBundle);
    initializeInternal(bootstrap);
  }

  @Override
  public final void run(final T configuration, final Environment environment) throws Exception {
    environment.servlets().setSessionHandler(new SessionHandler());

    LOGGER.info("Application name: {}, Version: {}", configuration.getApplicationName(),
        configuration.getVersion());

    // #136994539: GOAL: Deserialize enums without resorting to hacks.
    // environment.getObjectMapper().enable(DeserializationFeature.READ_ENUMS_USING_TO_STRING);
    // environment.getObjectMapper().enable(DeserializationFeature.UNWRAP_ROOT_VALUE);

    SimpleModule module =
        new SimpleModule("SystemCodeModule", new Version(0, 1, 0, "a", "alpha", ""));

    // TODO: #137548119: Inject system code cache.
    module.addSerializer(Short.class,
        new CmsSystemCodeSerializer(new CmsSystemCodeCacheService(new SystemCodeDaoFileImpl())));
    environment.getObjectMapper().registerModule(module);

    // Guice.createInjector(modules)

    // Binding doesn't work because Short is a final class; cannot extend it.
    // final Injector injector = Guice.createInjector(environment.getObjectMapper(), new Module() {
    // @Override
    // public void configure(Binder binder) {
    // binder.bind(Short.class).annotatedWith(SystemCodeSerializer.class).toInstance(3);
    // }
    // });

    migrateDatabase(configuration);

    LOGGER.info("Configuring CORS: Cross-Origin Resource Sharing");
    configureCors(environment);

    LOGGER.info("Configuring SWAGGER");
    configureSwagger(configuration, environment);

    LOGGER.info("Registering Filters");
    registerFilters(environment);

    runInternal(configuration, environment);
  }

  private final void registerFilters(final Environment environment) {
    Injector injector = guiceBundle.getInjector();
    environment.servlets()
        .addFilter("AuditAndLoggingFilter",
            injector.getInstance(RequestResponseLoggingFilter.class))
        .addMappingForUrlPatterns(EnumSet.of(DispatcherType.REQUEST), true, "/*");
  }

  private final void configureCors(final Environment environment) {
    FilterRegistration.Dynamic filter =
        environment.servlets().addFilter("CORS", CrossOriginFilter.class);
    filter.addMappingForUrlPatterns(EnumSet.allOf(DispatcherType.class), true, "/*");
    filter.setInitParameter(CrossOriginFilter.ALLOWED_METHODS_PARAM, "GET,PUT,POST,DELETE,OPTIONS");
    filter.setInitParameter(CrossOriginFilter.ALLOWED_ORIGINS_PARAM, "*");
    filter.setInitParameter(CrossOriginFilter.ACCESS_CONTROL_ALLOW_ORIGIN_HEADER, "*");
    filter.setInitParameter("allowedHeaders",
        "Content-Type,Authorization,X-Requested-With,Content-Length,Accept,Origin,X-Auth-Token");
    filter.setInitParameter("allowCredentials", "true");
  }

  private final void configureSwagger(final T apiConfiguration, final Environment environment) {
    BeanConfig config = new BeanConfig();
    config.setTitle(apiConfiguration.getSwaggerConfiguration().getTitle());
    config.setDescription(apiConfiguration.getSwaggerConfiguration().getDescription());
    config.setResourcePackage(apiConfiguration.getSwaggerConfiguration().getResourcePackage());
    config.setScan(true);

    new AssetsBundle(apiConfiguration.getSwaggerConfiguration().getAssetsPath(),
        apiConfiguration.getSwaggerConfiguration().getAssetsPath(), null, "swagger")
            .run(environment);
    environment.getObjectMapper().setSerializationInclusion(JsonInclude.Include.NON_NULL);
    environment.getObjectMapper().enable(SerializationFeature.INDENT_OUTPUT);

    LOGGER.info("Registering ApiListingResource");
    environment.jersey().register(new ApiListingResource());

    LOGGER.info("Registering SwaggerResource");
    final SwaggerResource swaggerResource =
        new SwaggerResource(apiConfiguration.getSwaggerConfiguration());
    environment.jersey().register(swaggerResource);
  }

  private final boolean isFlywayConfigured(final T configuration) {
    return configuration.getFlywayFactory() != null;
  }

  private final void migrateDatabase(final T configuration) {
    if (isFlywayConfigured(configuration)) {
      LOGGER.info("Migrating New System Database");
      Flyway flyway = new Flyway();
      flyway.setDataSource(flywayBundle.getDataSourceFactory(configuration).build(null, "ds"));
      List<String> locations = flywayBundle.getFlywayFactory(configuration).getLocations();
      flyway.setLocations(locations.toArray(new String[locations.size()]));
      flyway.setSqlMigrationPrefix(
          flywayBundle.getFlywayFactory(configuration).getSqlMigrationPrefix());
      flyway.migrate();
    } else {
      LOGGER.info("No Flyway Factory found - not migrating New System Database");
    }
  }
}
