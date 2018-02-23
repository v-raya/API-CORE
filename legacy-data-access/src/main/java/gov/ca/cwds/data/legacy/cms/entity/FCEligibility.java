package gov.ca.cwds.data.legacy.cms.entity;

import gov.ca.cwds.data.legacy.cms.CmsPersistentObject;
import gov.ca.cwds.data.legacy.cms.entity.syscodes.FcEligibilityDentalReason;
import gov.ca.cwds.data.legacy.cms.entity.syscodes.FcEligibilityTermReason;
import gov.ca.cwds.data.persistence.CompositeKey;
import java.io.Serializable;
import java.time.LocalDate;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.NamedQuery;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;
import org.hibernate.annotations.Type;

/** @author CWDS TPT-3 Team */
@Entity
@Table(name = "FC_ELIGT")
@NamedQuery(
    name = FCEligibility.FIND_BY_CLIENT,
    query = "FROM gov.ca.cwds.data.legacy.cms.entity.FCEligibility WHERE childClientId = :childId and terminationDate=null"
)
public class FCEligibility extends CmsPersistentObject {

  public static final String FIND_BY_CLIENT = "FCEligibility.findByClient";

  @Column(name = "FC_ELG_DT")
  private LocalDate date;

  @NotFound(action = NotFoundAction.IGNORE)
  @ManyToOne(fetch = FetchType.LAZY)
  @Fetch(FetchMode.SELECT)
  @JoinColumn(name = "ELG_DNLC", referencedColumnName = "SYS_ID")
  private FcEligibilityDentalReason fcEligibilityDentalReason;

  @NotFound(action = NotFoundAction.IGNORE)
  @ManyToOne(fetch = FetchType.LAZY)
  @Fetch(FetchMode.SELECT)
  @JoinColumn(name = "ELG_TRMC", referencedColumnName = "SYS_ID")
  private FcEligibilityTermReason fcEligibilityTermReason;

  @Column(name = "FKCHLD_CLT")
  @Id
  private String childClientId;

  @Type(type = "yes_no")
  @Column(name = "FND_APVIND")
  private boolean fundingApprovedIndicator;

  @Column(name = "REDTRMN_DT")
  private LocalDate rededeterminationDate;

  @Column(name = "ELG_TRM_DT")
  private LocalDate terminationDate;

  @Column(name = "THIRD_ID" )
  @Id
  private String thirdId;

  @Override
  public Serializable getPrimaryKey() {
    return new CompositeKey(getThirdId(), getChildClientId());
  }

  public LocalDate getDate() {
    return date;
  }

  public void setDate(LocalDate date) {
    this.date = date;
  }

  public FcEligibilityDentalReason getFcEligibilityDentalReason() {
    return fcEligibilityDentalReason;
  }

  public void setFcEligibilityDentalReason(
      FcEligibilityDentalReason fcEligibilityDentalReason) {
    this.fcEligibilityDentalReason = fcEligibilityDentalReason;
  }

  public FcEligibilityTermReason getFcEligibilityTermReason() {
    return fcEligibilityTermReason;
  }

  public void setFcEligibilityTermReason(
      FcEligibilityTermReason fcEligibilityTermReason) {
    this.fcEligibilityTermReason = fcEligibilityTermReason;
  }

  public String getChildClientId() {
    return childClientId;
  }

  public void setChildClientId(String childClientId) {
    this.childClientId = childClientId;
  }

  public boolean isFundingApprovedIndicator() {
    return fundingApprovedIndicator;
  }

  public void setFundingApprovedIndicator(boolean fundingApprovedIndicator) {
    this.fundingApprovedIndicator = fundingApprovedIndicator;
  }

  public LocalDate getRededeterminationDate() {
    return rededeterminationDate;
  }

  public void setRededeterminationDate(LocalDate rededeterminationDate) {
    this.rededeterminationDate = rededeterminationDate;
  }

  public LocalDate getTerminationDate() {
    return terminationDate;
  }

  public void setTerminationDate(LocalDate terminationDate) {
    this.terminationDate = terminationDate;
  }

  public String getThirdId() {
    return thirdId;
  }

  public void setThirdId(String thirdId) {
    this.thirdId = thirdId;
  }
}
