package gov.ca.cwds.data.legacy.cms.entity.enums;

import java.util.Collections;
import java.util.Map;
import javax.persistence.Converter;

/**
 * @author CWDS TPT-3 Team
 */
public enum ScreeningResult implements EntityEnum<String> {
  NO_REFERRAL_NEEDED("N", "No Referral Needed"),
  REFERRAL_FOR_SERVICES("P", "Referral for Services");

  private final String code;
  private final String description;

  ScreeningResult(String code, String description) {
    this.code = code;
    this.description = description;
  }

  public static ScreeningResult fromCode(String code) {
    return new ScreeningResultConverter().convertToEntityAttribute(code);
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
  public static class ScreeningResultConverter
      extends BaseEntityEnumConverter<ScreeningResult, String> {

    private static final Map<String, ScreeningResult> codeMap =
        Collections.unmodifiableMap(initializeMapping(ScreeningResult.values()));

    @Override
    Map<String, ScreeningResult> getCodeMap() {
      return codeMap;
    }
  }
}
