package gov.ca.cwds.rest.authenticate;

import gov.ca.cwds.authenticate.config.SocialWorkerOnly;
import gov.ca.cwds.authenticate.config.TokenCredentials;

/**
 * Get the values of the token credentials.
 * 
 * @author CWDS TPT-4 Team
 *
 */
public class CWDSAuthenticationClientConfig {

  private String authenticationMode;
  private TokenCredentials tokenCredentials;
  private SocialWorkerOnly socialWorkerOnly;

  public String getAuthenticationMode() {
    return authenticationMode;
  }

  public void setAuthenticationMode(String authenticationMode) {
    this.authenticationMode = authenticationMode;
  }

  /**
   * @return the tokenCredentials
   */
  public TokenCredentials getTokenCredentials() {
    return tokenCredentials;
  }

  /**
   * @param tokenCredentials - tokenCredentials
   */
  public void setTokenCredentials(TokenCredentials tokenCredentials) {
    this.tokenCredentials = tokenCredentials;
  }

  public SocialWorkerOnly getSocialWorkerOnly() {
    return socialWorkerOnly;
  }

  public void setSocialWorkerOnly(SocialWorkerOnly socialWorkerOnly) {
    this.socialWorkerOnly = socialWorkerOnly;
  }

}
