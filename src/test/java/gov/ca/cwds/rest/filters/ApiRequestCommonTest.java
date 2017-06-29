package gov.ca.cwds.rest.filters;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;

import org.junit.Test;

import gov.ca.cwds.auth.User;

public class ApiRequestCommonTest {

  @Test
  public void type() throws Exception {
    assertThat(ApiRequestCommon.class, notNullValue());
  }

  @Test
  public void getRequestCommon_Args__() throws Exception {
    User user_ = mock(User.class);
    ApiRequestCommon.pegRequest(user_);
    ApiRequestCommon actual = ApiRequestCommon.getRequestCommon();
    assertThat(actual, is(notNullValue()));
  }

}
