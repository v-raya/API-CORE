package gov.ca.cwds.data.persistence.cms;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;

import java.io.Serializable;
import java.util.Date;

import org.junit.Test;

public class CmsPersistentObjectTest {

  private static final class TestCmsPersistentObject extends CmsPersistentObject {
    private String id;

    public TestCmsPersistentObject() {}

    protected TestCmsPersistentObject(String lastUpdatedId) {
      super(lastUpdatedId);
    }

    protected TestCmsPersistentObject(String lastUpdatedId, Date lastUpdatedTime) {
      super(lastUpdatedId, lastUpdatedTime);
    }

    @Override
    public Serializable getPrimaryKey() {
      return id;
    }

  }

  @Test
  public void type() throws Exception {
    assertThat(CmsPersistentObject.class, notNullValue());
  }

  @Test
  public void instantiation() throws Exception {
    CmsPersistentObject target = new TestCmsPersistentObject();
    assertThat(target, notNullValue());
  }

  @Test
  public void getLastUpdatedId_Args__() throws Exception {
    CmsPersistentObject target = new TestCmsPersistentObject();
    String actual = target.getLastUpdatedId();
    String expected = null;
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void getLastUpdatedTime_Args__() throws Exception {
    CmsPersistentObject target = new TestCmsPersistentObject();
    Date actual = target.getLastUpdatedTime();
    Date expected = null;
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void setLastUpdatedId_Args__String() throws Exception {
    CmsPersistentObject target = new TestCmsPersistentObject();
    String lastUpdatedId = null;
    target.setLastUpdatedId(lastUpdatedId);
  }

  @Test
  public void setLastUpdatedTime_Args__Date() throws Exception {
    CmsPersistentObject target = new TestCmsPersistentObject();
    Date lastUpdatedTime = mock(Date.class);
    target.setLastUpdatedTime(lastUpdatedTime);
  }

}

