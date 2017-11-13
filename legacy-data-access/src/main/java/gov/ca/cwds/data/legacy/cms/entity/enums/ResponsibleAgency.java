package gov.ca.cwds.data.legacy.cms.entity.enums;

import java.util.Collections;
import java.util.Map;
import javax.persistence.Converter;

/**
 * @author CWDS CASE API Team
 */
public enum ResponsibleAgency implements EntityEnum<Character> {
  PRIVATE_ADOPTION_AGENCY('A', "Private Adoption Agency"),
  COUNTY_WELFARE_DEPARTMENT('C', "County Welfare Department"),
  INDIAN_CHILD_WELFARE('I', "Indian Child Welfare"),
  KIN_GAP('K', "Kin-Gap"),
  MENTAL_HEALTH('M', "Mental Health"),
  OUT_OF_STATE_AGENCY('O', "Out of State Agency"),
  PROBATION('P', "Probation"),
  STATE_ADOPTION_DISTRICT_OFFICE('S', "State Adoption District Office");

  private final Character code;
  private final String description;

  ResponsibleAgency(Character code, String description) {
    this.code = code;
    this.description = description;
  }

  @Override
  public Character getCode() {
    return code;
  }

  @Override
  public String getDescription() {
    return description;
  }

  @Converter
  public static class ResponsibleAgencyConverter extends
      BaseEntityEnumConvertor<ResponsibleAgency, Character> {

    private static final Map<Character, ResponsibleAgency> codeMap =
        Collections.unmodifiableMap(initializeMapping(ResponsibleAgency.values()));

    @Override
    Map<Character, ResponsibleAgency> getCodeMap() {
      return codeMap;
    }
  }
}

