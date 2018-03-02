package gov.ca.cwds.data.legacy.cms.entity.enums;

import java.util.Collections;
import java.util.Map;
import javax.persistence.Converter;

public enum AdoptionStatus implements EntityEnum<String> {
  TOTALLY_FREE("T", "Totally Free"),
  PARTIALLY_FREE("P", "Partially Free"),
  NOT_FREE("N", "Not Free"),
  NOT_APPLICABLE("A", "Not Applicable");

  private final String code;
  private final String description;

  AdoptionStatus(String code, String description) {
    this.code = code;
    this.description = description;
  }

  public static AdoptionStatus fromCode(String code) {
    return new AdoptionStatusConverter().convertToEntityAttribute(code);
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
  public static class AdoptionStatusConverter
      extends BaseEntityEnumConverter<AdoptionStatus, String> {

    private static final Map<String, AdoptionStatus> codeMap =
        Collections.unmodifiableMap(initializeMapping(AdoptionStatus.values()));

    @Override
    Map<String, AdoptionStatus> getCodeMap() {
      return codeMap;
    }
  }
}
