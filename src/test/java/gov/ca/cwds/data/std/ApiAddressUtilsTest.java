package gov.ca.cwds.data.std;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;

import org.junit.Test;

public class ApiAddressUtilsTest {

  @Test
  public void type() throws Exception {
    assertThat(ApiAddressUtils.class, notNullValue());
  }

  @Test
  public void formatZip4_Args__Short() throws Exception {
    Short zip4 = null;
    String actual = ApiAddressUtils.formatZip4(zip4);
    String expected = null;
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void formatZip4_Args__Short2() throws Exception {
    Short zip4 = (short) 123;
    String actual = ApiAddressUtils.formatZip4(zip4);
    String expected = "0123";
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void formatZip4_Args__Short3() throws Exception {
    Short zip4 = (short) 1234;
    String actual = ApiAddressUtils.formatZip4(zip4);
    String expected = "1234";
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void formatZip5_Args__Short() throws Exception {
    Short zip5 = null;
    String actual = ApiAddressUtils.formatZip5(zip5);
    String expected = null;
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void formatZip5_Args__Short2() throws Exception {
    Short zip5 = (short) 1234;
    String actual = ApiAddressUtils.formatZip5(zip5);
    String expected = "01234";
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void formatZip5_Args__Short3() throws Exception {
    Short zip5 = (short) 12345;
    String actual = ApiAddressUtils.formatZip5(zip5);
    String expected = "12345";
    assertThat(actual, is(equalTo(expected)));
  }

}
