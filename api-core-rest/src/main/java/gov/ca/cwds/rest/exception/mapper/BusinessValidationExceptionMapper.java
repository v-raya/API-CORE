package gov.ca.cwds.rest.exception.mapper;

import java.util.Set;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;

import gov.ca.cwds.rest.exception.BaseExceptionResponse;
import gov.ca.cwds.rest.exception.BusinessValidationException;
import gov.ca.cwds.rest.exception.IssueDetails;
import gov.ca.cwds.rest.exception.IssueType;

/**
 * @author CWDS CALS API Team
 */

public class BusinessValidationExceptionMapper
    implements ExceptionMapper<BusinessValidationException> {

  @Override
  public Response toResponse(BusinessValidationException exception) {
    Set<IssueDetails> detailsList = exception.getValidationDetailsList();
    for (IssueDetails details : detailsList) {
      if (details.getType() == null) {
        details.setType(IssueType.BUSINESS_VALIDATION);
      }
    }
    BaseExceptionResponse manualValidationResponse = new BaseExceptionResponse();
    manualValidationResponse.setIssueDetails(detailsList);

    return Response.status(422).type(MediaType.APPLICATION_JSON).entity(manualValidationResponse)
        .build();
  }
}
