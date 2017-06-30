package gov.ca.cwds.inject;

import com.google.inject.AbstractModule;

import gov.ca.cwds.rest.BaseApiApplication;
import gov.ca.cwds.rest.BaseApiConfiguration;
import gov.ca.cwds.rest.filters.RequestCommonInfoFilter;
import gov.ca.cwds.rest.filters.RequestResponseLoggingFilter;
import gov.ca.cwds.rest.filters.WebSecurityFilter;

/**
 * Dependency injection (DI) for Filter classes.
 * 
 * <p>
 * Register filters her with Guice and configure them in {@link BaseApiApplication}, method
 * registerFilters.
 * </p>
 * 
 * @author CWDS API Team
 * @param <T> type of API configuration
 */
public class FiltersModule<T extends BaseApiConfiguration> extends AbstractModule {

  @Override
  protected void configure() {
    bind(RequestCommonInfoFilter.class);
    bind(RequestResponseLoggingFilter.class);
    bind(WebSecurityFilter.class);
  }

  /*
   * This @Provides should work but it is throwing this error:<br>
   * "T cannot be used as a key; It is not fully specified."
   */
  // @Provides
  // public WebSecurityConfiguration provideWebSecurityConfiguration(T configuration) {
  // return configuration.getWebSecurityConfiguration();
  // }

}
