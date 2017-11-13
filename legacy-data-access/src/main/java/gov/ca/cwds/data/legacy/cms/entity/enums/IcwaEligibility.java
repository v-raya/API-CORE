package gov.ca.cwds.data.legacy.cms.entity.enums;

import java.util.Collections;
import java.util.Map;
import javax.persistence.Converter;

/**
 * @author CWDS CASE API Team
 */
public enum IcwaEligibility implements EntityEnum<Character> {
  NOT_ELIGIBLE('N', "Not Eligible"),
  PENDING('P', "Pending"),
  UNKNOWN('U', "Not asked, unknown"),
  ELIGIBLE('Y', "Eligible");

  private final Character code;
  private final String description;

  IcwaEligibility(Character code, String description) {
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
  public static class IcwaEligibilityConverter extends
      BaseEntityEnumConvertor<IcwaEligibility, Character> {

    private static final Map<Character, IcwaEligibility> codeMap =
        Collections.unmodifiableMap(initializeMapping(IcwaEligibility.values()));

    @Override
    Map<Character, IcwaEligibility> getCodeMap() {
      return codeMap;
    }
  }
}

