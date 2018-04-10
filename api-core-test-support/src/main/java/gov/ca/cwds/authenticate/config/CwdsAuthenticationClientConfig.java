package gov.ca.cwds.authenticate.config;

import java.util.List;

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
  private List<User> defaultUsers;

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

  public List<User> getDefaultUsers() {
    return defaultUsers;
  }

  public void setDefaultUsers(List<User> defaultUsers) {
    this.defaultUsers = defaultUsers;
  }

}
