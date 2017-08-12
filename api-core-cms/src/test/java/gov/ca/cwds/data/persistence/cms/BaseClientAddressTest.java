package gov.ca.cwds.data.persistence.cms;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;

import java.util.Date;

import org.junit.Test;

public class BaseClientAddressTest {

  private static final class TestOnlyClientAddress extends BaseClientAddress {

  }

  @Test
  public void type() throws Exception {
    assertThat(BaseClientAddress.class, notNullValue());
  }

  @Test
  public void instantiation() throws Exception {
    BaseClientAddress target = new TestOnlyClientAddress();
    assertThat(target, notNullValue());
  }

  @Test
  public void getPrimaryKey_Args__() throws Exception {
    BaseClientAddress target = new TestOnlyClientAddress();
    String actual = target.getPrimaryKey();
    String expected = null;
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void getId_Args__() throws Exception {
    BaseClientAddress target = new TestOnlyClientAddress();
    String actual = target.getId();
    String expected = null;
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void setId_Args__String() throws Exception {
    BaseClientAddress target = new TestOnlyClientAddress();
    String id = null;
    target.setId(id);
  }

  @Test
  public void getAddressType_Args__() throws Exception {
    BaseClientAddress target = new TestOnlyClientAddress();
    Short actual = target.getAddressType();
    Short expected = null;
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void setAddressType_Args__Short() throws Exception {
    BaseClientAddress target = new TestOnlyClientAddress();
    Short addressType = null;
    target.setAddressType(addressType);
  }

  @Test
  public void getBkInmtId_Args__() throws Exception {
    BaseClientAddress target = new TestOnlyClientAddress();
    String actual = target.getBkInmtId();
    String expected = null;
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void setBkInmtId_Args__String() throws Exception {
    BaseClientAddress target = new TestOnlyClientAddress();
    String bkInmtId = null;
    target.setBkInmtId(bkInmtId);
  }

  @Test
  public void getFkAddress_Args__() throws Exception {
    BaseClientAddress target = new TestOnlyClientAddress();
    String actual = target.getFkAddress();
    String expected = null;
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void setFkAddress_Args__String() throws Exception {
    BaseClientAddress target = new TestOnlyClientAddress();
    String fkAddress = null;
    target.setFkAddress(fkAddress);
  }

  @Test
  public void getFkClient_Args__() throws Exception {
    BaseClientAddress target = new TestOnlyClientAddress();
    String actual = target.getFkClient();
    String expected = null;
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void setFkClient_Args__String() throws Exception {
    BaseClientAddress target = new TestOnlyClientAddress();
    String fkClient = null;
    target.setFkClient(fkClient);
  }

  @Test
  public void getHomelessInd_Args__() throws Exception {
    BaseClientAddress target = new TestOnlyClientAddress();
    String actual = target.getHomelessInd();
    String expected = null;
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void setHomelessInd_Args__String() throws Exception {
    BaseClientAddress target = new TestOnlyClientAddress();
    String homelessInd = null;
    target.setHomelessInd(homelessInd);
  }

  @Test
  public void getFkReferral_Args__() throws Exception {
    BaseClientAddress target = new TestOnlyClientAddress();
    String actual = target.getFkReferral();
    String expected = null;
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void setFkReferral_Args__String() throws Exception {
    BaseClientAddress target = new TestOnlyClientAddress();
    String fkReferral = null;
    target.setFkReferral(fkReferral);
  }

  @Test
  public void getEffEndDt_Args__() throws Exception {
    BaseClientAddress target = new TestOnlyClientAddress();
    Date actual = target.getEffEndDt();
    Date expected = null;
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void setEffEndDt_Args__Date() throws Exception {
    BaseClientAddress target = new TestOnlyClientAddress();
    Date effEndDt = mock(Date.class);
    target.setEffEndDt(effEndDt);
  }

  @Test
  public void getEffStartDt_Args__() throws Exception {
    BaseClientAddress target = new TestOnlyClientAddress();
    Date actual = target.getEffStartDt();
    Date expected = null;
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void setEffStartDt_Args__Date() throws Exception {
    BaseClientAddress target = new TestOnlyClientAddress();
    Date effStartDt = mock(Date.class);
    target.setEffStartDt(effStartDt);
  }

}
