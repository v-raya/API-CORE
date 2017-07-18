package gov.ca.cwds.data.std;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;

import org.junit.Test;

public class ApiAddressAwareTest {

  private static final class TestOnlyApiAddressAware implements ApiAddressAware {

    @Override
    public String getAddressId() {
      return null;
    }

    @Override
    public String getStreetAddress() {
      return null;
    }

    @Override
    public String getCity() {
      return null;
    }

    @Override
    public String getState() {
      return null;
    }

    @Override
    public String getZip() {
      return null;
    }

    @Override
    public String getCounty() {
      return null;
    }

    @Override
    public Short getStateCd() {
      return null;
    }

  }

  @Test
  public void type() throws Exception {
    assertThat(ApiAddressAware.class, notNullValue());
  }

  @Test
  public void instantiation() throws Exception {
    ApiAddressAware target = new TestOnlyApiAddressAware();
    assertThat(target, notNullValue());
  }

  @Test
  public void getStreetName_Args__() throws Exception {
    ApiAddressAware target = new TestOnlyApiAddressAware();
    String actual = target.getStreetName();
    String expected = null;
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void getStreetNumber_Args__() throws Exception {
    ApiAddressAware target = new TestOnlyApiAddressAware();
    String actual = target.getStreetNumber();
    String expected = null;
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void getApiAdrZip4_Args__() throws Exception {
    ApiAddressAware target = new TestOnlyApiAddressAware();
    String actual = target.getApiAdrZip4();
    String expected = null;
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void getApiAdrUnitNumber_Args__() throws Exception {
    ApiAddressAware target = new TestOnlyApiAddressAware();
    String actual = target.getApiAdrUnitNumber();
    String expected = null;
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void getApiAdrAddressType_Args__() throws Exception {
    ApiAddressAware target = new TestOnlyApiAddressAware();
    Short actual = target.getApiAdrAddressType();
    Short expected = null;
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void getApiAdrUnitType_Args__() throws Exception {
    ApiAddressAware target = new TestOnlyApiAddressAware();
    Short actual = target.getApiAdrUnitType();
    Short expected = null;
    assertThat(actual, is(equalTo(expected)));
  }

}
