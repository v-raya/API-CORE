package gov.ca.cwds.data.legacy.cms.entity;

import gov.ca.cwds.data.legacy.cms.CmsPersistentObject;
import gov.ca.cwds.data.legacy.cms.entity.syscodes.ApprovalStatusType;
import gov.ca.cwds.data.legacy.cms.entity.syscodes.County;
import gov.ca.cwds.data.persistence.CompositeKey;
import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;
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

  @NotFound(action = NotFoundAction.IGNORE)
  @ManyToOne(fetch = FetchType.LAZY)
  @Fetch(FetchMode.SELECT)
  @JoinColumn(name = "APV_STC", referencedColumnName = "SYS_ID", insertable = false, updatable = false)
  private ApprovalStatusType approvalStatusType;

  @Type(type = "short")
  @Column(name = "DSP_RSNC")
  private Short dispositionClosureReasonType;

  @Column(name = "DISPSTN_CD")
  private String dispositionCode;

  @Type(type = "date")
  @Column(name = "RCL_DISPDT")
  private Date dispositionDate;

  @Type(type = "yes_no")
  @Column(name = "SLFRPT_IND")
  private Boolean selfReportedIndicator;

  @Type(type = "yes_no")
  @Column(name = "STFADD_IND")
  private Boolean staffPersonAddedIndicator;

  @Column(name = "DSP_CLSDSC")
  private String dispositionClosureDescription;

  @Type(type = "short")
  @Column(name = "RFCL_AGENO")
  private Short ageNumber;

  @Column(name = "AGE_PRD_CD")
  private String agePeriodCode;

  @NotFound(action = NotFoundAction.IGNORE)
  @ManyToOne(fetch = FetchType.LAZY)
  @Fetch(FetchMode.SELECT)
  @JoinColumn(
      name = "CNTY_SPFCD",
      referencedColumnName = "SYS_ID",
      insertable = false,
      updatable = false
  )
  private County countySpecificCode;

  @Column(name = "MHLTH_IND")
  private String mentalHealthIssuesIndicator;

  @Type(type = "yes_no")
  @Column(name = "ALCHL_IND")
  private Boolean alcoholIndicator;

  @Type(type = "yes_no")
  @Column(name = "DRUG_IND")
  private Boolean drugIndicator;

  @Override
  public Serializable getPrimaryKey() {
    return new CompositeKey(getReferral(), getClient());
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

  public ApprovalStatusType getApprovalStatusType() {
    return approvalStatusType;
  }

  public void setApprovalStatusType(
      ApprovalStatusType approvalStatusType) {
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

  public County getCountySpecificCode() {
    return countySpecificCode;
  }

  public void setCountySpecificCode(County countySpecificCode) {
    this.countySpecificCode = countySpecificCode;
  }

  public String getMentalHealthIssuesIndicator() {
    return mentalHealthIssuesIndicator;
  }

  public void setMentalHealthIssuesIndicator(String mentalHealthIssuesIndicator) {
    this.mentalHealthIssuesIndicator = mentalHealthIssuesIndicator;
  }

  public Boolean getSelfReportedIndicator() {
    return selfReportedIndicator;
  }

  public void setSelfReportedIndicator(Boolean selfReportedIndicator) {
    this.selfReportedIndicator = selfReportedIndicator;
  }

  public Boolean getStaffPersonAddedIndicator() {
    return staffPersonAddedIndicator;
  }

  public void setStaffPersonAddedIndicator(Boolean staffPersonAddedIndicator) {
    this.staffPersonAddedIndicator = staffPersonAddedIndicator;
  }

  public Boolean getAlcoholIndicator() {
    return alcoholIndicator;
  }

  public void setAlcoholIndicator(Boolean alcoholIndicator) {
    this.alcoholIndicator = alcoholIndicator;
  }

  public Boolean getDrugIndicator() {
    return drugIndicator;
  }

  public void setDrugIndicator(Boolean drugIndicator) {
    this.drugIndicator = drugIndicator;
  }
}
