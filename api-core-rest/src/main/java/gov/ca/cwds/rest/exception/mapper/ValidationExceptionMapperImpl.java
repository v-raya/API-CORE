package gov.ca.cwds.rest.exception.mapper;

import java.util.HashSet;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.ElementKind;
import javax.validation.Path;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;

import gov.ca.cwds.logging.LoggingContext;
import gov.ca.cwds.rest.exception.BaseExceptionResponse;
import gov.ca.cwds.rest.exception.IssueDetails;
import gov.ca.cwds.rest.exception.IssueType;

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
    Set<IssueDetails> validationDetailsList = new HashSet<>();
    Set<ConstraintViolation<?>> constraintViolations = exception.getConstraintViolations();

    for (ConstraintViolation<?> v : constraintViolations) {
      Path propertyPath = v.getPropertyPath();
      StringBuffer propertyBuf = new StringBuffer();

      for (Path.Node node : propertyPath) {
        if (ElementKind.PROPERTY.equals(node.getKind())) {
          propertyBuf.append(node.getName());
          propertyBuf.append(".");
        }
      }

      // Remove last dot .
      propertyBuf.deleteCharAt(propertyBuf.lastIndexOf("."));

      String property = propertyBuf.toString();
      Object invalidValue = v.getInvalidValue();
      String message = v.getMessage();
      IssueDetails details = new IssueDetails();
      details.setUserMessage(message);
      details.setType(IssueType.CONSTRAINT_VALIDATION);
      details.setProperty(property);
      details.setInvalidValue(invalidValue);
      details.setIncidentId(loggingContext.getUniqueId());
      validationDetailsList.add(details);
    }

    BaseExceptionResponse constraintViolationsResponse = new BaseExceptionResponse();
    constraintViolationsResponse.setIssueDetails(validationDetailsList);
    return Response.status(422).type(MediaType.APPLICATION_JSON_TYPE)
        .entity(constraintViolationsResponse).build();
  }
}
