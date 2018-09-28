package gov.ca.cwds.auth.realms;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;
import static com.google.common.base.Preconditions.checkState;

import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.SimplePrincipalCollection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.collect.Lists;

import gov.ca.cwds.auth.ApiAuthenticationException;
import gov.ca.cwds.auth.InvalidTokenException;
import gov.ca.cwds.auth.PerryShiroToken;
import gov.ca.cwds.auth.clients.PerryClient;

public class PerryRealm extends AuthorizingRealm {

  private static final Logger LOGGER = LoggerFactory.getLogger(PerryRealm.class);

  private PerryClient client = null;

  public PerryRealm() {
    setAuthenticationTokenClass(PerryShiroToken.class);
  }

  @Override
  protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
    return null;
  }

  @Override
  protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) {
    checkNotNull(token, "Authentication Token cannot be null.");
    checkArgument(token instanceof PerryShiroToken, "Token must be of instance `PerryShiroToken`.");
    checkState(client != null, "PerryClient must be set.");

    final PerryShiroToken perryShiroToken = (PerryShiroToken) token;
    try {
      LOGGER.debug("Reaching out to Perry for authentication...");
      return mapIdentity(client.validateToken(perryShiroToken), token);
    } catch (InvalidTokenException e) {
      LOGGER.warn("Invalid Token {}", perryShiroToken.getPrincipal(), e);
      return null;
    } catch (Exception e) {
      LOGGER.error("Error authenticating user.", e);
      throw new ApiAuthenticationException("Error authenticating user.", e);
    }
  }

  protected AuthenticationInfo mapIdentity(String identity, AuthenticationToken token) {
    return new SimpleAuthenticationInfo(
        new SimplePrincipalCollection(Lists.newArrayList(identity), getName()), token);
  }

  /**
   * @param client the client to set
   */
  public void setClient(PerryClient client) {
    this.client = client;
  }

}
