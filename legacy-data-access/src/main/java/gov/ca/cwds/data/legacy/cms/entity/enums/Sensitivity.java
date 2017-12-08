package gov.ca.cwds.data.legacy.cms.entity.enums;

import java.util.Collections;
import java.util.Map;
import javax.persistence.Converter;

public enum Sensitivity implements EntityEnum<Character> {
  NOT_APPLICABLE('N', "Not Applicable"),
  SEALED('R', "This specifies a SEALED Case"),
  SENSITIVE('S', "This specifies a SENSITIVE Case");

  private final Character code;
  private final String description;

  Sensitivity(Character code, String description) {
    this.code = code;
    this.description = description;
  }

  @Override
  public Character getCode() {
    return code;
  }

  @Override
  public String getDescription() {
    return description;
  }

  @Converter
  public static class SensitivityConverter
      extends BaseEntityEnumConverter<Sensitivity, Character> {

    private static final Map<Character, Sensitivity> codeMap =
        Collections.unmodifiableMap(initializeMapping(Sensitivity.values()));

    @Override
    Map<Character, Sensitivity> getCodeMap() {
      return codeMap;
    }
  }
}
