package gov.ca.cwds.auth;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;

import org.apache.shiro.subject.Subject;
import org.junit.Test;

public class UserTest {

  @Test
  public void type() throws Exception {
    assertThat(User.class, notNullValue());
  }

  @Test
  public void instantiation() throws Exception {
    String racf = "0x5";
    Subject subject = mock(Subject.class);
    User target = new User(racf, subject);
    assertThat(target, notNullValue());
  }

  @Test
  public void getRacf_Args__() throws Exception {
    String racf = "0x5";
    Subject subject = mock(Subject.class);

    User target = new User(racf, subject);
    String actual = target.getRacf();
    String expected = racf;
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void getSubject_Args__() throws Exception {
    String racf = "0x5";
    Subject subject = mock(Subject.class);

    User target = new User(racf, subject);
    Subject actual = target.getSubject();
    Subject expected = subject;
    assertThat(actual, is(equalTo(expected)));
  }

}
