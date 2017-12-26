package gov.ca.cwds.data.legacy.cms.entity.enums;

import java.util.Arrays;
import java.util.Collections;
import java.util.Map;
import javax.persistence.Converter;

public enum Sensitivity implements EntityEnum<String> {
  NOT_APPLICABLE("N", "Not Applicable"),
  SEALED("R", "This specifies a SEALED Case"),
  SENSITIVE("S", "This specifies a SENSITIVE Case");

  private final String code;
  private final String description;

  Sensitivity(String code, String description) {
    this.code = code;
    this.description = description;
  }

  public static Sensitivity from(String code) {
    return Arrays.asList(Sensitivity.values())
        .stream()
        .findFirst()
        .filter(e -> e.code.equals(code))
        .orElse(null);
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
  public static class SensitivityConverter
      extends BaseEntityEnumConverter<Sensitivity, String> {

    private static final Map<String, Sensitivity> codeMap =
        Collections.unmodifiableMap(initializeMapping(Sensitivity.values()));

    @Override
    Map<String, Sensitivity> getCodeMap() {
      return codeMap;
    }
  }
}
