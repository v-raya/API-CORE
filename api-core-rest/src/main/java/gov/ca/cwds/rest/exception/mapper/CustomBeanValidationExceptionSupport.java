package gov.ca.cwds.rest.exception.mapper;

import gov.ca.cwds.rest.exception.IssueDetails;
import gov.ca.cwds.utils.JsonUtils;
import java.io.IOException;
import javax.validation.ConstraintViolation;
import org.apache.commons.lang3.StringUtils;
import org.glassfish.jersey.server.model.Invocable;

/**
 * @author CWDS CALS API Team
 */
public interface CustomBeanValidationExceptionSupport {

  /**
   * hibernate validation framework updates user message with prefix that should be removed.
   *
   * @param data constraint violation message
   * @return validation details in case of business validation message or null in case of constraint
   * validation message
   * @see {@link CustomConstraintMessage#calculateMessage(ConstraintViolation, Invocable)}
   */
  default IssueDetails unmarshallData(String data) {
    String marshalledDetails = StringUtils.removeStart(data, "The request body");
    IssueDetails details = null;
    try {
      details = (IssueDetails) JsonUtils.from(marshalledDetails, IssueDetails.class);
    } catch (IOException e) {
      throw new IllegalStateException(e);
    }
    return details;
  }

}
