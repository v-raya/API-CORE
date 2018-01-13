package gov.ca.cwds.data.legacy.cms.entity;

import gov.ca.cwds.data.legacy.cms.CmsPersistentObject;
import gov.ca.cwds.data.legacy.cms.entity.enums.AssignmentRecipient;
import gov.ca.cwds.data.legacy.cms.entity.enums.AssignmentType;
import gov.ca.cwds.data.legacy.cms.entity.syscodes.County;
import gov.ca.cwds.data.legacy.cms.entity.syscodes.SecondaryAssignmentRoleType;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;
import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import org.hibernate.annotations.ColumnTransformer;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

/**
 * Entity representing an ASSIGNMENT from legacy system.
 * Identifies the responsibility of a specific STAFF PERSON to a specific CASE or REFERRAL.
 * At any point in time, there may be many activities related to a REFERRAL or CASE for which
 * different STAFF PERSONs have varying responsibilities; however, only one individual can be
 * assigned the primary responsibility for that REFERRAL or CASE at a time. Secondary assignments
 * are also made to CASEs and/or REFERRALs. For example, providing courtesy supervision or
 * attending a court hearing may be  performed by a secondary worker. Over time, the STAFF PERSONs
 * assigned to a CASE or REFERRAL may change. When an ASSIGNMENT is  transferred to a new worker,
 * the old ASSIGNMENT will be end dated, and a new occurrence of ASSIGNMENT will be created
 * for the new STAFF PERSON.
 *
 * @author CWDS TPT-3 Team
 */
@Entity
@DiscriminatorColumn(name = "ESTBLSH_CD")
@Table(name = "ASGNM_T")
public abstract class BaseAssignment extends CmsPersistentObject {

  private static final long serialVersionUID = -1710649737368440885L;

  @Id
  @Column(name = "IDENTIFIER", nullable = false, length = 10)
  private String identifier;

  @Column(name = "ESTBLSH_ID", nullable = false, length = 10)
  private String establishedForId;

  @Column(name = "ESTBLSH_CD", nullable = false, length = 1, insertable=false, updatable=false)
  @Convert(converter = AssignmentRecipient.AssignmentRecipientConverter.class)
  private AssignmentRecipient establishedFor;

  @NotFound(action = NotFoundAction.IGNORE)
  @ManyToOne(fetch = FetchType.LAZY)
  @Fetch(FetchMode.SELECT)
  @JoinColumn(name = "ASSG_RLC", referencedColumnName = "SYS_ID", insertable = false, updatable = false)
  private SecondaryAssignmentRoleType secondaryAssignmentRoleType;

  @Column(name = "ASGNMNT_CD", nullable = false, length = 1)
  @Convert(converter = AssignmentType.AssignmentTypeConverter.class)
  private AssignmentType assignmentType;

  @Column(name = "WGHTNG_NO", nullable = false, precision = 3)
  private BigDecimal weightingNumber;

  @Column(name = "RESPL_DSC", nullable = false, length = 160)
  @ColumnTransformer(read = "trim(RESPL_DSC)")
  private String responsibilityDescription;

  @NotFound(action = NotFoundAction.IGNORE)
  @ManyToOne(fetch = FetchType.LAZY)
  @Fetch(FetchMode.SELECT)
  @JoinColumn(name = "CNTY_SPFCD", referencedColumnName = "SYS_ID", insertable = false, updatable = false)
  private County county;

  @Column(name = "START_DT", nullable = false)
  private LocalDate startDate;

  @Column(name = "START_TM", nullable = false)
  private LocalTime startTime;

  @Column(name = "END_DT")
  private LocalDate endDate;

  @Column(name = "END_TM")
  private LocalTime endTime;

  @Column(name = "FKCASE_LDT", length = 10)
  private String fkCaseLoad;

  @Column(name = "FKOST_PTYT", length = 10)
  private String fkOutOfStateContactParty;

  @NotFound(action = NotFoundAction.IGNORE)
  @ManyToOne(fetch = FetchType.LAZY)
  @Fetch(FetchMode.SELECT)
  @JoinColumn(name = "FKCASE_LDT", referencedColumnName = "IDENTIFIER", insertable = false, updatable = false)
  private CaseLoad caseLoad;

  @Override
  public Serializable getPrimaryKey() {
    return identifier;
  }

  public String getIdentifier() {
    return identifier;
  }

  public void setIdentifier(String identifier) {
    this.identifier = identifier;
  }

  public LocalDate getEndDate() {
    return endDate;
  }

  public void setEndDate(LocalDate endDt) {
    this.endDate = endDt;
  }

  public LocalTime getEndTime() {
    return endTime;
  }

  public void setEndTime(LocalTime endTm) {
    this.endTime = endTm;
  }

  public String getEstablishedForId() {
    return establishedForId;
  }

  public void setEstablishedForId(String estblshId) {
    this.establishedForId = estblshId;
  }

  public AssignmentRecipient getEstablishedFor() {
    return establishedFor;
  }

  public void setEstablishedFor(
      AssignmentRecipient establishedFor) {
    this.establishedFor = establishedFor;
  }

  public SecondaryAssignmentRoleType getSecondaryAssignmentRoleType() {
    return secondaryAssignmentRoleType;
  }

  public void setSecondaryAssignmentRoleType(
      SecondaryAssignmentRoleType secondaryAssignmentRoleType) {
    this.secondaryAssignmentRoleType = secondaryAssignmentRoleType;
  }

  public LocalDate getStartDate() {
    return startDate;
  }

  public void setStartDate(LocalDate startDt) {
    this.startDate = startDt;
  }

  public LocalTime getStartTime() {
    return startTime;
  }

  public void setStartTime(LocalTime startTm) {
    this.startTime = startTm;
  }

  public AssignmentType getAssignmentType() {
    return assignmentType;
  }

  public void setAssignmentType(AssignmentType asgnmntCd) {
    this.assignmentType = asgnmntCd;
  }

  public BigDecimal getWeightingNumber() {
    return weightingNumber;
  }

  public void setWeightingNumber(BigDecimal wghtngNo) {
    this.weightingNumber = wghtngNo;
  }

  public String getFkOutOfStateContactParty() {
    return fkOutOfStateContactParty;
  }

  public void setFkOutOfStateContactParty(String fkostPtyt) {
    this.fkOutOfStateContactParty = fkostPtyt;
  }

  public String getFkCaseLoad() {
    return fkCaseLoad;
  }

  public void setFkCaseLoad(String fkcaseLdt) {
    this.fkCaseLoad = fkcaseLdt;
  }

  public String getResponsibilityDescription() {
    return responsibilityDescription;
  }

  public void setResponsibilityDescription(String resplDsc) {
    this.responsibilityDescription = resplDsc;
  }

  public County getCounty() {
    return county;
  }

  public void setCounty(County county) {
    this.county = county;
  }

  public CaseLoad getCaseLoad() {
    return caseLoad;
  }

  public void setCaseLoad(CaseLoad caseLoad) {
    this.caseLoad = caseLoad;
  }

}
