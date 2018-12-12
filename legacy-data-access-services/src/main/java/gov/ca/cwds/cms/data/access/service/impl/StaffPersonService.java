package gov.ca.cwds.cms.data.access.service.impl;

import com.google.inject.Inject;
import gov.ca.cwds.cms.data.access.dao.StaffPersonDao;
import gov.ca.cwds.cms.data.access.dto.StaffPersonEntityAwareDTO;
import gov.ca.cwds.cms.data.access.service.DataAccessServicesException;
import gov.ca.cwds.cms.data.access.service.lifecycle.DataAccessServiceLifecycle;
import gov.ca.cwds.cms.data.access.service.lifecycle.DefaultDataAccessLifeCycle;
import gov.ca.cwds.data.legacy.cms.entity.StaffPerson;
import java.io.Serializable;

/** @author CWDS CALS API Team */
public class StaffPersonService
    extends DataAccessServiceBase<StaffPersonDao, StaffPerson, StaffPersonEntityAwareDTO> {

  @Override
  public StaffPerson create(StaffPersonEntityAwareDTO entityAwareDTO)
      throws DataAccessServicesException {
    return super.create(entityAwareDTO);
  }

  @Inject
  public StaffPersonService(StaffPersonDao crudDao) {
    super(crudDao);
  }

  @Override
  public StaffPerson find(Serializable primaryKey) {
    return super.find(primaryKey);
  }

  @Override
  public DataAccessServiceLifecycle getUpdateLifeCycle() {
    return new DefaultDataAccessLifeCycle();
  }

  @Override
  public DataAccessServiceLifecycle getCreateLifeCycle() {
    return new DefaultDataAccessLifeCycle();
  }

  @Override
  public DataAccessServiceLifecycle getDeleteLifeCycle() {
    return new DefaultDataAccessLifeCycle();
  }

}
