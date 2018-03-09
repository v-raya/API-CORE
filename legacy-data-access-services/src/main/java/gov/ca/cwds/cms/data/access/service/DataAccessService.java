package gov.ca.cwds.cms.data.access.service;

import gov.ca.cwds.cms.data.access.dto.BaseEntityAwareDTO;
import gov.ca.cwds.data.persistence.PersistentObject;
import gov.ca.cwds.drools.DroolsException;
import java.io.Serializable;

/**
 * @author CWDS CALS API Team
 */

public interface DataAccessService<T extends PersistentObject, P extends BaseEntityAwareDTO<T>> {

  default T find(Serializable primaryKey) {
    throw new UnsupportedOperationException();
  }

  default T create(P entityAwareDTO) throws DataAccessServicesException {
    throw new UnsupportedOperationException();
  };

  default T update(P entityAwareDTO) throws DataAccessServicesException, DroolsException {
    throw new UnsupportedOperationException();
  };

  default T delete(Serializable primaryKey) {
    throw new UnsupportedOperationException();
  }
}
