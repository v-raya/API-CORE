package gov.ca.cwds.authenticate.config;

import org.yaml.snakeyaml.TypeDescription;
import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.constructor.Constructor;

import io.dropwizard.configuration.ConfigurationSourceProvider;

/**
 * This interface is used to implement the {@link ConfigUtils}.
 * 
 * @author CWDS TPT-4 Team
 *
 */
public interface ConfigReader extends ConfigurationSourceProvider {

  /**
   * readConfig.
   * 
   * @return the config values
   */
  public CwdsAuthenticationClientConfig readConfig();

  /**
   * read the yaml values in the List.
   * 
   * @return the yaml list vales
   */
  default Yaml getYaml() {
    Constructor constructor = new Constructor(CwdsAuthenticationClientConfig.class);
    TypeDescription configDesc = new TypeDescription(CwdsAuthenticationClientConfig.class);
    configDesc.putListPropertyType("defaultUsers", User.class);
    constructor.addTypeDescription(configDesc);
    return new Yaml(constructor);
  }
}
