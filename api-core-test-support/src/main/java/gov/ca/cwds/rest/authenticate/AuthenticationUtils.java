package gov.ca.cwds.rest.authenticate;

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

  CWDSLoginType cwdsLoginType = new CWDSLoginType();
  private ConfigUtils configUtils = new ConfigUtils();

  /**
   * @param userType - Type user to get the token {@link UserGroup}
   * @return the user based token
   */
  public String getToken(UserGroup userType) {

    if (userType != null) {
      String loginType = configUtils.getYamlValues().getTokenCredentials().getAuthenticationMode();
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
    if ("TEST".equals(loginType))
      return cwdsLoginType.login("username", "password"); // will be updated to read the
                                                          // username/password from yml file
    else {
      return cwdsLoginType.login("username.Json"); // will be updated
    }
  }

}
