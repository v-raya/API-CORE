package gov.ca.cwds.rest.exception.mapper;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;

import org.apache.commons.lang3.NotImplementedException;

import gov.ca.cwds.logging.LoggingContext;
import gov.ca.cwds.rest.exception.IssueType;
import gov.ca.cwds.rest.services.ServiceException;

/**
 * Service exception mapper
 * 
 * @author CWDS API Team
 */
public class ServiceExceptionMapper implements ExceptionMapper<ServiceException> {

  private LoggingContext loggingContext;

  public ServiceExceptionMapper(LoggingContext loggingContext) {
    this.loggingContext = loggingContext;
  }

  @Override
  public Response toResponse(ServiceException exception) {
    Throwable cause = exception.getCause();
    int responseStatus = 500;

    if (cause != null) {
      if (cause instanceof EntityNotFoundException) {
        responseStatus = Response.Status.NOT_FOUND.getStatusCode();
      } else if (exception.getCause() instanceof EntityExistsException) {
        responseStatus = Response.Status.CONFLICT.getStatusCode();
      } else if (exception.getCause() instanceof NotImplementedException) {
        responseStatus = Response.Status.NOT_IMPLEMENTED.getStatusCode();
      }
    }

    return ExceptionMapperUtils.createGenericResponse(exception, IssueType.UNEXPECTED_EXCEPTION,
        responseStatus, loggingContext.getUniqueId());
  }
}
