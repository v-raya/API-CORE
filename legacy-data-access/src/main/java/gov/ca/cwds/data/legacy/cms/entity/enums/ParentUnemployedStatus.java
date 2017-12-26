package gov.ca.cwds.data.legacy.cms.entity.enums;

import java.util.Arrays;
import java.util.Collections;
import java.util.Map;
import javax.persistence.Converter;

public enum ParentUnemployedStatus implements EntityEnum<String> {
  NO("N", "No"),
  NOT_APPLICABLE("NA", "Not Applicable, not a parent"),
  UNKNOWN("U", "Unknown, can not be determine at this time"),
  YES("Y", "Yes");

  private final String code;
  private final String description;

  ParentUnemployedStatus(String code, String description) {
    this.code = code;
    this.description = description;
  }

  public static ParentUnemployedStatus from(String code) {
    return Arrays.asList(ParentUnemployedStatus.values())
        .stream()
        .findFirst()
        .filter(e -> e.code.equals(code))
        .orElse(null);
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
  public static class ParentUnemployedStatusConverter
      extends BaseEntityEnumConverter<ParentUnemployedStatus, String> {

    private static final Map<String, ParentUnemployedStatus> codeMap =
        Collections.unmodifiableMap(initializeMapping(ParentUnemployedStatus.values()));

    @Override
    Map<String, ParentUnemployedStatus> getCodeMap() {
      return codeMap;
    }
  }
}
