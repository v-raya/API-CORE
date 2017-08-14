package gov.ca.cwds.rest;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;

import org.junit.Test;

public class ElasticsearchConfigurationTest {

  @Test
  public void type() throws Exception {
    assertThat(ElasticsearchConfiguration.class, notNullValue());
  }

  @Test
  public void instantiation() throws Exception {
    ElasticsearchConfiguration target = new ElasticsearchConfiguration();
    assertThat(target, notNullValue());
  }

  @Test
  public void getElasticsearchHost_Args__() throws Exception {
    ElasticsearchConfiguration target = new ElasticsearchConfiguration();
    String actual = target.getElasticsearchHost();
    String expected = null;
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void getElasticsearchCluster_Args__() throws Exception {
    ElasticsearchConfiguration target = new ElasticsearchConfiguration();
    String actual = target.getElasticsearchCluster();
    String expected = null;
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void getElasticsearchPort_Args__() throws Exception {
    ElasticsearchConfiguration target = new ElasticsearchConfiguration();
    String actual = target.getElasticsearchPort();
    String expected = null;
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void getElasticsearchAlias_Args__() throws Exception {
    ElasticsearchConfiguration target = new ElasticsearchConfiguration();
    String actual = target.getElasticsearchAlias();
    String expected = null;
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void getElasticsearchDocType_Args__() throws Exception {
    ElasticsearchConfiguration target = new ElasticsearchConfiguration();
    String actual = target.getElasticsearchDocType();
    String expected = null;
    assertThat(actual, is(equalTo(expected)));
  }

}
