package gov.ca.cwds.data.legacy.cms.entity.enums;

import java.util.Collections;
import java.util.Map;
import javax.persistence.Converter;

/** @author CWDS CASE API Team */
public enum LimitedAccess implements EntityEnum<String> {
  NO_RESTRICTION("N", "N/A"),
  SEALED("R", "Sealed"),
  SENSITIVE("S", "Sensitive");

  private final String code;
  private final String description;

  LimitedAccess(String code, String description) {
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
  public static class LimitedAccessConverter
      extends BaseEntityEnumConverter<LimitedAccess, String> {

    private static final Map<String, LimitedAccess> codeMap =
        Collections.unmodifiableMap(initializeMapping(LimitedAccess.values()));

    @Override
    Map<String, LimitedAccess> getCodeMap() {
      return codeMap;
    }
  }
}
