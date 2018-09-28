package gov.ca.cwds.rest.validation;

import java.util.Set;

import gov.ca.cwds.rest.api.domain.cms.SystemCode;

abstract class LovValidation {

  Set<SystemCode> systemCodes;

  public LovValidation(Set<SystemCode> systemCodes) {
    this.systemCodes = systemCodes;
  }

  public boolean isValid(Object systemCodeId) {
    return isValid(systemCodeId, false);
  }

  public boolean isValid(Object systemCodeId, boolean checkCategoryIdValueIsZero) {
    boolean valid = false;
    if (systemCodes != null) {
      for (SystemCode systemCode : systemCodes) {
        valid = isValidCode(systemCodeId, systemCode, checkCategoryIdValueIsZero);
        if (valid) {
          break;
        }
      }
    }

    return valid; // single return point.
  }

  protected abstract boolean isValidCode(Object systemCodeId, SystemCode systemCode,
      boolean checkCategoryIdValueIsZero);

}
