package gov.ca.cwds.data;

import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import org.junit.Test;

import gov.ca.cwds.data.persistence.cms.SystemCode;

/**
 * @author CWDS API Team
 *
 */
public class ApiHibernateInterceptorTest {

  /**
   * @throws Exception - exception
   */
  @SuppressWarnings("unused")
  @Test
  public void testForSynchronizedPreFlush() throws Exception {
    ExecutorService service = Executors.newFixedThreadPool(4);
    ApiHibernateInterceptor interceptor = new ApiHibernateInterceptor();
    HashMap<Integer, Object> map = new HashMap<>();
    SystemCode code1 = new SystemCode();
    SystemCode code2 = new SystemCode();
    SystemCode code3 = new SystemCode();
    SystemCode code4 = new SystemCode();
    map.put(1, code1);
    map.put(2, code2);
    map.put(3, code3);
    map.put(4, code4);
    Iterator<?> iter = map.entrySet().iterator();

    Future<Date> future1 = execCallable(service, iter, interceptor);
    Future<Date> future2 = execCallable(service, iter, interceptor);
    Future<Date> future3 = execCallable(service, iter, interceptor);
    Future<Date> future4 = execCallable(service, iter, interceptor);
  }

  protected Future<Date> execCallable(ExecutorService service, Iterator<?> iter,
      ApiHibernateInterceptor interceptor) {
    return service.submit(() -> {
      final long tid = Thread.currentThread().getId();
      System.out.println("Thread started.. Id is " + tid);
      interceptor.preFlush(iter);
      return null;
    });
  }

}
