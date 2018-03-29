package gov.ca.cwds.rest.authenticate;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Get the values of the token credentials.
 * 
 * @author CWDS TPT-4 Team
 *
 */
public class CWDSAuthenticationClientConfig {

  @JsonProperty
  private TokenCredentials tokenCredentials;

  /**
   * @return the tokenCredentials
   */
  public TokenCredentials getTokenCredentials() {
    return tokenCredentials;
  }

}

