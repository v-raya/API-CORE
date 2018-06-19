package gov.ca.cwds.cms.data.access.service;

import java.time.LocalDateTime;
import gov.ca.cwds.cms.data.access.dto.BaseEntityAwareDTO;
import gov.ca.cwds.cms.data.access.service.impl.IdGenerator;
import gov.ca.cwds.cms.data.access.service.lifecycle.DataAccessBundle;
import gov.ca.cwds.cms.data.access.service.lifecycle.DataAccessServiceLifecycle;
import gov.ca.cwds.cms.data.access.service.lifecycle.DefaultDataAccessLifeCycle;
import gov.ca.cwds.data.CrudsDao;
import gov.ca.cwds.data.legacy.cms.CmsPersistentObject;
import gov.ca.cwds.security.utils.PrincipalUtils;

public abstract class DefaultCmsDataAccessService<E extends CrudsDao<T>, T extends CmsPersistentObject, P extends BaseEntityAwareDTO<T>> extends DataAccessServiceBase<E, T, P> {
  protected DefaultCmsDataAccessService(E crudDao) {
    super(crudDao);
  }

  @Override
  protected DataAccessServiceLifecycle<P> getUpdateLifeCycle() {
    return new DefaultDataAccessLifeCycle<P>() {
      @Override
      public void afterBusinessValidation(DataAccessBundle<P> bundle) {
        CmsPersistentObject entity = bundle.getAwareDto().getEntity();
        entity.setLastUpdateTime(LocalDateTime.now());
        entity.setLastUpdateId(PrincipalUtils.getStaffPersonId());
      }
    };
  }

  @Override
  protected DataAccessServiceLifecycle<P> getCreateLifeCycle() {
    return new DefaultDataAccessLifeCycle<P>() {
      @Override
      public void afterBusinessValidation(DataAccessBundle<P> bundle) {
        T entity = bundle.getAwareDto().getEntity();
        entity.setLastUpdateTime(LocalDateTime.now());
        entity.setLastUpdateId(PrincipalUtils.getStaffPersonId());
        setId(entity, IdGenerator.generateId());
      }
    };
  }

  @Override
  protected DataAccessServiceLifecycle<P> getDeleteLifeCycle() {
    return new DefaultDataAccessLifeCycle<>();
  }

  protected abstract void setId(T entity, String id);
}
