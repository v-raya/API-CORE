package gov.ca.cwds.rest.filters;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;

import com.google.inject.Inject;

import gov.ca.cwds.logging.AuditLoggerImpl;

@Provider
public class UnhandledExceptionMapperImpl implements ExceptionMapper<Exception> {

  private static final Logger LOGGER = LoggerFactory.getLogger(UnhandledExceptionMapperImpl.class);

  @Inject
  public UnhandledExceptionMapperImpl() {}

  @Override
  public Response toResponse(Exception ex) {
    LOGGER.error("EXCEPTION MAPPER: {}", ex.getMessage(), ex);
    final String uniqueId = MDC.get(AuditLoggerImpl.UNIQUE_ID);
    final Result result = new Result(uniqueId, "500");
    return Response.status(500).entity(result).type(MediaType.APPLICATION_JSON).build();
  }

  private class Result {
    private String uniqueId;
    private String code;
    private String message =
        "There was an error processing your request. It has been logged with uniqueId";

    public Result(String uniqueId, String code) {
      this.uniqueId = uniqueId;
      message = message + " " + uniqueId;
      this.code = code;
    }

    @SuppressWarnings("unused")
    public String getUniqueId() {
      return uniqueId;
    }

    @SuppressWarnings("unused")
    public String getCode() {
      return code;
    }

    @SuppressWarnings("unused")
    public String getMessage() {
      return message;
    }

  }

}
