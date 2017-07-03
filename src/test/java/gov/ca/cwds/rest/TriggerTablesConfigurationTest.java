package gov.ca.cwds.rest;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;

import org.junit.Test;

public class TriggerTablesConfigurationTest {

  @Test
  public void type() throws Exception {
    assertThat(TriggerTablesConfiguration.class, notNullValue());
  }

  @Test
  public void instantiation() throws Exception {
    TriggerTablesConfiguration target = new TriggerTablesConfiguration();
    assertThat(target, notNullValue());
  }

  @Test
  public void getLaCountySpecificCode_Args__() throws Exception {
    TriggerTablesConfiguration target = new TriggerTablesConfiguration();
    String actual = target.getLaCountySpecificCode();
    String expected = null;
    assertThat(actual, is(equalTo(expected)));
  }

}
