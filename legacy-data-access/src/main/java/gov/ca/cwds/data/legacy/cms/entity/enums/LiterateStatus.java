package gov.ca.cwds.data.legacy.cms.entity.enums;

import java.util.Collections;
import java.util.Map;
import javax.persistence.Converter;

public enum LiterateStatus implements EntityEnum<Character> {
  NOT_APPLICABLE('D', "Not Applicable"),
  ILLITERATE('N', "Illiterate"),
  UNKNOWN('U', "Unknown"),
  LITERATE('Y', "Literate");

  private final Character code;
  private final String description;

  LiterateStatus(Character code, String description) {
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
  public static class LiterateStatusConverter
      extends BaseEntityEnumConverter<LiterateStatus, Character> {

    private static final Map<Character, LiterateStatus> codeMap =
        Collections.unmodifiableMap(initializeMapping(LiterateStatus.values()));

    @Override
    Map<Character, LiterateStatus> getCodeMap() {
      return codeMap;
    }
  }
}
