package gov.ca.cwds.rest.validation;

import gov.ca.cwds.rest.api.domain.cms.SystemCode;
import java.util.Set;

abstract class LovValidation {

  Set<SystemCode>  systemCodes;
  public LovValidation(Set<SystemCode> systemCodes) {
    this.systemCodes = systemCodes;
  }

  public boolean isValid(Object systemCodeId){
    boolean valid = false;
    if (systemCodes != null) {
      for (SystemCode systemCode : systemCodes) {
        valid = isValidCode(systemCodeId, systemCode);
        if (valid){
          return true;
        }
      }
    }
    return valid;

  }

  protected abstract boolean isValidCode(Object systemCodeId, SystemCode systemCode );
}
