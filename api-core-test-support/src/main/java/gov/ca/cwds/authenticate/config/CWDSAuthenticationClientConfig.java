package gov.ca.cwds.authenticate.config;

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

  /**
   * @return the authenticationMode
   */
  public String getAuthenticationMode() {
    return authenticationMode;
  }

  /**
   * @param authenticationMode - authenticationMode
   */
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

  /**
   * @return the socialWorkerOnly
   */
  public SocialWorkerOnly getSocialWorkerOnly() {
    return socialWorkerOnly;
  }

  /**
   * @param socialWorkerOnly - socialWorkerOnly
   */
  public void setSocialWorkerOnly(SocialWorkerOnly socialWorkerOnly) {
    this.socialWorkerOnly = socialWorkerOnly;
  }
}
