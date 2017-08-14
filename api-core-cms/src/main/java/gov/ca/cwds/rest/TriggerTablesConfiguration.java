package gov.ca.cwds.rest;

import com.fasterxml.jackson.annotation.JsonProperty;
import javax.validation.constraints.NotNull;

/**
 * Represents the configuration settings for TriggerTables
 *
 */
public class TriggerTablesConfiguration {

  @NotNull
  @JsonProperty("laCountySpecificCode")
  private String laCountySpecificCode;

  /**
   * @return the countySpecificCode
   */
  public String getLaCountySpecificCode() {
    return laCountySpecificCode;
  }

}
