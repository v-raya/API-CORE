package gov.ca.cwds.rest.exception;

import java.util.HashSet;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.ElementKind;
import javax.validation.Path;

/**
 * @author CWDS API Team
 */
public class IssueDetailsCreator {

  public static Set<IssueDetails> create(ConstraintViolationException exception,
      String incidentId) {
    Set<IssueDetails> issueDetailsSet = new HashSet<>();
    Set<ConstraintViolation<?>> constraintViolations = exception.getConstraintViolations();

    for (ConstraintViolation<?> violation : constraintViolations) {
      IssueDetails issueDetails = create(violation, incidentId);
      issueDetailsSet.add(issueDetails);
    }
    return issueDetailsSet;
  }

  public static IssueDetails create(ConstraintViolation<?> violation, String incidentId) {
    Path propertyPath = violation.getPropertyPath();
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
    Object invalidValue = violation.getInvalidValue();
    String message = violation.getMessage();
    IssueDetails issueDetails = new IssueDetails();
    issueDetails.setUserMessage(message);
    issueDetails.setType(IssueType.CONSTRAINT_VALIDATION);
    issueDetails.setProperty(property);
    issueDetails.setInvalidValue(invalidValue);
    issueDetails.setIncidentId(incidentId);
    return issueDetails;
  }
}
