package gov.ca.cwds.rest.api.domain.cms;

import org.apache.commons.lang3.StringUtils;

/**
 * Enumeration of legacy table names and human readable descriptions.
 * 
 * @author CWDS API Team
 */
public enum LegacyTable {

  //
  // CHECKSTYLE:OFF
  //

  ADULT_IN_PLACEMENT_HOME("OTH_ADLT", "Adult in placement home"),

  ADDRESS("ADDRS_T", "Address"),

  ADDRESS_PHONETIC("ADR_PHTT", "Address Phonetic"),

  ALLEGATION("ALLGTN_T", "Allegation"),

  ALIAS_OR_OTHER_CLIENT_NAME("OCL_NM_T", "Alias or other client name"),

  ASSIGNMENT("ASGNM_T", "Assignment"),

  ATTORNEY("ATTRNY_T", "Attorney"),

  CASE("CASE_T", "Case"),

  CLIENT("CLIENT_T", "Client"),

  CLIENT_ADDRESS("CL_ADDRT", "Client Address"),

  CLINET_PHONETIC("CLT_PHTT", "Client Phonetic"),

  CROSS_REPORT("CRSS_RPT", "Cross Report"),

  COLLATERAL_INDIVIDUAL("COLTRL_T", "Collateral individual"),

  CLIENT_RELATIONSHIP("CLN_RELT", "Client Relationship"),

  CHILD_IN_PLACEMENT_HOME("OTH_KIDT", "Child in placement home"),

  DELIVERED_SERVICE("DL_SVC_T", "Delivered Service"),

  EDUCATION_PROVIDER("EDPRVCNT", "Education provider"),

  GOVERNMENT_ORGANIZATION_ENTITY("GVR_ENTC", "Government Organization Entity"),

  REFERRAL("REFERL_T", "Referral"),

  REPORTER("REPTR_T", "Reporter"),

  REFERRAL_CLIENT("REFR_CLT", "Referral Client"),

  SERVICE_PROVIDER("SVC_PVRT", "Service provider"),

  SUBSTITUTE_CARE_PROVIDER("SB_PVDRT", "Substitute care provider"),

  SUBSTITUTE_CARE_PROVIDER_PHONETIC("SCP_PHTT", "Substitute care provider phonetic"),

  STAFF_PERSON("STFPERST", "Staff"),

  SAFETY_ALERT("SAF_ALRT", "Safety Alert");

  //
  // CHECKSTYLE:ON
  //

  private String name;
  private String description;

  /**
   * Construct with args.
   * 
   * @param name Legacy table name
   * @param description Legacy table human readable description.
   */
  private LegacyTable(String name, String description) {
    this.name = name;
    this.description = description;
  }

  /**
   * Get Legacy table name.
   * 
   * @return Legacy table name.
   */
  public String getName() {
    return this.name;
  }

  /**
   * Get legacy table human readable description.
   * 
   * @return description of this legacy table
   */
  public String getDescription() {
    return description;
  }

  /**
   * Lookup a legacy table by table name.
   * 
   * @param tableName The legacy table name
   * @return LegacyTable for given name if found, null otherwise.
   */
  public static LegacyTable lookupByName(String tableName) {
    if (StringUtils.isBlank(tableName)) {
      return null;
    }

    LegacyTable legacyTable = null;
    for (LegacyTable lt : LegacyTable.values()) {
      if (lt.getName().equals(tableName.trim())) {
        legacyTable = lt;
        break;
      }
    }
    return legacyTable;
  }
}
