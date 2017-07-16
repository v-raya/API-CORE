package gov.ca.cwds.data.persistence.cms;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;

import java.math.BigDecimal;
import java.util.Date;

import org.junit.Test;

import gov.ca.cwds.data.std.ApiPhoneAware;

public class BaseReporterTest {

  private static final class TestOnlyBaseReporter extends BaseReporter {

  }

  @Test
  public void type() throws Exception {
    assertThat(BaseReporter.class, notNullValue());
  }

  @Test
  public void instantiation() throws Exception {
    BaseReporter target = new TestOnlyBaseReporter();
    assertThat(target, notNullValue());
  }

  @Test
  public void getPrimaryKey_Args__() throws Exception {
    BaseReporter target = new TestOnlyBaseReporter();
    String actual = target.getPrimaryKey();
    String expected = null;
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void getReferralId_Args__() throws Exception {
    BaseReporter target = new TestOnlyBaseReporter();
    String actual = target.getReferralId();
    String expected = null;
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void getBadgeNumber_Args__() throws Exception {
    BaseReporter target = new TestOnlyBaseReporter();
    String actual = target.getBadgeNumber();
    String expected = "";
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void getCityName_Args__() throws Exception {
    BaseReporter target = new TestOnlyBaseReporter();
    String actual = target.getCityName();
    String expected = "";
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void getColltrClientRptrReltnshpType_Args__() throws Exception {
    BaseReporter target = new TestOnlyBaseReporter();
    Short actual = target.getColltrClientRptrReltnshpType();
    Short expected = null;
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void getCommunicationMethodType_Args__() throws Exception {
    BaseReporter target = new TestOnlyBaseReporter();
    Short actual = target.getCommunicationMethodType();
    Short expected = null;
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void getConfidentialWaiverIndicator_Args__() throws Exception {
    BaseReporter target = new TestOnlyBaseReporter();
    String actual = target.getConfidentialWaiverIndicator();
    String expected = null;
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void getDrmsMandatedRprtrFeedback_Args__() throws Exception {
    BaseReporter target = new TestOnlyBaseReporter();
    String actual = target.getDrmsMandatedRprtrFeedback();
    String expected = "";
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void getEmployerName_Args__() throws Exception {
    BaseReporter target = new TestOnlyBaseReporter();
    String actual = target.getEmployerName();
    String expected = "";
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void getFeedbackDate_Args__() throws Exception {
    BaseReporter target = new TestOnlyBaseReporter();
    Date actual = target.getFeedbackDate();
    Date expected = null;
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void getFeedbackRequiredIndicator_Args__() throws Exception {
    BaseReporter target = new TestOnlyBaseReporter();
    String actual = target.getFeedbackRequiredIndicator();
    String expected = null;
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void getFirstName_Args__() throws Exception {
    BaseReporter target = new TestOnlyBaseReporter();
    String actual = target.getFirstName();
    String expected = "";
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void getLastName_Args__() throws Exception {
    BaseReporter target = new TestOnlyBaseReporter();
    String actual = target.getLastName();
    String expected = "";
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void getMandatedReporterIndicator_Args__() throws Exception {
    BaseReporter target = new TestOnlyBaseReporter();
    String actual = target.getMandatedReporterIndicator();
    String expected = null;
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void getMessagePhoneExtensionNumber_Args__() throws Exception {
    BaseReporter target = new TestOnlyBaseReporter();
    Integer actual = target.getMessagePhoneExtensionNumber();
    Integer expected = null;
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void getMessagePhoneNumber_Args__() throws Exception {
    BaseReporter target = new TestOnlyBaseReporter();
    BigDecimal actual = target.getMessagePhoneNumber();
    BigDecimal expected = null;
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void getMiddleInitialName_Args__() throws Exception {
    BaseReporter target = new TestOnlyBaseReporter();
    String actual = target.getMiddleInitialName();
    String expected = "";
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void getNamePrefixDescription_Args__() throws Exception {
    BaseReporter target = new TestOnlyBaseReporter();
    String actual = target.getNamePrefixDescription();
    String expected = "";
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void getPrimaryPhoneNumber_Args__() throws Exception {
    BaseReporter target = new TestOnlyBaseReporter();
    BigDecimal actual = target.getPrimaryPhoneNumber();
    BigDecimal expected = null;
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void getPrimaryPhoneExtensionNumber_Args__() throws Exception {
    BaseReporter target = new TestOnlyBaseReporter();
    Integer actual = target.getPrimaryPhoneExtensionNumber();
    Integer expected = null;
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void getStateCodeType_Args__() throws Exception {
    BaseReporter target = new TestOnlyBaseReporter();
    Short actual = target.getStateCodeType();
    Short expected = null;
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void getStreetName_Args__() throws Exception {
    BaseReporter target = new TestOnlyBaseReporter();
    String actual = target.getStreetName();
    String expected = "";
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void getStreetNumber_Args__() throws Exception {
    BaseReporter target = new TestOnlyBaseReporter();
    String actual = target.getStreetNumber();
    String expected = "";
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void getSuffixTitleDescription_Args__() throws Exception {
    BaseReporter target = new TestOnlyBaseReporter();
    String actual = target.getSuffixTitleDescription();
    String expected = "";
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void getZipNumber_Args__() throws Exception {
    BaseReporter target = new TestOnlyBaseReporter();
    Integer actual = target.getZipNumber();
    Integer expected = null;
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void getLawEnforcementId_Args__() throws Exception {
    BaseReporter target = new TestOnlyBaseReporter();
    String actual = target.getLawEnforcementId();
    String expected = null;
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void getZipSuffixNumber_Args__() throws Exception {
    BaseReporter target = new TestOnlyBaseReporter();
    Short actual = target.getZipSuffixNumber();
    Short expected = null;
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void getCountySpecificCode_Args__() throws Exception {
    BaseReporter target = new TestOnlyBaseReporter();
    String actual = target.getCountySpecificCode();
    String expected = null;
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void getMiddleName_Args__() throws Exception {
    BaseReporter target = new TestOnlyBaseReporter();
    String actual = target.getMiddleName();
    String expected = "";
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void getGender_Args__() throws Exception {
    BaseReporter target = new TestOnlyBaseReporter();
    String actual = target.getGender();
    String expected = null;
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void getBirthDate_Args__() throws Exception {
    BaseReporter target = new TestOnlyBaseReporter();
    Date actual = target.getBirthDate();
    Date expected = null;
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void getSsn_Args__() throws Exception {
    BaseReporter target = new TestOnlyBaseReporter();
    String actual = target.getSsn();
    String expected = null;
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void getNameSuffix_Args__() throws Exception {
    BaseReporter target = new TestOnlyBaseReporter();
    String actual = target.getNameSuffix();
    String expected = "";
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void getStreetAddress_Args__() throws Exception {
    BaseReporter target = new TestOnlyBaseReporter();
    String actual = target.getStreetAddress();
    String expected = " ";
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void getCity_Args__() throws Exception {
    BaseReporter target = new TestOnlyBaseReporter();
    String actual = target.getCity();
    String expected = "";
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void getState_Args__() throws Exception {
    BaseReporter target = new TestOnlyBaseReporter();
    String actual = target.getState();
    String expected = null;
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void getZip_Args__() throws Exception {
    BaseReporter target = new TestOnlyBaseReporter();
    String actual = target.getZip();
    String expected = "";
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void getCounty_Args__() throws Exception {
    BaseReporter target = new TestOnlyBaseReporter();
    String actual = target.getCounty();
    String expected = null;
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void getPhones_Args__() throws Exception {
    BaseReporter target = new TestOnlyBaseReporter();
    ApiPhoneAware[] actual = target.getPhones();
    ApiPhoneAware[] expected = new ApiPhoneAware[0];
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void getAddressId_Args__() throws Exception {
    BaseReporter target = new TestOnlyBaseReporter();
    String actual = target.getAddressId();
    String expected = null;
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void getStateCd_Args__() throws Exception {
    BaseReporter target = new TestOnlyBaseReporter();
    Short actual = target.getStateCd();
    Short expected = null;
    assertThat(actual, is(equalTo(expected)));
  }

}
