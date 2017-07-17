package gov.ca.cwds.data.es;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;

import org.junit.Test;

public class NsElasticsearchObjectTest {

  private static final class TestOnlyNsElasticsearchObject extends NsElasticsearchObject {

  }

  @Test
  public void type() throws Exception {
    assertThat(NsElasticsearchObject.class, notNullValue());
  }

  @Test
  public void instantiation() throws Exception {
    NsElasticsearchObject target = new TestOnlyNsElasticsearchObject();
    assertThat(target, notNullValue());
  }

  @Test
  public void getUpdatedAt_Args__() throws Exception {
    NsElasticsearchObject target = new TestOnlyNsElasticsearchObject();
    String actual = target.getUpdatedAt();
    String expected = null;
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void getCreatedAt_Args__() throws Exception {
    NsElasticsearchObject target = new TestOnlyNsElasticsearchObject();
    String actual = target.getCreatedAt();
    String expected = null;
    assertThat(actual, is(equalTo(expected)));
  }

}

