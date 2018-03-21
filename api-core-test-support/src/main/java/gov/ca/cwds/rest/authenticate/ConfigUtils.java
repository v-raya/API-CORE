package gov.ca.cwds.rest.authenticate;

import java.io.File;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;

public class ConfigUtils {

  private static final Logger LOGGER = LoggerFactory.getLogger(ConfigUtils.class);

  private ObjectMapper mapper = new ObjectMapper(new YAMLFactory());
  private CWDSAuthenticationClientConfig cwdsAuthenticationClientConfig;

  /**
   * @return
   */
  public CWDSAuthenticationClientConfig getYamlValues() {
    try {
      cwdsAuthenticationClientConfig =
          mapper.readValue(new File("config/config.yml"), CWDSAuthenticationClientConfig.class);
    } catch (Exception e) {
      e.printStackTrace();
    }
    return cwdsAuthenticationClientConfig;
  }


}
