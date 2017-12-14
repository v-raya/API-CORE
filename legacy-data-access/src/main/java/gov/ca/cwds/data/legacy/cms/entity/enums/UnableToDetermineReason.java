package gov.ca.cwds.data.legacy.cms.entity.enums;

import java.util.Collections;
import java.util.Map;
import javax.persistence.Converter;

public enum UnableToDetermineReason implements EntityEnum<Character> {
  ABANDONMENT('A', "Abandonment"),
  INCAPACITATION('I', "Incapacitation"),
  INDIVIDUAL_DOES_NOT_KNOW('K', "Individual Does Not Know");

  private final Character code;
  private final String description;

  UnableToDetermineReason(Character code, String description) {
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
  public static class UnableToDetermineReasonConverter
      extends BaseEntityEnumConverter<UnableToDetermineReason, Character> {

    private static final Map<Character, UnableToDetermineReason> codeMap =
        Collections.unmodifiableMap(initializeMapping(UnableToDetermineReason.values()));

    @Override
    Map<Character, UnableToDetermineReason> getCodeMap() {
      return codeMap;
    }
  }
}
