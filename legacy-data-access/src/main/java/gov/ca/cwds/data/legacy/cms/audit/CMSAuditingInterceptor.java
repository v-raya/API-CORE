package gov.ca.cwds.data.legacy.cms.audit;

import java.io.Serializable;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import gov.ca.cwds.data.legacy.cms.CmsPersistentObjectVersioned;
import gov.ca.cwds.security.realm.PerrySubject;
import org.hibernate.EmptyInterceptor;
import org.hibernate.type.Type;

//hibernate.session_factory.interceptor
public class CMSAuditingInterceptor extends EmptyInterceptor {
  @Override
  public boolean onSave(
    Object entity,
    Serializable id,
    Object[] state,
    String[] propertyNames,
    Type[] types) {
    if (entity instanceof CmsPersistentObjectVersioned) {
      CmsPersistentObjectVersioned cmsPersistentObject = (CmsPersistentObjectVersioned) entity;
      cmsPersistentObject.setLastUpdateTime(Timestamp.valueOf(LocalDateTime.now()));
      String staffId = PerrySubject.getPerryAccount().getStaffId();
      cmsPersistentObject.setLastUpdateId(staffId);
      return true;
    }
    return false;
  }

}
