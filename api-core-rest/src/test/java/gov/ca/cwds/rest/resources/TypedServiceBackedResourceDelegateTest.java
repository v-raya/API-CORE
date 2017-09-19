package gov.ca.cwds.rest.resources;

import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;

import org.hamcrest.junit.ExpectedException;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;

import gov.ca.cwds.rest.api.Request;
import gov.ca.cwds.rest.api.Response;
import gov.ca.cwds.rest.services.TypedCrudsService;

public class TypedServiceBackedResourceDelegateTest {

  public static class TestOnlyResponse implements Response {
  }

  public static class TestOnlyRequest implements Request {
  }

  private static class TestOnlyTypedServiceBackedResourceDelegate
      implements TypedResourceDelegate<String, TestOnlyRequest> {
    public TestOnlyTypedServiceBackedResourceDelegate(TestOnlyTypedCrudsService service) {}

    @Override
    public javax.ws.rs.core.Response get(String id) {
      return null;
    }

    @Override
    public javax.ws.rs.core.Response delete(String id) {
      return null;
    }

    @Override
    public javax.ws.rs.core.Response create(TestOnlyRequest request) {
      return null;
    }

    @Override
    public javax.ws.rs.core.Response update(String id, TestOnlyRequest request) {
      return null;
    }

  }

  private static class TestOnlyTypedCrudsService
      implements TypedCrudsService<String, TestOnlyRequest, TestOnlyResponse> {
    @Override
    public TestOnlyResponse find(String primaryKey) {
      return null;
    }

    @Override
    public TestOnlyResponse delete(String primaryKey) {
      return null;
    }

    @Override
    public TestOnlyResponse create(TestOnlyRequest request) {
      return null;
    }

    @Override
    public TestOnlyResponse update(String primaryKey, TestOnlyRequest request) {
      return null;
    }

  }

  @Rule
  public ExpectedException thrown = ExpectedException.none();

  @Mock
  private TestOnlyTypedCrudsService service;

  @Mock
  private TestOnlyRequest persDoc;

  @Mock
  private TestOnlyResponse domainDoc;

  @Mock
  private TypedResourceDelegate<String, TestOnlyRequest> resourceDelegate;

  @InjectMocks
  @Spy
  private TestOnlyTypedServiceBackedResourceDelegate target; // "Class Under Test"

  @Before
  public void initMocks() {
    MockitoAnnotations.initMocks(this);
    target = new TestOnlyTypedServiceBackedResourceDelegate(service);
  }

  @Test
  public void type() throws Exception {
    assertThat(TypedServiceBackedResourceDelegate.class, notNullValue());
  }

  @Test
  public void instantiation() throws Exception {
    TypedServiceBackedResourceDelegate target = new TypedServiceBackedResourceDelegate(service);
    assertThat(target, notNullValue());
  }

  @Test
  public void get_Args__Object() throws Exception {
    TypedServiceBackedResourceDelegate target = new TypedServiceBackedResourceDelegate(service);
    String id = null;
    javax.ws.rs.core.Response actual = target.get(id);
    assertThat(actual, notNullValue());
  }

  @Test
  public void delete_Args__Object() throws Exception {
    TypedServiceBackedResourceDelegate target = new TypedServiceBackedResourceDelegate(service);
    String id = null;
    javax.ws.rs.core.Response actual = target.delete(id);
    assertThat(actual, notNullValue());
  }

  @Test
  public void create_Args__Object() throws Exception {
    TypedServiceBackedResourceDelegate target = new TypedServiceBackedResourceDelegate(service);
    TestOnlyRequest request = new TestOnlyRequest();
    javax.ws.rs.core.Response actual = target.create(request);
    assertThat(actual, notNullValue());
  }

  // @Test
  // public void create_Args__Object2() throws Exception {
  // TypedServiceBackedResourceDelegate target = new TypedServiceBackedResourceDelegate(service);
  // TestOnlyRequest request = new TestOnlyRequest();
  // when(service.create(any())).thenThrow(new ServiceException("hello world"));
  // javax.ws.rs.core.Response actual = target.create(request);
  // assertThat(actual, notNullValue());
  // }

  // @Test
  // public void create_Args__Object3() throws Exception {
  // TypedServiceBackedResourceDelegate target = new TypedServiceBackedResourceDelegate(service);
  // TestOnlyRequest request = new TestOnlyRequest();
  // when(service.create(any()))
  // .thenThrow(new ServiceException("hello world", new EntityExistsException("something")));
  // javax.ws.rs.core.Response actual = target.create(request);
  // assertThat(actual, notNullValue());
  // }

  @Test
  public void update_Args__Object__Object() throws Exception {
    TypedServiceBackedResourceDelegate target = new TypedServiceBackedResourceDelegate(service);
    String id = null;
    TestOnlyRequest request = new TestOnlyRequest();
    javax.ws.rs.core.Response actual = target.update(id, request);
    assertThat(actual, notNullValue());
  }

  // @Test
  // public void update_Args__Object2() throws Exception {
  // TypedServiceBackedResourceDelegate target = new TypedServiceBackedResourceDelegate(service);
  // TestOnlyRequest request = new TestOnlyRequest();
  // String id = "";
  // when(service.update(any(), any())).thenThrow(new ServiceException("hello world"));
  // javax.ws.rs.core.Response actual = target.update(id, request);
  // assertThat(actual, notNullValue());
  // }

  // @Test
  // public void update_Args__Object3() throws Exception {
  // TypedServiceBackedResourceDelegate target = new TypedServiceBackedResourceDelegate(service);
  // TestOnlyRequest request = new TestOnlyRequest();
  // String id = "";
  // when(service.update(any(), any()))
  // .thenThrow(new ServiceException("hello world", new EntityNotFoundException("something")));
  // javax.ws.rs.core.Response actual = target.update(id, request);
  // assertThat(actual, notNullValue());
  // }

  @Test
  public void getService_Args__() throws Exception {
    TypedServiceBackedResourceDelegate target = new TypedServiceBackedResourceDelegate(service);
    assertThat(target.getService(), notNullValue());
  }

}
