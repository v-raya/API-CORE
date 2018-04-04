package gov.ca.cwds.authenticate.config;

import java.io.IOException;
import java.io.InputStream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.yaml.snakeyaml.Yaml;

import io.dropwizard.configuration.EnvironmentVariableSubstitutor;
import io.dropwizard.configuration.SubstitutingSourceProvider;

/**
 * @author CWDS TPT-4 Team
 *
 */
public class ConfigUtils implements ConfigReader {

  private static final Logger LOGGER = LoggerFactory.getLogger(ConfigUtils.class);

  private CWDSAuthenticationClientConfig cwdsAuthenticationClientConfig;

  /**
   * @return the yaml file values
   */
  @Override
  public CWDSAuthenticationClientConfig readConfig() {
    Yaml yaml = new Yaml();
    try {
      InputStream ymlTestingSourceProvider =
          new SubstitutingSourceProvider(this, new EnvironmentVariableSubstitutor(false))
              .open("/testConfig.yml");
      cwdsAuthenticationClientConfig =
          yaml.loadAs(ymlTestingSourceProvider, CWDSAuthenticationClientConfig.class);
    } catch (IOException e) {
      LOGGER.error("Unable to read the yml file {}", e);
    }
    return cwdsAuthenticationClientConfig;
  }

  @Override
  public InputStream open(String path) throws IOException {
    return getClass().getResourceAsStream(path);
  }

}
