package gov.ca.cwds.data.legacy.cms.entity.enums;

import java.util.Collections;
import java.util.Map;
import javax.persistence.Converter;

/** @author CWDS CASE API Team */
public enum ReferralOutcome implements EntityEnum<String> {
  ACCEPTED("A", "Accepted"),
  NOT_ACCEPTED("N", "Not Accepted");

  private final String code;
  private final String description;

  ReferralOutcome(String code, String description) {
    this.code = code;
    this.description = description;
  }

  public static ReferralOutcome fromCode(String code) {
    return new ReferralOutcomeConverter().convertToEntityAttribute(code);
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
  public static class ReferralOutcomeConverter extends BaseEntityEnumConverter<ReferralOutcome, String> {

    private static final Map<String, ReferralOutcome> codeMap =
        Collections.unmodifiableMap(initializeMapping(ReferralOutcome.values()));

    @Override
    Map<String, ReferralOutcome> getCodeMap() {
      return codeMap;
    }
  }
}
