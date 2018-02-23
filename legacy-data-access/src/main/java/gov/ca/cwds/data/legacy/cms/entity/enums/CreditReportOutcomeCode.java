package gov.ca.cwds.data.legacy.cms.entity.enums;

import java.util.Collections;
import java.util.Map;
import javax.persistence.Converter;

/** @author CWDS TPT-3 Team */
public enum CreditReportOutcomeCode implements EntityEnum<String> {
  CLEAR("C", "Clear"),
  NOT_CLEAR("N", "Not Clear");

  private final String code;
  private final String description;

  CreditReportOutcomeCode(String code, String description) {
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
  public static class CreditReportOutcomeCodeConverter
      extends BaseEntityEnumConverter<CreditReportOutcomeCode, String> {

    private static final Map<String, CreditReportOutcomeCode> codeMap =
        Collections.unmodifiableMap(initializeMapping(CreditReportOutcomeCode.values()));

    @Override
    Map<String, CreditReportOutcomeCode> getCodeMap() {
      return codeMap;
    }
  }
}
