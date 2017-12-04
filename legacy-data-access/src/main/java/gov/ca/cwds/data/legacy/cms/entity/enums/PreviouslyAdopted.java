package gov.ca.cwds.data.legacy.cms.entity.enums;

import java.util.Collections;
import java.util.Map;
import javax.persistence.Converter;

/**
 * @author CWDS CASE API Team
 */
public enum PreviouslyAdopted implements EntityEnum<Character> {
  NO('N', "No"),
  UNKNOWN('U', "Unknown"),
  NO_USER_SELECTION('X', "No User Selection"),
  YES('Y', "Eligible");

  private final Character code;
  private final String description;

  PreviouslyAdopted(Character code, String description) {
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
  public static class PreviouslyAdoptedConverter extends
      BaseEntityEnumConverter<PreviouslyAdopted, Character> {

    private static final Map<Character, PreviouslyAdopted> codeMap =
        Collections.unmodifiableMap(initializeMapping(PreviouslyAdopted.values()));

    @Override
    Map<Character, PreviouslyAdopted> getCodeMap() {
      return codeMap;
    }
  }
}

