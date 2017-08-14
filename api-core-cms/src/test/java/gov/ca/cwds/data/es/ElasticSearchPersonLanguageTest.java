package gov.ca.cwds.data.es;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;

import org.junit.Test;

import gov.ca.cwds.data.ApiSysCodeAware;

public class ElasticSearchPersonLanguageTest {

  @Test
  public void type() throws Exception {
    assertThat(ElasticSearchPersonLanguage.class, notNullValue());
  }

  @Test
  public void getSysId_Args__() throws Exception {
    ElasticSearchPersonLanguage target = ElasticSearchPersonLanguage.ARABIC;
    int actual = target.getSysId();
    int expected = 1249;
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void getDescription_Args__() throws Exception {
    ElasticSearchPersonLanguage target = ElasticSearchPersonLanguage.CAMBODIAN;
    String actual = target.getDescription();
    String expected = "Cambodian";
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void getDisplayOrder_Args__() throws Exception {
    ElasticSearchPersonLanguage target = ElasticSearchPersonLanguage.GERMAN;
    int actual = target.getDisplayOrder();
    int expected = 29;
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void lookupBySysId_Args__int() throws Exception {
    ElasticSearchPersonLanguage target = ElasticSearchPersonLanguage.ENGLISH;
    int sysId_ = 1253;
    ApiSysCodeAware actual = target.lookupBySysId(sysId_);
    assertThat(actual, notNullValue());
  }

  @Test
  public void findBySysId_Args__int() throws Exception {
    int sysId_ = 0;
    ElasticSearchPersonLanguage actual = ElasticSearchPersonLanguage.findBySysId(sysId_);
    ElasticSearchPersonLanguage expected = null;
    assertThat(actual, is(equalTo(expected)));
  }

}
