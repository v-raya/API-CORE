package gov.ca.cwds.data.legacy.cms.entity.enums;

import java.util.Collections;
import java.util.Map;
import javax.persistence.Converter;

/**
 * @author CWDS CASE API Team
 */
public enum AwolAbducted implements EntityEnum<Character> {
  AWOL_BASED_ON_USER_ENTRY('A', "AWOL - user-entered"),
  ABDUCTED_BASED_ON_USER_ENTRY('B', "Abducted - user-entered"),
  ABDUCTED_BASED_ON_SYSTEM_SETTING('D', "Abducted - system-entered"),
  NOT_APPLICABLE('N', "Not Applicable"),
  AWOL_BASED_ON_SYSTEM_SETTING('P',  "AWOL - system-entered");

  private final Character code;
  private final String description;

  AwolAbducted(Character code, String description) {
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
  public static class AwolAbductedConverter extends
      BaseEntityEnumConverter<AwolAbducted, Character> {

    private static final Map<Character, AwolAbducted> codeMap =
        Collections.unmodifiableMap(initializeMapping(AwolAbducted.values()));

    @Override
    Map<Character, AwolAbducted> getCodeMap() {
      return codeMap;
    }
  }
}

