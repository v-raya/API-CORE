package gov.ca.cwds.data.legacy.cms.entity.enums;

import java.util.Collections;
import java.util.Map;
import javax.persistence.Converter;

public enum Gender implements EntityEnum<String> {
  FEMALE("F", "Female"),
  MALE("M", "Male"),
  INTERSEX("I", "Intersex"),
  UNKNOWN("U", "Unknown");

  private final String code;
  private final String description;

  Gender(String code, String description) {
    this.code = code;
    this.description = description;
  }

  public static Gender fromCode(String code) {
    return new GenderConverter().convertToEntityAttribute(code);
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
  public static class GenderConverter extends BaseEntityEnumConverter<Gender, String> {

    private static final Map<String, Gender> codeMap =
        Collections.unmodifiableMap(initializeMapping(Gender.values()));

    @Override
    Map<String, Gender> getCodeMap() {
      return codeMap;
    }
  }
}
