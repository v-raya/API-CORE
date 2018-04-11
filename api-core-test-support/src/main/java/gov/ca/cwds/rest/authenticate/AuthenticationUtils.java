package gov.ca.cwds.rest.authenticate;

import gov.ca.cwds.authenticate.config.ConfigReader;
import gov.ca.cwds.authenticate.config.ConfigUtils;

/**
 * @author CWDS TPT-4 Team
 *
 */
public class AuthenticationUtils {

  CwdsLoginType cwdsLoginType = null;
  private ConfigReader configReader;

  /**
   * @param configReader - configReader
   */
  public AuthenticationUtils(ConfigReader configReader) {
    if (configReader == null) {
      this.configReader = new ConfigUtils();
    } else {
      this.configReader = configReader;
    }
  }

  /**
   * @param userType - Type of user to get the token {@link UserGroup}
   * @return the user based token
   */
  public String getToken(UserGroup userType) {
    cwdsLoginType = new CwdsLoginType(configReader);
    return cwdsLoginType.login(userType);
  }

}
