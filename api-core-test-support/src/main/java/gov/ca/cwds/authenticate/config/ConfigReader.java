package gov.ca.cwds.authenticate.config;

import io.dropwizard.configuration.ConfigurationSourceProvider;

/**
 * @author CWDS TPT-4 Team
 *
 */
public interface ConfigReader extends ConfigurationSourceProvider {

  /**
   * @return the config valus
   */
  public CWDSAuthenticationClientConfig readConfig();
}
