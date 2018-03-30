package gov.ca.cwds.rest.authenticate;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * This class provide all the values of the url's to get the token.
 * 
 * @author CWDS TPT4 Team
 *
 */
public class TokenCredentials {

  @JsonProperty
  private String baseUrl;

  @JsonProperty
  private String perryLoginUrl;

  @JsonProperty
  private String authLoginUrl;

  @JsonProperty
  private String tokenUrl;

  @JsonProperty
  private String callBackUrl;

  @JsonProperty
  private String logOutUrl;

  @JsonProperty
  private String authenticationMode;

  /**
   * getBaseUrl.
   * 
   * @return the baseUrl
   */
  public String getBaseUrl() {
    return baseUrl;
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
   * getAuthenticationMode.
   * 
   * @return the authenticationMode
   */
  public String getAuthenticationMode() {
    return authenticationMode;
  }

}
