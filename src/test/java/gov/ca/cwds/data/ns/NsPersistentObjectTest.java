package gov.ca.cwds.data.ns;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;

import java.io.Serializable;
import java.util.Date;

import org.junit.Test;

public class NsPersistentObjectTest {
  private static final class TestNsPersistentObject extends NsPersistentObject {

    private String id;

    public TestNsPersistentObject() {}

    public TestNsPersistentObject(String id) {
      this.id = id;
    }

    @Override
    public Serializable getPrimaryKey() {
      return id;
    }

    String getId() {
      return id;
    }

    void setId(String id) {
      this.id = id;
    }

  }

  @Test
  public void type() throws Exception {
    assertThat(NsPersistentObject.class, notNullValue());
  }

  @Test
  public void instantiation() throws Exception {
    NsPersistentObject target = new TestNsPersistentObject();
    assertThat(target, notNullValue());
  }

  @Test
  public void getTimestampFormat_Args__() throws Exception {
    String actual = NsPersistentObject.getTimestampFormat();
    String expected = "yyyy-MM-dd-HH.mm.ss.SSS";
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void getCreateUserId_Args__() throws Exception {
    NsPersistentObject target = new TestNsPersistentObject();
    String actual = target.getCreateUserId();
    String expected = null;
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void getCreateDateTime_Args__() throws Exception {
    NsPersistentObject target = new TestNsPersistentObject();
    Date actual = target.getCreateDateTime();
    Date expected = null;
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void getLastUpdatedId_Args__() throws Exception {
    NsPersistentObject target = new TestNsPersistentObject();
    String actual = target.getLastUpdatedId();
    String expected = null;
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void getLastUpdatedTime_Args__() throws Exception {
    NsPersistentObject target = new TestNsPersistentObject();
    Date actual = target.getLastUpdatedTime();
    Date expected = null;
    assertThat(actual, is(equalTo(expected)));
  }

}
