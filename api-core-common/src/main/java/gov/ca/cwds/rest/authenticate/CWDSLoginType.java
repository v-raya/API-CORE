package gov.ca.cwds.rest.authenticate;

/**
 * @author CWDS TPT-4 Team
 *
 */
public class CWDSLoginType {

  /**
   * @param params - params
   * @return the token based the type of login
   */
  public String login(String... params) {

    if (params != null) {
      if (params.length > 1) {
        return new CWDSAuthenticationClient(params[0], params[1]).getToken();
      } else
        return new CWDSDevAuthenticationClient(params[0]).getToken();
    }
    return null;
  }

}
