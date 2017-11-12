package gov.ca.cwds.data.legacy.cms.entity.enums;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * @author CWDS CASE API Team
 */
public enum LimitedAccess {
  NO_RESTRICTION("N/A",  " N/A"),
  SEALED("R", "Sealed"),
  SENSITIVE("S", "Sensitive");

  private static final Map<String, LimitedAccess> codeMap =
      Collections.unmodifiableMap(initializeMapping());

  private final String code;
  private final String description;

  LimitedAccess(String code, String description) {
    this.code = code;
    this.description = description;
  }

  public static LimitedAccess fromCode(String code) {
    LimitedAccess result = codeMap.get(code);

    if (result == null) {
      throw new UnsupportedOperationException("The code " + code + " is not supported!");
    }
    return result;
  }

  private static Map<String, LimitedAccess> initializeMapping() {
    Map result = new HashMap<String, LimitedAccess>();
    for (LimitedAccess limitedAccess : LimitedAccess.values()) {
      result.put(limitedAccess.code, limitedAccess);
    }
    return result;
  }

  public String getCode() {
    return code;
  }

  public String getDescription() {
    return description;
  }
}

