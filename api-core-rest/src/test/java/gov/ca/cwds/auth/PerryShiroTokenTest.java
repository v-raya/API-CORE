package gov.ca.cwds.auth;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;

import org.junit.Test;

public class PerryShiroTokenTest {

  @Test
  public void type() throws Exception {
    assertThat(PerryShiroToken.class, notNullValue());
  }

  @Test
  public void instantiation() throws Exception {
    String token = "abc1234";
    PerryShiroToken target = new PerryShiroToken(token);
    assertThat(target, notNullValue());
  }

  @Test
  public void getPrincipal_Args__() throws Exception {
    String token = "abc1234";
    PerryShiroToken target = new PerryShiroToken(token);

    Object actual = target.getPrincipal();
    Object expected = token;
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void getCredentials_Args__() throws Exception {
    String token = "abc1234";
    PerryShiroToken target = new PerryShiroToken(token);

    Object actual = target.getCredentials();
    Object expected = token;
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void getToken_Args__() throws Exception {
    String token = "abc1234";
    PerryShiroToken target = new PerryShiroToken(token);

    String actual = target.getToken();
    String expected = token;
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void equals_Args__Object() throws Exception {
    String token = "abc1234";
    PerryShiroToken target = new PerryShiroToken(token);

    Object o = null;
    boolean actual = target.equals(o);
    boolean expected = false;
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void hashCode_Args__() throws Exception {
    String token = "abc1234";
    PerryShiroToken target = new PerryShiroToken(token);

    int actual = target.hashCode();
    int expected = -1207861340;
    assertThat(actual, is(equalTo(expected)));
  }

}
