package gov.ca.cwds.data.legacy.cms.entity.enums;

import java.util.Collections;
import java.util.Map;
import javax.persistence.Converter;

/**
 * @author CWDS TPT-3 Team
 */
public enum LegalStatus implements EntityEnum<String> {
  ALLEGED("A", "Alleged"),
  PRESUMED("P", "Presumed");

  private final String code;
  private final String description;

  LegalStatus(String code, String description) {
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
  public static class LegalStatusConverter
      extends BaseEntityEnumConverter<LegalStatus, String> {

    private static final Map<String, LegalStatus> codeMap =
        Collections.unmodifiableMap(initializeMapping(LegalStatus.values()));

    @Override
    Map<String, LegalStatus> getCodeMap() {
      return codeMap;
    }
  }
}
