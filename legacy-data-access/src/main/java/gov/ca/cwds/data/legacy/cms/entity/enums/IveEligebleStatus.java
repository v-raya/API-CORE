package gov.ca.cwds.data.legacy.cms.entity.enums;

import javax.persistence.Converter;
import java.util.Collections;
import java.util.Map;

public enum IveEligebleStatus implements EntityEnum<String> {
  MIGRATED_TO_CLIENT_SERVICES("U", "Unknown - Can not be determined at this time"),
  NO_SOC_158_PLACEMENTS("N", "Not eligible for AFDC-FC"),
  NOT_YET_MIGRATED("Y", "Eligible for AFDC-FC");

  private final String code;
  private final String description;

  IveEligebleStatus(String code, String description) {
    this.code = code;
    this.description = description;
  }

  public static IveEligebleStatus fromCode(String code) {
    return new IveEligebleStatusConverter().convertToEntityAttribute(code);
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
  public static class IveEligebleStatusConverter
      extends BaseEntityEnumConverter<IveEligebleStatus, String> {

    private static final Map<String, IveEligebleStatus> codeMap =
        Collections.unmodifiableMap(initializeMapping(IveEligebleStatus.values()));

    @Override
    Map<String, IveEligebleStatus> getCodeMap() {
      return codeMap;
    }
  }
}
