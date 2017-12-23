package gov.ca.cwds.data.es;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;

import org.junit.Test;

public class ElasticSearchPersonAddressTest {

  @Test
  public void type() throws Exception {
    assertThat(ElasticSearchPersonAddress.class, notNullValue());
  }

  @Test
  public void instantiation() throws Exception {
    ElasticSearchPersonAddress target = new ElasticSearchPersonAddress();
    assertThat(target, notNullValue());
  }

  @Test
  public void getId_Args__() throws Exception {
    ElasticSearchPersonAddress target = new ElasticSearchPersonAddress();
    String actual = target.getId();
    String expected = null;
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void setId_Args__String() throws Exception {
    ElasticSearchPersonAddress target = new ElasticSearchPersonAddress();
    String id = null;
    target.setId(id);
  }

  @Test
  public void getStreetAddress_Args__() throws Exception {
    ElasticSearchPersonAddress target = new ElasticSearchPersonAddress();
    String actual = target.getStreetAddress();
    String expected = null;
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void setStreetAddress_Args__String() throws Exception {
    ElasticSearchPersonAddress target = new ElasticSearchPersonAddress();
    String streetAddress = null;
    target.setStreetAddress(streetAddress);
  }

  @Test
  public void getCity_Args__() throws Exception {
    ElasticSearchPersonAddress target = new ElasticSearchPersonAddress();
    String actual = target.getCity();
    String expected = null;
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void setCity_Args__String() throws Exception {
    ElasticSearchPersonAddress target = new ElasticSearchPersonAddress();
    String city = null;
    target.setCity(city);
  }

  @Test
  public void getState_Args__() throws Exception {
    ElasticSearchPersonAddress target = new ElasticSearchPersonAddress();
    String actual = target.getState();
    String expected = null;
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void setState_Args__String() throws Exception {
    ElasticSearchPersonAddress target = new ElasticSearchPersonAddress();
    String state = null;
    target.setState(state);
  }

  @Test
  public void getCounty_Args__() throws Exception {
    ElasticSearchPersonAddress target = new ElasticSearchPersonAddress();
    String actual = target.getCounty();
    String expected = null;
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void setCounty_Args__String() throws Exception {
    ElasticSearchPersonAddress target = new ElasticSearchPersonAddress();
    String county = null;
    target.setCounty(county);
  }

  @Test
  public void getZip_Args__() throws Exception {
    ElasticSearchPersonAddress target = new ElasticSearchPersonAddress();
    String actual = target.getZip();
    String expected = null;
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void setZip_Args__String() throws Exception {
    ElasticSearchPersonAddress target = new ElasticSearchPersonAddress();
    String zip = null;
    target.setZip(zip);
  }

  @Test
  public void getType_Args__() throws Exception {
    ElasticSearchPersonAddress target = new ElasticSearchPersonAddress();
    ElasticSearchSystemCode actual = target.getType();
    String expected = null;
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void setType_Args__String() throws Exception {
    ElasticSearchPersonAddress target = new ElasticSearchPersonAddress();
    ElasticSearchSystemCode type = null;
    target.setType(type);
  }

  @Test
  public void getAddressId_Args__() throws Exception {
    ElasticSearchPersonAddress target = new ElasticSearchPersonAddress();
    String actual = target.getAddressId();
    String expected = null;
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void getStateCd_Args__() throws Exception {
    ElasticSearchPersonAddress target = new ElasticSearchPersonAddress();
    Short actual = target.getStateCd();
    Short expected = null;
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void getStreetName_Args__() throws Exception {
    ElasticSearchPersonAddress target = new ElasticSearchPersonAddress();
    String actual = target.getStreetName();
    String expected = null;
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void getStreetNumber_Args__() throws Exception {
    ElasticSearchPersonAddress target = new ElasticSearchPersonAddress();
    String actual = target.getStreetNumber();
    String expected = null;
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void getApiAdrZip4_Args__() throws Exception {
    ElasticSearchPersonAddress target = new ElasticSearchPersonAddress();
    String actual = target.getApiAdrZip4();
    String expected = null;
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void getApiAdrUnitNumber_Args__() throws Exception {
    ElasticSearchPersonAddress target = new ElasticSearchPersonAddress();
    String actual = target.getApiAdrUnitNumber();
    String expected = null;
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void getStateName_Args__() throws Exception {
    ElasticSearchPersonAddress target = new ElasticSearchPersonAddress();
    String actual = target.getStateName();
    String expected = null;
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void setStateName_Args__String() throws Exception {
    ElasticSearchPersonAddress target = new ElasticSearchPersonAddress();
    String stateName = null;
    target.setStateName(stateName);
  }

  @Test
  public void getZip4_Args__() throws Exception {
    ElasticSearchPersonAddress target = new ElasticSearchPersonAddress();
    String actual = target.getZip4();
    String expected = null;
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void setZip4_Args__String() throws Exception {
    ElasticSearchPersonAddress target = new ElasticSearchPersonAddress();
    String zip4 = null;
    target.setZip4(zip4);
  }

  @Test
  public void getUnitType_Args__() throws Exception {
    ElasticSearchPersonAddress target = new ElasticSearchPersonAddress();
    String actual = target.getUnitType();
    String expected = null;
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void setUnitType_Args__String() throws Exception {
    ElasticSearchPersonAddress target = new ElasticSearchPersonAddress();
    String unitType = null;
    target.setUnitType(unitType);
  }

  @Test
  public void getUnitNumber_Args__() throws Exception {
    ElasticSearchPersonAddress target = new ElasticSearchPersonAddress();
    String actual = target.getUnitNumber();
    String expected = null;
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void setUnitNumber_Args__String() throws Exception {
    ElasticSearchPersonAddress target = new ElasticSearchPersonAddress();
    String unitNumber = null;
    target.setUnitNumber(unitNumber);
  }

  @Test
  public void setStreetNumber_Args__String() throws Exception {
    ElasticSearchPersonAddress target = new ElasticSearchPersonAddress();
    String streetNumber = null;
    target.setStreetNumber(streetNumber);
  }

  @Test
  public void setStreetName_Args__String() throws Exception {
    ElasticSearchPersonAddress target = new ElasticSearchPersonAddress();
    String streetName = null;
    target.setStreetName(streetName);
  }

  @Test
  public void getApiAdrAddressType_Args__() throws Exception {
    ElasticSearchPersonAddress target = new ElasticSearchPersonAddress();
    Short actual = target.getApiAdrAddressType();
    Short expected = null;
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void getApiAdrUnitType_Args__() throws Exception {
    ElasticSearchPersonAddress target = new ElasticSearchPersonAddress();
    Short actual = target.getApiAdrUnitType();
    Short expected = null;
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void getLegacyDescriptor_Args__() throws Exception {
    ElasticSearchPersonAddress target = new ElasticSearchPersonAddress();
    ElasticSearchLegacyDescriptor actual = target.getLegacyDescriptor();
    assertThat(actual, notNullValue());
  }

  @Test
  public void setLegacyDescriptor_Args__ElasticSearchLegacyDescriptor() throws Exception {
    ElasticSearchPersonAddress target = new ElasticSearchPersonAddress();
    ElasticSearchLegacyDescriptor legacyDescriptor = mock(ElasticSearchLegacyDescriptor.class);
    target.setLegacyDescriptor(legacyDescriptor);
  }

}
