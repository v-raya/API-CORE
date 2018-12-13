package gov.ca.cwds.cms.data.access.service.impl;

import gov.ca.cwds.cms.data.access.dto.BaseEntityAwareDTO;
import gov.ca.cwds.cms.data.access.service.DataAccessService;
import gov.ca.cwds.cms.data.access.service.DataAccessServicesException;
import gov.ca.cwds.cms.data.access.service.lifecycle.DataAccessBundle;
import gov.ca.cwds.cms.data.access.service.lifecycle.DataAccessServiceLifecycle;
import gov.ca.cwds.cms.data.access.service.lifecycle.DefaultDataAccessLifeCycle;
import gov.ca.cwds.data.CrudsDao;
import gov.ca.cwds.data.persistence.PersistentObject;
import gov.ca.cwds.security.realm.PerryAccount;
import gov.ca.cwds.security.utils.PrincipalUtils;
import java.io.Serializable;

/**
 * Abstract implementation for DataAccessServiceBase.
 *
 * @param <E> DAO type
 * @param <T> persistence type
 * @param <P> DTO type
 * @author CWDS TPT-3 Team
 */
public abstract class DataAccessServiceBase<E extends CrudsDao<T>, T extends PersistentObject, P extends BaseEntityAwareDTO<T>>
  implements DataAccessService<T, P> {

  private final E xaCrudDao;
  private final E nonXaCrudDao;

  private DataAccessServiceLifecycle<P> updateLifecycle;
  private DataAccessServiceLifecycle<P> createLifecycle;

  protected DataAccessServiceBase(E xaCrudDao) {
    this.xaCrudDao = xaCrudDao;
    this.nonXaCrudDao = xaCrudDao;
  }

  protected DataAccessServiceBase(E xaCrudDao, E nonXaCrudDao) {
    this.xaCrudDao = xaCrudDao;
    this.nonXaCrudDao = nonXaCrudDao;
  }

  @Override
  public T find(Serializable primaryKey) {
    return find(primaryKey, false);
  }

  @Override
  public T xaFind(Serializable primaryKey) {
    return find(primaryKey, true);
  }

  T find(Serializable primaryKey, boolean isXaTransaction) {
    return getCrudDao(isXaTransaction).find(primaryKey);
  }

  @Override
  public T create(P entityAwareDto) throws DataAccessServicesException {
    return create(entityAwareDto, false);
  }

  @Override
  public T xaCreate(P entityAwareDto) throws DataAccessServicesException {
    return create(entityAwareDto, true);
  }

  T create(P entityAwareDTO, boolean isXaTransaction) throws DataAccessServicesException {
    if (getCreateLifeCycle() == null) {
      createLifecycle = new DefaultDataAccessLifeCycle<>();
    } else {
      createLifecycle = getCreateLifeCycle();
    }

    final DataAccessBundle<P> dataAccessBundle = new DataAccessBundle<>(entityAwareDTO);
    doEnrichDataAccessBundle(dataAccessBundle);
    createLifecycle.beforeDataProcessing(dataAccessBundle);

    final PerryAccount perryAccount = PrincipalUtils.getPrincipal();
    createLifecycle.dataProcessing(dataAccessBundle, perryAccount);
    createLifecycle.afterDataProcessing(dataAccessBundle);
    createLifecycle.beforeBusinessValidation(dataAccessBundle);
    createLifecycle.businessValidation(dataAccessBundle, perryAccount);
    createLifecycle.afterBusinessValidation(dataAccessBundle);

    final T t = getCrudDao(isXaTransaction).create(entityAwareDTO.getEntity());
    createLifecycle.afterStore(dataAccessBundle);
    return t;
  }

  @Override
  public T update(P entityAwareDto) throws DataAccessServicesException {
    return update(entityAwareDto, false);
  }

  @Override
  public T xaUpdate(P entityAwareDto) throws DataAccessServicesException {
    return update(entityAwareDto, true);
  }

  T update(P entityAwareDTO, boolean isXaTransaction) throws DataAccessServicesException {
    if (getUpdateLifeCycle() == null) {
      updateLifecycle = new DefaultDataAccessLifeCycle<>();
    } else {
      updateLifecycle = getUpdateLifeCycle();
    }

    final DataAccessBundle<P> dataAccessBundle = new DataAccessBundle<>(entityAwareDTO);
    doEnrichDataAccessBundle(dataAccessBundle);
    updateLifecycle.beforeDataProcessing(dataAccessBundle);

    final PerryAccount perryAccount = PrincipalUtils.getPrincipal();
    updateLifecycle.dataProcessing(dataAccessBundle, perryAccount);
    updateLifecycle.afterDataProcessing(dataAccessBundle);
    updateLifecycle.beforeBusinessValidation(dataAccessBundle);
    updateLifecycle.businessValidation(dataAccessBundle, perryAccount);
    updateLifecycle.afterBusinessValidation(dataAccessBundle);

    final T t = getCrudDao(isXaTransaction).update(entityAwareDTO.getEntity());
    updateLifecycle.afterStore(dataAccessBundle);
    return t;
  }

  @Override
  public final T delete(Serializable primaryKey) {
    return delete(primaryKey, false);
  }

  @Override
  public final T xaDelete(Serializable primaryKey) {
    return delete(primaryKey, true);
  }

  final T delete(Serializable primaryKey, boolean isXaTransaction) {
    return getCrudDao(isXaTransaction).delete(primaryKey);
  }

  protected void doEnrichDataAccessBundle(final DataAccessBundle<P> dataAccessBundle) {
    // Do nothing just a stub for subclasses
  }

  protected abstract DataAccessServiceLifecycle<P> getUpdateLifeCycle();

  protected abstract DataAccessServiceLifecycle<P> getCreateLifeCycle();

  protected abstract DataAccessServiceLifecycle<P> getDeleteLifeCycle();

  protected E getCrudDao(boolean isXaTransaction) {
    return isXaTransaction ? xaCrudDao : nonXaCrudDao;
  }

}
