package gov.ca.cwds.cms.data.access.dto;

import gov.ca.cwds.data.persistence.PersistentObject;

/**
 * @author CWDS CALS API Team
 */

public class BaseEntityAwareDTO<T extends PersistentObject> {

  private T entity;

  public T getEntity() {
    return entity;
  }

  public void setEntity(T entity) {
    this.entity = entity;
  }

}
