package gov.ca.cwds.rest.exception.mapper;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;

import gov.ca.cwds.logging.LoggingContext;
import gov.ca.cwds.rest.exception.IssueType;
import gov.ca.cwds.rest.validation.ReferentialIntegrityException;

/**
 * @author CWDS API Team
 */
public class ReferentialIntegrityExceptionMapper
    implements ExceptionMapper<ReferentialIntegrityException> {

  private LoggingContext loggingContext;

  public ReferentialIntegrityExceptionMapper(LoggingContext loggingContext) {
    this.loggingContext = loggingContext;
  }

  @Override
  public Response toResponse(ReferentialIntegrityException exception) {
    return ExceptionMapperUtils.createGenericResponse(exception,
        IssueType.REFRENTIAL_INTEGRITY_VIOLATION, loggingContext.getUniqueId());
  }
}
