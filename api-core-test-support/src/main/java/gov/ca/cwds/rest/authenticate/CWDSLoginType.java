package gov.ca.cwds.rest.authenticate;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import gov.ca.cwds.authenticate.config.ConfigReader;

/**
 * @author CWDS TPT-4 Team
 *
 */
public class CWDSLoginType {

  private static final Logger LOGGER = LoggerFactory.getLogger(CWDSLoginType.class);

  CWDSClientCommon cwdsClientCommon = null;
  ConfigReader configReader = null;

  /**
   * Constructor.
   * 
   * @param configReader - configReader
   */
  public CWDSLoginType(ConfigReader configReader) {
    this.configReader = configReader;
  }

  /**
   * @param userType - userType
   * @return the valid user token
   */
  public String login(UserGroup userType) {
    if (!"TEST".equals(configReader.readConfig().getAuthenticationMode())) {
      new CWDSAuthenticationClient(configReader, "", "");
    } else {
      String jsonFile = "/LoginUser/" + userType.getName() + ".json";
      String userJson = "";
      try {
        userJson = new String(IOUtils.toByteArray(getClass().getResourceAsStream(jsonFile)),
            StandardCharsets.UTF_8);
        cwdsClientCommon = new CWDSDevAuthenticationClient(configReader, userJson);

      } catch (IOException e) {
        LOGGER.error("Unable to parse the json into String {}", e);
      }
    }
    return cwdsClientCommon.getToken();
  }

}
