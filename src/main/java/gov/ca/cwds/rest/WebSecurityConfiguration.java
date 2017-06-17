package gov.ca.cwds.rest;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Nullable;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Web security configuration.
 * 
 * @author CWDS API Team
 */
public class WebSecurityConfiguration {

  @Nullable
  @JsonProperty("httpResponseSecurityHeaders")
  private Map<String, String> httpResponseSecurityHeaders = new HashMap<>();

  /**
   * Get a Map where:<br>
   * Key: HTTP response header to set<br>
   * Value: Response header value
   * 
   * @return A Map of headers names and their values.
   */
  public Map<String, String> getHttpResponseSecurityHeaders() {
    return httpResponseSecurityHeaders;
  }

  /**
   * Set a Map where:<br>
   * Key: HTTP response header to set<br>
   * Value: Response header value
   * 
   * @param httpResponseSecurityHeaders A Map of headers names and their values.
   */
  public void setHttpResponseSecurityHeaders(Map<String, String> httpResponseSecurityHeaders) {
    this.httpResponseSecurityHeaders = httpResponseSecurityHeaders;
  }
}
