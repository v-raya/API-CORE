package gov.ca.cwds.inject;

import com.google.inject.AbstractModule;
import com.google.inject.Provides;

import gov.ca.cwds.rest.BaseApiConfiguration;
import gov.ca.cwds.rest.WebSecurityConfiguration;
import gov.ca.cwds.rest.filters.RequestResponseLoggingFilter;
import gov.ca.cwds.rest.filters.WebSecurityFilter;

/**
 * DI for Filter classes
 * 
 * @author CWDS API Team
 */
public class FiltersModule extends AbstractModule {

  @Override
  protected void configure() {
    bind(RequestResponseLoggingFilter.class);
    bind(WebSecurityFilter.class);
  }

  @Provides
  public WebSecurityConfiguration webSecurityConfiguration(BaseApiConfiguration apiConfiguration) {
    return apiConfiguration.getWebSecurityConfiguration();
  }
}
