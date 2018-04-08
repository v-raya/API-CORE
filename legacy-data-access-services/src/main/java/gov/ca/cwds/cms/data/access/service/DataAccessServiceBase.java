package gov.ca.cwds.cms.data.access.service;

import gov.ca.cwds.cms.data.access.dto.BaseEntityAwareDTO;
import gov.ca.cwds.cms.data.access.service.lifecycle.DataAccessBundle;
import gov.ca.cwds.cms.data.access.service.lifecycle.DataAccessServiceLifecycle;
import gov.ca.cwds.cms.data.access.service.lifecycle.DefaultDataAccessLifeCycle;
import gov.ca.cwds.data.CrudsDao;
import gov.ca.cwds.data.persistence.PersistentObject;
import gov.ca.cwds.drools.DroolsException;
import gov.ca.cwds.security.realm.PerryAccount;
import gov.ca.cwds.security.utils.PrincipalUtils;
import java.io.Serializable;

/** @author CWDS TPT-3 Team */
public abstract class DataAccessServiceBase<
    E extends CrudsDao<T>, T extends PersistentObject, P extends BaseEntityAwareDTO<T>>
    implements DataAccessService<T, P> {

  protected final E crudDao;

  private DataAccessServiceLifecycle<P> updateLifecycle;
  private DataAccessServiceLifecycle<P> createLifecycle;
  private DataAccessServiceLifecycle<P> deleteLifecycle;

  protected DataAccessServiceBase(E crudDao) {
    this.crudDao = crudDao;
  }

  @Override
  public T find(Serializable primaryKey) {
    return crudDao.find(primaryKey);
  }

  @Override
  public T create(P entityAwareDTO) throws DataAccessServicesException {
    try {

      if (getCreateLifeCycle() == null) {
        createLifecycle = new DefaultDataAccessLifeCycle<>();
      } else {
        createLifecycle = getCreateLifeCycle();
      }

      DataAccessBundle<P> dataAccessBundle = new DataAccessBundle<>(entityAwareDTO);
      createLifecycle.beforeDataProcessing(dataAccessBundle);

      PerryAccount perryAccount = PrincipalUtils.getPrincipal();

      createLifecycle.dataProcessing(dataAccessBundle, perryAccount);
      createLifecycle.afterDataProcessing(dataAccessBundle);
      createLifecycle.beforeBusinessValidation(dataAccessBundle);
      createLifecycle.businessValidation(dataAccessBundle, perryAccount);
      createLifecycle.afterBusinessValidation(dataAccessBundle);
      T t = crudDao.create(entityAwareDTO.getEntity());
      createLifecycle.afterStore(dataAccessBundle);
      return t;
    } catch (DroolsException e) {
      throw new DataAccessServicesException(e);
    }
  }

  @Override
  public T update(P entityAwareDTO) throws DataAccessServicesException, DroolsException {
    try {

      if (getUpdateLifeCycle() == null) {
        updateLifecycle = new DefaultDataAccessLifeCycle<>();
      } else {
        updateLifecycle = getUpdateLifeCycle();
      }

      DataAccessBundle<P> dataAccessBundle = new DataAccessBundle<>(entityAwareDTO);
      updateLifecycle.beforeDataProcessing(dataAccessBundle);

      PerryAccount perryAccount = PrincipalUtils.getPrincipal();

      updateLifecycle.dataProcessing(dataAccessBundle, perryAccount);
      updateLifecycle.afterDataProcessing(dataAccessBundle);
      updateLifecycle.beforeBusinessValidation(dataAccessBundle);
      updateLifecycle.businessValidation(dataAccessBundle, perryAccount);
      updateLifecycle.afterBusinessValidation(dataAccessBundle);
      T t = crudDao.update(entityAwareDTO.getEntity());
      updateLifecycle.afterStore(dataAccessBundle);
      return t;
    } catch (DroolsException e) {
      throw new DataAccessServicesException(e);
    }
  }

  @Override
  public final T delete(Serializable primaryKey) {
    if (getDeleteLifeCycle() == null) {
      deleteLifecycle = new DefaultDataAccessLifeCycle<>();
    } else {
      deleteLifecycle = getDeleteLifeCycle();
    }

    return crudDao.delete(primaryKey);
  }

  protected abstract DataAccessServiceLifecycle<P> getUpdateLifeCycle();
  protected abstract DataAccessServiceLifecycle<P> getCreateLifeCycle();
  protected abstract DataAccessServiceLifecycle<P> getDeleteLifeCycle();
}
