package gov.ca.cwds.cms.data.access.service;

import gov.ca.cwds.cms.data.access.dto.BaseEntityAwareDTO;
import gov.ca.cwds.data.persistence.PersistentObject;
import gov.ca.cwds.drools.DroolsException;
import gov.ca.cwds.security.realm.PerryAccount;
import java.io.Serializable;

/** @author CWDS CALS API Team */
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

  interface DataAccessServiceLifecycle<F extends BaseEntityAwareDTO> {
    default void beforeDataProcessing(DataAccessBundle<F> bundle) {};

    default void afterDataProcessing(DataAccessBundle<F> bundle) {};

    default void beforeBusinessValidation(DataAccessBundle<F> bundle) {};

    default void afterBusinessValidation(DataAccessBundle<F> bundle) {};

    default void dataProcessing(DataAccessBundle<F> bundle, PerryAccount perryAccount)
        throws DroolsException {};

    default void businessValidation(DataAccessBundle<F> bundle, PerryAccount perryAccount)
        throws DroolsException {};

    default void afterCreate(DataAccessBundle<F> bundle) throws DataAccessServicesException {}

    default void afterUpdate(DataAccessBundle<F> bundle) {}
  }

  final class DataAccessBundle<P extends BaseEntityAwareDTO> {
    private P awareDto;

    public DataAccessBundle(P awareDto) {
      this.awareDto = awareDto;
    }

    public P getAwareDto() {
      return awareDto;
    }

    public void setAwareDto(P awareDto) {
      this.awareDto = awareDto;
    }
  }
}
