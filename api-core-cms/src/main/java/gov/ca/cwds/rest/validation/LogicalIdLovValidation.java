package gov.ca.cwds.rest.validation;

import java.util.Set;

import gov.ca.cwds.rest.api.domain.cms.SystemCode;

public class LogicalIdLovValidation extends LovValidation {

  public LogicalIdLovValidation(Set<SystemCode> systemCodes) {
    super(systemCodes);
  }

  @Override
  protected boolean isValidCode(Object logicalId, SystemCode systemCode,
      boolean checkCategoryIdValueIsZero) {
    if (!(logicalId instanceof String)) {
      return false;
    }

    if (logicalId.equals(systemCode.getLogicalId())) {
      return "N".equalsIgnoreCase(systemCode.getInactiveIndicator());
    }

    return false;
  }

}
