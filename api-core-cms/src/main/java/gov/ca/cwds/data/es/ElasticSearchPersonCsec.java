package gov.ca.cwds.data.es;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import gov.ca.cwds.data.std.ApiObjectIdentity;

/**
 * Person CSEC history.
 * 
 * @author CWDS API Team
 */
public class ElasticSearchPersonCsec extends ApiObjectIdentity {

  private static final long serialVersionUID = 1L;

  @JsonProperty("id")
  private String id;

  @JsonProperty("start_date")
  private String startDate;

  @JsonProperty("end_date")
  private String endDate;

  @JsonProperty("csec_code_id")
  private String csecCodeId;

  @JsonProperty("description")
  private String csecDesc;

  @JsonProperty("legacy_descriptor")
  @JsonInclude(JsonInclude.Include.NON_NULL)
  private ElasticSearchLegacyDescriptor legacyDescriptor = new ElasticSearchLegacyDescriptor();

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getStartDate() {
    return startDate;
  }

  public void setStartDate(String startDate) {
    this.startDate = startDate;
  }

  public String getEndDate() {
    return endDate;
  }

  public void setEndDate(String endDate) {
    this.endDate = endDate;
  }

  public String getCsecCodeId() {
    return csecCodeId;
  }

  public void setCsecCodeId(String csecCodeId) {
    this.csecCodeId = csecCodeId;
  }

  public String getCsecDesc() {
    return csecDesc;
  }

  public void setCsecDesc(String csecDesc) {
    this.csecDesc = csecDesc;
  }

  public ElasticSearchLegacyDescriptor getLegacyDescriptor() {
    return legacyDescriptor;
  }

  public void setLegacyDescriptor(ElasticSearchLegacyDescriptor legacyDescriptor) {
    this.legacyDescriptor = legacyDescriptor;
  }

}
