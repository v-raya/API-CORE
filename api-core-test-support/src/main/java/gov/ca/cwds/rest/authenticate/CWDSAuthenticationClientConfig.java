package gov.ca.cwds.rest.authenticate;

import java.util.Map;

/**
 * @author CWDS TPT-4 Team
 *
 */
public class CWDSAuthenticationClientConfig {

  class TokenCredentials {

    public TokenCredentials() {
      // no-opt
    }

    private String baseUrl;
    private String perryLoginUrl;
    private String authLoginUrl;
    private String tokenUrl;
    private String callBackUrl;
    private String logOutUrl;
    private String authenticationMode;

    public String getBaseUrl() {
      return baseUrl;
    }

    public String getPerryLoginUrl() {
      return perryLoginUrl;
    }

    public String getAuthLoginUrl() {
      return authLoginUrl;
    }

    public String getTokenUrl() {
      return tokenUrl;
    }

    public String getCallBackUrl() {
      return callBackUrl;
    }

    public String getLogOutUrl() {
      return logOutUrl;
    }

    public String getAuthenticationMode() {
      return authenticationMode;
    }

  }

  private TokenCredentials tokenCredentials;
  private Map<String, String> socialWorkerOnly;
  private Map<String, String> countySensitivePrivilegeUser;
  private Map<String, String> countySealedPrivilegeUser;
  private Map<String, String> stateSensitivePrivilegeUser;
  private Map<String, String> stateSealedPrivilegeUser;

  public TokenCredentials getTokenCredentials() {
    return tokenCredentials;
  }

  public Map<String, String> getSocialWorkerOnly() {
    return socialWorkerOnly;
  }

  public Map<String, String> getCountySensitivePrivilegeUser() {
    return countySensitivePrivilegeUser;
  }

  public Map<String, String> getCountySealedPrivilegeUser() {
    return countySealedPrivilegeUser;
  }

  public Map<String, String> getStateSensitivePrivilegeUser() {
    return stateSensitivePrivilegeUser;
  }

  public Map<String, String> getStateSealedPrivilegeUser() {
    return stateSealedPrivilegeUser;
  }

}
