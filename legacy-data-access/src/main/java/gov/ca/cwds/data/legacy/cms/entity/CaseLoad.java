package gov.ca.cwds.data.legacy.cms.entity;

import gov.ca.cwds.data.legacy.cms.CmsPersistentObject;
import gov.ca.cwds.data.legacy.cms.entity.syscodes.County;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import org.hibernate.annotations.ColumnTransformer;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;
import org.hibernate.annotations.Type;

/**
 * @author CWDS TPT-3 Team
 */
@Entity
@Table(name = "CASE_LDT")
public class CaseLoad extends CmsPersistentObject {

  private static final long serialVersionUID = -971097719222381738L;

  @Id
  @Column(name = "IDENTIFIER", nullable = false, length = 10)
  private String identifier;

  @Column(name = "ARCASS_IND", nullable = false, length = 1)
  @Type(type = "yes_no")
  private boolean archiveAssociation;

  @Column(name = "ASGDSK_IND", nullable = false, length = 1)
  @Type(type = "yes_no")
  private boolean assignmentDeskCaseLoad;

  @Column(name = "CLD_CEILNO", nullable = false, precision = 3)
  private BigDecimal ceilingNumber;

  @Column(name = "IDENTFR_NM", nullable = false, length = 30)
  @ColumnTransformer(read = "trim(IDENTFR_NM)")
  private String identifierName;

  @Column(name = "ON_HLD_IND", nullable = false, length = 1)
  @Type(type = "yes_no")
  private boolean onHold;

  @Column(name = "START_DT", nullable = false)
  private LocalDate startDate;

  @Column(name = "END_DT")
  private LocalDate endDate;

  /**
   * @deprecated XTools: This attribute is no longer used per SCR 8688 (R7.5.0)
   *
   * The field doesn't have a setter (getter only), it is not mapped to Enum as we usually do
   * and it has a default "None" value since it is deprecated.
   */
  @Deprecated
  @Column(name = "CASELD_B", nullable = false, length = 1)
  private char caseLoadIndicator = 'N';

  @NotFound(action = NotFoundAction.IGNORE)
  @ManyToOne(fetch = FetchType.LAZY)
  @Fetch(FetchMode.SELECT)
  @JoinColumn(name = "CNTY_SPFCD", referencedColumnName = "SYS_ID", insertable = false, updatable = false)
  private County county;

  @Column(name = "FKASG_UNIT", nullable = false, length = 10)
  private String fkasgUnit;

  @OneToMany(fetch = FetchType.LAZY)
  @JoinColumn(name = "FKCASE_LDT")
  private List<StaffPersonCaseLoad> staffPersonCaseLoads;

  @OneToMany(fetch = FetchType.LAZY)
  @JoinColumn(name = "FKCASE_LDT")
  private List<CaseAssignment> caseAssignments;

  @NotFound(action = NotFoundAction.IGNORE)
  @OneToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "IDENTIFIER", referencedColumnName = "FKCASE_LDT")
  private CaseLoadWeighting caseLoadWeighting;

  @Override
  public Serializable getPrimaryKey() {
    return getIdentifier();
  }

  public String getIdentifier() {
    return identifier;
  }

  public void setIdentifier(String identifier) {
    this.identifier = identifier;
  }

  public boolean getAssignmentDeskCaseLoad() {
    return assignmentDeskCaseLoad;
  }

  public void setAssignmentDeskCaseLoad(boolean assignmentDeskCaseLoad) {
    this.assignmentDeskCaseLoad = assignmentDeskCaseLoad;
  }

  public BigDecimal getCeilingNumber() {
    return ceilingNumber;
  }

  public void setCeilingNumber(BigDecimal ceilingNumber) {
    this.ceilingNumber = ceilingNumber;
  }

  public LocalDate getEndDate() {
    return endDate;
  }

  public void setEndDate(LocalDate endDt) {
    this.endDate = endDt;
  }

  public String getIdentifierName() {
    return identifierName;
  }

  public void setIdentifierName(String identfrNm) {
    this.identifierName = identfrNm;
  }

  public boolean getOnHold() {
    return onHold;
  }

  public void setOnHold(boolean onHold) {
    this.onHold = onHold;
  }

  public LocalDate getStartDate() {
    return startDate;
  }

  public void setStartDate(LocalDate startDt) {
    this.startDate = startDt;
  }

  public String getFkasgUnit() {
    return fkasgUnit;
  }

  public void setFkasgUnit(String fkasgUnit) {
    this.fkasgUnit = fkasgUnit;
  }

  public char getCaseLoadIndicator() {
    return caseLoadIndicator;
  }

  public County getCounty() {
    return county;
  }

  public void setCounty(County county) {
    this.county = county;
  }

  public boolean getArchiveAssociation() {
    return archiveAssociation;
  }

  public void setArchiveAssociation(boolean arcassInd) {
    this.archiveAssociation = arcassInd;
  }

  public List<StaffPersonCaseLoad> getStaffPersonCaseLoads() {
    return staffPersonCaseLoads;
  }

  public void setStaffPersonCaseLoads(List<StaffPersonCaseLoad> staffPersonCaseLoad) {
    this.staffPersonCaseLoads = staffPersonCaseLoad;
  }

  public List<CaseAssignment> getCaseAssignments() {
    return caseAssignments;
  }

  public void setCaseAssignments(
      List<CaseAssignment> caseAssignments) {
    this.caseAssignments = caseAssignments;
  }

  public CaseLoadWeighting getCaseLoadWeighting() {
    return caseLoadWeighting;
  }

  public void setCaseLoadWeighting(
      CaseLoadWeighting caseLoadWeightings) {
    this.caseLoadWeighting = caseLoadWeightings;
  }
}
