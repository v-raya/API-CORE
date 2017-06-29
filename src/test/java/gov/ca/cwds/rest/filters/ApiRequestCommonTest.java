package gov.ca.cwds.rest.filters;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;

import java.util.Date;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

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

  @Test
  public void getRequestCommon_Thread() throws Exception {
    ExecutorService service = Executors.newSingleThreadExecutor();

    Future<Date> future1 = execCallable(service, 1500);
    Date result = null;
    try {
      result = future1.get();
    } catch (InterruptedException e) {
      e.printStackTrace();
    } catch (ExecutionException e) {
      e.printStackTrace();
    }
    System.out.println("date = " + result);
  }

  protected Future<Date> execCallable(ExecutorService service, int sleepMillis) {
    return service.submit(() -> {
      Thread.sleep(sleepMillis); // NOSONAR
      User user_ = mock(User.class);
      ApiRequestCommon.pegRequest(user_);
      ApiRequestCommon common = ApiRequestCommon.getRequestCommon();
      return common.getRequestBegin();
    });
  }

}
