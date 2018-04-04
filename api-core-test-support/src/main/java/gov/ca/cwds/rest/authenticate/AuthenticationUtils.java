package gov.ca.cwds.rest.authenticate;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import gov.ca.cwds.authenticate.config.ConfigReader;
import gov.ca.cwds.authenticate.config.ConfigUtils;

/**
 * @author CWDS TPT-4 Team
 *
 */
public class AuthenticationUtils {

  private static final Logger LOGGER = LoggerFactory.getLogger(AuthenticationUtils.class);

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
   * @param userType - Type user to get the token {@link UserGroup}
   * @return the user based token
   */
  public String getToken(UserGroup userType) {

    cwdsLoginType = new CWDSLoginType(configReader);
    if (userType != null) {
      String loginType = configReader.readConfig().getAuthenticationMode();
      switch (userType) {
        case SOCIAL_WORKER:
          return getSocialWorkerToken(loginType, userType);

        case COUNTY_SENSITIVE:
          return getCountySensitiveToken(userType, loginType);

        case COUNTY_SEALED:
          return getCountySealedToken(userType, loginType);

        case STATE_SENSITIVE:
          return getStateSenstiveToken(userType, loginType);

        case STATE_SEALED:
          return getStateSealedToken(userType, loginType);
      }
    }
    return null;
  }

  private String getStateSealedToken(UserGroup userType, String loginType) {
    if (stateSealedToken == null) {
      stateSealedToken = getToken(loginType, userType);
    }
    return stateSealedToken;
  }

  private String getStateSenstiveToken(UserGroup userType, String loginType) {
    if (stateSensitiveToken == null) {
      stateSensitiveToken = getToken(loginType, userType);
    }
    return stateSensitiveToken;
  }

  private String getCountySealedToken(UserGroup userType, String loginType) {
    if (countySealedToken == null) {
      countySealedToken = getToken(loginType, userType);
    }
    return countySealedToken;
  }

  private String getCountySensitiveToken(UserGroup userType, String loginType) {
    if (countySensitiveToken == null) {
      countySensitiveToken = getToken(loginType, userType);
    }
    return countySensitiveToken;
  }

  private String getSocialWorkerToken(String loginType, UserGroup userType) {
    if (token == null) {
      token = getToken(loginType, userType);
    }
    return token;
  }

  private String getToken(String loginType, UserGroup userType) {
    if (!"TEST".equals(loginType)) {
      return cwdsLoginType.login("username", "password");
    } else {
      String jsonFile = "";
      String userJson = "";
      if (userType != null) {
        jsonFile = "/LoginUser/" + userType.getName() + ".json";
      }
      try {
        userJson = new String(IOUtils.toByteArray(getClass().getResourceAsStream(jsonFile)),
            StandardCharsets.UTF_8);
      } catch (IOException e) {
        LOGGER.error("Unable to parse the json into String {}", e);
      }
      return cwdsLoginType.login(userJson);
    }
  }

}
