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
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.Module;
import com.hubspot.dropwizard.guice.GuiceBundle;

import gov.ca.cwds.data.CmsSystemCodeSerializer;
import gov.ca.cwds.data.persistence.cms.ISystemCodeCache;
import gov.ca.cwds.rest.filters.DiscardErrors;
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

    migrateDatabase(configuration);

    LOGGER.info("Configuring CORS: Cross-Origin Resource Sharing");
    configureCors(environment);

    LOGGER.info("Configuring SWAGGER");
    configureSwagger(configuration, environment);

    LOGGER.info("Registering Filters");

    // TESTING: #129093035.
    // registerFilters(environment);
    environment.jersey().register(new DiscardErrors("search_person"));

    runInternal(configuration, environment);
  }

  private final void registerFilters(final Environment environment) {
    Injector injector = guiceBundle.getInjector();

    // Drop to Jackson 2.7.8. Runtime incompatibility with 2.8.6.
    // Register the ObjectMapper with Guice.
    // ObjectMapperModule omm =
    // new ObjectMapperModule().withObjectMapper(environment.getObjectMapper());

    // Inject system code cache.
    SimpleModule module =
        new SimpleModule("SystemCodeModule", new Version(0, 1, 0, "a", "alpha", ""));
    module.addSerializer(Short.class,
        new CmsSystemCodeSerializer(injector.getInstance(ISystemCodeCache.class)));
    environment.getObjectMapper().registerModule(module);
    Guice.createInjector().getInstance(ObjectMapper.class).registerModule(module);

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
