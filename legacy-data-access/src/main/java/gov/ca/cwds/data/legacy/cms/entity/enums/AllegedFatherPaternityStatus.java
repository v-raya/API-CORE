package gov.ca.cwds.data.legacy.cms.entity.enums;

import java.util.Collections;
import java.util.Map;
import javax.persistence.Converter;

public enum AllegedFatherPaternityStatus implements EntityEnum<String> {
  NO("N", "Alleged father in Not Legal father"),
  UNKNOWN("U", "Undetermined/Inconclusive"),
  YES("Y", "Alleged father is Legal father");

  private final String code;
  private final String description;

  AllegedFatherPaternityStatus(String code, String description) {
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
  public static class AllegedFatherPaternityStatusConverter extends BaseEntityEnumConverter<AllegedFatherPaternityStatus, String> {

    private static final Map<String, AllegedFatherPaternityStatus> codeMap =
        Collections.unmodifiableMap(initializeMapping(AllegedFatherPaternityStatus.values()));

    @Override
    Map<String, AllegedFatherPaternityStatus> getCodeMap() {
      return codeMap;
    }
  }
}
