package gov.ca.cwds.rest.api.domain.cms;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;

import org.junit.Test;

public class LegacyTableTest {

  @Test
  public void type() throws Exception {
    assertThat(LegacyTable.class, notNullValue());
  }

  @Test
  public void getName_Args__() throws Exception {
    LegacyTable target = LegacyTable.ADDRESS;
    String actual = target.getName();
    String expected = "ADDRS_T";
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void getDescription_Args__() throws Exception {
    LegacyTable target = LegacyTable.ADDRESS;
    String actual = target.getDescription();
    String expected = "Address";
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void lookupByName_Args__String() throws Exception {
    String tableName = null;
    LegacyTable actual = LegacyTable.lookupByName(tableName);
    LegacyTable expected = null;
    assertThat(actual, is(equalTo(expected)));
  }

}
