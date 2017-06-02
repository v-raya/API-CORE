package gov.ca.cwds.auth.realms;

import gov.ca.cwds.auth.PerryShiroToken;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.SimplePrincipalCollection;
import org.junit.Assert;
import org.junit.Test;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class PerryAccountRealmTest {
    /*@Test
    public void testDoGetAuthorizationInfo() {
        PerryAccountRealm perryAccountRealm = new PerryAccountRealm();
        PerryAccountRealm.PerryAccount perryAccount = perryAccount();
        List<Object> principals = Arrays.asList(perryAccount.getUser(), perryAccount);
        PrincipalCollection principalCollection =
                new SimplePrincipalCollection(principals, "testRealm");
        AuthorizationInfo authorizationInfo = perryAccountRealm.doGetAuthorizationInfo(principalCollection);
        Assert.assertEquals(perryAccount.getRoles(), authorizationInfo.getRoles());

    }

    @Test
    public void testMapIdentity() throws Exception {
        PerryAccountRealm perryAccountRealm = new PerryAccountRealm();
        AuthenticationToken token = new PerryShiroToken("test token");
        String identity = new String(Files.readAllBytes(Paths.get(getClass().getResource("/auth/token.json").toURI())));
        AuthenticationInfo authenticationInfo = perryAccountRealm.mapIdentity(identity, token);
        Assert.assertEquals(2, authenticationInfo.getPrincipals().asList().size());
        Assert.assertEquals(authenticationInfo.getPrincipals().asList().get(0), "testuser");
        PerryAccountRealm.PerryAccount perryAccount = (PerryAccountRealm.PerryAccount) authenticationInfo.getPrincipals().asList().get(1);
        Assert.assertEquals("testuser", perryAccount.getUser());
        Assert.assertEquals(new HashSet<>(Arrays.asList("role1", "role2")), perryAccount.getRoles());
    }

    private PerryAccountRealm.PerryAccount perryAccount() {
        PerryAccountRealm.PerryAccount perryAccount = new PerryAccountRealm.PerryAccount();
        perryAccount.setUser("testuser");
        Set<String> roles = new HashSet<>(Arrays.asList("role1", "role2"));
        perryAccount.setRoles(roles);
        return perryAccount;
    }*/
}
