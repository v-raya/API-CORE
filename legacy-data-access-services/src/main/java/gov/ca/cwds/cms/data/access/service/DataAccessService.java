package gov.ca.cwds.cms.data.access.service;

import gov.ca.cwds.cms.data.access.parameter.BaseParameterObject;
import gov.ca.cwds.data.persistence.PersistentObject;

/**
 * @author CWDS CALS API Team
 */

public interface DataAccessService<T extends PersistentObject, P extends BaseParameterObject<T>> {

  T create(P parameterObject);

  default void runBusinessValidation(T entity) {
    //Empty by default
  }

}
