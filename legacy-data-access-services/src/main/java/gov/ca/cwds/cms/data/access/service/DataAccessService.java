package gov.ca.cwds.cms.data.access.service;

import gov.ca.cwds.data.persistence.PersistentObject;

/**
 * @author CWDS CALS API Team
 */

public interface DataAccessService<T extends PersistentObject> {

  T create(T entity, String staffPersonId);

  default void runBusinessRules(T entity) {
    //Empty by default
  }

}
