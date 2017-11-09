package gov.ca.cwds.data.legacy.cms.entity.enums;

/**
 * @author CWDS CASE API Team
 */
public enum LimitedAccess {
  NO_RESTRICTION("N/A"),
  SEALED("R"),
  SENSITIVE("S");

  private final String code;

  LimitedAccess(String code) {
    this.code = code;
  }

  public static LimitedAccess fromCode(String code) {
    if ("N/A".equals(code)) {
      return NO_RESTRICTION;
    } else if ("R".equals(code)) {
      return SEALED;
    } else if ("S".equals(code)) {
      return SENSITIVE;
    }
    throw new UnsupportedOperationException(
        "The code " + code + " is not supported!"
    );
  }

  public String getCode() {
    return code;
  }
}

