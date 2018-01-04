package gov.ca.cwds.data.legacy.cms.entity.enums;

import java.util.Collections;
import java.util.Map;
import javax.persistence.Converter;

/** @author CWDS CASE API Team */
public enum ResponsibleAgency implements EntityEnum<String> {
  PRIVATE_ADOPTION_AGENCY("A", "Private Adoption Agency"),
  COUNTY_WELFARE_DEPARTMENT("C", "County Welfare Department"),
  INDIAN_CHILD_WELFARE("I", "Indian Child Welfare"),
  KIN_GAP("K", "Kin-Gap"),
  MENTAL_HEALTH("M", "Mental Health"),
  OUT_OF_STATE_AGENCY("O", "Out of State Agency"),
  PROBATION("P", "Probation"),
  STATE_ADOPTION_DISTRICT_OFFICE("S", "State Adoption District Office");

  private final String code;
  private final String description;

  ResponsibleAgency(String code, String description) {
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
  public static class ResponsibleAgencyConverter
      extends BaseEntityEnumConverter<ResponsibleAgency, String> {

    private static final Map<String, ResponsibleAgency> codeMap =
        Collections.unmodifiableMap(initializeMapping(ResponsibleAgency.values()));

    @Override
    Map<String, ResponsibleAgency> getCodeMap() {
      return codeMap;
    }
  }
}
