package gov.ca.cwds.cms.data.access.service;

import gov.ca.cwds.cms.data.access.dto.BaseEntityAwareDTO;
import gov.ca.cwds.data.persistence.PersistentObject;

/**
 * @author CWDS CALS API Team
 */

public interface DataAccessService<T extends PersistentObject, P extends BaseEntityAwareDTO<T>> {

  T create(P entityAwareDTO);

  default void runBusinessValidation(T entity) {
    //Empty by default
  }

}
