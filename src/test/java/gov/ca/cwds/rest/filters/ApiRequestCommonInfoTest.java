package gov.ca.cwds.rest.filters;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.LinkedBlockingDeque;

import org.junit.Test;

import gov.ca.cwds.auth.User;

public class ApiRequestCommonInfoTest {

  protected LinkedBlockingDeque<Date> resultQueue = new LinkedBlockingDeque<>(10);

  @Test
  public void type() throws Exception {
    assertThat(ApiRequestCommonInfo.class, notNullValue());
  }

  @Test
  public void getRequestCommon_Args__() throws Exception {
    User user_ = mock(User.class);
    ApiRequestCommonInfo.startRequest("0x5");
    ApiRequestCommonInfo actual = ApiRequestCommonInfo.getRequestCommon();
    assertThat(actual, is(notNullValue()));
  }

  @Test
  public void getRequestCommon_Thread() throws Exception {
    ExecutorService service = Executors.newFixedThreadPool(4);
    Future<Date> future1 = execCallable(service, 4500);
    Future<Date> future2 = execCallable(service, 3500);
    Future<Date> future3 = execCallable(service, 2900);
    Future<Date> future4 = execCallable(service, 2500);

    waitOnFuture(future1);
    waitOnFuture(future2);
    waitOnFuture(future3);
    waitOnFuture(future4);

    Set<Date> set = new HashSet<>();
    for (Date dt : resultQueue) {
      System.out.println("dt=" + new SimpleDateFormat("yyyy-MM-dd-HH.mm.ss.SSS").format(dt));
      set.add(dt);
    }

    assertThat(set.size(), is(4));
  }

  protected void waitOnFuture(final Future<Date> future) {
    try {
      Date result = future.get();
    } catch (InterruptedException e) {
      e.printStackTrace();
    } catch (ExecutionException e) {
      e.printStackTrace();
    }
  }

  protected Future<Date> execCallable(ExecutorService service, int sleepMillis) {
    return service.submit(() -> {
      final long tid = Thread.currentThread().getId();
      Thread.sleep(sleepMillis); // NOSONAR
      ApiRequestCommonInfo.startRequest(tid + "_" + sleepMillis);
      ApiRequestCommonInfo common = ApiRequestCommonInfo.getRequestCommon();
      final Date date = common.getRequestBegin();
      System.out.println("thread id: " + tid + ", racf: " + common.getRacf() + ", date = "
          + new SimpleDateFormat("yyyy-MM-dd-HH.mm.ss.SSS").format(date));
      resultQueue.add(date);
      return date;
    });
  }

}
