package gov.ca.cwds.data.legacy.cms.dao;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;

import java.util.Date;

import org.junit.Before;
import org.junit.Test;

import gov.ca.cwds.data.legacy.cms.entity.Client;

public class SsaName3ParameterObjectTest extends LegacyBarneyTest<Client> {

  SsaName3ParameterObject target;

  @Before
  @Override
  public void setup() throws Exception {
    super.setup();
    target = new SsaName3ParameterObject();
  }

  @Test
  public void type() throws Exception {
    assertThat(SsaName3ParameterObject.class, notNullValue());
  }

  @Test
  public void instantiation() throws Exception {
    assertThat(target, notNullValue());
  }

  @Test
  public void getTableName_A$() throws Exception {
    String actual = target.getTableName();
    String expected = null;
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void setTableName_A$String() throws Exception {
    String tableName = null;
    target.setTableName(tableName);
  }

  @Test
  public void getCrudOper_A$() throws Exception {
    String actual = target.getCrudOper();
    String expected = " ";
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void setCrudOper_A$String() throws Exception {
    String crudOper = null;
    target.setCrudOper(crudOper);
  }

  @Test
  public void getIdentifier_A$() throws Exception {
    String actual = target.getIdentifier();
    String expected = " ";
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void setIdentifier_A$String() throws Exception {
    String identifier = null;
    target.setIdentifier(identifier);
  }

  @Test
  public void getNameCd_A$() throws Exception {
    String actual = target.getNameCd();
    String expected = " ";
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void setNameCd_A$String() throws Exception {
    String nameCd = null;
    target.setNameCd(nameCd);
  }

  @Test
  public void getFirstName_A$() throws Exception {
    String actual = target.getFirstName();
    String expected = " ";
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void setFirstName_A$String() throws Exception {
    String firstName = null;
    target.setFirstName(firstName);
  }

  @Test
  public void getMiddleName_A$() throws Exception {
    String actual = target.getMiddleName();
    String expected = " ";
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void setMiddleName_A$String() throws Exception {
    String middleName = null;
    target.setMiddleName(middleName);
  }

  @Test
  public void getLastName_A$() throws Exception {
    String actual = target.getLastName();
    String expected = " ";
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void setLastName_A$String() throws Exception {
    String lastName = null;
    target.setLastName(lastName);
  }

  @Test
  public void getStreettNumber_A$() throws Exception {
    String actual = target.getStreettNumber();
    String expected = " ";
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void setStreetNumber_A$String() throws Exception {
    String streettNumber = null;
    target.setStreetNumber(streettNumber);
  }

  @Test
  public void getStreetName_A$() throws Exception {
    String actual = target.getStreetName();
    String expected = " ";
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void setStreetName_A$String() throws Exception {
    String streetName = null;
    target.setStreetName(streetName);
  }

  @Test
  public void getUpdateId_A$() throws Exception {
    String actual = target.getUpdateId();
    String expected = " ";
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void setUpdateId_A$String() throws Exception {
    String updateId = null;
    target.setUpdateId(updateId);
  }

  @Test
  public void getGvrEntc_A$() throws Exception {
    Short actual = target.getGvrEntc();
    Short expected = null;
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void setGvrEntc_A$Short() throws Exception {
    Short gvrEntc = null;
    target.setGvrEntc(gvrEntc);
  }

  @Test
  public void getUpdateTimeStamp_A$() throws Exception {
    Date actual = target.getUpdateTimeStamp();
    Date expected = null;
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void setUpdateTimeStamp_A$Date() throws Exception {
    Date updateTimeStamp = new Date();
    target.setUpdateTimeStamp(updateTimeStamp);
  }

}
