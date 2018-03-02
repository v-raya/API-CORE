package gov.ca.cwds.data.persistence.cms;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThat;

import java.util.Date;

import org.junit.Test;

public class BaseCollateralIndividualTest {

  private static final class TestOnlyCollateralIndividual extends BaseCollateralIndividual {

  }

  @Test
  public void type() throws Exception {
    assertThat(BaseCollateralIndividual.class, notNullValue());
  }

  @Test
  public void instantiation() throws Exception {
    BaseCollateralIndividual target = new TestOnlyCollateralIndividual();
    assertThat(target, notNullValue());
  }

  @Test
  public void getSerialversionuid_Args__() throws Exception {
    long actual = BaseCollateralIndividual.getSerialversionuid();
    long expected = 1L;
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void getBadgeNumber_Args__() throws Exception {
    BaseCollateralIndividual target = new TestOnlyCollateralIndividual();
    String actual = target.getBadgeNumber();
    String expected = "";
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void getBirthDate_Args__() throws Exception {
    BaseCollateralIndividual target = new TestOnlyCollateralIndividual();
    Date actual = target.getBirthDate();
    Date expected = null;
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void getCityName_Args__() throws Exception {
    BaseCollateralIndividual target = new TestOnlyCollateralIndividual();
    String actual = target.getCityName();
    String expected = "";
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void getCommentDescription_Args__() throws Exception {
    BaseCollateralIndividual target = new TestOnlyCollateralIndividual();
    String actual = target.getCommentDescription();
    String expected = "";
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void getEmailAddress_Args__() throws Exception {
    BaseCollateralIndividual target = new TestOnlyCollateralIndividual();
    String actual = target.getEmailAddress();
    String expected = "";
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void getEmployerName_Args__() throws Exception {
    BaseCollateralIndividual target = new TestOnlyCollateralIndividual();
    String actual = target.getEmployerName();
    String expected = "";
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void getEstablishedForCode_Args__() throws Exception {
    BaseCollateralIndividual target = new TestOnlyCollateralIndividual();
    String actual = target.getEstablishedForCode();
    String expected = "";
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void getFaxNumber_Args__() throws Exception {
    BaseCollateralIndividual target = new TestOnlyCollateralIndividual();
    assertNull(target.getFaxNumber());
  }

  @Test
  public void getFirstName_Args__() throws Exception {
    BaseCollateralIndividual target = new TestOnlyCollateralIndividual();
    String actual = target.getFirstName();
    String expected = "";
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void getForeignAddressIndicatorVariable_Args__() throws Exception {
    BaseCollateralIndividual target = new TestOnlyCollateralIndividual();
    String actual = target.getForeignAddressIndicatorVariable();
    String expected = null;
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void getGenderCode_Args__() throws Exception {
    BaseCollateralIndividual target = new TestOnlyCollateralIndividual();
    String actual = target.getGenderCode();
    String expected = "";
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void getId_Args__() throws Exception {
    BaseCollateralIndividual target = new TestOnlyCollateralIndividual();
    String actual = target.getId();
    String expected = null;
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void getLastName_Args__() throws Exception {
    BaseCollateralIndividual target = new TestOnlyCollateralIndividual();
    String actual = target.getLastName();
    String expected = "";
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void getMaritalStatus_Args__() throws Exception {
    BaseCollateralIndividual target = new TestOnlyCollateralIndividual();
    Short actual = target.getMaritalStatus();
    Short expected = null;
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void getMiddleInitialName_Args__() throws Exception {
    BaseCollateralIndividual target = new TestOnlyCollateralIndividual();
    String actual = target.getMiddleInitialName();
    String expected = "";
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void getNamePrefixDescription_Args__() throws Exception {
    BaseCollateralIndividual target = new TestOnlyCollateralIndividual();
    String actual = target.getNamePrefixDescription();
    String expected = "";
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void getPrimaryExtensionNumber_Args__() throws Exception {
    BaseCollateralIndividual target = new TestOnlyCollateralIndividual();
    Integer actual = target.getPrimaryExtensionNumber();
    Integer expected = null;
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void getPrimaryPhoneNo_Args__() throws Exception {
    BaseCollateralIndividual target = new TestOnlyCollateralIndividual();
    Long actual = target.getPrimaryPhoneNo();
    assertNull(actual);
  }

  @Test
  public void getResidedOutOfStateIndicator_Args__() throws Exception {
    BaseCollateralIndividual target = new TestOnlyCollateralIndividual();
    String actual = target.getResidedOutOfStateIndicator();
    String expected = null;
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void getStateCode_Args__() throws Exception {
    BaseCollateralIndividual target = new TestOnlyCollateralIndividual();
    Short actual = target.getStateCode();
    Short expected = null;
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void getStreetName_Args__() throws Exception {
    BaseCollateralIndividual target = new TestOnlyCollateralIndividual();
    String actual = target.getStreetName();
    String expected = "";
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void getStreetNumber_Args__() throws Exception {
    BaseCollateralIndividual target = new TestOnlyCollateralIndividual();
    String actual = target.getStreetNumber();
    String expected = "";
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void getSuffixTitleDescription_Args__() throws Exception {
    BaseCollateralIndividual target = new TestOnlyCollateralIndividual();
    String actual = target.getSuffixTitleDescription();
    String expected = "";
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void getZipNumber_Args__() throws Exception {
    BaseCollateralIndividual target = new TestOnlyCollateralIndividual();
    Integer actual = target.getZipNumber();
    Integer expected = null;
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void getZipSuffixNumber_Args__() throws Exception {
    BaseCollateralIndividual target = new TestOnlyCollateralIndividual();
    Short actual = target.getZipSuffixNumber();
    Short expected = null;
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void getPrimaryKey_Args__() throws Exception {
    BaseCollateralIndividual target = new TestOnlyCollateralIndividual();
    String actual = target.getPrimaryKey();
    String expected = null;
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void getMiddleName_Args__() throws Exception {
    BaseCollateralIndividual target = new TestOnlyCollateralIndividual();
    String actual = target.getMiddleName();
    String expected = "";
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void getGender_Args__() throws Exception {
    BaseCollateralIndividual target = new TestOnlyCollateralIndividual();
    String actual = target.getGender();
    String expected = "";
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void getSsn_Args__() throws Exception {
    BaseCollateralIndividual target = new TestOnlyCollateralIndividual();
    String actual = target.getSsn();
    String expected = null;
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void getNameSuffix_Args__() throws Exception {
    BaseCollateralIndividual target = new TestOnlyCollateralIndividual();
    String actual = target.getNameSuffix();
    String expected = "";
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void getStreetAddress_Args__() throws Exception {
    BaseCollateralIndividual target = new TestOnlyCollateralIndividual();
    String actual = target.getStreetAddress();
    String expected = " ";
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void getCity_Args__() throws Exception {
    BaseCollateralIndividual target = new TestOnlyCollateralIndividual();
    String actual = target.getCity();
    String expected = "";
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void getState_Args__() throws Exception {
    BaseCollateralIndividual target = new TestOnlyCollateralIndividual();
    String actual = target.getState();
    String expected = null;
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void getState_Args2_() throws Exception {
    BaseCollateralIndividual target = new TestOnlyCollateralIndividual();
    target.stateCode = (short) 1860;
    String actual = target.getState();
    assertThat(actual, notNullValue());
  }

  @Test
  public void getZip_Args__() throws Exception {
    BaseCollateralIndividual target = new TestOnlyCollateralIndividual();
    String actual = target.getZip();
    String expected = null;
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void getZip_Args2_() throws Exception {
    BaseCollateralIndividual target = new TestOnlyCollateralIndividual();
    target.zipNumber = 95762;
    String actual = target.getZip();
    assertThat(actual, notNullValue());
  }

  @Test
  public void getCounty_Args__() throws Exception {
    BaseCollateralIndividual target = new TestOnlyCollateralIndividual();
    String actual = target.getCounty();
    assertThat(actual, nullValue());
  }

  @Test
  public void getAddressId_Args__() throws Exception {
    BaseCollateralIndividual target = new TestOnlyCollateralIndividual();
    String actual = target.getAddressId();
    String expected = null;
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void getStateCd_Args__() throws Exception {
    BaseCollateralIndividual target = new TestOnlyCollateralIndividual();
    Short actual = target.getStateCd();
    Short expected = null;
    assertThat(actual, is(equalTo(expected)));
  }

}
