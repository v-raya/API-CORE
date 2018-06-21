package gov.ca.cwds.cms.data.access.service;

import gov.ca.cwds.cms.data.access.dto.BaseEntityAwareDTO;
import gov.ca.cwds.cms.data.access.service.impl.IdGenerator;
import gov.ca.cwds.cms.data.access.service.lifecycle.DataAccessServiceLifecycle;
import gov.ca.cwds.cms.data.access.service.lifecycle.DefaultDataAccessLifeCycle;
import gov.ca.cwds.data.CrudsDao;
import gov.ca.cwds.data.legacy.cms.CmsPersistentObject;
import gov.ca.cwds.security.utils.PrincipalUtils;
import java.time.LocalDateTime;
import java.util.function.Consumer;

public abstract class DefaultCmsDataAccessService
  <E extends CrudsDao<T>, T extends CmsPersistentObject, P extends BaseEntityAwareDTO<T>>
  extends DataAccessServiceBase<E, T, P> {

  protected DefaultCmsDataAccessService(E crudDao) {
    super(crudDao);
  }

  /**
   * Creates new db record, runs generic entity creation lifecycle.
   *
   * @param entityAwareDTO entity holder
   * @return newly created entity
   * @throws DataAccessServicesException if something went wrong
   */
  public T create(P entityAwareDTO) throws DataAccessServicesException {
    T entity = entityAwareDTO.getEntity();
    idSetter(entity).accept(IdGenerator.generateId());
    audit(entity);
    return super.create(entityAwareDTO);
  }

  /**
   * Updates entity, runs generic 'update' lifecycle.
   *
   * @param entityAwareDTO entity holder
   * @return updated entity
   * @throws DataAccessServicesException if something went wrong
   */
  public T update(P entityAwareDTO) throws DataAccessServicesException {
    audit(entityAwareDTO.getEntity());
    return super.update(entityAwareDTO);
  }

  @Override
  protected DataAccessServiceLifecycle<P> getUpdateLifeCycle() {
    return new DefaultDataAccessLifeCycle<>();
  }

  @Override
  protected DataAccessServiceLifecycle<P> getCreateLifeCycle() {
    return new DefaultDataAccessLifeCycle<>();
  }

  @Override
  protected DataAccessServiceLifecycle<P> getDeleteLifeCycle() {
    return new DefaultDataAccessLifeCycle<>();
  }

  protected abstract Consumer<String> idSetter(T entity);

  private void audit(CmsPersistentObject entity) {
    entity.setLastUpdateTime(LocalDateTime.now());
    entity.setLastUpdateId(PrincipalUtils.getStaffPersonId());
  }
}
