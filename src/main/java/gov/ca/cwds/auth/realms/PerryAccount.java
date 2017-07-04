package gov.ca.cwds.auth.realms;

import java.util.Set;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by dmitry.rudenko on 6/2/2017.
 */
public class PerryAccount {

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
