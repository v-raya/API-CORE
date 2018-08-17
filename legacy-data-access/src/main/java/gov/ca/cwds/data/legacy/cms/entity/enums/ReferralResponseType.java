package gov.ca.cwds.data.legacy.cms.entity.enums;

import java.util.Collections;
import java.util.Map;

import javax.persistence.Converter;

/**
 * @author CWDS API Team
 *
 */
public enum ReferralResponseType implements EntityEnum<Short> {
  RESPONSE_TIME_3_DAYS((short) 1516, "3 Day"), RESPONSE_TIME_5_DAYS((short) 1517,
      "5 Day"), RESPONSE_TIME_10_DAYS((short) 1518, "10 Day"), EVALUATE_OUT((short) 1519,
          "Evaluate Out"), IMMEDIATE((short) 1520, "Immediate");

  private final Short code;
  private final String description;

  ReferralResponseType(Short code, String description) {
    this.code = code;
    this.description = description;
  }

  /**
   * @param code - code
   * @return the Entity code
   */
  public static ReferralResponseType fromCode(Short code) {
    return new ReferralResponseTypeConverter().convertToEntityAttribute(code);
  }


  @Override
  public Short getCode() {
    return code;
  }

  @Override
  public String getDescription() {
    return description;
  }

  /**
   * 
   *
   */
  @Converter
  public static class ReferralResponseTypeConverter
      extends BaseEntityEnumConverter<ReferralResponseType, Short> {

    private static final Map<Short, ReferralResponseType> codeMap =
        Collections.unmodifiableMap(initializeMapping(ReferralResponseType.values()));

    @Override
    Map<Short, ReferralResponseType> getCodeMap() {
      return codeMap;
    }
  }

}
