package gov.ca.cwds.data.legacy.cms.entity.enums;

import java.util.Collections;
import java.util.Map;
import javax.persistence.Converter;

/** @author CWDS CASE API Team */
public enum Disability implements EntityEnum<String> {
  NOT_YET_DETERMINED("D", "Not Yet Determined"),
  NO("N", "No"),
  YES("Y", "Yes");

  private final String code;
  private final String description;

  Disability(String code, String description) {
    this.code = code;
    this.description = description;
  }

  public static Disability fromCode(String code) {
    return new DisabilityConverter().convertToEntityAttribute(code);
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
  public static class DisabilityConverter extends BaseEntityEnumConverter<Disability, String> {

    private static final Map<String, Disability> codeMap =
        Collections.unmodifiableMap(initializeMapping(Disability.values()));

    @Override
    Map<String, Disability> getCodeMap() {
      return codeMap;
    }
  }
}
