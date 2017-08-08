package gov.ca.cwds.rest.filters;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import gov.ca.cwds.logging.AuditLoggerImpl;
import gov.ca.cwds.logging.LoggingContext;
import gov.ca.cwds.logging.LoggingContext.LogParameter;
import gov.ca.cwds.logging.MDCLoggingContext;
import gov.ca.cwds.rest.api.ApiException;
import gov.ca.cwds.rest.filters.CustomExceptionMapper.Result;
import gov.ca.cwds.rest.services.ServiceException;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * Test cases for Unexpected Errors
 * 
 * @author CWDS API Team
 */
public class CustomExceptionMapperTest {

  private AuditLoggerImpl auditLogger = new AuditLoggerImpl();
  private LoggingContext loggingContext = new MDCLoggingContext();

  private CustomExceptionMapper customExceptionMapper;

  @Before
  public void setup() {
    loggingContext.initialize();
    loggingContext.setLogParameter(LogParameter.REMOTE_ADDRESS, "Host");
    customExceptionMapper = new CustomExceptionMapper(loggingContext);
  }

  /*
   * Test for the status code
   */
  @Test
  public void testToResponseStatus() {
    Response response = customExceptionMapper.toResponse(new ApiException());
    int expectedStatus = 500;
    int receivedStatus = response.getStatus();
    assertThat(receivedStatus, is(expectedStatus));
  }

  /*
   * Test for the not null response
   */
  @Test
  public void testToResponseNotNull() {
    Response response = customExceptionMapper.toResponse(new ApiException());
    Result result = (Result) response.getEntity();
    Assert.assertNotNull(result);
  }

  /*
   * Test for expected error response
   */
  @Test
  public void testToResponseResult() {
    Response response = customExceptionMapper.toResponse(new ApiException());
    Result result = (Result) response.getEntity();
    assertThat(result.getCode(), is("500"));
    assertThat(
        result.getMessage(),
        is("There was an error processing your request. It has been logged with uniqueId "
            + result.getUniqueId()));
    Assert.assertNotNull(result.getUniqueId());
  }

  /*
   * Test for expected error response for Service Exception
   */
  @Test
  public void testServiceExceptionToResponseResult() {
    Response response =
        customExceptionMapper.toResponse(new ServiceException("This is a service exception"));
    Result result = (Result) response.getEntity();
    assertThat(result.getCode(), is("500"));
    assertThat(
        result.getMessage(),
        is("There was an error processing your request. It has been logged with uniqueId "
            + result.getUniqueId()));
    assertThat(result.getError(), is("This is a service exception"));
    Assert.assertNotNull(result.getUniqueId());
  }

  @Test
  public void testWebApplicationExceptionResponseUnaltered() {
    WebApplicationException webApplicationException = new WebApplicationException(404);
    Response response = customExceptionMapper.toResponse(webApplicationException);
    assertThat(response, is(webApplicationException.getResponse()));
  }


}
