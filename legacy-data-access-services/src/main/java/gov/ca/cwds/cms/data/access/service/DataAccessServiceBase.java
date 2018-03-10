package gov.ca.cwds.cms.data.access.service;

import gov.ca.cwds.cms.data.access.dto.BaseEntityAwareDTO;
import gov.ca.cwds.cms.data.access.service.DataAccessService.DataAccessServiceLifecycle;
import gov.ca.cwds.data.CrudsDao;
import gov.ca.cwds.data.persistence.PersistentObject;
import gov.ca.cwds.drools.DroolsException;
import gov.ca.cwds.security.utils.PrincipalUtils;
import java.io.Serializable;

/** @author CWDS TPT-3 Team */
public abstract class DataAccessServiceBase<
        E extends CrudsDao, T extends PersistentObject, P extends BaseEntityAwareDTO<T>>
    implements DataAccessService<T, P>, DataAccessServiceLifecycle {

  private final E crudDao;

  protected DataAccessServiceBase(E crudDao) {
    this.crudDao = crudDao;
  }

  @Override
  public T find(Serializable primaryKey) {
    return (T) crudDao.find(primaryKey);
  }

  @Override
  public final T create(P entityAwareDTO) throws DataAccessServicesException {
    try {
      DataAccessBundle<P> dataAccessBundle = new DataAccessBundle<>(entityAwareDTO);
      beforeDataProcessing(dataAccessBundle);
      dataProcessing(dataAccessBundle, PrincipalUtils.getPrincipal());
      beforeBusinessValidation(dataAccessBundle);
      businessValidation(dataAccessBundle, PrincipalUtils.getPrincipal());
      afterBusinessValidation(dataAccessBundle);
      T t = (T) crudDao.create(entityAwareDTO.getEntity());
      afterCreate(dataAccessBundle);
      return t;
    } catch (DroolsException e) {
      throw new DataAccessServicesException(e);
    }
  }

  @Override
  public final T update(P entityAwareDTO) throws DataAccessServicesException, DroolsException {
    return (T) crudDao.update(entityAwareDTO.getEntity());
  }

  @Override
  public final T delete(Serializable primaryKey) {
    return (T) crudDao.delete(primaryKey);
  }
}
