package gov.ca.cwds.data.legacy.cms.entity.enums;

import java.util.Collections;
import java.util.Map;
import javax.persistence.Converter;

public enum UnableToDetermineReason implements EntityEnum<String> {
  ABANDONMENT("A", "Abandonment"),
  INCAPACITATION("I", "Incapacitation"),
  INDIVIDUAL_DOES_NOT_KNOW("K", "Individual Does Not Know");

  private final String code;
  private final String description;

  UnableToDetermineReason(String code, String description) {
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
  public static class UnableToDetermineReasonConverter
      extends BaseEntityEnumConverter<UnableToDetermineReason, String> {

    private static final Map<String, UnableToDetermineReason> codeMap =
        Collections.unmodifiableMap(initializeMapping(UnableToDetermineReason.values()));

    @Override
    Map<String, UnableToDetermineReason> getCodeMap() {
      return codeMap;
    }
  }
}
