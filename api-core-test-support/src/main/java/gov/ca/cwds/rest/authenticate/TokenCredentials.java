package gov.ca.cwds.rest.authenticate;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
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
   * @return the baseUrl
   */
  public String getBaseUrl() {
    return baseUrl;
  }

  /**
   * @return the perryLoginUrl
   */
  public String getPerryLoginUrl() {
    return perryLoginUrl;
  }

  /**
   * @return the authLoginUrl
   */
  public String getAuthLoginUrl() {
    return authLoginUrl;
  }

  /**
   * @return the tokenUrl
   */
  public String getTokenUrl() {
    return tokenUrl;
  }

  /**
   * @return the callBackUrl
   */
  public String getCallBackUrl() {
    return callBackUrl;
  }

  /**
   * @return the logOutUrl
   */
  public String getLogOutUrl() {
    return logOutUrl;
  }

  /**
   * @return the authenticationMode
   */
  public String getAuthenticationMode() {
    return authenticationMode;
  }

}
