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

  /**
   * @param userType - Type user to get the token {@link UserGroup}
   * @return the user based token
   */
  public String getToken(UserGroup userType) {

    if (userType != null) {
      String loginType = ""; // config read to TEST or not
      switch (userType) {
        case SOCIAL_WORKER:
          return getSocialWorkerToken(loginType, userType);

        case COUNTY_SENSITIVE:
          if (countySensitiveToken == null) {
            countySensitiveToken = getToken(loginType, userType);
          }
          return countySensitiveToken;

        case COUNTY_SEALED:
          if (countySealedToken == null) {
            countySealedToken = getToken(loginType, userType);
          }
          return countySealedToken;

        case STATE_SENSITIVE:
          if (stateSensitiveToken == null) {
            stateSensitiveToken = getToken(loginType, userType);
          }
          return stateSensitiveToken;

        case STATE_SEALED:
          if (stateSealedToken == null) {
            stateSealedToken = getToken(loginType, userType);
          }
          return stateSealedToken;
      }

    }

    return null;

  }

  private String getSocialWorkerToken(String loginType, UserGroup userType) {
    if (token == null) {
      token = getToken(loginType, userType);
    }
    return token;
  }

  private String getToken(String loginType, UserGroup userType) {
    if ("REST".equals(loginType))
      return cwdsLoginType.login("username", "password"); // will be updated to read the
                                                          // username/password from yml file
    else {
      return cwdsLoginType.login("username.Json"); // will be updated
    }
  }

}
