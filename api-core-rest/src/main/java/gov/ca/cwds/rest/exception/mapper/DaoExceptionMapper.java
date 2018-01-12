package gov.ca.cwds.rest.exception.mapper;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;

import gov.ca.cwds.data.DaoException;
import gov.ca.cwds.logging.LoggingContext;
import gov.ca.cwds.rest.exception.IssueType;

/**
 * DAO exception mapper
 * 
 * @author CWDS API Team
 */
public class DaoExceptionMapper implements ExceptionMapper<DaoException> {

  private LoggingContext loggingContext;

  public DaoExceptionMapper(LoggingContext loggingContext) {
    this.loggingContext = loggingContext;
  }

  @Override
  public Response toResponse(DaoException ex) {
    return ExceptionMapperUtils.createGenericResponse(ex, IssueType.DATA_ACCESS_EXCEPTION, loggingContext.getUniqueId());
  }
}
