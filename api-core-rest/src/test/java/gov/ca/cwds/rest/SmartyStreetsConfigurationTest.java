package gov.ca.cwds.rest;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;

import org.junit.Test;

public class SmartyStreetsConfigurationTest {

  @Test
  public void type() throws Exception {
    assertThat(SmartyStreetsConfiguration.class, notNullValue());
  }

  @Test
  public void instantiation() throws Exception {
    SmartyStreetsConfiguration target = new SmartyStreetsConfiguration();
    assertThat(target, notNullValue());
  }

  @Test
  public void getClientId_Args__() throws Exception {
    SmartyStreetsConfiguration target = new SmartyStreetsConfiguration();
    String actual = target.getClientId();
    String expected = null;
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void getToken_Args__() throws Exception {
    SmartyStreetsConfiguration target = new SmartyStreetsConfiguration();
    String actual = target.getToken();
    String expected = null;
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void getMaxCandidates_Args__() throws Exception {
    SmartyStreetsConfiguration target = new SmartyStreetsConfiguration();
    Integer actual = target.getMaxCandidates();
    Integer expected = null;
    assertThat(actual, is(equalTo(expected)));
  }

}
