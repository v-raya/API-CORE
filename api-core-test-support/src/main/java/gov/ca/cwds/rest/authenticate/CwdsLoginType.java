package gov.ca.cwds.rest.authenticate;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Optional;

import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import gov.ca.cwds.authenticate.config.ConfigReader;
import gov.ca.cwds.authenticate.config.User;

/**
 * This class will handle the different type of login based on the authentication mode, If the
 * Authentication mode in the Yaml file is set to TEST it uses the Json type or else username and
 * password to get the token.
 * 
 * <p>
 * authenticationMode: TEST mean perry is in dev mode
 * </p>
 * 
 * @author CWDS TPT-4 Team
 *
 */
public class CwdsLoginType {

  private static final Logger LOGGER = LoggerFactory.getLogger(CwdsLoginType.class);

  CwdsClientCommon cwdsClientCommon = null;
  ConfigReader configReader = null;

  /**
   * Constructor.
   * 
   * @param configReader - configReader
   */
  public CwdsLoginType(ConfigReader configReader) {
    this.configReader = configReader;
  }

  /**
   * @param userType - userType
   * @return the valid user token
   */
  public String login(UserGroup userType) {
    if ("TEST".equals(configReader.readConfig().getAuthenticationMode())) {
      List<User> users = configReader.readConfig().getDefaultUsers();
      Optional<User> user = users.stream()
          .filter(value -> userType.getName().equals(value.getUserType())).findFirst();
      String userName = null;
      String password = null;
      if (user.isPresent()) {
        userName = user.get().getUsername();
        password = user.get().getPassword();
      }
      cwdsClientCommon = new CwdsAuthenticationClient(configReader, userName, password);
    } else {
      String jsonFile = "/LoginUser/" + userType.getName() + ".json";
      String userJson = "";
      try {
        userJson = new String(IOUtils.toByteArray(getClass().getResourceAsStream(jsonFile)),
            StandardCharsets.UTF_8);
        cwdsClientCommon = new CwdsDevAuthenticationClient(configReader, userJson);

      } catch (IOException e) {
        LOGGER.error("Unable to parse the json into String {}", e);
      }
    }
    return cwdsClientCommon.getToken();
  }

}
