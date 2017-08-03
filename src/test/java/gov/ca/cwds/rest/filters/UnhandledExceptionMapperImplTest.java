package gov.ca.cwds.rest.filters;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import gov.ca.cwds.logging.AuditLoggerImpl;
import gov.ca.cwds.logging.LoggingContext;
import gov.ca.cwds.logging.LoggingContext.LogParameter;
import gov.ca.cwds.logging.MDCLoggingContext;
import gov.ca.cwds.rest.api.ApiException;
import gov.ca.cwds.rest.filters.UnhandledExceptionMapperImpl.Result;

/**
 * Test cases for Unexpected Errors
 * 
 * @author CWDS API Team
 */
public class UnhandledExceptionMapperImplTest {

  private AuditLoggerImpl auditLogger = new AuditLoggerImpl();
  private LoggingContext loggingContext = new MDCLoggingContext();

  private UnhandledExceptionMapperImpl unhandledExceptionMapper;

  @Before
  public void setup() {
    loggingContext.initialize();
    loggingContext.setLogParameter(LogParameter.REMOTE_ADDRESS, "Host");
    unhandledExceptionMapper = new UnhandledExceptionMapperImpl(loggingContext);
  }

  /*
   * Test for the status code
   */
  @Test
  public void testToResponseStatus() {
    Response response = unhandledExceptionMapper.toResponse(new ApiException());
    int expectedStatus = 500;
    int receivedStatus = response.getStatus();
    assertThat(receivedStatus, is(expectedStatus));
  }

  /*
   * Test for the not null response
   */
  @Test
  public void testToResponseNotNull() {
    Response response = unhandledExceptionMapper.toResponse(new ApiException());
    Result result = (Result) response.getEntity();
    Assert.assertNotNull(result);
  }

  /*
   * Test for expected error response
   */
  @Test
  public void testToResponseResult() {
    Response response = unhandledExceptionMapper.toResponse(new ApiException());
    Result result = (Result) response.getEntity();
    assertThat(result.getCode(), is("500"));
    assertThat(result.getMessage(),
        is("There was an error processing your request. It has been logged with uniqueId "
            + result.getUniqueId()));
    Assert.assertNotNull(result.getUniqueId());
  }

  @Test
  public void testWebApplicationExceptionResponseUnaltered() {
    WebApplicationException webApplicationException = new WebApplicationException(404);
    Response response = unhandledExceptionMapper.toResponse(webApplicationException);
    assertThat(response, is(webApplicationException.getResponse()));
  }


}
