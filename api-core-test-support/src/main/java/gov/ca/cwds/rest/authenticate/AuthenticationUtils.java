package gov.ca.cwds.rest.authenticate;

/**
 * @author CWDS TPT-4 Team
 *
 */
public class AuthenticationUtils {

  private static String token;
  private static String countySensitiveToken;
  private static String countySealedToken;
  private static String stateSensitiveToken;
  private static String stateSealedToken;
  private static CWDSLoginType cwdsLoginType = new CWDSLoginType();

  /**
   * @param userType - Type user to get the token {@link UserGroup}
   * @return the user based token
   */
  public static String getToken(UserGroup userType) {

    if (userType != null) {
      String loginType = ""; // config read to TEST or not
      switch (userType) {
        case SOCIAL_WORKER:
          if (token == null) {
            token = getToken(loginType, userType);
          }
          return token;

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

  private static String getToken(String loginType, UserGroup userType) {
    if ("REST".equals(loginType))
      return cwdsLoginType.login("username", "password"); // will be updated to read the
                                                          // username/password from yml file
    else {
      return cwdsLoginType.login("username.Json"); // will be updated
    }
  }

}
