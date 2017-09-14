package gov.ca.cwds.rest.exception.mapper;

import static gov.ca.cwds.rest.exception.IssueDetails.BASE_MESSAGE;

import gov.ca.cwds.logging.LoggingContext;
import gov.ca.cwds.rest.exception.BaseExceptionResponse;
import gov.ca.cwds.rest.exception.IssueDetails;
import gov.ca.cwds.rest.exception.IssueType;
import java.util.HashSet;
import java.util.Set;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.commons.text.StringEscapeUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author CWDS CALS API Team
 */

public class UnexpectedExceptionMapperImpl implements ExceptionMapper<Exception> {

  private static final Logger LOGGER = LoggerFactory
      .getLogger(UnexpectedExceptionMapperImpl.class);

  private final LoggingContext loggingContext;

  public UnexpectedExceptionMapperImpl(LoggingContext loggingContext) {
    this.loggingContext = loggingContext;
  }

  @Override
  public Response toResponse(Exception ex) {
    LOGGER.error("EXCEPTION MAPPER: {}", ex.getMessage(), ex);
    IssueDetails details = new IssueDetails();

    details.setType(IssueType.UNEXPECTED_EXCEPTION);
    details.setIncidentId(loggingContext.getUniqueId());
    details.setUserMessage(BASE_MESSAGE);
    details.setTechnicalMessage(ex.getMessage());

    if (ex.getCause() != null) {
      details.setTechnicalMessage(ex.getCause().getMessage());
      details.setCauseStackTrace(
          StringEscapeUtils.escapeJson(ExceptionUtils.getStackTrace(ex.getCause())));
    }
    String stackTrace = ExceptionUtils.getStackTrace(ex);
    details.setStackTrace(StringEscapeUtils.escapeJson(stackTrace));

    Set<IssueDetails> detailsList = new HashSet<>();
    detailsList.add(details);
    BaseExceptionResponse unexpectedException = new BaseExceptionResponse();
    unexpectedException.setIssueDetails(detailsList);

    return Response.status(500).entity(unexpectedException).type(MediaType.APPLICATION_JSON)
        .build();
  }

}
