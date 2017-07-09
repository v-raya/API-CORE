package gov.ca.cwds.auth.realms;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.Key;
import java.security.KeyStore;
import java.util.ArrayList;
import java.util.List;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.SimplePrincipalCollection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.databind.ObjectMapper;

import gov.ca.cwds.auth.ApiAuthenticationException;
import gov.ca.cwds.auth.PerryShiroToken;
import gov.ca.cwds.rest.api.ApiException;
import io.jsonwebtoken.Jwts;

/**
 * Implementation of JWT processing realm. This realm validates JWT token and extract identity claim
 * from JWT payload. Identity claim example: {"user" : "username", "roles" : ["role1", "role2"] }
 * <p>
 * During authentication process this realm puts user name as primary credential and mapped identity
 * claim as secondary. So authorization process will expect 2 principals.
 */
public class JwtRealm extends AuthorizingRealm {

  private static final Logger LOGGER = LoggerFactory.getLogger(JwtRealm.class);

  /**
   * primary principal equals to username secondary principal equals to user token
   */
  private static final int PRINCIPALS_COUNT = 2;
  private static final String IDENTITY_CLAIM = "identity";
  private ObjectMapper objectMapper;
  private String keyStorePath;
  private String keyStoreAlias;
  private String keyStorePassword;
  private String keyStoreKeyPassword;
  private String tokenIssuer;
  private Key validationKey;

  @Override
  protected void onInit() {
    objectMapper = new ObjectMapper();
    validationKey = getPublicKey();
  }

  /**
   * @return the keyStorePath
   */
  public String getKeyStorePath() {
    return keyStorePath;
  }

  /**
   * @param keyStorePath - keyStorePath
   */
  public void setKeyStorePath(String keyStorePath) {
    this.keyStorePath = keyStorePath;
  }

  /**
   * @return the keyStoreAlias
   */
  public String getKeyStoreAlias() {
    return keyStoreAlias;
  }

  /**
   * @param keyStoreAlias - keyStoreAlias
   */
  public void setKeyStoreAlias(String keyStoreAlias) {
    this.keyStoreAlias = keyStoreAlias;
  }

  /**
   * @return the keyStorePassword
   */
  public String getKeyStorePassword() {
    return keyStorePassword;
  }

  /**
   * @param keyStorePassword - keyStorePassword
   */
  public void setKeyStorePassword(String keyStorePassword) {
    this.keyStorePassword = keyStorePassword;
  }

  /**
   * @return the keyStoreKeyPassword
   */
  public String getKeyStoreKeyPassword() {
    return keyStoreKeyPassword;
  }

  /**
   * @param keyStoreKeyPassword - keyStoreKeyPassword
   */
  public void setKeyStoreKeyPassword(String keyStoreKeyPassword) {
    this.keyStoreKeyPassword = keyStoreKeyPassword;
  }

  /**
   * @return the tokenIssuer
   */
  public String getTokenIssuer() {
    return tokenIssuer;
  }

  /**
   * @param tokenIssuer - tokenIssuer
   */
  public void setTokenIssuer(String tokenIssuer) {
    this.tokenIssuer = tokenIssuer;
  }

  /**
   * Default Constructor
   */
  public JwtRealm() {
    setAuthenticationTokenClass(PerryShiroToken.class);
  }

  @Override
  protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
    List principalsList = principals.asList();
    if (principalsList.size() == PRINCIPALS_COUNT) {
      PerryAccount perryAccount = (PerryAccount) principalsList.get(1);
      return new SimpleAuthorizationInfo(perryAccount.getRoles());
    }
    throw new ApiAuthenticationException("User authorization failed!");
  }

  @Override
  protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token)
      throws AuthenticationException {
    String jwt = ((PerryShiroToken) token).getToken();
    String json = validate(jwt);
    PerryAccount perryAccount = map(json);
    return getAuthenticationInfo(perryAccount);
  }

  private AuthenticationInfo getAuthenticationInfo(PerryAccount perryAccount) {
    List<Object> principals = new ArrayList<>();
    principals.add(perryAccount.getUser());
    principals.add(perryAccount);
    PrincipalCollection principalCollection = new SimplePrincipalCollection(principals, getName());
    return new SimpleAuthenticationInfo(principalCollection, "N/A");
  }

  private String validate(String token) throws ApiAuthenticationException {
    return (String) Jwts.parser().setSigningKey(validationKey).requireIssuer(tokenIssuer)
        .parseClaimsJws(token).getBody().get(IDENTITY_CLAIM);
  }

  private KeyStore getKeyStore() throws Exception {
    KeyStore ks = KeyStore.getInstance("JKS");
    try (InputStream readStream = new FileInputStream(keyStorePath)) {
      char keyPassword[] = keyStorePassword.toCharArray();
      ks.load(readStream, keyPassword);
      return ks;
    }
  }

  private Key getPublicKey() throws ApiException {
    try {
      return getKeyStore().getCertificate(keyStoreAlias).getPublicKey();
    } catch (Exception e) {
      throw new ApiAuthenticationException("Can't load key", e);
    }
  }

  /**
   * Maps JWT payload to user info. For more complex user info override this method. User info will
   * be accessible as secondary principal:
   * <p>
   * Subject subject = SecurityUtils.getSubject(); List principals =
   * subject.getPrincipals().asList(); PerryAccount account = (PerryAccount) principals.get(1);
   *
   * @param json jwt payload
   * @return mapped jwt payload
   */
  protected PerryAccount map(String json) {
    try {
      return objectMapper.readValue(json, PerryAccount.class);
    } catch (IOException e) {
      LOGGER.info(e.getMessage());
      // Mapping doesn't apply
      return new PerryAccount() {
        {
          setUser(json);
        }
      };
    }
  }
}
