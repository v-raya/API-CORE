package gov.ca.cwds.data.legacy.cms.entity.enums;

import java.util.Collections;
import java.util.Map;
import javax.persistence.Converter;

/** @author CWDS TPT-3 Team */
public enum IndividualType implements EntityEnum<String> {
  ATTORNEYS("A", "Attorneys"),
  CLIENTS("C", "Clients"),
  COLLATERAL_INDIVIDUALS("O", "Collateral Individuals"),
  SERVICE_PROVIDERS("P", "Service Providers"),
  REPORTERS("R", "Reporters"),
  SUBSTITUTE_CARE_PROVIDERS("S", "Substitute Care Providers"),
  STAFF_PERSON("T", "Staff Person"),
  OUT_OF_STATE_CONTACT_PARTY("U", "Out of State Contact Party");

  private final String code;
  private final String description;

  IndividualType(String code, String description) {
    this.code = code;
    this.description = description;
  }

  public static IndividualType fromCode(String code) {
    return new IndividualTypeConverter().convertToEntityAttribute(code);
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
  public static class IndividualTypeConverter
      extends BaseEntityEnumConverter<IndividualType, String> {

    private static final Map<String, IndividualType> codeMap =
        Collections.unmodifiableMap(initializeMapping(IndividualType.values()));

    @Override
    Map<String, IndividualType> getCodeMap() {
      return codeMap;
    }
  }
}
