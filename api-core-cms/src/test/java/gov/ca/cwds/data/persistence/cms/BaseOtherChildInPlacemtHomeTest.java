package gov.ca.cwds.data.persistence.cms;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;

import java.math.BigDecimal;
import java.util.Date;

import org.junit.Test;

public class BaseOtherChildInPlacemtHomeTest {

  private static final class TestOnlyBaseOtherChildInPlacemtHome
      extends BaseOtherChildInPlacemtHome {

  }

  @Test
  public void type() throws Exception {
    assertThat(BaseOtherChildInPlacemtHome.class, notNullValue());
  }

  @Test
  public void instantiation() throws Exception {
    BaseOtherChildInPlacemtHome target = new TestOnlyBaseOtherChildInPlacemtHome();
    assertThat(target, notNullValue());
  }

  @Test
  public void getFirstName_Args__() throws Exception {
    BaseOtherChildInPlacemtHome target = new TestOnlyBaseOtherChildInPlacemtHome();
    String actual = target.getFirstName();
    String expected = null;
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void getMiddleName_Args__() throws Exception {
    BaseOtherChildInPlacemtHome target = new TestOnlyBaseOtherChildInPlacemtHome();
    String actual = target.getMiddleName();
    String expected = null;
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void getLastName_Args__() throws Exception {
    BaseOtherChildInPlacemtHome target = new TestOnlyBaseOtherChildInPlacemtHome();
    String actual = target.getLastName();
    String expected = "";
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void getGender_Args__() throws Exception {
    BaseOtherChildInPlacemtHome target = new TestOnlyBaseOtherChildInPlacemtHome();
    String actual = target.getGender();
    String expected = "";
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void getSsn_Args__() throws Exception {
    BaseOtherChildInPlacemtHome target = new TestOnlyBaseOtherChildInPlacemtHome();
    String actual = target.getSsn();
    String expected = null;
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void getNameSuffix_Args__() throws Exception {
    BaseOtherChildInPlacemtHome target = new TestOnlyBaseOtherChildInPlacemtHome();
    String actual = target.getNameSuffix();
    String expected = null;
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void getPrimaryKey_Args__() throws Exception {
    BaseOtherChildInPlacemtHome target = new TestOnlyBaseOtherChildInPlacemtHome();
    String actual = target.getPrimaryKey();
    String expected = "";
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void getAnnualUnearnedIncomeAmount_Args__() throws Exception {
    BaseOtherChildInPlacemtHome target = new TestOnlyBaseOtherChildInPlacemtHome();
    BigDecimal actual = target.getAnnualUnearnedIncomeAmount();
    BigDecimal expected = null;
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void getBirthDate_Args__() throws Exception {
    BaseOtherChildInPlacemtHome target = new TestOnlyBaseOtherChildInPlacemtHome();
    Date actual = target.getBirthDate();
    Date expected = null;
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void getFkplcHmT_Args__() throws Exception {
    BaseOtherChildInPlacemtHome target = new TestOnlyBaseOtherChildInPlacemtHome();
    String actual = target.getFkplcHmT();
    String expected = "";
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void getGenderCode_Args__() throws Exception {
    BaseOtherChildInPlacemtHome target = new TestOnlyBaseOtherChildInPlacemtHome();
    String actual = target.getGenderCode();
    String expected = "";
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void getId_Args__() throws Exception {
    BaseOtherChildInPlacemtHome target = new TestOnlyBaseOtherChildInPlacemtHome();
    String actual = target.getId();
    String expected = "";
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void getName_Args__() throws Exception {
    BaseOtherChildInPlacemtHome target = new TestOnlyBaseOtherChildInPlacemtHome();
    String actual = target.getName();
    String expected = "";
    assertThat(actual, is(equalTo(expected)));
  }

}
