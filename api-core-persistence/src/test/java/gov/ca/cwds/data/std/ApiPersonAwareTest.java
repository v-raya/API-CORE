package gov.ca.cwds.data.std;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;

import java.io.Serializable;
import java.util.Date;

import org.junit.Test;

public class ApiPersonAwareTest {

  private static final class TestOnlyApiPersonAware implements ApiPersonAware {

    @Override
    public Serializable getPrimaryKey() {
      return null;
    }

    @Override
    public String getFirstName() {
      return null;
    }

    @Override
    public String getMiddleName() {
      return null;
    }

    @Override
    public String getLastName() {
      return null;
    }

    @Override
    public String getGender() {
      return null;
    }

    @Override
    public Date getBirthDate() {
      return null;
    }

    @Override
    public String getSsn() {
      return null;
    }

    @Override
    public String getNameSuffix() {
      return null;
    }

  }

  @Test
  public void type() throws Exception {
    assertThat(ApiPersonAware.class, notNullValue());
  }

  @Test
  public void instantiation() throws Exception {
    ApiPersonAware target = new TestOnlyApiPersonAware();
    assertThat(target, notNullValue());
  }

  @Test
  public void getSensitivityIndicator_Args__() throws Exception {
    ApiPersonAware target = new TestOnlyApiPersonAware();
    String actual = target.getSensitivityIndicator();
    String expected = null;
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void getSoc158SealedClientIndicator_Args__() throws Exception {
    ApiPersonAware target = new TestOnlyApiPersonAware();
    String actual = target.getSoc158SealedClientIndicator();
    String expected = null;
    assertThat(actual, is(equalTo(expected)));
  }

}
