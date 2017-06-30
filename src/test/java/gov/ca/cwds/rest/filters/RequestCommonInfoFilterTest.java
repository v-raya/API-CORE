package gov.ca.cwds.rest.filters;

import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

public class RequestCommonInfoFilterTest {

  @Mock
  ServletRequest request;
  @Mock
  ServletResponse response;
  @Mock
  FilterChain chain;
  @Mock
  FilterConfig filterConfig;

  @Before
  public void setup() {
    MockitoAnnotations.initMocks(this);
    request = mock(HttpServletRequest.class);
    response = mock(HttpServletResponse.class);
    chain = mock(FilterChain.class);
    filterConfig = mock(FilterConfig.class);
  }

  @Test
  public void type() throws Exception {
    assertThat(RequestCommonInfoFilter.class, notNullValue());
  }

  @Test
  public void instantiation() throws Exception {
    RequestCommonInfoFilter target = new RequestCommonInfoFilter();
    assertThat(target, notNullValue());
  }

  @Test
  public void doFilter_Args__ServletRequest__ServletResponse__FilterChain() throws Exception {
    RequestCommonInfoFilter target = new RequestCommonInfoFilter();
    target.doFilter(request, response, chain);
  }

  @Test
  public void doFilter_Args__ServletRequest__ServletResponse__FilterChain_T__IOException()
      throws Exception {
    RequestCommonInfoFilter target = new RequestCommonInfoFilter();
    doThrow(new IOException("uh oh")).when(chain).doFilter(request, response);

    try {
      target.doFilter(request, response, chain);
      fail("Expected exception was not thrown!");
    } catch (IOException e) {
    }
  }

  @Test
  public void doFilter_Args__ServletRequest__ServletResponse__FilterChain_T__ServletException()
      throws Exception {
    RequestCommonInfoFilter target = new RequestCommonInfoFilter();
    doThrow(new ServletException("uh oh")).when(chain).doFilter(request, response);

    try {
      target.doFilter(request, response, chain);
      fail("Expected exception was not thrown!");
    } catch (ServletException e) {
    }
  }

  @Test
  public void init_Args__FilterConfig() throws Exception {
    RequestCommonInfoFilter target = new RequestCommonInfoFilter();
    target.init(filterConfig);
  }

  // @Test
  public void init_Args__FilterConfig_T__ServletException() throws Exception {
    RequestCommonInfoFilter target = new RequestCommonInfoFilter();
    doThrow(new ServletException("uh oh")).when(filterConfig).getFilterName();

    try {
      target.init(filterConfig);
      fail("Expected exception was not thrown!");
    } catch (ServletException e) {
    }
  }

  @Test
  public void destroy_Args__() throws Exception {
    RequestCommonInfoFilter target = new RequestCommonInfoFilter();
    target.destroy();
  }

}
