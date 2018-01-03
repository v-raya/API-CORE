package gov.ca.cwds.data.legacy.cms.entity.enums;

import java.util.Arrays;
import java.util.Collections;
import java.util.Map;
import javax.persistence.Converter;

public enum LiterateStatus implements EntityEnum<String> {
  NOT_APPLICABLE("D", "Not Applicable"),
  ILLITERATE("N", "Illiterate"),
  UNKNOWN("U", "Unknown"),
  LITERATE("Y", "Literate");

  private final String code;
  private final String description;

  LiterateStatus(String code, String description) {
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
  public static class LiterateStatusConverter
      extends BaseEntityEnumConverter<LiterateStatus, String> {

    private static final Map<String, LiterateStatus> codeMap =
        Collections.unmodifiableMap(initializeMapping(LiterateStatus.values()));

    @Override
    Map<String, LiterateStatus> getCodeMap() {
      return codeMap;
    }
  }
}
