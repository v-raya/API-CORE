package gov.ca.cwds.rest.filters;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.inject.Inject;

import gov.ca.cwds.logging.AuditLogger;

@Provider
public class ExceptionMapperImpl implements ExceptionMapper<Exception> {

  private static final Logger LOGGER = LoggerFactory.getLogger(ExceptionMapperImpl.class);

  private AuditLogger auditLogger;

  // @Context
  // private HttpServletRequest request;

  @Inject
  public ExceptionMapperImpl(AuditLogger auditLogger) {
    this.auditLogger = auditLogger;
  }

  @Override
  public Response toResponse(Exception ex) {

    // ContainerRequestContext

    LOGGER.error("EXCEPTION MAPPER: {}", ex.getMessage(), ex);
    final String uniqueId =
        auditLogger.buildMDC("Host", "STUBBED_USER", "sessionId", Thread.currentThread().getName());
    auditLogger.audit(uniqueId);
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
