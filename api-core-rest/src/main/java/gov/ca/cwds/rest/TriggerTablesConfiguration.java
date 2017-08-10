package gov.ca.cwds.rest;

import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonProperty;

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
