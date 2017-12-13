package gov.ca.cwds.cms.data.access.dto;

import gov.ca.cwds.data.persistence.PersistentObject;
import gov.ca.cwds.security.realm.PerryAccount;

/**
 * @author CWDS CALS API Team
 */

public class BaseEntityAwareDTO<T extends PersistentObject> {

  private T entity;

  private PerryAccount perryAccount;

  public BaseEntityAwareDTO(PerryAccount perryAccount) {
    this.perryAccount = perryAccount;
  }

  public String getStaffPersonId() {
    return perryAccount.getStaffId();
  }

  public T getEntity() {
    return entity;
  }

  public void setEntity(T entity) {
    this.entity = entity;
  }

  public PerryAccount getPerryAccount() {
    return perryAccount;
  }
}
