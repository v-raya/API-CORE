package gov.ca.cwds.logging;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;

import org.apache.logging.log4j.Logger;
import org.junit.Test;

import gov.ca.cwds.rest.api.ApiException;

public class ApiLogUtilsTest {

  @Test
  public void type() throws Exception {
    assertThat(ApiLogUtils.class, notNullValue());
  }

  @Test
  public void newInstance_Args__String__Throwable() throws Exception {
    final ApiLogUtils<ApiException> target = new ApiLogUtils<>(ApiException.class);
    String msg = "hola";
    Throwable t = new IllegalStateException("something bad");
    Object actual = target.newInstance(msg, t);
    Object expected = new ApiException(msg, t);
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void logEvery_Args__Logger__int__String__ObjectArray() throws Exception {
    // given
    Logger log = mock(Logger.class);
    int cntr = 0;
    String action = null;
    Object[] args = new Object[] {};
    // e.g. : given(mocked.called()).willReturn(1);
    // when
    ApiLogUtils.logEvery(log, cntr, action, args);
    // then
    // e.g. : verify(mocked).called();
  }

  @Test
  public void logEvery_Args__int__String__ObjectArray() throws Exception {
    ApiLogUtils target = null;
    // given
    int cntr = 0;
    String action = null;
    Object[] args = new Object[] {};
    // e.g. : given(mocked.called()).willReturn(1);
    // when
    target.logEvery(cntr, action, args);
    // then
    // e.g. : verify(mocked).called();
  }

  @Test
  public void throwFatalError_Args__Logger__Throwable__String__ObjectArray() throws Exception {
    ApiLogUtils target = null;
    // given
    Logger log = mock(Logger.class);
    Throwable t = null;
    String pattern = null;
    Object[] args = new Object[] {};
    // e.g. : given(mocked.called()).willReturn(1);
    // when
    target.throwFatalError(log, t, pattern, args);
    // then
    // e.g. : verify(mocked).called();
  }

  @Test
  public void throwFatalError_Args__Logger__Throwable__ObjectArray() throws Exception {
    ApiLogUtils target = null;
    // given
    Logger log = mock(Logger.class);
    Throwable t = null;
    Object[] args = new Object[] {};
    // e.g. : given(mocked.called()).willReturn(1);
    // when
    target.throwFatalError(log, t, args);
    // then
    // e.g. : verify(mocked).called();
  }

}
