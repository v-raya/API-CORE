package gov.ca.cwds.rest.filters;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.ext.Provider;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.inject.Inject;

import gov.ca.cwds.rest.api.ApiException;

/**
 * 
 * 
 * @author CWDS API Team
 * @see ApiRequestCommonInfo
 */
@Provider
public class RequestCommonInfoFilter implements Filter {

  private static final Logger LOGGER = LoggerFactory.getLogger(RequestCommonInfoFilter.class);

  /**
   * Constructor
   */
  @Inject
  public RequestCommonInfoFilter() {
    // No-op.
  }

  @Override
  public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
      throws IOException, ServletException {

    if (request instanceof HttpServletRequest) {
      final HttpServletRequest httpServletRequest = (HttpServletRequest) request;
      final HttpServletResponse httpServletResponse = (HttpServletResponse) response;
      try {
        ApiRequestCommonInfo.startRequest("0x5");
        chain.doFilter(httpServletRequest, httpServletResponse);
      } catch (Exception e) {
        LOGGER.error(e.getMessage(), e);
        throw new ApiException("Unable to handle request", e);
      }
    }

  }

  @Override
  public void init(FilterConfig filterConfig) throws ServletException {
    // No-op.
  }

  @Override
  public void destroy() {
    // No-op.
  }

}
