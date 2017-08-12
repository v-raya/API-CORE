package gov.ca.cwds.data.persistence.cms;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;

import java.io.Serializable;
import java.util.Date;

import org.junit.Test;

public class BaseOtherClientNameTest {

  private static final class TestOnlyBaseOtherClientName extends BaseOtherClientName {

  }

  @Test
  public void type() throws Exception {
    assertThat(BaseOtherClientName.class, notNullValue());
  }

  @Test
  public void instantiation() throws Exception {
    BaseOtherClientName target = new TestOnlyBaseOtherClientName();
    assertThat(target, notNullValue());
  }

  @Test
  public void getFirstName_Args__() throws Exception {
    BaseOtherClientName target = new TestOnlyBaseOtherClientName();
    String actual = target.getFirstName();
    String expected = "";
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void getLastName_Args__() throws Exception {
    BaseOtherClientName target = new TestOnlyBaseOtherClientName();
    String actual = target.getLastName();
    String expected = "";
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void getMiddleName_Args__() throws Exception {
    BaseOtherClientName target = new TestOnlyBaseOtherClientName();
    String actual = target.getMiddleName();
    String expected = "";
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void getNamePrefixDescription_Args__() throws Exception {
    BaseOtherClientName target = new TestOnlyBaseOtherClientName();
    String actual = target.getNamePrefixDescription();
    String expected = "";
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void getNameType_Args__() throws Exception {
    BaseOtherClientName target = new TestOnlyBaseOtherClientName();
    Short actual = target.getNameType();
    Short expected = null;
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void getSuffixTitleDescription_Args__() throws Exception {
    BaseOtherClientName target = new TestOnlyBaseOtherClientName();
    String actual = target.getSuffixTitleDescription();
    String expected = "";
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void getClientId_Args__() throws Exception {
    BaseOtherClientName target = new TestOnlyBaseOtherClientName();
    String actual = target.getClientId();
    String expected = "";
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void getThirdId_Args__() throws Exception {
    BaseOtherClientName target = new TestOnlyBaseOtherClientName();
    String actual = target.getThirdId();
    String expected = "";
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void setClientId_Args__String() throws Exception {
    BaseOtherClientName target = new TestOnlyBaseOtherClientName();
    String clientId = null;
    target.setClientId(clientId);
  }

  @Test
  public void setThirdId_Args__String() throws Exception {
    BaseOtherClientName target = new TestOnlyBaseOtherClientName();
    String thirdId = null;
    target.setThirdId(thirdId);
  }

  @Test
  public void getGender_Args__() throws Exception {
    BaseOtherClientName target = new TestOnlyBaseOtherClientName();
    String actual = target.getGender();
    String expected = null;
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void getBirthDate_Args__() throws Exception {
    BaseOtherClientName target = new TestOnlyBaseOtherClientName();
    Date actual = target.getBirthDate();
    Date expected = null;
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void getSsn_Args__() throws Exception {
    BaseOtherClientName target = new TestOnlyBaseOtherClientName();
    String actual = target.getSsn();
    String expected = null;
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void getNameSuffix_Args__() throws Exception {
    BaseOtherClientName target = new TestOnlyBaseOtherClientName();
    String actual = target.getNameSuffix();
    String expected = null;
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void getPrimaryKey_Args__() throws Exception {
    BaseOtherClientName target = new TestOnlyBaseOtherClientName();
    Serializable actual = target.getPrimaryKey();
    Serializable expected = null;
    assertThat(actual, is(equalTo(expected)));
  }

}
