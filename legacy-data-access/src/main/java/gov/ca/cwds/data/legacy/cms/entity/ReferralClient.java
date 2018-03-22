package gov.ca.cwds.data.legacy.cms.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Embeddable;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;
import org.hibernate.annotations.Type;

import gov.ca.cwds.data.legacy.cms.CmsPersistentObject;
import gov.ca.cwds.data.legacy.cms.entity.converter.NullableBooleanConverter;
import gov.ca.cwds.data.legacy.cms.entity.syscodes.ApprovalStatusType;
import gov.ca.cwds.data.legacy.cms.entity.syscodes.County;
import gov.ca.cwds.data.persistence.CompositeKey;

/** @author CWDS TPT-3 Team */
@Entity
@Table(name = "REFR_CLT")
public class ReferralClient extends CmsPersistentObject {

  @EmbeddedId
  private ReferralId embeded = new ReferralId();

  @JoinColumn(name = "FKREFERL_T", insertable = false, updatable = false,
      referencedColumnName = "IDENTIFIER")
  @ManyToOne
  private Referral referral;

  @JoinColumn(name = "FKCLIENT_T", insertable = false, updatable = false,
      referencedColumnName = "IDENTIFIER")
  @ManyToOne
  private Client client;

  @Column(name = "APRVL_NO", length = CMS_ID_LEN)
  private String approvalNumber;

  @NotFound(action = NotFoundAction.IGNORE)
  @ManyToOne(fetch = FetchType.LAZY)
  @Fetch(FetchMode.SELECT)
  @JoinColumn(name = "APV_STC", referencedColumnName = "SYS_ID")
  private ApprovalStatusType approvalStatusType;

  @Type(type = "short")
  @Column(name = "DSP_RSNC")
  private short dispositionClosureReasonType;

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
  private boolean staffPersonAddedIndicator;

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
  @JoinColumn(name = "CNTY_SPFCD", referencedColumnName = "SYS_ID")
  private County countySpecificCode;

  @Column(name = "MHLTH_IND")
  private String mentalHealthIssuesIndicator;

  @Convert(converter = NullableBooleanConverter.class)
  @Column(name = "ALCHL_IND")
  private Boolean alcoholIndicator;

  @Convert(converter = NullableBooleanConverter.class)
  @Column(name = "DRUG_IND")
  private Boolean drugIndicator;

  @Override
  public Serializable getPrimaryKey() {
    return new CompositeKey(getReferral().getId(), getClient().getIdentifier());
  }

  public Referral getReferral() {
    return referral;
  }

  public void setReferral(Referral referral) {
    this.referral = referral;
  }

  public Client getClient() {
    return client;
  }

  public void setClient(Client client) {
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

  public void setApprovalStatusType(ApprovalStatusType approvalStatusType) {
    this.approvalStatusType = approvalStatusType;
  }

  public short getDispositionClosureReasonType() {
    return dispositionClosureReasonType;
  }

  public void setDispositionClosureReasonType(short dispositionClosureReasonType) {
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

  public Boolean getSelfReportedIndicator() {
    return selfReportedIndicator;
  }

  public void setSelfReportedIndicator(Boolean selfReportedIndicator) {
    this.selfReportedIndicator = selfReportedIndicator;
  }

  public boolean isStaffPersonAddedIndicator() {
    return staffPersonAddedIndicator;
  }

  public void setStaffPersonAddedIndicator(boolean staffPersonAddedIndicator) {
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

  public ReferralId getEmbeded() {
    return embeded;
  }

  public void setEmbeded(ReferralId embeded) {
    this.embeded = embeded;
  }

  @Embeddable
  public static class ReferralId implements Serializable {
    @Column(name = "FKREFERL_T")
    private String referralId;

    @Column(name = "FKCLIENT_T")
    private String clientId;

    public ReferralId() {}

    public ReferralId(String referralId, String clientId) {
      this.referralId = referralId;
      this.clientId = clientId;
    }

    @Override
    public boolean equals(Object o) {
      if (o instanceof ReferralId) {
        ReferralId that = (ReferralId) o;
        return this.referralId.equals(that.referralId) && this.clientId.equals(that.clientId);
      } else {
        return false;
      }
    }

    @Override
    public int hashCode() {
      return clientId.hashCode() + referralId.hashCode();
    }
  }
}
