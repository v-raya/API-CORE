package gov.ca.cwds.rest.exception;

import java.util.Set;

/**
 * @author CWDS CALS API Team
 */

public class BusinessValidationException extends RuntimeException {

  private static final long serialVersionUID = 1L;

  private final Set<IssueDetails> validationDetailsList;

  public BusinessValidationException(Set<IssueDetails> validationDetailsList) {
    this.validationDetailsList = validationDetailsList;
  }

  public Set<IssueDetails> getValidationDetailsList() {
    return validationDetailsList;
  }

}
