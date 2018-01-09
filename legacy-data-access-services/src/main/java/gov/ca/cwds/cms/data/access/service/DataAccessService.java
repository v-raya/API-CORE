package gov.ca.cwds.cms.data.access.service;

import gov.ca.cwds.cms.data.access.dto.BaseEntityAwareDTO;
import gov.ca.cwds.data.persistence.PersistentObject;
import gov.ca.cwds.drools.DroolsException;
import gov.ca.cwds.security.realm.PerryAccount;

/**
 * @author CWDS CALS API Team
 */

public interface DataAccessService<T extends PersistentObject, P extends BaseEntityAwareDTO<T>> {

  T create(P entityAwareDTO) throws DataAccessServicesException;

  T update(P entityAwareDTO) throws DataAccessServicesException;

  default void runBusinessValidation(P entityAwareDTO, PerryAccount principal) throws DroolsException {
    //Empty by default
  }

  default void runDataProcessing(P entityAwareDTO, PerryAccount principal) throws DroolsException  {
    //Empty by default
  }

}
