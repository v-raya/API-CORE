package gov.ca.cwds.data.legacy.cms.entity.enums;

import java.util.Collections;
import java.util.Map;
import javax.persistence.Converter;

public enum HispanicOrigin implements EntityEnum<Character> {
  DECLINES_TO_STATE('D', "Declines to State"),
  NO('N', "No"),
  UNDETERMINED('U', "Undetermined"),
  NO_USER_SELECTION('X', "No User Selection"),
  YES('Y', "Yes"),
  UNABLE_TO_DETERMINE('Z', "Unable to Determine");

  private final Character code;
  private final String description;

  HispanicOrigin(Character code, String description) {
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
  public static class HispanicOriginConverter
      extends BaseEntityEnumConverter<HispanicOrigin, Character> {

    private static final Map<Character, HispanicOrigin> codeMap =
        Collections.unmodifiableMap(initializeMapping(HispanicOrigin.values()));

    @Override
    Map<Character, HispanicOrigin> getCodeMap() {
      return codeMap;
    }
  }
}
