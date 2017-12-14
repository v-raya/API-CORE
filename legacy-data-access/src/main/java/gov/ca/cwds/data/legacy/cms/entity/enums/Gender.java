package gov.ca.cwds.data.legacy.cms.entity.enums;

import java.util.Collections;
import java.util.Map;
import javax.persistence.Converter;

public enum Gender implements EntityEnum<Character> {
  FEMALE('F', "Female"),
  MALE('M', "Male"),
  UNKNOWN('U', "Unknown");

  private final Character code;
  private final String description;

  Gender(Character code, String description) {
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
  public static class GenderConverter
      extends BaseEntityEnumConverter<Gender, Character> {

    private static final Map<Character, Gender> codeMap =
        Collections.unmodifiableMap(initializeMapping(Gender.values()));

    @Override
    Map<Character, Gender> getCodeMap() {
      return codeMap;
    }
  }
}
