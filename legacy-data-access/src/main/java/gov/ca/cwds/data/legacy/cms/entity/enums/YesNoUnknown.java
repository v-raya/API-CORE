package gov.ca.cwds.data.legacy.cms.entity.enums;

import java.util.Collections;
import java.util.Map;
import javax.persistence.Converter;

public enum YesNoUnknown implements EntityEnum<String> {
  NO("N", "No"),
  UNKNOWN("U", "Unknown"),
  YES("Y", "Yes");

  private final String code;
  private final String description;

  YesNoUnknown(String code, String description) {
    this.code = code;
    this.description = description;
  }

  public static YesNoUnknown fromCode(String code) {
    return new YesNoUnknownConverter().convertToEntityAttribute(code);
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
  public static class YesNoUnknownConverter extends BaseEntityEnumConverter<YesNoUnknown, String> {

    private static final Map<String, YesNoUnknown> codeMap =
        Collections.unmodifiableMap(initializeMapping(YesNoUnknown.values()));

    @Override
    Map<String, YesNoUnknown> getCodeMap() {
      return codeMap;
    }
  }
}
