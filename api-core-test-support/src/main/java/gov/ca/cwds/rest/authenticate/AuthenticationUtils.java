package gov.ca.cwds.rest.authenticate;

import gov.ca.cwds.authenticate.config.ConfigReader;
import gov.ca.cwds.authenticate.config.ConfigUtils;

/**
 * @author CWDS TPT-4 Team
 *
 */
public class AuthenticationUtils {

  private String token;
  private String countySensitiveToken;
  private String countySealedToken;
  private String stateSensitiveToken;
  private String stateSealedToken;

  CWDSLoginType cwdsLoginType = null;
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
    cwdsLoginType = new CWDSLoginType(configReader);
    if (userType != null) {
      switch (userType) {
        case SOCIAL_WORKER:
          return getSocialWorkerToken(userType);

        case COUNTY_SENSITIVE:
          return getCountySensitiveToken(userType);

        case COUNTY_SEALED:
          return getCountySealedToken(userType);

        case STATE_SENSITIVE:
          return getStateSenstiveToken(userType);

        case STATE_SEALED:
          return getStateSealedToken(userType);
      }
    }
    return null;
  }

  private String getStateSealedToken(UserGroup userType) {
    if (stateSealedToken == null) {
      stateSealedToken = cwdsLoginType.login(userType);
    }
    return stateSealedToken;
  }

  private String getStateSenstiveToken(UserGroup userType) {
    if (stateSensitiveToken == null) {
      stateSensitiveToken = cwdsLoginType.login(userType);
    }
    return stateSensitiveToken;
  }

  private String getCountySealedToken(UserGroup userType) {
    if (countySealedToken == null) {
      countySealedToken = cwdsLoginType.login(userType);
    }
    return countySealedToken;
  }

  private String getCountySensitiveToken(UserGroup userType) {
    if (countySensitiveToken == null) {
      countySensitiveToken = cwdsLoginType.login(userType);
    }
    return countySensitiveToken;
  }

  private String getSocialWorkerToken(UserGroup userType) {
    if (token == null) {
      token = cwdsLoginType.login(userType);
    }
    return token;
  }

}
