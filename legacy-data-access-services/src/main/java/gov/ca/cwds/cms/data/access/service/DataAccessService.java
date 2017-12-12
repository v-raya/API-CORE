package gov.ca.cwds.cms.data.access.service;

import gov.ca.cwds.cms.data.access.dto.BaseEntityAwareDTO;
import gov.ca.cwds.data.persistence.PersistentObject;
import gov.ca.cwds.drools.DroolsException;

/**
 * @author CWDS CALS API Team
 */

public interface DataAccessService<T extends PersistentObject, P extends BaseEntityAwareDTO<T>> {

  T create(P entityAwareDTO) throws DataAccessServicesException;

  default void runBusinessValidation(P entityAwareDTO) throws DroolsException {
    //Empty by default
  }

}
