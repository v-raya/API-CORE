package gov.ca.cwds.data.legacy.cms.entity.enums;

import java.util.Collections;
import java.util.Map;
import javax.persistence.Converter;

public enum AdoptionStatus implements EntityEnum<Character> {
  TOTALLY_FREE('T', "Totally Free"),
  PARTIALLY_FREE('P', "Partially Free"),
  NOT_FREE('N', "Not Free"),
  NOT_APPLICABLE('A', "Not Applicable");

  private final Character code;
  private final String description;

  AdoptionStatus(Character code, String description) {
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
  public static class AdoptionStatusConverter
      extends BaseEntityEnumConverter<AdoptionStatus, Character> {

    private static final Map<Character, AdoptionStatus> codeMap =
        Collections.unmodifiableMap(initializeMapping(AdoptionStatus.values()));

    @Override
    Map<Character, AdoptionStatus> getCodeMap() {
      return codeMap;
    }
  }
}
