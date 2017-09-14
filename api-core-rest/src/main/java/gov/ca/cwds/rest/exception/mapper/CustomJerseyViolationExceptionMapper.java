package gov.ca.cwds.rest.exception.mapper;


import gov.ca.cwds.rest.exception.BaseExceptionResponse;
import gov.ca.cwds.rest.exception.IssueDetails;
import gov.ca.cwds.rest.exception.IssueType;
import gov.ca.cwds.utils.JsonUtils;
import io.dropwizard.jersey.validation.JerseyViolationException;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;
import javax.validation.ConstraintViolation;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import org.apache.commons.lang3.StringUtils;
import org.glassfish.jersey.server.model.Invocable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author CWDS CALS API Team
 */

public class CustomJerseyViolationExceptionMapper implements
    ExceptionMapper<JerseyViolationException> {

  private static final Logger LOG = LoggerFactory
      .getLogger(CustomJerseyViolationExceptionMapper.class);


  @Override
  public Response toResponse(final JerseyViolationException exception) {
    Set<IssueDetails> validationDetailsList = new HashSet<>();
    Set<ConstraintViolation<?>> constraintViolations = exception.getConstraintViolations();
    for (ConstraintViolation<?> v : constraintViolations) {
      String message = CustomConstraintMessage.getMessage(v, exception.getInvocable()).trim();
      IssueDetails details = unmarshallData(message);
      if (details != null) {
        details.setType(IssueType.BUSINESS_VALIDATION);
      } else {
        details = new IssueDetails();
        details.setUserMessage(message);
        details.setType(IssueType.CONSTRAINT_VALIDATION);
      }
      validationDetailsList.add(details);
    }

    BaseExceptionResponse constraintViolationsResponse = new BaseExceptionResponse();
    constraintViolationsResponse.setIssueDetails(validationDetailsList);

    int status = CustomConstraintMessage.determineStatus(
        exception.getConstraintViolations(), exception.getInvocable());
    return Response.status(status)
        .type(MediaType.APPLICATION_JSON_TYPE)
        .entity(constraintViolationsResponse)
        .build();
  }

  /**
   * hibernate validation framework updates user message with prefix that should be removed.
   *
   * @param data constraint violation message
   * @return validation details in case of business validation message or null in case of constraint
   * validation message
   * @see {@link CustomConstraintMessage#calculateMessage(ConstraintViolation, Invocable)}
   */
  private IssueDetails unmarshallData(String data) {
    String marshalledDetails = StringUtils.removeStart(data, "The request body");
    IssueDetails details = null;
    try {
      details = (IssueDetails) JsonUtils.from(marshalledDetails, IssueDetails.class);
    } catch (IOException e) {
      LOG.debug("Cannot unmarshall validation details", e);
    }
    return details;
  }

}


