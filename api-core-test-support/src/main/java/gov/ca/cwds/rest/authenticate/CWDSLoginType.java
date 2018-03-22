package gov.ca.cwds.rest.authenticate;

/**
 * @author CWDS TPT-4 Team
 *
 */
public class CWDSLoginType {

  CWDSClientCommon cwdsClientCommon = null;

  /**
   * @param params - params
   * @return the token based the type of login
   */
  public String login(String... params) {

    if (params != null) {
      if (params.length > 1) {
        cwdsClientCommon = new CWDSAuthenticationClient(params[0], params[1]);
      } else {
        cwdsClientCommon = new CWDSDevAuthenticationClient(params[0]);
      }
    }
    return cwdsClientCommon.getToken();
  }

}
