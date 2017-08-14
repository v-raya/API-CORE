package gov.ca.cwds.rest.resources;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;

import gov.ca.cwds.rest.api.Request;
import gov.ca.cwds.rest.services.ServiceException;
import java.util.ArrayList;
import java.util.UUID;
import javax.validation.constraints.Pattern;
import org.hamcrest.junit.ExpectedException;
import org.hibernate.SessionFactory;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;

public class SimpleResourceServiceTest {

  private static class TestApiRequest implements Request {

    @Pattern(regexp = "\\w+")
    private String key;

    TestApiRequest() {
      this.key = UUID.randomUUID().toString();
    }

    TestApiRequest(String key) {
      this.key = key;
    }

    public String getKey() {
      return key;
    }

    @Override
    public int hashCode() {
      final int prime = 31;
      int result = 1;
      result = prime * result + ((key == null) ? 0 : key.hashCode());
      return result;
    }

    @Override
    public boolean equals(Object obj) {
      if (this == obj)
        return true;
      if (obj == null)
        return false;
      if (getClass() != obj.getClass())
        return false;
      TestApiRequest other = (TestApiRequest) obj;
      if (key == null) {
        if (other.key != null)
          return false;
      } else if (!key.equals(other.key))
        return false;
      return true;
    }
  }

  private static class TestApiResponse implements gov.ca.cwds.rest.api.Response {
    private String key;

    public TestApiResponse(TestApiRequest req) {
      this.key = req.getKey();
    }

    public String getKey() {
      return key;
    }

    @Override
    public int hashCode() {
      final int prime = 31;
      int result = 1;
      result = prime * result + ((key == null) ? 0 : key.hashCode());
      return result;
    }

    @Override
    public boolean equals(Object obj) {
      if (this == obj)
        return true;
      if (obj == null)
        return false;
      if (getClass() != obj.getClass())
        return false;
      TestApiResponse other = (TestApiResponse) obj;
      if (key == null) {
        if (other.key != null)
          return false;
      } else if (!key.equals(other.key))
        return false;
      return true;
    }

    @Override
    public boolean hasMessages() {
      return false;
    }

    @Override
    public ArrayList getMessages() {
      return null;
    }
  }

  private static interface SimpleResourceServiceTestDao {
    TestApiResponse find(String key);

    TestApiResponse handle(TestApiRequest req);
  }

  private static class TestSimpleResourceServiceImpl
      extends SimpleResourceService<String, TestApiRequest, TestApiResponse> {

    private SimpleResourceServiceTestDao dao;

    public TestSimpleResourceServiceImpl(SimpleResourceServiceTestDao dao) {
      this.dao = dao;
    }

    @Override
    protected TestApiResponse handleRequest(TestApiRequest req) {
      return this.dao.handle(req);
    }

    @Override
    protected TestApiResponse handleFind(String key) {
      return new TestApiResponse(new TestApiRequest(key));
    }

  }

  @Mock
  private ApiSimpleResourceService<String, TestApiRequest, TestApiResponse> svc;

  @Rule
  public ExpectedException thrown = ExpectedException.none();

  @Mock
  private SessionFactory sf;

  @Mock
  private SimpleResourceServiceTestDao dao;

  @InjectMocks
  @Spy
  private TestSimpleResourceServiceImpl target; // "Class Under Test"

  @Before
  public void initMocks() {
    MockitoAnnotations.initMocks(this);
  }

  @Test
  public void type() throws Exception {
    assertThat(SimpleResourceService.class, notNullValue());
  }

  @Test
  public void instantiation() throws Exception {
    assertThat(target, notNullValue());
  }

  @Test
  public void handle_A$Object() throws Exception {
    final String key = "1234";
    TestApiRequest req = new TestApiRequest(key);
    when(dao.handle(any())).thenReturn(new TestApiResponse(req));
    TestApiResponse actual = target.handle(req);
    TestApiResponse expected = new TestApiResponse(req);
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void handle_A$Object_T$ServiceException() throws Exception {
    final String key = null;
    try {
      target.find(key);
      fail("Expected exception was not thrown!");
    } catch (ServiceException e) {
      // all good
    }
  }

  @Test(expected = ServiceException.class)
  public void handle_A$Object_T$ServiceException__NullParm() throws Exception {
    TestApiRequest req = null;
    target.handle(req);
    fail("Expected exception was not thrown!");
  }

  @Test
  public void find_A$Object() throws Exception {
    final String key = "abc1234";
    when(svc.find(key)).thenReturn(new TestApiResponse(new TestApiRequest(key)));
    TestApiResponse actual = target.find(key);
    assertTrue(actual instanceof TestApiResponse);
  }

  @Test
  public void find_A$Object_T$ServiceException() throws Exception {
    String id = null;
    try {
      target.find(id);
      fail("Expected exception was not thrown!");
    } catch (ServiceException e) {
      // all good
    }
  }

  @Test(expected = ServiceException.class)
  public void find_A$Object_T$ServiceException_NullParm() throws Exception {
    String key = null;
    target.find(key);
    fail("Expected exception was not thrown!");
  }

}
