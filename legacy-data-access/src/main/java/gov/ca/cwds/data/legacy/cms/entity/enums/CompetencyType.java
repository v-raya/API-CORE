package gov.ca.cwds.data.legacy.cms.entity.enums;

import java.util.Collections;
import java.util.Map;
import javax.persistence.Converter;

/** @author CWDS CASE API Team */
public enum CompetencyType implements EntityEnum<String> {
  ADOPTION_WORKER("A", "Adoption worker"),
  NEITHER("N", "Neither"),
  PROFESSIONAL("P", "Professional");

  private final String code;
  private final String description;

  CompetencyType(String code, String description) {
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
  public static class CompetencyTypeConverter extends BaseEntityEnumConverter<CompetencyType, String> {

    private static final Map<String, CompetencyType> codeMap =
        Collections.unmodifiableMap(initializeMapping(CompetencyType.values()));

    @Override
    Map<String, CompetencyType> getCodeMap() {
      return codeMap;
    }
  }
}
