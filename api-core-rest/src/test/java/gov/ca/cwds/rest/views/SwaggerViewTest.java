package gov.ca.cwds.rest.views;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;

import org.junit.Ignore;
import org.junit.Test;

import gov.ca.cwds.rest.SwaggerConfiguration;

public class SwaggerViewTest {

  @Test
  @Ignore
  public void type() throws Exception {
    assertThat(SwaggerView.class, notNullValue());
  }

  @Test
  @Ignore
  public void instantiation() throws Exception {
    SwaggerConfiguration swaggerConfiguration = new SwaggerConfiguration();
    String swaggerJsonUrl = null;
    String callbackUrl = null;
    SwaggerView target = new SwaggerView(swaggerConfiguration, swaggerJsonUrl, callbackUrl);
    assertThat(target, notNullValue());
  }

  @Test
  @Ignore
  public void getAssetsPath_Args__() throws Exception {
    SwaggerConfiguration swaggerConfiguration = new SwaggerConfiguration();
    String swaggerJsonUrl = null;
    String callbackUrl = null;
    SwaggerView target = new SwaggerView(swaggerConfiguration, swaggerJsonUrl, callbackUrl);
    String actual = target.getAssetsPath();
    String expected = null;
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  @Ignore
  public void getTitle_Args__() throws Exception {
    SwaggerConfiguration swaggerConfiguration = new SwaggerConfiguration();
    String swaggerJsonUrl = null;
    String callbackUrl = null;
    SwaggerView target = new SwaggerView(swaggerConfiguration, swaggerJsonUrl, callbackUrl);
    String actual = target.getTitle();
    String expected = null;
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  @Ignore
  public void getJsonUrl_Args__() throws Exception {
    SwaggerConfiguration swaggerConfiguration = new SwaggerConfiguration();
    String swaggerJsonUrl = null;
    String callbackUrl = null;
    SwaggerView target = new SwaggerView(swaggerConfiguration, swaggerJsonUrl, callbackUrl);
    String actual = target.getJsonUrl();
    String expected = null;
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  @Ignore
  public void getLogo_Args__() throws Exception {
    SwaggerConfiguration swaggerConfiguration = new SwaggerConfiguration();
    String swaggerJsonUrl = null;
    String callbackUrl = null;
    SwaggerView target = new SwaggerView(swaggerConfiguration, swaggerJsonUrl, callbackUrl);
    String actual = target.getLogo();
    String expected = null;
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  @Ignore
  public void getLoginUrl_Args__() throws Exception {
    SwaggerConfiguration swaggerConfiguration = new SwaggerConfiguration();
    String swaggerJsonUrl = null;
    String callbackUrl = null;
    SwaggerView target = new SwaggerView(swaggerConfiguration, swaggerJsonUrl, callbackUrl);
    String actual = target.getLoginUrl();
    String expected = null;
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  @Ignore
  public void getShowLoginButton_Args__() throws Exception {
    SwaggerConfiguration swaggerConfiguration = new SwaggerConfiguration();
    String swaggerJsonUrl = null;
    String callbackUrl = null;
    SwaggerView target = new SwaggerView(swaggerConfiguration, swaggerJsonUrl, callbackUrl);
    boolean actual = target.getShowLoginButton();
    boolean expected = false;
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  @Ignore
  public void getCallbackUrl_Args__() throws Exception {
    SwaggerConfiguration swaggerConfiguration = new SwaggerConfiguration();
    String swaggerJsonUrl = null;
    String callbackUrl = null;
    SwaggerView target = new SwaggerView(swaggerConfiguration, swaggerJsonUrl, callbackUrl);
    String actual = target.getCallbackUrl();
    String expected = null;
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  @Ignore
  public void getSpId_Args__() throws Exception {
    SwaggerConfiguration swaggerConfiguration = new SwaggerConfiguration();
    String swaggerJsonUrl = null;
    String callbackUrl = null;
    SwaggerView target = new SwaggerView(swaggerConfiguration, swaggerJsonUrl, callbackUrl);
    String actual = target.getSpId();
    String expected = null;
    assertThat(actual, is(equalTo(expected)));
  }

}
