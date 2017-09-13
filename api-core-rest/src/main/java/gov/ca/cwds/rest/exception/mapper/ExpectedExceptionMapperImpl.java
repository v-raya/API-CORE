package gov.ca.cwds.rest.exception.mapper;

import static gov.ca.cwds.rest.exception.IssueDetails.BASE_MESSAGE;

import gov.ca.cwds.logging.LoggingContext.LogParameter;
import gov.ca.cwds.rest.exception.BaseExceptionResponse;
import gov.ca.cwds.rest.exception.ExpectedException;
import gov.ca.cwds.rest.exception.IssueDetails;
import gov.ca.cwds.rest.exception.IssueType;
import java.util.HashSet;
import java.util.Set;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;
import org.slf4j.MDC;

/**
 * @author CWDS CALS API Team
 */

@Provider
public class ExpectedExceptionMapperImpl implements ExceptionMapper<ExpectedException> {

  @Override
  public Response toResponse(ExpectedException exception) {
    IssueDetails details = new IssueDetails();
    details.setType(IssueType.EXPECTED_EXCEPTION);
    details.setIncidentId(MDC.get(LogParameter.UNIQUE_ID.name()));
    details.setTechnicalMessage(exception.getMessage());
    details.setUserMessage(BASE_MESSAGE);

    Set<IssueDetails> detailsList = new HashSet<>();
    detailsList.add(details);
    BaseExceptionResponse expectedExceptionResponse = new BaseExceptionResponse();
    expectedExceptionResponse.setIssueDetails(detailsList);

    return Response.status(exception.getResponseStatus()).entity(expectedExceptionResponse)
        .type(MediaType.APPLICATION_JSON).build();
  }
}
