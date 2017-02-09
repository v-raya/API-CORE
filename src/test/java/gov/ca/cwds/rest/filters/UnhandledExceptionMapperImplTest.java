package gov.ca.cwds.rest.filters;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

import javax.ws.rs.core.Response;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import gov.ca.cwds.logging.AuditLoggerImpl;
import gov.ca.cwds.rest.api.ApiException;
import gov.ca.cwds.rest.filters.UnhandledExceptionMapperImpl.Result;

/**
 * Test cases for Unexpected Errors
 * 
 * @author CWDS API Team
 */
public class UnhandledExceptionMapperImplTest {

  private AuditLoggerImpl auditLogger = new AuditLoggerImpl();

  private UnhandledExceptionMapperImpl unhandledExceptionMapper;

  @Before
  public void setup() {
    auditLogger.uniqueId();
    auditLogger.storeRemoteAddress("Host");
    unhandledExceptionMapper = new UnhandledExceptionMapperImpl();
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

}
