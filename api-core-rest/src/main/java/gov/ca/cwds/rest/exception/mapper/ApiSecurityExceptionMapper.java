package gov.ca.cwds.rest.exception.mapper;

import java.util.HashSet;
import java.util.Set;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.commons.text.StringEscapeUtils;
import org.apache.shiro.authz.AuthorizationException;
import org.secnod.shiro.jaxrs.ShiroExceptionMapper;

import gov.ca.cwds.rest.exception.BaseExceptionResponse;
import gov.ca.cwds.rest.exception.IssueDetails;
import gov.ca.cwds.rest.exception.IssueType;

/**
 * @author CWDS API Team
 */
public class ApiSecurityExceptionMapper extends ShiroExceptionMapper {

  @Override
  public Response toResponse(AuthorizationException exception) {
    int status = super.toResponse(exception).getStatus();

    IssueDetails issueDetails = new IssueDetails();

    issueDetails.setType(IssueType.SECURITY_EXCEPTION);
    issueDetails.setUserMessage(exception.getMessage());

    if (exception.getCause() != null) {
      issueDetails.setTechnicalMessage(exception.getCause().getMessage());
      issueDetails.setCauseStackTrace(
          StringEscapeUtils.escapeJson(ExceptionUtils.getStackTrace(exception.getCause())));
    }

    String stackTrace = ExceptionUtils.getStackTrace(exception);
    issueDetails.setStackTrace(StringEscapeUtils.escapeJson(stackTrace));

    Set<IssueDetails> detailsList = new HashSet<>();
    detailsList.add(issueDetails);
    BaseExceptionResponse response = new BaseExceptionResponse();
    response.setIssueDetails(detailsList);

    return Response.status(status).entity(response).type(MediaType.APPLICATION_JSON).build();
  }
}
