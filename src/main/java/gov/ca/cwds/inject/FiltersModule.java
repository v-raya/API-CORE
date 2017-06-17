package gov.ca.cwds.inject;

import com.google.inject.AbstractModule;

import gov.ca.cwds.rest.BaseApiConfiguration;
import gov.ca.cwds.rest.filters.RequestResponseLoggingFilter;
import gov.ca.cwds.rest.filters.WebSecurityFilter;

/**
 * DI for Filter classes
 * 
 * @author CWDS API Team
 * @param <T> configuration type
 */
public class FiltersModule<T extends BaseApiConfiguration> extends AbstractModule {

  @Override
  protected void configure() {
    bind(RequestResponseLoggingFilter.class);
    bind(WebSecurityFilter.class);
  }

  // @Provides
  // public WebSecurityConfiguration provideWebSecurityConfiguration(T configuration) {
  // return configuration.getWebSecurityConfiguration();
  // }
}
