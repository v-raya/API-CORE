package gov.ca.cwds.inject;

import com.google.inject.AbstractModule;

import gov.ca.cwds.rest.BaseApiConfiguration;
import gov.ca.cwds.rest.filters.RequestResponseLoggingFilter;
import gov.ca.cwds.rest.filters.WebSecurityFilter;

/**
 * Dependency injection (DI) for Filter classes.
 * 
 * @author CWDS API Team
 * @param <T> type of API configuration
 */
public class FiltersModule<T extends BaseApiConfiguration> extends AbstractModule {

  @Override
  protected void configure() {
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
