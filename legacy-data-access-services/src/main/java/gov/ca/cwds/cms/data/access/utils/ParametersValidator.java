package gov.ca.cwds.cms.data.access.utils;

import gov.ca.cwds.data.persistence.PersistentObject;
import org.apache.commons.lang3.StringUtils;

/**
 * @author CWDS CALS API Team
 */

public final class ParametersValidator {


  public static final String INSTANCE_MUST_NOT_BE_PERSISTED = "Instance of %s must not be persisted";

  private ParametersValidator() {}

  public static void checkNotPersisted(PersistentObject persistentObject) {
    String primaryKey = null;
    if (persistentObject.getPrimaryKey() != null && persistentObject.getPrimaryKey().getClass()
        .isAssignableFrom(String.class)) {
      primaryKey = (String) persistentObject.getPrimaryKey();
    }
    if (StringUtils.isNoneEmpty(primaryKey)) {
      throw new IllegalStateException(String.format(INSTANCE_MUST_NOT_BE_PERSISTED,
          persistentObject.getClass().getSimpleName()));
    }
  }

}
