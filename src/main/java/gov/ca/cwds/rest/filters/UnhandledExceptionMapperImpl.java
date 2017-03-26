package gov.ca.cwds.rest.filters;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;

import com.google.inject.Inject;

import gov.ca.cwds.logging.AuditLoggerImpl;

/**
 * This is the main class to catch exceptions in CWDS API and PERRY with a valid uniqueId to track
 * the errors
 * 
 * @author CWDS API Team
 */
@Provider
public class UnhandledExceptionMapperImpl implements ExceptionMapper<Exception> {

  private static final Logger LOGGER = LoggerFactory.getLogger(UnhandledExceptionMapperImpl.class);

  /**
   * Default constructor
   */
  @Inject
  public UnhandledExceptionMapperImpl() {}

  /**
   * Get the uniqueId from the auditLoggerImpl for the exception thrown and log the error. This
   * method will create a Json with message, error code and uniqueId
   */
  @Override
  public Response toResponse(Exception ex) {
    if (!(ex instanceof WebApplicationException)) {
      LOGGER.error("EXCEPTION MAPPER: {}", ex.getMessage(), ex);
      final String uniqueId = MDC.get(AuditLoggerImpl.UNIQUE_ID);
      final Result result = new Result(uniqueId, "500");
      return Response.status(500).entity(result).type(MediaType.APPLICATION_JSON).build();
    } else {
      WebApplicationException webApplicationException = (WebApplicationException) ex;
      return webApplicationException.getResponse();
    }
  }

  @SuppressWarnings("javadoc")
  public class Result {
    private String uniqueId;
    private String code;
    private String message =
        "There was an error processing your request. It has been logged with uniqueId";

    public Result(String uniqueId, String code) {
      this.uniqueId = uniqueId;
      message = message + " " + uniqueId;
      this.code = code;
    }

    public String getUniqueId() {
      return uniqueId;
    }

    public String getCode() {
      return code;
    }

    public String getMessage() {
      return message;
    }

  }

}
