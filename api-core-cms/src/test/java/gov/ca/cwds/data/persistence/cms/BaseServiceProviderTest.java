package gov.ca.cwds.data.persistence.cms;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThat;

import java.util.Date;

import org.junit.Test;

public class BaseServiceProviderTest {

  private static final class TestOnlyBaseServiceProvider extends BaseServiceProvider {

  }

  @Test
  public void type() throws Exception {
    assertThat(BaseServiceProvider.class, notNullValue());
  }

  @Test
  public void instantiation() throws Exception {
    BaseServiceProvider target = new TestOnlyBaseServiceProvider();
    assertThat(target, notNullValue());
  }

  @Test
  public void getPrimaryKey_Args__() throws Exception {
    BaseServiceProvider target = new TestOnlyBaseServiceProvider();
    String actual = target.getPrimaryKey();
    String expected = "";
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void getAgencyName_Args__() throws Exception {
    BaseServiceProvider target = new TestOnlyBaseServiceProvider();
    String actual = target.getAgencyName();
    String expected = "";
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void getArchiveAssociationIndicator_Args__() throws Exception {
    BaseServiceProvider target = new TestOnlyBaseServiceProvider();
    String actual = target.getArchiveAssociationIndicator();
    String expected = "";
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void getCityName_Args__() throws Exception {
    BaseServiceProvider target = new TestOnlyBaseServiceProvider();
    String actual = target.getCityName();
    String expected = "";
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void getEmailAddress_Args__() throws Exception {
    BaseServiceProvider target = new TestOnlyBaseServiceProvider();
    String actual = target.getEmailAddress();
    String expected = "";
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void getFaxNumber_Args__() throws Exception {
    BaseServiceProvider target = new TestOnlyBaseServiceProvider();
    Long actual = target.getFaxNumber();
    assertNull(actual);
  }

  @Test
  public void getFirstName_Args__() throws Exception {
    BaseServiceProvider target = new TestOnlyBaseServiceProvider();
    String actual = target.getFirstName();
    String expected = "";
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void getId_Args__() throws Exception {
    BaseServiceProvider target = new TestOnlyBaseServiceProvider();
    String actual = target.getId();
    String expected = "";
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void getLastName_Args__() throws Exception {
    BaseServiceProvider target = new TestOnlyBaseServiceProvider();
    String actual = target.getLastName();
    String expected = "";
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void getNamePrefixDescription_Args__() throws Exception {
    BaseServiceProvider target = new TestOnlyBaseServiceProvider();
    String actual = target.getNamePrefixDescription();
    String expected = "";
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void getPhoneExtensionNumber_Args__() throws Exception {
    BaseServiceProvider target = new TestOnlyBaseServiceProvider();
    Integer actual = target.getPhoneExtensionNumber();
    Integer expected = null;
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void getPhoneNumberAsDecimal_Args__() throws Exception {
    BaseServiceProvider target = new TestOnlyBaseServiceProvider();
    Long actual = target.getPhoneNumberAsDecimal();
    assertNull(actual);
  }

  @Test
  public void getPhoneNumber_Args__() throws Exception {
    BaseServiceProvider target = new TestOnlyBaseServiceProvider();
    String actual = target.getPhoneNumber();
    String expected = null;
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void getPositionTitleDescription_Args__() throws Exception {
    BaseServiceProvider target = new TestOnlyBaseServiceProvider();
    String actual = target.getPositionTitleDescription();
    String expected = "";
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void getServiceProviderType_Args__() throws Exception {
    BaseServiceProvider target = new TestOnlyBaseServiceProvider();
    Short actual = target.getServiceProviderType();
    Short expected = null;
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void getStateCodeType_Args__() throws Exception {
    BaseServiceProvider target = new TestOnlyBaseServiceProvider();
    Short actual = target.getStateCodeType();
    Short expected = null;
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void getStreetName_Args__() throws Exception {
    BaseServiceProvider target = new TestOnlyBaseServiceProvider();
    String actual = target.getStreetName();
    String expected = "";
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void getStreetNumber_Args__() throws Exception {
    BaseServiceProvider target = new TestOnlyBaseServiceProvider();
    String actual = target.getStreetNumber();
    String expected = "";
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void getSuffixTitleDescription_Args__() throws Exception {
    BaseServiceProvider target = new TestOnlyBaseServiceProvider();
    String actual = target.getSuffixTitleDescription();
    String expected = "";
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void getZipNumber_Args__() throws Exception {
    BaseServiceProvider target = new TestOnlyBaseServiceProvider();
    Integer actual = target.getZipNumber();
    Integer expected = null;
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void getZipSuffixNumber_Args__() throws Exception {
    BaseServiceProvider target = new TestOnlyBaseServiceProvider();
    Short actual = target.getZipSuffixNumber();
    Short expected = null;
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void getStreetAddress_Args__() throws Exception {
    BaseServiceProvider target = new TestOnlyBaseServiceProvider();
    String actual = target.getStreetAddress();
    String expected = " ";
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void getCity_Args__() throws Exception {
    BaseServiceProvider target = new TestOnlyBaseServiceProvider();
    String actual = target.getCity();
    String expected = "";
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void getState_Args__() throws Exception {
    BaseServiceProvider target = new TestOnlyBaseServiceProvider();
    String actual = target.getState();
    String expected = null;
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void getZip_Args__() throws Exception {
    BaseServiceProvider target = new TestOnlyBaseServiceProvider();
    target.zipNumber = 95762;
    target.zipSuffixNumber = (short) 1234;
    String actual = target.getZip();
    String expected = "95762-1234";
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void getCounty_Args__() throws Exception {
    BaseServiceProvider target = new TestOnlyBaseServiceProvider();
    String actual = target.getCounty();
    String expected = null;
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void getMiddleName_Args__() throws Exception {
    BaseServiceProvider target = new TestOnlyBaseServiceProvider();
    String actual = target.getMiddleName();
    String expected = null;
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void getGender_Args__() throws Exception {
    BaseServiceProvider target = new TestOnlyBaseServiceProvider();
    String actual = target.getGender();
    String expected = null;
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void getBirthDate_Args__() throws Exception {
    BaseServiceProvider target = new TestOnlyBaseServiceProvider();
    Date actual = target.getBirthDate();
    Date expected = null;
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void getSsn_Args__() throws Exception {
    BaseServiceProvider target = new TestOnlyBaseServiceProvider();
    String actual = target.getSsn();
    String expected = null;
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void getNameSuffix_Args__() throws Exception {
    BaseServiceProvider target = new TestOnlyBaseServiceProvider();
    String actual = target.getNameSuffix();
    String expected = null;
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void getPhoneNumberExtension_Args__() throws Exception {
    BaseServiceProvider target = new TestOnlyBaseServiceProvider();
    String actual = target.getPhoneNumberExtension();
    String expected = null;
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void getPhoneType_Args__() throws Exception {
    BaseServiceProvider target = new TestOnlyBaseServiceProvider();
    Object actual = target.getPhoneType();
    Object expected = null;
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void getPhoneId_Args__() throws Exception {
    BaseServiceProvider target = new TestOnlyBaseServiceProvider();
    String actual = target.getPhoneId();
    String expected = null;
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void getAddressId_Args__() throws Exception {
    BaseServiceProvider target = new TestOnlyBaseServiceProvider();
    String actual = target.getAddressId();
    String expected = null;
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void getStateCd_Args__() throws Exception {
    BaseServiceProvider target = new TestOnlyBaseServiceProvider();
    Short actual = target.getStateCd();
    Short expected = null;
    assertThat(actual, is(equalTo(expected)));
  }

}
