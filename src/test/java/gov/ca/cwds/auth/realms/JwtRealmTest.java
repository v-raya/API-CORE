package gov.ca.cwds.auth.realms;

import gov.ca.cwds.auth.PerryShiroToken;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.impl.DefaultClaims;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.SimplePrincipalCollection;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;

import java.io.FileInputStream;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.Key;
import java.security.KeyStore;
import java.util.*;

@Ignore
public class JwtRealmTest {

    private static final String IDENTITY_CLAIM = "identity";
    private static String keyStorePath;
    private static String keyStoreAlias;
    private static String keyStorePassword;
    private static String keyStoreKeyPassword;
    private static String tokenIssuer;
    private static JwtRealm jwtRealm;

    @BeforeClass
    public static void init() throws Exception {
        keyStorePath = Paths.get(JwtRealmTest.class.getResource("/auth/test.jks").toURI()).toAbsolutePath().toString();
        keyStoreAlias = "test";
        keyStorePassword = "test";
        keyStoreKeyPassword = "test";
        tokenIssuer = "test";
        jwtRealm = new JwtRealm();
        jwtRealm.setKeyStoreAlias(keyStoreAlias);
        jwtRealm.setKeyStoreKeyPassword(keyStoreKeyPassword);
        jwtRealm.setKeyStorePassword(keyStorePassword);
        jwtRealm.setTokenIssuer(tokenIssuer);
        jwtRealm.setKeyStorePath(keyStorePath);
        jwtRealm.onInit();
    }

    @Test
    public void testDoGetAuthorizationInfo() {
        PerryAccount perryAccount = perryAccount();
        List<Object> principals = Arrays.asList(perryAccount.getUser(), perryAccount);
        PrincipalCollection principalCollection =
                new SimplePrincipalCollection(principals, "testRealm");
        AuthorizationInfo authorizationInfo = jwtRealm.doGetAuthorizationInfo(principalCollection);
        Assert.assertEquals(perryAccount.getRoles(), authorizationInfo.getRoles());

    }

    @Test
    public void testDoGetAuthenticationInfo() throws Exception {
        String jwtToken = generateToken("testuser");
        AuthenticationToken token = new PerryShiroToken(jwtToken);
        AuthenticationInfo authenticationInfo = jwtRealm.doGetAuthenticationInfo(token);
        Assert.assertEquals(2, authenticationInfo.getPrincipals().asList().size());
        Assert.assertEquals(authenticationInfo.getPrincipals().asList().get(0), "testuser");
        PerryAccount perryAccount = (PerryAccount) authenticationInfo.getPrincipals().asList().get(1);
        Assert.assertEquals("testuser", perryAccount.getUser());
        Assert.assertEquals(new HashSet<>(Arrays.asList("role1", "role2")), perryAccount.getRoles());
    }

    private String generateToken(String subject) throws Exception {
        String identity = new String(Files.readAllBytes(Paths.get(getClass().getResource("/auth/token.json").toURI())));
        SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.RS256;
        JwtBuilder builder = Jwts.builder().setId("id");
        long nowMillis = System.currentTimeMillis();
        Date now = new Date(nowMillis);
        Claims claims = new DefaultClaims();
        claims.setId("id");
        claims.setIssuedAt(now);
        claims.setSubject(subject);
        claims.setExpiration(new Date(nowMillis + 30 * 60 * 1000)); //30 mins
        claims.setIssuer("test");
        claims.setSubject(subject);
        claims.put(IDENTITY_CLAIM,  identity);
        builder.setClaims(claims)
                .signWith(signatureAlgorithm, getSigningKey());
        return builder.compact();
    }

    private PerryAccount perryAccount() {
        PerryAccount perryAccount = new PerryAccount();
        perryAccount.setUser("testuser");
        Set<String> roles = new HashSet<>(Arrays.asList("role1", "role2"));
        perryAccount.setRoles(roles);
        return perryAccount;
    }

    private Key getSigningKey() throws Exception {
        return getKeyStore().getKey(keyStoreAlias,
                keyStoreKeyPassword.toCharArray());
    }

    private KeyStore getKeyStore() throws Exception {
        KeyStore ks = KeyStore.getInstance("JKS");
        try (InputStream readStream = new FileInputStream(keyStorePath)) {
            char keyPassword[] = keyStorePassword.toCharArray();
            ks.load(readStream, keyPassword);
            return ks;
        }
    }
}
