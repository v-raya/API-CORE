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

  CLIENT("CLIENT_T", "Client"),

  COLLATERAL_INDIVIDUAL("COLTRL_T", "Collateral individual"),

  EDUCATION_PROVIDER("EDPRVCNT", "Education provider"),

  ATTORNEY("ATTRNY_T", "Attorney"),

  CLIENT_RELATIONSHIP("CLN_RELT", "Client Relationship"),

  ADULT_IN_PLACEMENT_HOME("OTH_ADLT", "Adult in placement home"),

  CHILD_IN_PLACEMENT_HOME("OTH_KIDT", "Child in placement home"),

  ALIAS_OR_OTHER_CLIENT_NAME("OCL_NM_T", "Alias or other client name"),

  REPORTER("REPTR_T", "Reporter"),

  SERVICE_PROVIDER("SVC_PVRT", "Service provider"),

  SUBSTITUTE_CARE_PROVIDER("SB_PVDRT", "Substitute care provider"),

  CASE("CASE_T", "Case"),

  STAFF_PERSON("STFPERST", "Staff"),

  REFERRAL("REFERL_T", "Referral"),

  ALLEGATION("ALLGTN_T", "Allegation"),

  ADDRESS("ADDRS_T", "Address");

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
