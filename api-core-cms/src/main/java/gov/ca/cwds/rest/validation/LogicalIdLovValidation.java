package gov.ca.cwds.rest.validation;

import gov.ca.cwds.rest.api.domain.cms.SystemCode;
import java.util.Set;

public class LogicalIdLovValidation extends LovValidation {

  public LogicalIdLovValidation(Set<SystemCode> systemCodes) {
    super(systemCodes);
  }

  @Override
  protected boolean isValidCode(Object logicalId, SystemCode systemCode) {
    if (!(logicalId instanceof String)){
      return false;
    }

    if (logicalId.equals(systemCode.getLogicalId())) {
      return "N".equalsIgnoreCase(systemCode.getInactiveIndicator());
    }
    return false;
  }
}
