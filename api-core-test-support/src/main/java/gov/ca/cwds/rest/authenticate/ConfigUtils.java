package gov.ca.cwds.rest.authenticate;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;

/**
 * @author CWDS TPT-4 Team
 *
 */
public class ConfigUtils {

  private static final Logger LOGGER = LoggerFactory.getLogger(ConfigUtils.class);

  private ObjectMapper mapper = new ObjectMapper(new YAMLFactory());
  private CWDSAuthenticationClientConfig cwdsAuthenticationClientConfig;

  /**
   * @return the yaml file values
   */
  public CWDSAuthenticationClientConfig getYamlValues() {
    try {
      cwdsAuthenticationClientConfig = mapper.readValue(
          getClass().getResourceAsStream("/config.yml"), CWDSAuthenticationClientConfig.class);
    } catch (Exception e) {
      LOGGER.error("Unable to read the yml file {}", e);
    }
    return cwdsAuthenticationClientConfig;

  }

}
