package gov.ca.cwds.data.legacy.cms.entity.enums;

import java.util.Collections;
import java.util.Map;
import javax.persistence.Converter;

/**
 * @author CWDS CASE API Team
 */
public enum AwolAbducted implements EntityEnum<String> {
  AWOL_BASED_ON_USER_ENTRY("A", "AWOL - user-entered"),
  ABDUCTED_BASED_ON_USER_ENTRY("B", "Abducted - user-entered"),
  ABDUCTED_BASED_ON_SYSTEM_SETTING("D", "Abducted - system-entered"),
  NOT_APPLICABLE("N", "Not Applicable"),
  AWOL_BASED_ON_SYSTEM_SETTING("P",  "AWOL - system-entered");

  private final String code;
  private final String description;

  AwolAbducted(String code, String description) {
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
  public static class AwolAbductedConverter extends
      BaseEntityEnumConverter<AwolAbducted, String> {

    private static final Map<String, AwolAbducted> codeMap =
        Collections.unmodifiableMap(initializeMapping(AwolAbducted.values()));

    @Override
    Map<String, AwolAbducted> getCodeMap() {
      return codeMap;
    }
  }
}

