package gov.ca.cwds.data.persistence.cms;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;

import java.io.Serializable;
import java.util.Date;

import org.junit.Test;

public class CmsTypedPersistentObjectTest {

  private static final class TestOnlyCmsTypedPersistentObject extends CmsTypedPersistentObject {

    private String id;

    protected TestOnlyCmsTypedPersistentObject() {
      super();
    }

    protected TestOnlyCmsTypedPersistentObject(String lastUpdatedId) {
      super(lastUpdatedId);
    }

    @Override
    public Serializable getPrimaryKey() {
      return null;
    }

  }

  @Test
  public void type() throws Exception {
    assertThat(CmsTypedPersistentObject.class, notNullValue());
  }

  @Test
  public void instantiation() throws Exception {
    CmsTypedPersistentObject target = new TestOnlyCmsTypedPersistentObject();
    assertThat(target, notNullValue());
  }

  @Test
  public void getLastUpdatedId_Args__() throws Exception {
    CmsTypedPersistentObject target = new TestOnlyCmsTypedPersistentObject();
    String actual = target.getLastUpdatedId();
    String expected = null;
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void getLastUpdatedTime_Args__() throws Exception {
    CmsTypedPersistentObject target = new TestOnlyCmsTypedPersistentObject();
    Date actual = target.getLastUpdatedTime();
    Date expected = null;
    assertThat(actual, is(equalTo(expected)));
  }

}
