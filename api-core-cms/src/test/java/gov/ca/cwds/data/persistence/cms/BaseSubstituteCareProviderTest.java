package gov.ca.cwds.data.persistence.cms;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;

import java.math.BigDecimal;
import java.util.Date;

import org.junit.Test;

public class BaseSubstituteCareProviderTest {

  private static final class TestOnlyBaseSubstituteCareProvider extends BaseSubstituteCareProvider {

  }

  @Test
  public void type() throws Exception {
    assertThat(BaseSubstituteCareProvider.class, notNullValue());
  }

  @Test
  public void instantiation() throws Exception {
    BaseSubstituteCareProvider target = new TestOnlyBaseSubstituteCareProvider();
    assertThat(target, notNullValue());
  }

  @Test
  public void getPrimaryKey_Args__() throws Exception {
    BaseSubstituteCareProvider target = new TestOnlyBaseSubstituteCareProvider();
    String actual = target.getPrimaryKey();
    String expected = "";
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void getId_Args__() throws Exception {
    BaseSubstituteCareProvider target = new TestOnlyBaseSubstituteCareProvider();
    String actual = target.getId();
    String expected = "";
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void getAdditionalPhoneNumber_Args__() throws Exception {
    BaseSubstituteCareProvider target = new TestOnlyBaseSubstituteCareProvider();
    BigDecimal actual = target.getAdditionalPhoneNumber();
    BigDecimal expected = null;
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void getAdditionlPhoneExtensionNumber_Args__() throws Exception {
    BaseSubstituteCareProvider target = new TestOnlyBaseSubstituteCareProvider();
    Integer actual = target.getAdditionlPhoneExtensionNumber();
    Integer expected = null;
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void getAnnualIncomeAmount_Args__() throws Exception {
    BaseSubstituteCareProvider target = new TestOnlyBaseSubstituteCareProvider();
    BigDecimal actual = target.getAnnualIncomeAmount();
    BigDecimal expected = null;
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void getBirthDate_Args__() throws Exception {
    BaseSubstituteCareProvider target = new TestOnlyBaseSubstituteCareProvider();
    Date actual = target.getBirthDate();
    Date expected = null;
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void getCaDriverLicenseNumber_Args__() throws Exception {
    BaseSubstituteCareProvider target = new TestOnlyBaseSubstituteCareProvider();
    String actual = target.getCaDriverLicenseNumber();
    String expected = "";
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void getCityName_Args__() throws Exception {
    BaseSubstituteCareProvider target = new TestOnlyBaseSubstituteCareProvider();
    String actual = target.getCityName();
    String expected = "";
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void getEducationType_Args__() throws Exception {
    BaseSubstituteCareProvider target = new TestOnlyBaseSubstituteCareProvider();
    Short actual = target.getEducationType();
    Short expected = null;
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void getEmailAddress_Args__() throws Exception {
    BaseSubstituteCareProvider target = new TestOnlyBaseSubstituteCareProvider();
    String actual = target.getEmailAddress();
    String expected = "";
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void getEmployerName_Args__() throws Exception {
    BaseSubstituteCareProvider target = new TestOnlyBaseSubstituteCareProvider();
    String actual = target.getEmployerName();
    String expected = "";
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void getEmploymentStatusType_Args__() throws Exception {
    BaseSubstituteCareProvider target = new TestOnlyBaseSubstituteCareProvider();
    Short actual = target.getEmploymentStatusType();
    Short expected = null;
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void getEthUnableToDetReasonCode_Args__() throws Exception {
    BaseSubstituteCareProvider target = new TestOnlyBaseSubstituteCareProvider();
    String actual = target.getEthUnableToDetReasonCode();
    String expected = "";
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void getFirstName_Args__() throws Exception {
    BaseSubstituteCareProvider target = new TestOnlyBaseSubstituteCareProvider();
    String actual = target.getFirstName();
    String expected = "";
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void getForeignAddressIndicatorVar_Args__() throws Exception {
    BaseSubstituteCareProvider target = new TestOnlyBaseSubstituteCareProvider();
    String actual = target.getForeignAddressIndicatorVar();
    String expected = "";
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void getGenderIndicator_Args__() throws Exception {
    BaseSubstituteCareProvider target = new TestOnlyBaseSubstituteCareProvider();
    String actual = target.getGenderIndicator();
    String expected = "";
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void getHispUnableToDetReasonCode_Args__() throws Exception {
    BaseSubstituteCareProvider target = new TestOnlyBaseSubstituteCareProvider();
    String actual = target.getHispUnableToDetReasonCode();
    String expected = "";
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void getHispanicOriginCode_Args__() throws Exception {
    BaseSubstituteCareProvider target = new TestOnlyBaseSubstituteCareProvider();
    String actual = target.getHispanicOriginCode();
    String expected = "";
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void getIndianTribeType_Args__() throws Exception {
    BaseSubstituteCareProvider target = new TestOnlyBaseSubstituteCareProvider();
    Short actual = target.getIndianTribeType();
    Short expected = null;
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void getLastName_Args__() throws Exception {
    BaseSubstituteCareProvider target = new TestOnlyBaseSubstituteCareProvider();
    String actual = target.getLastName();
    String expected = "";
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void getLisOwnershipIndicator_Args__() throws Exception {
    BaseSubstituteCareProvider target = new TestOnlyBaseSubstituteCareProvider();
    String actual = target.getLisOwnershipIndicator();
    String expected = "";
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void getLisPersonId_Args__() throws Exception {
    BaseSubstituteCareProvider target = new TestOnlyBaseSubstituteCareProvider();
    String actual = target.getLisPersonId();
    String expected = "";
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void getMaritalStatusType_Args__() throws Exception {
    BaseSubstituteCareProvider target = new TestOnlyBaseSubstituteCareProvider();
    Short actual = target.getMaritalStatusType();
    Short expected = null;
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void getMiddleInitialName_Args__() throws Exception {
    BaseSubstituteCareProvider target = new TestOnlyBaseSubstituteCareProvider();
    String actual = target.getMiddleInitialName();
    String expected = "";
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void getNamePrefixDescription_Args__() throws Exception {
    BaseSubstituteCareProvider target = new TestOnlyBaseSubstituteCareProvider();
    String actual = target.getNamePrefixDescription();
    String expected = "";
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void getPassedBackgroundCheckCode_Args__() throws Exception {
    BaseSubstituteCareProvider target = new TestOnlyBaseSubstituteCareProvider();
    String actual = target.getPassedBackgroundCheckCode();
    String expected = "";
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void getPrimaryIncomeType_Args__() throws Exception {
    BaseSubstituteCareProvider target = new TestOnlyBaseSubstituteCareProvider();
    Short actual = target.getPrimaryIncomeType();
    Short expected = null;
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void getResidedOutOfStateIndicator_Args__() throws Exception {
    BaseSubstituteCareProvider target = new TestOnlyBaseSubstituteCareProvider();
    String actual = target.getResidedOutOfStateIndicator();
    String expected = "";
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void getSecondaryIncomeType_Args__() throws Exception {
    BaseSubstituteCareProvider target = new TestOnlyBaseSubstituteCareProvider();
    Short actual = target.getSecondaryIncomeType();
    Short expected = null;
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void getSocialSecurityNumber_Args__() throws Exception {
    BaseSubstituteCareProvider target = new TestOnlyBaseSubstituteCareProvider();
    String actual = target.getSocialSecurityNumber();
    String expected = "";
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void getStateCodeType_Args__() throws Exception {
    BaseSubstituteCareProvider target = new TestOnlyBaseSubstituteCareProvider();
    Short actual = target.getStateCodeType();
    Short expected = null;
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void getStreetName_Args__() throws Exception {
    BaseSubstituteCareProvider target = new TestOnlyBaseSubstituteCareProvider();
    String actual = target.getStreetName();
    String expected = "";
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void getStreetNumber_Args__() throws Exception {
    BaseSubstituteCareProvider target = new TestOnlyBaseSubstituteCareProvider();
    String actual = target.getStreetNumber();
    String expected = "";
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void getSuffixTitleDescription_Args__() throws Exception {
    BaseSubstituteCareProvider target = new TestOnlyBaseSubstituteCareProvider();
    String actual = target.getSuffixTitleDescription();
    String expected = "";
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void getZipNumber_Args__() throws Exception {
    BaseSubstituteCareProvider target = new TestOnlyBaseSubstituteCareProvider();
    Integer actual = target.getZipNumber();
    Integer expected = null;
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void getZipSuffixNumber_Args__() throws Exception {
    BaseSubstituteCareProvider target = new TestOnlyBaseSubstituteCareProvider();
    Short actual = target.getZipSuffixNumber();
    Short expected = null;
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void getMiddleName_Args__() throws Exception {
    BaseSubstituteCareProvider target = new TestOnlyBaseSubstituteCareProvider();
    String actual = target.getMiddleName();
    String expected = "";
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void getGender_Args__() throws Exception {
    BaseSubstituteCareProvider target = new TestOnlyBaseSubstituteCareProvider();
    String actual = target.getGender();
    String expected = "";
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void getSsn_Args__() throws Exception {
    BaseSubstituteCareProvider target = new TestOnlyBaseSubstituteCareProvider();
    String actual = target.getSsn();
    String expected = "";
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void getNameSuffix_Args__() throws Exception {
    BaseSubstituteCareProvider target = new TestOnlyBaseSubstituteCareProvider();
    String actual = target.getNameSuffix();
    String expected = "";
    assertThat(actual, is(equalTo(expected)));
  }

}
