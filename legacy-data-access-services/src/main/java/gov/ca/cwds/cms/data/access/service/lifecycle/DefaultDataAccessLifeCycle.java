package gov.ca.cwds.cms.data.access.service.lifecycle;

import gov.ca.cwds.cms.data.access.dto.BaseEntityAwareDTO;
import gov.ca.cwds.cms.data.access.service.DataAccessServicesException;
import gov.ca.cwds.data.persistence.PersistentObject;
import gov.ca.cwds.security.realm.PerryAccount;

/**
 * @author CWDS TPT-3 Team
 */
public class DefaultDataAccessLifeCycle<T extends BaseEntityAwareDTO<? extends PersistentObject>>
    implements DataAccessServiceLifecycle<T> {

  @Override
  public void beforeDataProcessing(DataAccessBundle<T> bundle) throws DataAccessServicesException {
    // Do nothing just a stub
  }

  @Override
  public void afterDataProcessing(DataAccessBundle<T> bundle) {
    // Do nothing just a stub
  }

  @Override
  public void beforeBusinessValidation(DataAccessBundle<T> bundle) {
    // Do nothing just a stub
  }

  @Override
  public void afterBusinessValidation(DataAccessBundle<T> bundle) {
    // Do nothing just a stub
  }

  @Override
  public void dataProcessing(DataAccessBundle<T> bundle, PerryAccount perryAccount) {
    // Do nothing just a stub
  }

  @Override
  public void businessValidation(DataAccessBundle<T> bundle, PerryAccount perryAccount) {
    // Do nothing
  }

  @Override
  public void afterStore(DataAccessBundle<T> bundle) throws DataAccessServicesException {
    // Do nothing just a stub
  }

}
