package gov.ca.cwds.authenticate.config;

/**
 * Get the values of the token credentials.
 * 
 * @author CWDS TPT-4 Team
 *
 */
public class CwdsAuthenticationClientConfig {

  private String authenticationMode;
  private TokenCredentials testUrl;
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
  public TokenCredentials getTestUrl() {
    return testUrl;
  }

  /**
   * @param testUrl - testUrl
   */
  public void setTestUrl(TokenCredentials testUrl) {
    this.testUrl = testUrl;
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
