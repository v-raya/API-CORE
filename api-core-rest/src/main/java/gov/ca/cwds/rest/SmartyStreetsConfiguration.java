package gov.ca.cwds.rest;

import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Represents the configuration settings for SmartyStreets
 * 
 * @author CWDS API Team
 */
public class SmartyStreetsConfiguration {

  @NotNull
  @JsonProperty("clientId")
  private String clientId;

  @NotNull
  @JsonProperty("token")
  private String token;

  @NotNull
  @JsonProperty("maxCandidates")
  private Integer maxCandidates;

  /**
   * @return the id
   */
  public String getClientId() {
    return clientId;
  }

  /**
   * @return the token
   */
  public String getToken() {
    return token;
  }

  /**
   * @return the maxCandidates
   */
  public Integer getMaxCandidates() {
    return maxCandidates;
  }

}
