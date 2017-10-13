package gov.ca.cwds.rest.exception;

import java.util.Set;

import gov.ca.cwds.rest.api.ApiException;

/**
 * @author CWDS CALS API Team
 */
public class BusinessValidationException extends ApiException {

  private static final long serialVersionUID = 1L;

  private final Set<IssueDetails> validationDetailsList;

  public BusinessValidationException(Set<IssueDetails> validationDetailsList) {
    this.validationDetailsList = validationDetailsList;
  }

  public Set<IssueDetails> getValidationDetailsList() {
    return validationDetailsList;
  }

}
