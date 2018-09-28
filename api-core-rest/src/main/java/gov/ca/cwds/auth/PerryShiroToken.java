package gov.ca.cwds.auth;

import static com.google.common.base.Preconditions.checkNotNull;

import org.apache.shiro.authc.AuthenticationToken;

@SuppressWarnings("serial")
public class PerryShiroToken implements AuthenticationToken {

  private final String token;

  /**
   * Construct from authentication token.
   * 
   * @param token Perry token
   */
  public PerryShiroToken(String token) {
    this.token = checkNotNull(token, "token cannot be null.");
  }

  @Override
  public Object getPrincipal() {
    return token;
  }

  @Override
  public Object getCredentials() {
    return token;
  }

  public String getToken() {
    return token;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof PerryShiroToken)) {
      return false;
    }

    PerryShiroToken that = (PerryShiroToken) o;
    return token.equals(that.token);
  }

  @Override
  public int hashCode() {
    return token.hashCode();
  }

}
