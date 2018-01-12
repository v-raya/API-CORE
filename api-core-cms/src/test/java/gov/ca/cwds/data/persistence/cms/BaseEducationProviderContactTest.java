package gov.ca.cwds.data.persistence.cms;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThat;

import java.util.Date;

import org.junit.Test;

public class BaseEducationProviderContactTest {

  private static final class TestOnlyEducationProviderContact extends BaseEducationProviderContact {

    public TestOnlyEducationProviderContact() {
      super();
    }

    public TestOnlyEducationProviderContact(String lastUpdatedId) {
      super(lastUpdatedId);
    }

    public TestOnlyEducationProviderContact(String lastUpdatedId, Date lastUpdatedTime) {
      super(lastUpdatedId, lastUpdatedTime);
    }

  }

  @Test
  public void type() throws Exception {
    assertThat(BaseEducationProviderContact.class, notNullValue());
  }

  @Test
  public void instantiation() throws Exception {
    BaseEducationProviderContact target = new TestOnlyEducationProviderContact();
    assertThat(target, notNullValue());
  }

  @Test
  public void instantiation2() throws Exception {
    BaseEducationProviderContact target = new TestOnlyEducationProviderContact("0x5");
    assertThat(target, notNullValue());
  }

  @Test
  public void instantiation3() throws Exception {
    BaseEducationProviderContact target = new TestOnlyEducationProviderContact("0x5", new Date());
    assertThat(target, notNullValue());
  }

  @Test
  public void getSerialversionuid_Args__() throws Exception {
    long actual = BaseEducationProviderContact.getSerialversionuid();
    long expected = 1L;
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void getdepartmentOfEducationIndicator_Args__() throws Exception {
    BaseEducationProviderContact target = new TestOnlyEducationProviderContact();
    String actual = target.getdepartmentOfEducationIndicator();
    String expected = null;
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void getEmailAddress_Args__() throws Exception {
    BaseEducationProviderContact target = new TestOnlyEducationProviderContact();
    String actual = target.getEmailAddress();
    String expected = null;
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void getFaxNumber_Args__() throws Exception {
    BaseEducationProviderContact target = new TestOnlyEducationProviderContact();
    assertNull(target.getFaxNumber());
  }

  @Test
  public void getFirstName_Args__() throws Exception {
    BaseEducationProviderContact target = new TestOnlyEducationProviderContact();
    String actual = target.getFirstName();
    String expected = null;
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void getfKeyEducationProvider_Args__() throws Exception {
    BaseEducationProviderContact target = new TestOnlyEducationProviderContact();
    String actual = target.getfKeyEducationProvider();
    String expected = null;
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void getId_Args__() throws Exception {
    BaseEducationProviderContact target = new TestOnlyEducationProviderContact();
    String actual = target.getId();
    String expected = null;
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void getLastName_Args__() throws Exception {
    BaseEducationProviderContact target = new TestOnlyEducationProviderContact();
    String actual = target.getLastName();
    String expected = null;
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void getMiddleName_Args__() throws Exception {
    BaseEducationProviderContact target = new TestOnlyEducationProviderContact();
    String actual = target.getMiddleName();
    String expected = null;
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void getNamePrefixDescription_Args__() throws Exception {
    BaseEducationProviderContact target = new TestOnlyEducationProviderContact();
    String actual = target.getNamePrefixDescription();
    String expected = null;
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void getPhoneExtensionNumber_Args__() throws Exception {
    BaseEducationProviderContact target = new TestOnlyEducationProviderContact();
    assertNull(target.getPhoneExtensionNumber());
  }

  @Test
  public void getPhoneNumber_Args__() throws Exception {
    BaseEducationProviderContact target = new TestOnlyEducationProviderContact();
    String actual = target.getPhoneNumber();
    String expected = null;
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void getPhoneNumber_Args2_() throws Exception {
    BaseEducationProviderContact target = new TestOnlyEducationProviderContact();
    target.phoneNumber = 4083742790L;
    String actual = target.getPhoneNumber();
    assertNotNull(actual);
  }

  @Test
  public void getPrimaryContactIndicator_Args__() throws Exception {
    BaseEducationProviderContact target = new TestOnlyEducationProviderContact();
    String actual = target.getPrimaryContactIndicator();
    String expected = null;
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void getSuffixTitleDescription_Args__() throws Exception {
    BaseEducationProviderContact target = new TestOnlyEducationProviderContact();
    String actual = target.getSuffixTitleDescription();
    String expected = null;
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void getTitleDescription_Args__() throws Exception {
    BaseEducationProviderContact target = new TestOnlyEducationProviderContact();
    String actual = target.getTitleDescription();
    String expected = null;
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void getPrimaryKey_Args__() throws Exception {
    BaseEducationProviderContact target = new TestOnlyEducationProviderContact();
    String actual = target.getPrimaryKey();
    String expected = null;
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void getGender_Args__() throws Exception {
    BaseEducationProviderContact target = new TestOnlyEducationProviderContact();
    String actual = target.getGender();
    String expected = null;
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void getBirthDate_Args__() throws Exception {
    BaseEducationProviderContact target = new TestOnlyEducationProviderContact();
    Date actual = target.getBirthDate();
    Date expected = null;
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void getSsn_Args__() throws Exception {
    BaseEducationProviderContact target = new TestOnlyEducationProviderContact();
    String actual = target.getSsn();
    String expected = null;
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void getNameSuffix_Args__() throws Exception {
    BaseEducationProviderContact target = new TestOnlyEducationProviderContact();
    String actual = target.getNameSuffix();
    String expected = null;
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void getPhoneNumberExtension_Args__() throws Exception {
    BaseEducationProviderContact target = new TestOnlyEducationProviderContact();
    String actual = target.getPhoneNumberExtension();
    String expected = null;
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void getPhoneType_Args__() throws Exception {
    BaseEducationProviderContact target = new TestOnlyEducationProviderContact();
    Object actual = target.getPhoneType();
    Object expected = null;
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void getPhoneId_Args__() throws Exception {
    BaseEducationProviderContact target = new TestOnlyEducationProviderContact();
    String actual = target.getPhoneId();
    String expected = null;
    assertThat(actual, is(equalTo(expected)));
  }

}
