package gov.ca.cwds.auth.realms;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.Lists;
import gov.ca.cwds.rest.api.ApiException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.SimplePrincipalCollection;

import java.io.IOException;
import java.util.List;
import java.util.Set;

/**
 * Simple implementation of perry authorization realm. This realm sends
 * access token to perry and expects simple user token. Token format example:
 * {"user" : "username"
 *  "roles" ["role1", "role2"]
 * }
 *
 * During authentication process this realm puts user name as primary credential
 * and user token as secondary. So authorization process will expect 2 principals.
 * In case if we will not have user token as secondary principal - authorization will not
 * take place. In other words subject will not contain roles.
 *
 */
public class PerryAccountRealm extends PerryRealm {
    /**
     * primary principal equals to username
     * secondary principal equals to user token
     */
    private static final int PRINCIPALS_COUNT = 2;
    private ObjectMapper objectMapper;

    public PerryAccountRealm() {
        objectMapper = new ObjectMapper();
    }

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        List principalsList = principals.asList();
        if(principalsList.size() == PRINCIPALS_COUNT) {
            PerryAccount perryAccount = (PerryAccount) principalsList.get(1);
            return new SimpleAuthorizationInfo(perryAccount.getRoles());
        }
        return null;
    }

    protected AuthenticationInfo mapIdentity(String identity, AuthenticationToken token) {
        PerryAccount perryAccount = toPerryAccount(identity);
        List<Object> principals = Lists.newArrayList(perryAccount.getUser());
        principals.add(perryAccount);
        PrincipalCollection principalCollection =
                new SimplePrincipalCollection(principals, getName());
        return new SimpleAuthenticationInfo(principalCollection, token);
    }

    private PerryAccount toPerryAccount(String identity)  {
        try {
            return objectMapper.readValue(identity, PerryAccount.class);
        } catch (IOException e) {
            throw new ApiException(e);
        }
    }

    /**
     * Created by dmitry.rudenko on 5/9/2017.
     */
    public static class PerryAccount {
        @JsonProperty
        private String user;
        @JsonProperty
        private Set<String> roles;

        public String getUser() {
            return user;
        }

        public void setUser(String user) {
            this.user = user;
        }

        public Set<String> getRoles() {
            return roles;
        }

        public void setRoles(Set<String> roles) {
            this.roles = roles;
        }
    }
}
