package gov.ca.cwds.data;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import org.junit.Before;
import org.junit.Test;

import gov.ca.cwds.data.persistence.PersistentObject;
import gov.ca.cwds.data.persistence.cms.SystemCode;

/**
 * @author CWDS API Team
 */
public class ApiHibernateInterceptorTest {

  ApiHibernateInterceptor target;
  // Consumer<PersistentObject> check = c -> apply((TestPersistentObject) c);

  @Before
  public void setup() throws Exception {
    target = new ApiHibernateInterceptor();
  }

  public void consume(PersistentObject obj) {
    // nuttin
  }


  // afterTransactionBegin(Transaction)
  // afterTransactionCompletion(Transaction)
  // beforeTransactionCompletion(Transaction)
  // instantiate(String, EntityMode, Serializable)
  // iterToList(Iterator)
  // onDelete(Object, Serializable, Object[], String[], Type[])
  // onFlushDirty(Object, Serializable, Object[], Object[], String[], Type[])
  // onLoad(Object, Serializable, Object[], String[], Type[])
  // onSave(Object, Serializable, Object[], String[], Type[])


  @Test
  public void testPostFlush() throws Exception {
    List<PersistentObject> list = new ArrayList<>();
    list.add(new TestPersistentObject());
    Iterator<PersistentObject> iter = list.iterator();
    target.postFlush(iter);
  }

  @Test
  public void testPreFlush() throws Exception {
    List<PersistentObject> list = new ArrayList<>();
    list.add(new TestPersistentObject());
    Iterator<PersistentObject> iter = list.iterator();
    target.preFlush(iter);
  }

  @Test
  public void testAddHandler() throws Exception {
    ApiHibernateInterceptor.addHandler(TestPersistentObject.class, this::consume);
  }

  @SuppressWarnings("unused")
  @Test
  public void testForSynchronizedPreFlush() throws Exception {
    ExecutorService service = Executors.newFixedThreadPool(4);
    ApiHibernateInterceptor interceptor = new ApiHibernateInterceptor();
    final HashMap<Integer, Object> map = new HashMap<>();
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
