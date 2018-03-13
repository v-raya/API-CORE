package gov.ca.cwds.auth;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import org.junit.Test;

public class ApiAuthenticationExceptionTest {

  @Test
  public void testEmptyConstructor() throws Exception {
    assertThat(ApiAuthenticationException.class.newInstance(), is(notNullValue()));
  }
  
  @Test
  public void testMessageCauseConstructor() {
    Throwable thrown = new Throwable();
    String message = "authentication exception";
    
    ApiAuthenticationException apiAuthenticationException = new ApiAuthenticationException(message, thrown);
    assertThat(apiAuthenticationException, is(notNullValue()));
  }
  
  @Test
  public void testMessageConstructor() {
    String message = "authentication exception";
    ApiAuthenticationException apiAuthenticationException = new ApiAuthenticationException(message);
    assertThat(apiAuthenticationException, is(notNullValue()));    
  }
  
  @Test
  public void testCauseConstructor() {
    Throwable thrown = new Throwable();
    ApiAuthenticationException apiAuthenticationException = new ApiAuthenticationException(thrown);
    assertThat(apiAuthenticationException, is(notNullValue()));    
    
  }
}
