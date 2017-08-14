package gov.ca.cwds.rest.filters;

import static org.mockito.Mockito.when;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mockito;

import gov.ca.cwds.rest.WebSecurityConfiguration;

/**
 * Tests for WebSecurityFilter
 * 
 * @author CWDS API Team
 *
 */
public class WebSecurityFilterTest {

  @Test
  public void testDoFilterSetHeaders() throws Exception {
    WebSecurityConfiguration config = new WebSecurityConfiguration();
    Map<String, String> headers = new HashMap<String, String>();
    headers.put("MY-HEADER-PARAM-NAME", "MY-HEADER-PARAM-VALUE");
    config.setHttpResponseSecurityHeaders(headers);

    WebSecurityFilter webSecurityFilter = new WebSecurityFilter(config);

    HttpServletRequest mockHttpServletRequest = Mockito.mock(HttpServletRequest.class);
    HttpServletResponse mockHttpServletResponse = Mockito.mock(HttpServletResponse.class);
    FilterChain mockFilterChain = Mockito.mock(FilterChain.class);

    when(mockHttpServletResponse.getHeader("MY-HEADER-PARAM-NAME"))
        .thenReturn("MY-HEADER-PARAM-VALUE");

    webSecurityFilter.doFilter(mockHttpServletRequest, mockHttpServletResponse, mockFilterChain);

    Assert.assertTrue(
        headers.containsValue(mockHttpServletResponse.getHeader("MY-HEADER-PARAM-NAME")));
  }
}
