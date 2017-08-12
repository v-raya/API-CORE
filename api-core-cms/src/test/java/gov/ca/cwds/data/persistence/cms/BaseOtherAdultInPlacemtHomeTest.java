package gov.ca.cwds.data.persistence.cms;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;

import java.util.Date;

import org.junit.Test;

public class BaseOtherAdultInPlacemtHomeTest {

  private static final class TestOnlyOtherAdultInPlacemtHome extends BaseOtherAdultInPlacemtHome {

  }

  @Test
  public void type() throws Exception {
    assertThat(BaseOtherAdultInPlacemtHome.class, notNullValue());
  }

  @Test
  public void instantiation() throws Exception {
    BaseOtherAdultInPlacemtHome target = new TestOnlyOtherAdultInPlacemtHome();
    assertThat(target, notNullValue());
  }

  @Test
  public void getPrimaryKey_Args__() throws Exception {
    BaseOtherAdultInPlacemtHome target = new TestOnlyOtherAdultInPlacemtHome();
    String actual = target.getPrimaryKey();
    String expected = "";
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void getBirthDate_Args__() throws Exception {
    BaseOtherAdultInPlacemtHome target = new TestOnlyOtherAdultInPlacemtHome();
    Date actual = target.getBirthDate();
    Date expected = null;
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void getCommentDescription_Args__() throws Exception {
    BaseOtherAdultInPlacemtHome target = new TestOnlyOtherAdultInPlacemtHome();
    String actual = target.getCommentDescription();
    String expected = "";
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void getEndDate_Args__() throws Exception {
    BaseOtherAdultInPlacemtHome target = new TestOnlyOtherAdultInPlacemtHome();
    Date actual = target.getEndDate();
    Date expected = null;
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void getFkplcHmT_Args__() throws Exception {
    BaseOtherAdultInPlacemtHome target = new TestOnlyOtherAdultInPlacemtHome();
    String actual = target.getFkplcHmT();
    String expected = "";
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void getGenderCode_Args__() throws Exception {
    BaseOtherAdultInPlacemtHome target = new TestOnlyOtherAdultInPlacemtHome();
    String actual = target.getGenderCode();
    String expected = "";
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void getId_Args__() throws Exception {
    BaseOtherAdultInPlacemtHome target = new TestOnlyOtherAdultInPlacemtHome();
    String actual = target.getId();
    String expected = "";
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void getIdentifiedDate_Args__() throws Exception {
    BaseOtherAdultInPlacemtHome target = new TestOnlyOtherAdultInPlacemtHome();
    Date actual = target.getIdentifiedDate();
    Date expected = null;
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void getName_Args__() throws Exception {
    BaseOtherAdultInPlacemtHome target = new TestOnlyOtherAdultInPlacemtHome();
    String actual = target.getName();
    String expected = "";
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void getOtherAdultCode_Args__() throws Exception {
    BaseOtherAdultInPlacemtHome target = new TestOnlyOtherAdultInPlacemtHome();
    String actual = target.getOtherAdultCode();
    String expected = "";
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void getPassedBackgroundCheckCode_Args__() throws Exception {
    BaseOtherAdultInPlacemtHome target = new TestOnlyOtherAdultInPlacemtHome();
    String actual = target.getPassedBackgroundCheckCode();
    String expected = "";
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void getResidedOutOfStateIndicator_Args__() throws Exception {
    BaseOtherAdultInPlacemtHome target = new TestOnlyOtherAdultInPlacemtHome();
    String actual = target.getResidedOutOfStateIndicator();
    String expected = "";
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void getStartDate_Args__() throws Exception {
    BaseOtherAdultInPlacemtHome target = new TestOnlyOtherAdultInPlacemtHome();
    Date actual = target.getStartDate();
    Date expected = null;
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void getFirstName_Args__() throws Exception {
    BaseOtherAdultInPlacemtHome target = new TestOnlyOtherAdultInPlacemtHome();
    String actual = target.getFirstName();
    String expected = null;
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void getMiddleName_Args__() throws Exception {
    BaseOtherAdultInPlacemtHome target = new TestOnlyOtherAdultInPlacemtHome();
    String actual = target.getMiddleName();
    String expected = null;
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void getLastName_Args__() throws Exception {
    BaseOtherAdultInPlacemtHome target = new TestOnlyOtherAdultInPlacemtHome();
    String actual = target.getLastName();
    String expected = "";
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void getGender_Args__() throws Exception {
    BaseOtherAdultInPlacemtHome target = new TestOnlyOtherAdultInPlacemtHome();
    String actual = target.getGender();
    String expected = "";
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void getSsn_Args__() throws Exception {
    BaseOtherAdultInPlacemtHome target = new TestOnlyOtherAdultInPlacemtHome();
    String actual = target.getSsn();
    String expected = null;
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void getNameSuffix_Args__() throws Exception {
    BaseOtherAdultInPlacemtHome target = new TestOnlyOtherAdultInPlacemtHome();
    String actual = target.getNameSuffix();
    String expected = null;
    assertThat(actual, is(equalTo(expected)));
  }

}
