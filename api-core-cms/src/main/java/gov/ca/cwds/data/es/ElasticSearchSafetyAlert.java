package gov.ca.cwds.data.es;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import gov.ca.cwds.data.ApiTypedIdentifier;
import gov.ca.cwds.data.std.ApiObjectIdentity;

/**
 * Safety alert
 * 
 * @author CWDS API Team
 */
public class ElasticSearchSafetyAlert extends ApiObjectIdentity
    implements ApiTypedIdentifier<String> {

  private static final long serialVersionUID = 5481552446797727833L;

  @JsonProperty("id")
  private String id;

  @JsonProperty("activation_reason")
  private String activationReason;

  @JsonProperty("activation_reason_id")
  private String activationReasonId;

  @JsonProperty("activation_date")
  private String activationDate;

  @JsonProperty("activation_county")
  private String activationCounty;

  @JsonProperty("activation_county_id")
  private String activationCountyId;

  @JsonProperty("activation_explanation")
  private String activationExplanation;

  @JsonProperty("deactivation_date")
  private String deactivationDate;

  @JsonProperty("deactivation_county")
  private String deactivationCounty;

  @JsonProperty("deactivation_county_id")
  private String deactivationCountyId;

  @JsonProperty("deactivation_explanation")
  private String deactivationExplanation;

  @JsonProperty("legacy_descriptor")
  @JsonInclude(JsonInclude.Include.NON_NULL)
  private ElasticSearchLegacyDescriptor legacyDescriptor = new ElasticSearchLegacyDescriptor();

  /**
   * No-argument constructor.
   */
  public ElasticSearchSafetyAlert() {}

  @Override
  public String getId() {
    return id;
  }

  @Override
  public void setId(String id) {
    this.id = id;
  }

  public String getActivationReason() {
    return activationReason;
  }

  public void setActivationReason(String activationReason) {
    this.activationReason = activationReason;
  }

  public String getActivationDate() {
    return activationDate;
  }

  public void setActivationDate(String activationDate) {
    this.activationDate = activationDate;
  }

  public String getActivationCounty() {
    return activationCounty;
  }

  public void setActivationCounty(String activationCounty) {
    this.activationCounty = activationCounty;
  }

  public String getActivationExplanation() {
    return activationExplanation;
  }

  public void setActivationExplanation(String activationExplanation) {
    this.activationExplanation = activationExplanation;
  }

  public String getDeactivationDate() {
    return deactivationDate;
  }

  public void setDeactivationDate(String deactivationDate) {
    this.deactivationDate = deactivationDate;
  }

  public String getDeactivationCounty() {
    return deactivationCounty;
  }

  public void setDeactivationCounty(String deactivationCounty) {
    this.deactivationCounty = deactivationCounty;
  }

  public String getDeactivationExplanation() {
    return deactivationExplanation;
  }

  public void setDeactivationExplanation(String deactivationExplanation) {
    this.deactivationExplanation = deactivationExplanation;
  }

  public ElasticSearchLegacyDescriptor getLegacyDescriptor() {
    return legacyDescriptor;
  }

  public void setLegacyDescriptor(ElasticSearchLegacyDescriptor legacyDescriptor) {
    this.legacyDescriptor = legacyDescriptor;
  }

  public String getActivationReasonId() {
    return activationReasonId;
  }

  public void setActivationReasonId(String activationReasonId) {
    this.activationReasonId = activationReasonId;
  }

  public String getActivationCountyId() {
    return activationCountyId;
  }

  public void setActivationCountyId(String activationCountyId) {
    this.activationCountyId = activationCountyId;
  }

  public String getDeactivationCountyId() {
    return deactivationCountyId;
  }

  public void setDeactivationCountyId(String deactivationCountyId) {
    this.deactivationCountyId = deactivationCountyId;
  }
}
