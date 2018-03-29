package gov.ca.cwds.rest.authenticate;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author CWDS TPT-4 Team
 *
 */
public class CWDSAuthenticationClientConfig {

  @JsonProperty
  private TokenCredentials tokenCredentials;

  /**
   * @return
   */
  public TokenCredentials getTokenCredentials() {
    return tokenCredentials;
  }

}

