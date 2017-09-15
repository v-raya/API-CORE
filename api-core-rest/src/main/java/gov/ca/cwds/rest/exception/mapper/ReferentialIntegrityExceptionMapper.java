package gov.ca.cwds.rest.exception.mapper;

import java.util.HashSet;
import java.util.Set;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;

import gov.ca.cwds.logging.LoggingContext;
import gov.ca.cwds.rest.exception.BaseExceptionResponse;
import gov.ca.cwds.rest.exception.IssueDetails;
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
    Set<IssueDetails> validationDetailsList = new HashSet<>();

    IssueDetails issueDetails = new IssueDetails();
    issueDetails.setUserMessage(IssueDetails.BASE_MESSAGE);
    issueDetails.setTechnicalMessage(exception.getMessage());
    issueDetails.setType(IssueType.REFRENTIAL_INTEGRITY_VIOLATION);

    issueDetails.setIncidentId(loggingContext.getUniqueId());
    validationDetailsList.add(issueDetails);

    BaseExceptionResponse constraintViolationsResponse = new BaseExceptionResponse();
    constraintViolationsResponse.setIssueDetails(validationDetailsList);

    return Response.status(500).type(MediaType.APPLICATION_JSON_TYPE)
        .entity(constraintViolationsResponse).build();
  }
}
