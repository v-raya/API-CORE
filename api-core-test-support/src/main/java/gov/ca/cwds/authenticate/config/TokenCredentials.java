package gov.ca.cwds.authenticate.config;

/**
 * This class provide all the values of the url's to get the token.
 * 
 * @author CWDS TPT4 Team
 *
 */
public class TokenCredentials {

  private String authenticationBaseUrl;

  private String perryLoginUrl;

  private String authLoginUrl;

  private String tokenUrl;

  private String callBackUrl;

  private String logOutUrl;

  /**
   * getBaseUrl.
   * 
   * @return the baseUrl
   */
  public String getAuthenticationBaseUrl() {
    return authenticationBaseUrl;
  }

  /**
   * getPerryLoginUrl.
   * 
   * @return the perryLoginUrl
   */
  public String getPerryLoginUrl() {
    return perryLoginUrl;
  }

  /**
   * getAuthLoginUrl.
   * 
   * @return the authLoginUrl
   */
  public String getAuthLoginUrl() {
    return authLoginUrl;
  }

  /**
   * getTokenUrl.
   * 
   * @return the tokenUrl
   */
  public String getTokenUrl() {
    return tokenUrl;
  }

  /**
   * getCallBackUrl.
   * 
   * @return the callBackUrl
   */
  public String getCallBackUrl() {
    return callBackUrl;
  }

  /**
   * getLogOutUrl.
   * 
   * @return the logOutUrl
   */
  public String getLogOutUrl() {
    return logOutUrl;
  }

  /**
   * @param authenticationBaseUrl - authenticationBaseUrl
   */
  public void setAuthenticationBaseUrl(String authenticationBaseUrl) {
    this.authenticationBaseUrl = authenticationBaseUrl;
  }

  /**
   * @param perryLoginUrl - perryLoginUrl
   */
  public void setPerryLoginUrl(String perryLoginUrl) {
    this.perryLoginUrl = perryLoginUrl;
  }

  /**
   * @param authLoginUrl - authLoginUrl
   */
  public void setAuthLoginUrl(String authLoginUrl) {
    this.authLoginUrl = authLoginUrl;
  }

  /**
   * @param tokenUrl - tokenUrl
   */
  public void setTokenUrl(String tokenUrl) {
    this.tokenUrl = tokenUrl;
  }

  /**
   * @param callBackUrl - callBackUrl
   */
  public void setCallBackUrl(String callBackUrl) {
    this.callBackUrl = callBackUrl;
  }

  /**
   * @param logOutUrl - logOutUrl
   */
  public void setLogOutUrl(String logOutUrl) {
    this.logOutUrl = logOutUrl;
  }

}
