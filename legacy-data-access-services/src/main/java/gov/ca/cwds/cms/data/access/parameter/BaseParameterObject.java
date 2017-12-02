package gov.ca.cwds.cms.data.access.parameter;

import gov.ca.cwds.data.persistence.PersistentObject;

/**
 * @author CWDS CALS API Team
 */

public class BaseParameterObject<T extends PersistentObject> {

  private T entity;

  private String staffPersonId;

  public String getStaffPersonId() {
    return staffPersonId;
  }

  public void setStaffPersonId(String staffPersonId) {
    this.staffPersonId = staffPersonId;
  }

  public T getEntity() {
    return entity;
  }

  public void setEntity(T entity) {
    this.entity = entity;
  }
}
