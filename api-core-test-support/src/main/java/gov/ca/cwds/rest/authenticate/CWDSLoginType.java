package gov.ca.cwds.rest.authenticate;

/**
 * @author CWDS TPT-4 Team
 *
 */
public class CWDSLoginType {

  private static final int PASSWORD_FIELD = 1;
  private static final int USERNAME_FIELD = 0;
  CWDSClientCommon cwdsClientCommon = null;

  /**
   * @param params - params
   * @return the token based the type of login
   */
  public String login(String... params) {

    if (params != null) {
      if (params.length > 1) {
        cwdsClientCommon = new CWDSAuthenticationClient(params[USERNAME_FIELD], params[PASSWORD_FIELD]);
      } else {
        cwdsClientCommon = new CWDSDevAuthenticationClient(params[0]);
      }
    }
    return cwdsClientCommon.getToken();
  }

}
