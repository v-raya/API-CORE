package gov.ca.cwds.authenticate.config;

/**
 * @author CWDS TPT-4 Team
 *
 */
public class User {

  private String userType;
  private String username;
  private String password;

  /**
   * @return the username
   */
  public String getUsername() {
    return username;
  }

  /**
   * @param username - username
   */
  public void setUsername(String username) {
    this.username = username;
  }

  /**
   * @return the password
   */
  public String getPassword() {
    return password;
  }

  /**
   * @param password - password
   */
  public void setPassword(String password) {
    this.password = password;
  }

  /**
   * @return the userType
   */
  public String getUserType() {
    return userType;
  }

  /**
   * @param userType - userType
   */
  public void setUserType(String userType) {
    this.userType = userType;
  }

}
