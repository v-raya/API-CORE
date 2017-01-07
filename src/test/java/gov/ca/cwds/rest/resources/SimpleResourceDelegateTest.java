package gov.ca.cwds.rest.resources;

import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;

import javax.ws.rs.core.Response;

import org.hamcrest.junit.ExpectedException;
import org.hibernate.SessionFactory;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;

import gov.ca.cwds.rest.api.ApiException;
import gov.ca.cwds.rest.api.Request;
import gov.ca.cwds.rest.services.ServiceException;


public class SimpleResourceDelegateTest {

  private static class TestApiRequest implements Request {
  }

  private static class TestApiResponse implements gov.ca.cwds.rest.api.Response {
  }

  private static class TestSimpleResourceDelegateImpl extends
      SimpleResourceDelegate<String, TestApiRequest, TestApiResponse, ISimpleResourceService<String, TestApiRequest, TestApiResponse>>
      implements
      ISimpleResourceDelegate<String, TestApiRequest, TestApiResponse, ISimpleResourceService<String, TestApiRequest, TestApiResponse>> {

    public TestSimpleResourceDelegateImpl(
        ISimpleResourceService<String, TestApiRequest, TestApiResponse> service) {
      super(service);
    }

    @Override
    public TestApiResponse execFind(String key) throws ApiException {
      return super.execFind(key);
    }

    @Override
    public TestApiResponse execHandle(TestApiRequest req) throws ApiException {
      return super.execHandle(req);
    }

  }

  @Mock
  private ISimpleResourceService<String, TestApiRequest, TestApiResponse> svc;

  @Rule
  public ExpectedException thrown = ExpectedException.none();

  @Mock
  private SessionFactory sf;

  @InjectMocks
  @Spy
  private TestSimpleResourceDelegateImpl target; // "Class Under Test"

  @Before
  public void initMocks() {
    MockitoAnnotations.initMocks(this);
  }

  @Test
  public void type() throws Exception {
    assertThat(SimpleResourceDelegate.class, notNullValue());
  }

  @Test
  public void instantiation() throws Exception {
    assertThat(target, notNullValue());
  }

  @Test
  public void find_A$Object() throws Exception {
    String id = "1234";
    when(svc.find(id)).thenReturn(new TestApiResponse());
    Response actual = target.find(id);
    assertTrue(actual.getEntity() instanceof TestApiResponse);
  }

  @Test
  public void find_A$Object_T$ApiException() throws Exception {
    final String key = "asdfasdf";
    when(svc.find(any())).thenThrow(new ServiceException(new IllegalArgumentException()));
    final Response response = target.find(key);
    assertTrue(response.getStatus() != Response.Status.OK.ordinal());
  }

  @Test
  public void getService_A$() throws Exception {
    assertTrue((target.getService() instanceof ISimpleResourceService<?, ?, ?>));
  }

  @Test
  public void handle_A$Object() throws Exception {
    TestApiRequest req = new TestApiRequest();
    when(svc.handle(any())).thenReturn(new TestApiResponse());
    Response actual = target.handle(req);
    assertTrue(actual.getEntity() instanceof TestApiResponse);
  }

  @Test
  public void handle_A$Object_T$ApiException() throws Exception {
    TestApiRequest req = new TestApiRequest();
    when(svc.handle(req))
        .thenThrow(new ApiException(new ServiceException(new IllegalArgumentException())));
    target.handle(req);
    final Response response = target.handle(req);
    assertTrue(response.getStatus() != Response.Status.OK.ordinal());
  }

}
