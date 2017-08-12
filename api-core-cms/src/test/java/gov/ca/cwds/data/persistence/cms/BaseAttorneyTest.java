package gov.ca.cwds.data.persistence.cms;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;

import java.math.BigDecimal;
import java.util.Date;

import org.junit.Test;

import gov.ca.cwds.data.std.ApiPhoneAware;

public class BaseAttorneyTest {

  private static final class TestOnlyBaseAttorney extends BaseAttorney {

  }

  @Test
  public void type() throws Exception {
    assertThat(BaseAttorney.class, notNullValue());
  }

  @Test
  public void instantiation() throws Exception {
    BaseAttorney target = new TestOnlyBaseAttorney();
    assertThat(target, notNullValue());
  }

  @Test
  public void getPrimaryKey_Args__() throws Exception {
    BaseAttorney target = new TestOnlyBaseAttorney();
    String actual = target.getPrimaryKey();
    String expected = "";
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void getArchiveAssociationIndicator_Args__() throws Exception {
    BaseAttorney target = new TestOnlyBaseAttorney();
    String actual = target.getArchiveAssociationIndicator();
    String expected = "";
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void getBusinessName_Args__() throws Exception {
    BaseAttorney target = new TestOnlyBaseAttorney();
    String actual = target.getBusinessName();
    String expected = "";
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void getCityName_Args__() throws Exception {
    BaseAttorney target = new TestOnlyBaseAttorney();
    String actual = target.getCityName();
    String expected = "";
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void getCwsAttorneyIndicator_Args__() throws Exception {
    BaseAttorney target = new TestOnlyBaseAttorney();
    String actual = target.getCwsAttorneyIndicator();
    String expected = "";
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void getEmailAddress_Args__() throws Exception {
    BaseAttorney target = new TestOnlyBaseAttorney();
    String actual = target.getEmailAddress();
    String expected = "";
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void getEndDate_Args__() throws Exception {
    BaseAttorney target = new TestOnlyBaseAttorney();
    Date actual = target.getEndDate();
    Date expected = null;
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void getFaxNumber_Args__() throws Exception {
    BaseAttorney target = new TestOnlyBaseAttorney();
    BigDecimal actual = target.getFaxNumber();
    BigDecimal expected = null;
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void getFirstName_Args__() throws Exception {
    BaseAttorney target = new TestOnlyBaseAttorney();
    String actual = target.getFirstName();
    String expected = "";
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void getGovernmentEntityType_Args__() throws Exception {
    BaseAttorney target = new TestOnlyBaseAttorney();
    Short actual = target.getGovernmentEntityType();
    Short expected = null;
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void getId_Args__() throws Exception {
    BaseAttorney target = new TestOnlyBaseAttorney();
    String actual = target.getId();
    String expected = "";
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void getLanguageType_Args__() throws Exception {
    BaseAttorney target = new TestOnlyBaseAttorney();
    Short actual = target.getLanguageType();
    Short expected = null;
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void getLastName_Args__() throws Exception {
    BaseAttorney target = new TestOnlyBaseAttorney();
    String actual = target.getLastName();
    String expected = "";
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void getMessagePhoneExtensionNumber_Args__() throws Exception {
    BaseAttorney target = new TestOnlyBaseAttorney();
    Integer actual = target.getMessagePhoneExtensionNumber();
    Integer expected = null;
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void getMessagePhoneNumber_Args__() throws Exception {
    BaseAttorney target = new TestOnlyBaseAttorney();
    BigDecimal actual = target.getMessagePhoneNumber();
    BigDecimal expected = null;
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void getMiddleInitialName_Args__() throws Exception {
    BaseAttorney target = new TestOnlyBaseAttorney();
    String actual = target.getMiddleInitialName();
    String expected = "";
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void getNamePrefixDescription_Args__() throws Exception {
    BaseAttorney target = new TestOnlyBaseAttorney();
    String actual = target.getNamePrefixDescription();
    String expected = "";
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void getPositionTitleDescription_Args__() throws Exception {
    BaseAttorney target = new TestOnlyBaseAttorney();
    String actual = target.getPositionTitleDescription();
    String expected = "";
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void getPrimaryPhoneExtensionNumber_Args__() throws Exception {
    BaseAttorney target = new TestOnlyBaseAttorney();
    Integer actual = target.getPrimaryPhoneExtensionNumber();
    Integer expected = null;
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void getPrimaryPhoneNumber_Args__() throws Exception {
    BaseAttorney target = new TestOnlyBaseAttorney();
    BigDecimal actual = target.getPrimaryPhoneNumber();
    BigDecimal expected = null;
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void getStateCodeType_Args__() throws Exception {
    BaseAttorney target = new TestOnlyBaseAttorney();
    Short actual = target.getStateCodeType();
    Short expected = null;
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void getStreetName_Args__() throws Exception {
    BaseAttorney target = new TestOnlyBaseAttorney();
    String actual = target.getStreetName();
    String expected = "";
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void getStreetNumber_Args__() throws Exception {
    BaseAttorney target = new TestOnlyBaseAttorney();
    String actual = target.getStreetNumber();
    String expected = "";
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void getSuffixTitleDescription_Args__() throws Exception {
    BaseAttorney target = new TestOnlyBaseAttorney();
    String actual = target.getSuffixTitleDescription();
    String expected = "";
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void getZipNumber_Args__() throws Exception {
    BaseAttorney target = new TestOnlyBaseAttorney();
    Integer actual = target.getZipNumber();
    Integer expected = null;
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void getZipSuffixNumber_Args__() throws Exception {
    BaseAttorney target = new TestOnlyBaseAttorney();
    Short actual = target.getZipSuffixNumber();
    Short expected = null;
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void getMiddleName_Args__() throws Exception {
    BaseAttorney target = new TestOnlyBaseAttorney();
    String actual = target.getMiddleName();
    String expected = "";
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void getGender_Args__() throws Exception {
    BaseAttorney target = new TestOnlyBaseAttorney();
    String actual = target.getGender();
    String expected = null;
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void getBirthDate_Args__() throws Exception {
    BaseAttorney target = new TestOnlyBaseAttorney();
    Date actual = target.getBirthDate();
    Date expected = null;
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void getSsn_Args__() throws Exception {
    BaseAttorney target = new TestOnlyBaseAttorney();
    String actual = target.getSsn();
    String expected = null;
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void getNameSuffix_Args__() throws Exception {
    BaseAttorney target = new TestOnlyBaseAttorney();
    String actual = target.getNameSuffix();
    String expected = null;
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void getPhones_Args__() throws Exception {
    BaseAttorney target = new TestOnlyBaseAttorney();
    ApiPhoneAware[] actual = target.getPhones();
    ApiPhoneAware[] expected = new ApiPhoneAware[0];
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void getPhones_Args2__() throws Exception {
    BaseAttorney target = new TestOnlyBaseAttorney();
    target.primaryPhoneNumber = new BigDecimal("4083742790");
    target.messagePhoneNumber = new BigDecimal("4083742790");
    ApiPhoneAware[] actual = target.getPhones();
    assertThat(actual, notNullValue());
  }

  @Test
  public void setArchiveAssociationIndicator_Args__String() throws Exception {
    BaseAttorney target = new TestOnlyBaseAttorney();
    String archiveAssociationIndicator = null;
    target.setArchiveAssociationIndicator(archiveAssociationIndicator);
  }

  @Test
  public void setBusinessName_Args__String() throws Exception {
    BaseAttorney target = new TestOnlyBaseAttorney();
    String businessName = null;
    target.setBusinessName(businessName);
  }

  @Test
  public void setCityName_Args__String() throws Exception {
    BaseAttorney target = new TestOnlyBaseAttorney();
    String cityName = null;
    target.setCityName(cityName);
  }

  @Test
  public void setCwsAttorneyIndicator_Args__String() throws Exception {
    BaseAttorney target = new TestOnlyBaseAttorney();
    String cwsAttorneyIndicator = null;
    target.setCwsAttorneyIndicator(cwsAttorneyIndicator);
  }

  @Test
  public void setEmailAddress_Args__String() throws Exception {
    BaseAttorney target = new TestOnlyBaseAttorney();
    String emailAddress = null;
    target.setEmailAddress(emailAddress);
  }

  @Test
  public void setEndDate_Args__Date() throws Exception {
    BaseAttorney target = new TestOnlyBaseAttorney();
    Date endDate = mock(Date.class);
    target.setEndDate(endDate);
  }

  @Test
  public void setFaxNumber_Args__BigDecimal() throws Exception {
    BaseAttorney target = new TestOnlyBaseAttorney();
    BigDecimal faxNumber = mock(BigDecimal.class);
    target.setFaxNumber(faxNumber);
  }

  @Test
  public void setFirstName_Args__String() throws Exception {
    BaseAttorney target = new TestOnlyBaseAttorney();
    String firstName = null;
    target.setFirstName(firstName);
  }

  @Test
  public void setGovernmentEntityType_Args__Short() throws Exception {
    BaseAttorney target = new TestOnlyBaseAttorney();
    Short governmentEntityType = null;
    target.setGovernmentEntityType(governmentEntityType);
  }

  @Test
  public void setId_Args__String() throws Exception {
    BaseAttorney target = new TestOnlyBaseAttorney();
    String id = null;
    target.setId(id);
  }

  @Test
  public void setLanguageType_Args__Short() throws Exception {
    BaseAttorney target = new TestOnlyBaseAttorney();
    Short languageType = null;
    target.setLanguageType(languageType);
  }

  @Test
  public void setLastName_Args__String() throws Exception {
    BaseAttorney target = new TestOnlyBaseAttorney();
    String lastName = null;
    target.setLastName(lastName);
  }

  @Test
  public void setMessagePhoneExtensionNumber_Args__Integer() throws Exception {
    BaseAttorney target = new TestOnlyBaseAttorney();
    Integer messagePhoneExtensionNumber = null;
    target.setMessagePhoneExtensionNumber(messagePhoneExtensionNumber);
  }

  @Test
  public void setMessagePhoneNumber_Args__BigDecimal() throws Exception {
    BaseAttorney target = new TestOnlyBaseAttorney();
    BigDecimal messagePhoneNumber = mock(BigDecimal.class);
    target.setMessagePhoneNumber(messagePhoneNumber);
  }

  @Test
  public void setMiddleInitialName_Args__String() throws Exception {
    BaseAttorney target = new TestOnlyBaseAttorney();
    String middleInitialName = null;
    target.setMiddleInitialName(middleInitialName);
  }

  @Test
  public void setNamePrefixDescription_Args__String() throws Exception {
    BaseAttorney target = new TestOnlyBaseAttorney();
    String namePrefixDescription = null;
    target.setNamePrefixDescription(namePrefixDescription);
  }

  @Test
  public void setPositionTitleDescription_Args__String() throws Exception {
    BaseAttorney target = new TestOnlyBaseAttorney();
    String positionTitleDescription = null;
    target.setPositionTitleDescription(positionTitleDescription);
  }

  @Test
  public void setPrimaryPhoneExtensionNumber_Args__Integer() throws Exception {
    BaseAttorney target = new TestOnlyBaseAttorney();
    Integer primaryPhoneExtensionNumber = null;
    target.setPrimaryPhoneExtensionNumber(primaryPhoneExtensionNumber);
  }

  @Test
  public void setPrimaryPhoneNumber_Args__BigDecimal() throws Exception {
    BaseAttorney target = new TestOnlyBaseAttorney();
    BigDecimal primaryPhoneNumber = mock(BigDecimal.class);
    target.setPrimaryPhoneNumber(primaryPhoneNumber);
  }

  @Test
  public void setStateCodeType_Args__Short() throws Exception {
    BaseAttorney target = new TestOnlyBaseAttorney();
    Short stateCodeType = null;
    target.setStateCodeType(stateCodeType);
  }

  @Test
  public void setStreetName_Args__String() throws Exception {
    BaseAttorney target = new TestOnlyBaseAttorney();
    String streetName = null;
    target.setStreetName(streetName);
  }

  @Test
  public void setStreetNumber_Args__String() throws Exception {
    BaseAttorney target = new TestOnlyBaseAttorney();
    String streetNumber = null;
    target.setStreetNumber(streetNumber);
  }

  @Test
  public void setSuffixTitleDescription_Args__String() throws Exception {
    BaseAttorney target = new TestOnlyBaseAttorney();
    String suffixTitleDescription = null;
    target.setSuffixTitleDescription(suffixTitleDescription);
  }

  @Test
  public void setZipNumber_Args__Integer() throws Exception {
    BaseAttorney target = new TestOnlyBaseAttorney();
    Integer zipNumber = null;
    target.setZipNumber(zipNumber);
  }

  @Test
  public void setZipSuffixNumber_Args__Short() throws Exception {
    BaseAttorney target = new TestOnlyBaseAttorney();
    Short zipSuffixNumber = null;
    target.setZipSuffixNumber(zipSuffixNumber);
  }

}
