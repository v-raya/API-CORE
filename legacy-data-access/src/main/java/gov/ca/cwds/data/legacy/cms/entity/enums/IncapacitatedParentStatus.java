package gov.ca.cwds.data.legacy.cms.entity.enums;

import java.util.Collections;
import java.util.Map;
import javax.persistence.Converter;

public enum IncapacitatedParentStatus implements EntityEnum<String> {
  NO("N", "No"),
  NOT_APPLICABLE("NA", "Not Applicable, not a parent"),
  UNKNOWN("U", "Unknown, can not be determine at this time"),
  YES("Y", "Yes");

  private final String code;
  private final String description;

  IncapacitatedParentStatus(String code, String description) {
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
  public static class IncapacitatedParentStatusConverter
      extends BaseEntityEnumConverter<IncapacitatedParentStatus, String> {

    private static final Map<String, IncapacitatedParentStatus> codeMap =
        Collections.unmodifiableMap(initializeMapping(IncapacitatedParentStatus.values()));

    @Override
    Map<String, IncapacitatedParentStatus> getCodeMap() {
      return codeMap;
    }
  }
}
