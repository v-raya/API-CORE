package gov.ca.cwds.data.std;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;

import org.junit.Test;

public class ApiObjectIdentityTest {

  private static final class TestOnlyApiObjectIdentity extends ApiObjectIdentity {

    private String id;

    public TestOnlyApiObjectIdentity() {

    }

    public TestOnlyApiObjectIdentity(String id) {
      this.id = id;
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
    assertThat(ApiObjectIdentity.class, notNullValue());
  }

  @Test
  public void instantiation() throws Exception {
    ApiObjectIdentity target = new TestOnlyApiObjectIdentity();
    assertThat(target, notNullValue());
  }

  @Test
  public void toString_Args__() throws Exception {
    ApiObjectIdentity target = new TestOnlyApiObjectIdentity();
    String actual = target.toString();
    assertThat(actual, notNullValue());
  }

  @Test
  public void hashCode_Args__() throws Exception {
    ApiObjectIdentity target = new TestOnlyApiObjectIdentity();
    ApiObjectIdentity other = new TestOnlyApiObjectIdentity();
    int actual = target.hashCode();
    int expected = other.hashCode();
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void hashCode_Args_2() throws Exception {
    ApiObjectIdentity target = new TestOnlyApiObjectIdentity("1");
    ApiObjectIdentity other = new TestOnlyApiObjectIdentity("1");
    int actual = target.hashCode();
    int expected = other.hashCode();
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void equals_Args__Object() throws Exception {
    ApiObjectIdentity target = new TestOnlyApiObjectIdentity();
    ApiObjectIdentity other = new TestOnlyApiObjectIdentity();
    boolean actual = target.equals(other);
    boolean expected = true;
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void equals_Args2_Object() throws Exception {
    ApiObjectIdentity target = new TestOnlyApiObjectIdentity("2");
    ApiObjectIdentity other = new TestOnlyApiObjectIdentity("2");
    boolean actual = target.equals(other);
    boolean expected = true;
    assertThat(actual, is(equalTo(expected)));
  }

}
