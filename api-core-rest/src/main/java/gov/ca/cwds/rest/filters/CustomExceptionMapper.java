package gov.ca.cwds.rest.filters;

import gov.ca.cwds.logging.LoggingContext;
import gov.ca.cwds.logging.LoggingContext.LogParameter;
import gov.ca.cwds.rest.validation.ReferentialIntegrityException;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.inject.Inject;

/**
 * This is the main class to catch exceptions in CWDS API and PERRY with a valid uniqueId to track
 * the errors
 * 
 * @author CWDS API Team
 */
@Provider
public class CustomExceptionMapper implements ExceptionMapper<Exception> {

  private static final Logger LOGGER = LoggerFactory.getLogger(CustomExceptionMapper.class);
  private static final String REFERENTIAL_INTEGRITY_ERROR = "Referential Integrity Error: ";
  private LoggingContext loggingContext;


  /**
   * Default constructor
   * 
   * @param loggingContext API log context
   */
  @Inject
  public CustomExceptionMapper(LoggingContext loggingContext) {
    this.loggingContext = loggingContext;
  }

  /**
   * Get the uniqueId from the auditLoggerImpl for the exception thrown and log the error. This
   * method will create a Json with message, error code and uniqueId
   */
  @Override
  public Response toResponse(Exception ex) {
    if (!(ex instanceof WebApplicationException)) {
      LOGGER.error("EXCEPTION MAPPER: {}", ex.getMessage(), ex);
      final String uniqueId = loggingContext.getLogParameter(LogParameter.UNIQUE_ID);
      final Result result;
      int code = 500;
      if (ex instanceof ReferentialIntegrityException) {
        code = 422;
        String errors = REFERENTIAL_INTEGRITY_ERROR + ex.getMessage();
        result = new Result(uniqueId, "422", errors);
      } else {
        result = new Result(uniqueId, "500");
      }
      return Response.status(code).entity(result).type(MediaType.APPLICATION_JSON).build();
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
    private String error;

    public Result(String uniqueId, String code, String error) {
      this.uniqueId = uniqueId;
      message = message + " " + uniqueId;
      this.code = code;
      this.error = error;
    }

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

    public String getError() {
      return error;
    }

  }

}
