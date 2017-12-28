package gov.ca.cwds.rest.exception.mapper;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import gov.ca.cwds.logging.LoggingContext;
import gov.ca.cwds.rest.exception.IssueType;

/**
 * @author CWDS CALS API Team
 */

public class UnexpectedExceptionMapperImpl implements ExceptionMapper<Exception> {

  private static final Logger LOGGER = LoggerFactory.getLogger(UnexpectedExceptionMapperImpl.class);

  private final LoggingContext loggingContext;

  public UnexpectedExceptionMapperImpl(LoggingContext loggingContext) {
    this.loggingContext = loggingContext;
  }

  @Override
  public Response toResponse(Exception ex) {
    LOGGER.error("EXCEPTION MAPPER: {}", ex.getMessage(), ex);
    return ExceptionMapperUtils.createGenericResponse(ex, IssueType.UNEXPECTED_EXCEPTION, 500,
        loggingContext.getUniqueId());
  }
}
