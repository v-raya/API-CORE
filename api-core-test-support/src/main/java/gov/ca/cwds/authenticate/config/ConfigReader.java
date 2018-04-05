package gov.ca.cwds.authenticate.config;

import io.dropwizard.configuration.ConfigurationSourceProvider;

/**
 * This interface is used the implement the {@link ConfigUtils}.
 * 
 * @author CWDS TPT-4 Team
 *
 */
public interface ConfigReader extends ConfigurationSourceProvider {

  /**
   * @return the config values
   */
  public CwdsAuthenticationClientConfig readConfig();
}
