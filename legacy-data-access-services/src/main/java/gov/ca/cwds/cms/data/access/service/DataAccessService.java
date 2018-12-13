package gov.ca.cwds.cms.data.access.service;

import gov.ca.cwds.cms.data.access.dto.BaseEntityAwareDTO;
import gov.ca.cwds.data.persistence.PersistentObject;
import java.io.Serializable;

/**
 * @author CWDS CALS API Team
 */
public interface DataAccessService<T extends PersistentObject, P extends BaseEntityAwareDTO<T>> {

  default T find(Serializable primaryKey) {
    throw new UnsupportedOperationException();
  }

  default T xaFind(Serializable primaryKey) {
    throw new UnsupportedOperationException();
  }

  default T create(P entityAwareDto) throws DataAccessServicesException {
    throw new UnsupportedOperationException();
  }

  default T xaCreate(P entityAwareDto) throws DataAccessServicesException {
    throw new UnsupportedOperationException();
  }

  default T update(P entityAwareDto) throws DataAccessServicesException {
    throw new UnsupportedOperationException();
  }

  default T xaUpdate(P entityAwareDto) throws DataAccessServicesException {
    throw new UnsupportedOperationException();
  }

  default T delete(Serializable primaryKey) {
    throw new UnsupportedOperationException();
  }

  default T xaDelete(Serializable primaryKey) {
    throw new UnsupportedOperationException();
  }

}
