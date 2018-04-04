package gov.ca.cwds.rest.authenticate;

import gov.ca.cwds.authenticate.config.ConfigReader;

/**
 * @author CWDS TPT-4 Team
 *
 */
public class CWDSLoginType {

  private static final int PASSWORD_FIELD = 1;
  private static final int USERNAME_FIELD = 0;
  CWDSClientCommon cwdsClientCommon = null;
  ConfigReader configReader = null;

  /**
   * Constructor.
   * 
   * @param configReader - configReader
   */
  public CWDSLoginType(ConfigReader configReader) {
    this.configReader = configReader;
  }

  /**
   * @param params - params
   * @return the token based the type of login
   */
  public String login(String... params) {

    if (params != null) {
      if (params.length > 1) {
        cwdsClientCommon = new CWDSAuthenticationClient(configReader, params[USERNAME_FIELD],
            params[PASSWORD_FIELD]);
      } else {
        cwdsClientCommon = new CWDSDevAuthenticationClient(configReader, params[USERNAME_FIELD]);
      }
    }
    return cwdsClientCommon.getToken();
  }

}
