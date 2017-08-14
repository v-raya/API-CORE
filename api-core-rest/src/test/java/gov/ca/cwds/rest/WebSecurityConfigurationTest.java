package gov.ca.cwds.rest;

import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Nullable;

import org.junit.Assert;
import org.junit.Test;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import com.google.common.io.Resources;

/**
 * Tests for WebSecurityConfiguration
 * 
 * @author CWDS API Team
 */
public class WebSecurityConfigurationTest {

  @Test
  public void testCreationFromYaml() throws Exception {
    URL configFile = Resources.getResource("fixtures/rest/test-web-security-config.yaml");

    MyConfiguration myConfig =
        new ObjectMapper(new YAMLFactory()).readValue(configFile, MyConfiguration.class);

    WebSecurityConfiguration webSecurityConfig = myConfig.getWebSecurityConfiguration();
    Assert.assertNotNull(webSecurityConfig);

    Map<String, String> expectedHeaders = new HashMap<>();
    expectedHeaders.put("X-Content-Type-Options", "nosniff");
    expectedHeaders.put("X-Frame-Options", "deny");
    expectedHeaders.put("X-XSS-Protection", "1; mode=block");
    expectedHeaders.put("Content-Security-Policy", "default-src 'self';");

    Map<String, String> actualHeaders = webSecurityConfig.getHttpResponseSecurityHeaders();

    Assert.assertEquals(expectedHeaders, actualHeaders);

  }

  public static class MyConfiguration {

    @Nullable
    private WebSecurityConfiguration webSecurityConfiguration;

    @JsonProperty(value = "webSecurity")
    public WebSecurityConfiguration getWebSecurityConfiguration() {
      return webSecurityConfiguration;
    }

    @JsonProperty
    public void setWebSecurityConfiguration(WebSecurityConfiguration webSecurityConfiguration) {
      this.webSecurityConfiguration = webSecurityConfiguration;
    }
  }
}
