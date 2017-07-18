package gov.ca.cwds.data.es;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;

import org.junit.Test;

import gov.ca.cwds.data.std.ApiPhoneAware.PhoneType;

public class ElasticSearchPersonPhoneTest {

  @Test
  public void type() throws Exception {
    assertThat(ElasticSearchPersonPhone.class, notNullValue());
  }

  @Test
  public void instantiation() throws Exception {
    ElasticSearchPersonPhone target = new ElasticSearchPersonPhone();
    assertThat(target, notNullValue());
  }

  @Test
  public void getId_Args__() throws Exception {
    ElasticSearchPersonPhone target = new ElasticSearchPersonPhone();
    String actual = target.getId();
    String expected = null;
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void setId_Args__String() throws Exception {
    ElasticSearchPersonPhone target = new ElasticSearchPersonPhone();
    String id = null;
    target.setId(id);
  }

  @Test
  public void getPhoneNumber_Args__() throws Exception {
    ElasticSearchPersonPhone target = new ElasticSearchPersonPhone();
    String actual = target.getPhoneNumber();
    String expected = null;
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void getPhoneNumberExtension_Args__() throws Exception {
    ElasticSearchPersonPhone target = new ElasticSearchPersonPhone();
    String actual = target.getPhoneNumberExtension();
    String expected = null;
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void getPhoneType_Args__() throws Exception {
    ElasticSearchPersonPhone target = new ElasticSearchPersonPhone();
    PhoneType actual = target.getPhoneType();
    PhoneType expected = null;
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void setPhoneNumber_Args__String() throws Exception {
    ElasticSearchPersonPhone target = new ElasticSearchPersonPhone();
    String phoneNumber = null;
    target.setPhoneNumber(phoneNumber);
  }

  @Test
  public void getPhoneNumberExtension_Args__String() throws Exception {
    ElasticSearchPersonPhone target = new ElasticSearchPersonPhone();
    String phoneNumberExtension = null;
    target.getPhoneNumberExtension(phoneNumberExtension);
  }

  @Test
  public void setPhoneType_Args__PhoneType() throws Exception {
    ElasticSearchPersonPhone target = new ElasticSearchPersonPhone();
    target.setPhoneType(PhoneType.Cell);
  }

  @Test
  public void getPhoneId_Args__() throws Exception {
    ElasticSearchPersonPhone target = new ElasticSearchPersonPhone();
    String actual = target.getPhoneId();
    String expected = null;
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void setPhoneNumberExtension_Args__String() throws Exception {
    ElasticSearchPersonPhone target = new ElasticSearchPersonPhone();
    String phoneNumberExtension = null;
    target.setPhoneNumberExtension(phoneNumberExtension);
  }

}
