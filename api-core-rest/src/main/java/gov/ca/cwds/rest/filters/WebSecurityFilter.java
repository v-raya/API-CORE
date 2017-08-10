package gov.ca.cwds.rest.filters;

import java.io.IOException;
import java.util.Map;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;

import com.google.common.base.Strings;
import com.google.inject.Inject;

import gov.ca.cwds.rest.WebSecurityConfiguration;

/**
 * Sets web security response headers to prevent common web security threats.
 * 
 * @author CWDS API Team
 */
public class WebSecurityFilter implements Filter {

  private WebSecurityConfiguration webSecurityConfiguration;

  /**
   * Constructor
   * 
   * @param webSecurityConfiguration The configuration for this filter
   */
  @Inject
  public WebSecurityFilter(WebSecurityConfiguration webSecurityConfiguration) {
    this.webSecurityConfiguration = webSecurityConfiguration;
  }

  @Override
  public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
      throws IOException, ServletException {

    if (response instanceof HttpServletResponse) {
      HttpServletResponse httpResponse = (HttpServletResponse) response;
      Map<String, String> securityHeaders =
          this.webSecurityConfiguration.getHttpResponseSecurityHeaders();

      if (securityHeaders != null) {
        for (String headerName : securityHeaders.keySet()) {
          String headerValue = securityHeaders.get(headerName);
          if (!Strings.isNullOrEmpty(headerValue)) {
            httpResponse.setHeader(headerName, headerValue);
          }
        }
      }
    }

    chain.doFilter(request, response);
  }

  @Override
  public void init(FilterConfig filterConfig) throws ServletException {
    // No-op
  }

  @Override
  public void destroy() {
    // No-op
  }
}
