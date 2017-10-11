package gov.ca.cwds.data.es;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import gov.ca.cwds.data.std.ApiObjectIdentity;

public class ElasticSearchRaceAndEthnicity extends ApiObjectIdentity {

  private static final long serialVersionUID = -862544185919199877L;

  @JsonProperty("races")
  private List<ElasticSearchSystemCode> races;

  @JsonProperty("unable_to_determine_code")
  private String unableToDetermineCode;

  @JsonProperty("hispanic_origin_code")
  private String hispanicOriginCode;

  @JsonProperty("hispanic_unable_to_determine_code")
  private String hispanicUnableToDetermineCode;

  public ElasticSearchRaceAndEthnicity() {}

  public List<ElasticSearchSystemCode> getRaces() {
    return races;
  }

  public void setRaces(List<ElasticSearchSystemCode> races) {
    this.races = races;
  }

  public String getUnableToDetermineCode() {
    return unableToDetermineCode;
  }

  public void setUnableToDetermineCode(String unableToDetermineCode) {
    this.unableToDetermineCode = unableToDetermineCode;
  }

  public String getHispanicOriginCode() {
    return hispanicOriginCode;
  }

  public void setHispanicOriginCode(String hispanicOriginCode) {
    this.hispanicOriginCode = hispanicOriginCode;
  }

  public String getHispanicUnableToDetermineCode() {
    return hispanicUnableToDetermineCode;
  }

  public void setHispanicUnableToDetermineCode(String hispanicUnableToDetermineCode) {
    this.hispanicUnableToDetermineCode = hispanicUnableToDetermineCode;
  }
}
