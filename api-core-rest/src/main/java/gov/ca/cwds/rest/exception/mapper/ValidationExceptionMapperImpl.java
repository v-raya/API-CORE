package gov.ca.cwds.rest.exception.mapper;

import gov.ca.cwds.rest.exception.BaseExceptionResponse;
import gov.ca.cwds.rest.exception.IssueDetails;
import gov.ca.cwds.rest.exception.IssueType;
import java.util.HashSet;
import java.util.Set;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;

/**
 * @author CWDS CALS API Team
 */
public class ValidationExceptionMapperImpl implements
    ExceptionMapper<ConstraintViolationException> {

  @Override
  public Response toResponse(ConstraintViolationException exception) {
    Set<IssueDetails> validationDetailsList = new HashSet<>();
    Set<ConstraintViolation<?>> constraintViolations = exception.getConstraintViolations();
    for (ConstraintViolation<?> v : constraintViolations) {
      String message = v.getMessage();
      IssueDetails details = null;
      details = new IssueDetails();
      details.setUserMessage(message);
      details.setType(IssueType.CONSTRAINT_VALIDATION);
      validationDetailsList.add(details);
    }

    BaseExceptionResponse constraintViolationsResponse = new BaseExceptionResponse();
    constraintViolationsResponse.setIssueDetails(validationDetailsList);
    return Response.status(422)
        .type(MediaType.APPLICATION_JSON_TYPE)
        .entity(constraintViolationsResponse)
        .build();
  }
}
