package gov.ca.cwds.cms.data.access.service;

import gov.ca.cwds.data.persistence.PersistentObject;

/**
 * @author CWDS CALS API Team
 */

public interface DataAccessService<T extends PersistentObject> {

  default void runBusinessRules(T entity) {
    //Empty by default
  }

}
