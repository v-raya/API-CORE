package gov.ca.cwds.rest.exception.mapper;

import static gov.ca.cwds.rest.exception.IssueDetails.BASE_MESSAGE;

import com.fasterxml.jackson.core.JsonProcessingException;
import gov.ca.cwds.logging.LoggingContext.LogParameter;
import gov.ca.cwds.rest.exception.BaseExceptionResponse;
import gov.ca.cwds.rest.exception.IssueDetails;
import gov.ca.cwds.rest.exception.IssueType;
import io.dropwizard.jersey.errors.ErrorMessage;
import io.dropwizard.jersey.jackson.JsonProcessingExceptionMapper;
import java.util.HashSet;
import java.util.Set;
import javax.ws.rs.core.Response;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.MDC;

/**
 * @author CWDS CALS API Team
 */

public class CustomJsonProcessingExceptionMapper extends JsonProcessingExceptionMapper {

  public CustomJsonProcessingExceptionMapper(boolean showDetails) {
    super(showDetails);
  }

  @Override
  public Response toResponse(JsonProcessingException exception) {
    Response response = super.toResponse(exception);
    if (!(response.getEntity() instanceof ErrorMessage)) {
      throw new IllegalStateException("ErrorMessage class is expected here");
    }
    ErrorMessage errorMessage = (ErrorMessage) response.getEntity();

    IssueDetails details = new IssueDetails();
    details.setType(IssueType.JSON_PROCESSING_EXCEPTION);
    details.setIncidentId(MDC.get(LogParameter.UNIQUE_ID.name()));
    details.setUserMessage(BASE_MESSAGE);
    details.setTechnicalMessage(
        StringUtils.join(new Object[]{errorMessage.getMessage(), errorMessage.getDetails()}, ". "));

    Set<IssueDetails> detailsList = new HashSet<>();
    detailsList.add(details);
    BaseExceptionResponse jsonProcessingExceptionResponse = new BaseExceptionResponse();
    jsonProcessingExceptionResponse.setIssueDetails(detailsList);

    return Response.status(response.getStatus())
        .type(response.getMediaType())
        .entity(jsonProcessingExceptionResponse)
        .build();
  }

}


