package gov.ca.cwds.data.legacy.cms.entity.enums;

import java.util.Collections;
import java.util.Map;
import javax.persistence.Converter;

/**
 * @author CWDS CASE API Team
 */
public enum IcwaEligibility implements EntityEnum<String> {
  NOT_ELIGIBLE("N", "Not Eligible"),
  PENDING("P", "Pending"),
  UNKNOWN("U", "Not asked, unknown"),
  ELIGIBLE("Y", "Eligible");

  private final String code;
  private final String description;

  IcwaEligibility(String code, String description) {
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
  public static class IcwaEligibilityConverter extends
      BaseEntityEnumConverter<IcwaEligibility, String> {

    private static final Map<String, IcwaEligibility> codeMap =
        Collections.unmodifiableMap(initializeMapping(IcwaEligibility.values()));

    @Override
    Map<String, IcwaEligibility> getCodeMap() {
      return codeMap;
    }
  }
}

