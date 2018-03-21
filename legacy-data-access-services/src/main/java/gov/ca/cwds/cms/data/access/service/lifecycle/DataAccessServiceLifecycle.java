package gov.ca.cwds.cms.data.access.service.lifecycle;

import gov.ca.cwds.cms.data.access.dto.BaseEntityAwareDTO;
import gov.ca.cwds.cms.data.access.service.DataAccessServicesException;
import gov.ca.cwds.drools.DroolsException;
import gov.ca.cwds.security.realm.PerryAccount;

public interface DataAccessServiceLifecycle<F extends BaseEntityAwareDTO> {
  void beforeDataProcessing(DataAccessBundle<F> bundle);

  void afterDataProcessing(DataAccessBundle<F> bundle);

  void beforeBusinessValidation(DataAccessBundle<F> bundle);

  void afterBusinessValidation(DataAccessBundle<F> bundle);

  void dataProcessing(DataAccessBundle<F> bundle, PerryAccount perryAccount) throws DroolsException;

  void businessValidation(DataAccessBundle<F> bundle, PerryAccount perryAccount)
      throws DroolsException;

  void afterStore(DataAccessBundle<F> bundle) throws DataAccessServicesException;
}