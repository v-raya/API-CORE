package gov.ca.cwds.data.rules;

import com.google.inject.Inject;

import gov.ca.cwds.rest.TriggerTablesConfiguration;

/**
 * A DAO for Trigger tables.
 * 
 * @author CWDS API Team
 */
public class TriggerTablesDao {

  private final String laCountySpecificCode;

  /**
   * Constructor. Construct from required fields.
   * 
   * @param laCountySpecificCode The LA staff Person
   */
  public TriggerTablesDao(String laCountySpecificCode) {
    super();
    this.laCountySpecificCode = laCountySpecificCode;
  }

  /**
   * Constructor. Construct from YAML configuration.
   * 
   * @param triggerConfig The TriggerTables configuration
   */
  @Inject
  public TriggerTablesDao(TriggerTablesConfiguration triggerConfig) {
    this.laCountySpecificCode = triggerConfig.getLaCountySpecificCode();
  }

  /**
   * @return the laCountySpecificCode
   */
  public String getLaCountySpecificCode() {
    return laCountySpecificCode;
  }

}
