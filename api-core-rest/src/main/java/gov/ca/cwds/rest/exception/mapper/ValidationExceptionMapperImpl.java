package gov.ca.cwds.rest.exception.mapper;

import java.util.Set;

import javax.validation.ConstraintViolationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;

import gov.ca.cwds.logging.LoggingContext;
import gov.ca.cwds.rest.exception.BaseExceptionResponse;
import gov.ca.cwds.rest.exception.IssueDetails;
import gov.ca.cwds.rest.exception.IssueDetailsCreator;

/**
 * @author CWDS CALS API Team
 */
public class ValidationExceptionMapperImpl
    implements ExceptionMapper<ConstraintViolationException> {

  private LoggingContext loggingContext;

  public ValidationExceptionMapperImpl(LoggingContext loggingContext) {
    this.loggingContext = loggingContext;
  }

  @Override
  public Response toResponse(ConstraintViolationException exception) {
    Set<IssueDetails> validationDetailsList =
        IssueDetailsCreator.create(exception, loggingContext.getUniqueId());

    BaseExceptionResponse constraintViolationsResponse = new BaseExceptionResponse();
    constraintViolationsResponse.setIssueDetails(validationDetailsList);
    return Response.status(422).type(MediaType.APPLICATION_JSON_TYPE)
        .entity(constraintViolationsResponse).build();
  }
}
