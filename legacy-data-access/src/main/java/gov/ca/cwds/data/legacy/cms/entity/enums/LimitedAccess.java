package gov.ca.cwds.data.legacy.cms.entity.enums;

import java.util.Collections;
import java.util.Map;
import javax.persistence.Converter;

/**
 * @author CWDS CASE API Team
 */
public enum LimitedAccess implements EntityEnum<Character> {
  NO_RESTRICTION('N',  "N/A"),
  SEALED('R', "Sealed"),
  SENSITIVE('S', "Sensitive");

  private final Character code;
  private final String description;

  LimitedAccess(Character code, String description) {
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
  public static class LimitedAccessConverter extends BaseEntityEnumConvertor<LimitedAccess, Character> {

    private static final Map<Character, LimitedAccess> codeMap =
        Collections.unmodifiableMap(initializeMapping(LimitedAccess.values()));

    @Override
    Map<Character, LimitedAccess> getCodeMap() {
      return codeMap;
    }
  }
}

