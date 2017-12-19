package gov.ca.cwds.data.legacy.cms.entity;

import gov.ca.cwds.data.legacy.cms.CmsPersistentObject;
import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import org.hibernate.annotations.Type;

/**
 * @author CWDS TPT-3 Team
 */
@Entity
@Table(name = "REFR_CLT")
public class ReferralClient extends CmsPersistentObject {

  @Id
  @Column(name = "FKREFERL_T", length = CMS_ID_LEN)
  private String referral;

  @Id
  @Column(name = "FKCLIENT_T", length = CMS_ID_LEN)
  private String client;

  @Column(name = "APRVL_NO", length = CMS_ID_LEN)
  private String approvalNumber;

  @Type(type = "short")
  @Column(name = "APV_STC")
  private Short approvalStatusType;

  @Type(type = "short")
  @Column(name = "DSP_RSNC")
  private Short dispositionClosureReasonType;

  @Column(name = "DISPSTN_CD")
  private String dispositionCode;

  @Type(type = "date")
  @Column(name = "RCL_DISPDT")
  private Date dispositionDate;

  @Column(name = "SLFRPT_IND")
  private String selfReportedIndicator;

  @Column(name = "STFADD_IND")
  private String staffPersonAddedIndicator;

  @Column(name = "DSP_CLSDSC")
  private String dispositionClosureDescription;

  @Type(type = "short")
  @Column(name = "RFCL_AGENO")
  private Short ageNumber;

  @Column(name = "AGE_PRD_CD")
  private String agePeriodCode;

  @Column(name = "CNTY_SPFCD")
  private String countySpecificCode;

  @Column(name = "MHLTH_IND")
  private String mentalHealthIssuesIndicator;

  @Column(name = "ALCHL_IND")
  private String alcoholIndicator;

  @Column(name = "DRUG_IND")
  private String drugIndicator;

  @Override
  public Serializable getPrimaryKey() {
    //return new EmbeddableCompositeKey2();
    return null;
  }

  public String getReferral() {
    return referral;
  }

  public void setReferral(String referral) {
    this.referral = referral;
  }

  public String getClient() {
    return client;
  }

  public void setClient(String client) {
    this.client = client;
  }

  public String getApprovalNumber() {
    return approvalNumber;
  }

  public void setApprovalNumber(String approvalNumber) {
    this.approvalNumber = approvalNumber;
  }

  public Short getApprovalStatusType() {
    return approvalStatusType;
  }

  public void setApprovalStatusType(Short approvalStatusType) {
    this.approvalStatusType = approvalStatusType;
  }

  public Short getDispositionClosureReasonType() {
    return dispositionClosureReasonType;
  }

  public void setDispositionClosureReasonType(Short dispositionClosureReasonType) {
    this.dispositionClosureReasonType = dispositionClosureReasonType;
  }

  public String getDispositionCode() {
    return dispositionCode;
  }

  public void setDispositionCode(String dispositionCode) {
    this.dispositionCode = dispositionCode;
  }

  public Date getDispositionDate() {
    return dispositionDate;
  }

  public void setDispositionDate(Date dispositionDate) {
    this.dispositionDate = dispositionDate;
  }

  public String getSelfReportedIndicator() {
    return selfReportedIndicator;
  }

  public void setSelfReportedIndicator(String selfReportedIndicator) {
    this.selfReportedIndicator = selfReportedIndicator;
  }

  public String getStaffPersonAddedIndicator() {
    return staffPersonAddedIndicator;
  }

  public void setStaffPersonAddedIndicator(String staffPersonAddedIndicator) {
    this.staffPersonAddedIndicator = staffPersonAddedIndicator;
  }

  public String getDispositionClosureDescription() {
    return dispositionClosureDescription;
  }

  public void setDispositionClosureDescription(String dispositionClosureDescription) {
    this.dispositionClosureDescription = dispositionClosureDescription;
  }

  public Short getAgeNumber() {
    return ageNumber;
  }

  public void setAgeNumber(Short ageNumber) {
    this.ageNumber = ageNumber;
  }

  public String getAgePeriodCode() {
    return agePeriodCode;
  }

  public void setAgePeriodCode(String agePeriodCode) {
    this.agePeriodCode = agePeriodCode;
  }

  public String getCountySpecificCode() {
    return countySpecificCode;
  }

  public void setCountySpecificCode(String countySpecificCode) {
    this.countySpecificCode = countySpecificCode;
  }

  public String getMentalHealthIssuesIndicator() {
    return mentalHealthIssuesIndicator;
  }

  public void setMentalHealthIssuesIndicator(String mentalHealthIssuesIndicator) {
    this.mentalHealthIssuesIndicator = mentalHealthIssuesIndicator;
  }

  public String getAlcoholIndicator() {
    return alcoholIndicator;
  }

  public void setAlcoholIndicator(String alcoholIndicator) {
    this.alcoholIndicator = alcoholIndicator;
  }

  public String getDrugIndicator() {
    return drugIndicator;
  }

  public void setDrugIndicator(String drugIndicator) {
    this.drugIndicator = drugIndicator;
  }
}
