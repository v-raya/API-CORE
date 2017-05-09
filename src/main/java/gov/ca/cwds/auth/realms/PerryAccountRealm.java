package gov.ca.cwds.auth.realms;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.Lists;
import gov.ca.cwds.rest.api.ApiException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAccount;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.SimplePrincipalCollection;

import java.io.IOException;
import java.util.List;
import java.util.Set;

/**
 * Created by dmitry.rudenko on 5/9/2017.
 */
public class PerryAccountRealm extends PerryRealm {
    private ObjectMapper objectMapper;

    public PerryAccountRealm() {
        objectMapper = new ObjectMapper();
    }

    protected AuthenticationInfo mapIdentity(String identity, AuthenticationToken token) {
        PerryAccount perryAccount = toPerryAccount(identity);
        List<Object> principals = Lists.newArrayList(perryAccount.getUser());
        PrincipalCollection principalCollection =
                new SimplePrincipalCollection(principals, getName());
        return new SimpleAccount(principalCollection, token, perryAccount.getRoles());
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
