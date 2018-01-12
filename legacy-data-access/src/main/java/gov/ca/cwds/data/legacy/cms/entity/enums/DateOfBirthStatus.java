package gov.ca.cwds.data.legacy.cms.entity.enums;

import java.util.Arrays;
import java.util.Collections;
import java.util.Map;
import javax.persistence.Converter;

public enum DateOfBirthStatus implements EntityEnum<String> {
  ACTUALLY_ENTERED("N", "Actually Entered"),
  NOT_PROVIDED("U", "Not Provided"),
  ESTIMATED("Y", "Estimated");

  private final String code;
  private final String description;

  DateOfBirthStatus(String code, String description) {
    this.code = code;
    this.description = description;
  }

  public static DateOfBirthStatus from(String code) {
    return Arrays.asList(DateOfBirthStatus.values())
        .stream()
        .findFirst()
        .filter(e -> e.code.equals(code))
        .orElse(null);
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
  public static class DateOfBirthStatusConverter
      extends BaseEntityEnumConverter<DateOfBirthStatus, String> {

    private static final Map<String, DateOfBirthStatus> codeMap =
        Collections.unmodifiableMap(initializeMapping(DateOfBirthStatus.values()));

    @Override
    Map<String, DateOfBirthStatus> getCodeMap() {
      return codeMap;
    }
  }
}
