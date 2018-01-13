package gov.ca.cwds.data.legacy.cms.entity.enums;

import java.util.Arrays;
import java.util.Collections;
import java.util.Map;
import javax.persistence.Converter;

/** @author CWDS CASE API Team */
public enum PreviouslyAdopted implements EntityEnum<String> {
  NO("N", "No"),
  UNKNOWN("U", "Unknown"),
  NO_USER_SELECTION("X", "No User Selection"),
  YES("Y", "Eligible");

  private final String code;
  private final String description;

  PreviouslyAdopted(String code, String description) {
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
  public static class PreviouslyAdoptedConverter
      extends BaseEntityEnumConverter<PreviouslyAdopted, String> {

    private static final Map<String, PreviouslyAdopted> codeMap =
        Collections.unmodifiableMap(initializeMapping(PreviouslyAdopted.values()));

    @Override
    Map<String, PreviouslyAdopted> getCodeMap() {
      return codeMap;
    }
  }
}
