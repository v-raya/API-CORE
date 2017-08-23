package gov.ca.cwds.rest.validation;

import gov.ca.cwds.rest.api.domain.cms.SystemCode;
import java.util.Set;

public class SystemCodeIdLovLValidation extends LovValidation {

  public SystemCodeIdLovLValidation(Set<SystemCode> systemCodes) {
    super(systemCodes);
  }

  @Override
  protected boolean isValidCode(Object systemCodeId, SystemCode systemCode,
      boolean checkCategoryIdValueIsZero) {
    if (!(systemCodeId instanceof Number)) {
      return false;
    }
    if (systemCodeId.equals(systemCode.getSystemId())) {
      if (checkCategoryIdValueIsZero && systemCode.getCategoryId() != 0) {
        return false;
      }
      return "N".equalsIgnoreCase(systemCode.getInactiveIndicator());
    }
    return false;
  }
}
