package gov.ca.cwds.data.legacy.cms.entity.enums;

import java.util.Collections;
import java.util.Map;
import javax.persistence.Converter;

/** @author CWDS CASE API Team */
public enum InterventionPlanType implements EntityEnum<String> {
  INITIAL_MENTAL_HEALTH("F", "Initial Mental Health"),
  INITIAL_DEVELOPMENTAL("I", "Initial Developmental"),
  UPDATED_MENTAL_HEALTH("L", "Updated Mental Health"),
  UPDATED_DEVELOPMENTAL("U", "Updated Developmental");

  private final String code;
  private final String description;

  InterventionPlanType(String code, String description) {
    this.code = code;
    this.description = description;
  }

  @Override
  public String getCode() {
    return code;
  }

  @Override
  public String getDescription() {
    return description;
  }

  @Converter
  public static class InterventionPlanTypeConverter
      extends BaseEntityEnumConverter<InterventionPlanType, String> {

    private static final Map<String, InterventionPlanType> codeMap =
        Collections.unmodifiableMap(initializeMapping(InterventionPlanType.values()));

    @Override
    Map<String, InterventionPlanType> getCodeMap() {
      return codeMap;
    }
  }
}
