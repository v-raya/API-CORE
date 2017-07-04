package gov.ca.cwds.auth.realms;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;

import java.util.Set;

import org.junit.Test;

public class PerryAccountTest {

  @Test
  public void type() throws Exception {
    assertThat(PerryAccount.class, notNullValue());
  }

  @Test
  public void instantiation() throws Exception {
    PerryAccount target = new PerryAccount();
    assertThat(target, notNullValue());
  }

  @Test
  public void getUser_Args__() throws Exception {
    PerryAccount target = new PerryAccount();
    String actual = target.getUser();
    String expected = null;
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void setUser_Args__String() throws Exception {
    PerryAccount target = new PerryAccount();
    String user = null;
    target.setUser(user);
  }

  @Test
  public void getRoles_Args__() throws Exception {
    PerryAccount target = new PerryAccount();
    Set<String> actual = target.getRoles();
    Set<String> expected = null;
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void setRoles_Args__Set() throws Exception {
    PerryAccount target = new PerryAccount();
    Set<String> roles = mock(Set.class);
    target.setRoles(roles);
  }

}
