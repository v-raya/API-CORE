package gov.ca.cwds.data.legacy.cms.entity.enums;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * @author CWDS CASE API Team
 */
public enum ResponsibleAgency {
  PRIVATE_ADOPTION_AGENCY('A', "Private Adoption Agency"),
  COUNTY_WELFARE_DEPARTMENT('C', "County Welfare Department"),
  INDIAN_CHILD_WELFARE('I', "Indian Child Welfare"),
  KIN_GAP('K', "Kin-Gap"),
  MENTAL_HEALTH('M', "Mental Health"),
  OUT_OF_STATE_AGENCY('O', "Out of State Agency"),
  PROBATION('P', " Probation"),
  STATE_ADOPTION_DISTRICT_OFFICE('S', "State Adoption District Office");

  private static final Map<Character, ResponsibleAgency> codeMap =
      Collections.unmodifiableMap(initializeMapping());

  private final Character code;
  private final String description;

  ResponsibleAgency(Character code, String description) {
    this.code = code;
    this.description = description;
  }

  public static ResponsibleAgency fromCode(Character code) {
    ResponsibleAgency result = codeMap.get(code);

    if (result == null) {
      throw new UnsupportedOperationException("The code " + code + " is not supported!");
    }
    return result;
  }

  private static Map<Character, ResponsibleAgency> initializeMapping() {
    Map result = new HashMap<Character, ResponsibleAgency>();
    for (ResponsibleAgency responsibleAgency : ResponsibleAgency.values()) {
      result.put(responsibleAgency.code, responsibleAgency);
    }
    return result;
  }

  public Character getCode() {
    return code;
  }

  public String getDescription() {
    return description;
  }
}

