package gov.ca.cwds.data.legacy.cms.entity.enums;

import java.util.Arrays;
import java.util.Collections;
import java.util.Map;
import javax.persistence.Converter;

public enum HispanicOrigin implements EntityEnum<String> {
  DECLINES_TO_STATE("D", "Declines to State"),
  NO("N", "No"),
  UNDETERMINED("U", "Undetermined"),
  NO_USER_SELECTION("X", "No User Selection"),
  YES("Y", "Yes"),
  UNABLE_TO_DETERMINE("Z", "Unable to Determine");

  private final String code;
  private final String description;

  HispanicOrigin(String code, String description) {
    this.code = code;
    this.description = description;
  }

  public static HispanicOrigin from(String code) {
    return Arrays.asList(HispanicOrigin.values())
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
  public static class HispanicOriginConverter
      extends BaseEntityEnumConverter<HispanicOrigin, String> {

    private static final Map<String, HispanicOrigin> codeMap =
        Collections.unmodifiableMap(initializeMapping(HispanicOrigin.values()));

    @Override
    Map<String, HispanicOrigin> getCodeMap() {
      return codeMap;
    }
  }
}
