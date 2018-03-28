package gov.ca.cwds.cms.data.access.utils;

import gov.ca.cwds.cms.data.access.dto.BaseEntityAwareDTO;
import gov.ca.cwds.data.persistence.PersistentObject;
import java.util.List;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;

/** @author CWDS CALS API Team */
public final class ParametersValidator {

  public static final String INSTANCE_MUST_NOT_BE_PERSISTED =
      "Instance of %s must not be persisted";

  private ParametersValidator() {}

  public static <T extends BaseEntityAwareDTO> void validateParameterObjects(List<T> parameters) {
    if (CollectionUtils.isNotEmpty(parameters)) {
      parameters.forEach(parameter -> ParametersValidator.checkNotPersisted(parameter.getEntity()));
    }
  }

  public static <T extends PersistentObject> void validatePersistentObjects(List<T> parameters) {
    if (CollectionUtils.isNotEmpty(parameters)) {
      parameters.forEach(ParametersValidator::checkNotPersisted);
    }
  }

  public static void checkNotPersisted(PersistentObject persistentObject) {
    String primaryKey = null;
    if (persistentObject != null
        && persistentObject.getPrimaryKey() != null
        && persistentObject.getPrimaryKey().getClass().isAssignableFrom(String.class)) {
      primaryKey = (String) persistentObject.getPrimaryKey();
    }
    if (StringUtils.isNoneEmpty(primaryKey)) {
      String className =
          persistentObject == null ? "null" : persistentObject.getClass().getSimpleName();
      throw new IllegalStateException(String.format(INSTANCE_MUST_NOT_BE_PERSISTED, className));
    }
  }
}
