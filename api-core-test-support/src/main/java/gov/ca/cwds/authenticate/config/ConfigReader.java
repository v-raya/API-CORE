package gov.ca.cwds.authenticate.config;

import org.yaml.snakeyaml.TypeDescription;
import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.constructor.Constructor;

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

  default Yaml getYaml() {
    Constructor constructor = new Constructor(CwdsAuthenticationClientConfig.class);
    TypeDescription configDesc = new TypeDescription(CwdsAuthenticationClientConfig.class);
    configDesc.putListPropertyType("defaultUsers", User.class);
    constructor.addTypeDescription(configDesc);
    return new Yaml(constructor);
  }
}
