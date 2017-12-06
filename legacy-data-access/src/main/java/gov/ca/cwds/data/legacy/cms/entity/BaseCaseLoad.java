package gov.ca.cwds.data.legacy.cms.entity;

import gov.ca.cwds.data.legacy.cms.CmsPersistentObject;
import gov.ca.cwds.data.legacy.cms.entity.syscodes.County;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;
import org.hibernate.annotations.Type;

/**
 * Entity representing CASE_LOAD from legacy system.
 * A collection of all primary and/or secondary ASSIGNMENTS for a specific STAFF PERSON within
 * a unit at a particular point in time. When a STAFF PERSON stops working on a particular CASE LOAD,
 * the CASE LOAD is end dated and any active ASSIGNMENTs should be reassigned to either a new
 * STAFF PERSON or the supervisor of the unit.
 *
 * @author CWDS TPT-3 Team
 */
@MappedSuperclass
public abstract class BaseCaseLoad extends CmsPersistentObject {

  private static final long serialVersionUID = 83888166138861050L;

  @Id
  @Column(name = "IDENTIFIER", nullable = false, length = 10)
  private String id;

  @Column(name = "ARCASS_IND", nullable = false, length = 1)
  @Type(type = "yes_no")
  private Boolean archiveAssociation;

  @Column(name = "ASGDSK_IND", nullable = false, length = 1)
  @Type(type = "yes_no")
  private Boolean assignmentDeskCaseLoad;

  @Column(name = "CLD_CEILNO", nullable = false, precision = 3)
  private BigDecimal ceilingNumber;

  @Column(name = "IDENTFR_NM", nullable = false, length = 30)
  private String identifierName;

  @Column(name = "ON_HLD_IND", nullable = false, length = 1)
  @Type(type = "yes_no")
  private Boolean onHold;

  @Column(name = "START_DT", nullable = false)
  private LocalDate startDate;

  @Column(name = "END_DT")
  private LocalDate endDate;

  @Column(name = "CASELD_B", nullable = false, length = 1)
  @Type(type = "yes_no")
  private Boolean caseLoadIndicator;

  @NotFound(action = NotFoundAction.IGNORE)
  @ManyToOne(fetch = FetchType.LAZY)
  @Fetch(FetchMode.SELECT)
  @JoinColumn(name = "CNTY_SPFCD", referencedColumnName = "SYS_ID", insertable = false, updatable = false)
  private County county;

  @Column(name = "FKASG_UNIT", nullable = false, length = 10)
  private String fkasgUnit;

  @Override
  public Serializable getPrimaryKey() {
    return getId();
  }

  public String getId() {
    return id;
  }

  public void setId(String identifier) {
    this.id = identifier;
  }

  public Boolean getAssignmentDeskCaseLoad() {
    return assignmentDeskCaseLoad;
  }

  public void setAssignmentDeskCaseLoad(Boolean assignmentDeskCaseLoad) {
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

  public Boolean getOnHold() {
    return onHold;
  }

  public void setOnHold(Boolean onHold) {
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

  public Boolean getCaseLoadIndicator() {
    return caseLoadIndicator;
  }

  public void setCaseLoadIndicator(Boolean caseLoadIndicator) {
    this.caseLoadIndicator = caseLoadIndicator;
  }

  public County getCounty() {
    return county;
  }

  public void setCounty(County county) {
    this.county = county;
  }

  public Boolean getArchiveAssociation() {
    return archiveAssociation;
  }

  public void setArchiveAssociation(Boolean arcassInd) {
    this.archiveAssociation = arcassInd;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }

    if (!(o instanceof BaseCaseLoad)) {
      return false;
    }

    BaseCaseLoad that = (BaseCaseLoad) o;

    return new EqualsBuilder()
        .appendSuper(super.equals(o))
        .append(assignmentDeskCaseLoad, that.assignmentDeskCaseLoad)
        .append(ceilingNumber, that.ceilingNumber)
        .append(endDate, that.endDate)
        .append(identifierName, that.identifierName)
        .append(onHold, that.onHold)
        .append(startDate, that.startDate)
        .append(fkasgUnit, that.fkasgUnit)
        .append(caseLoadIndicator, that.caseLoadIndicator)
        .append(county, that.county)
        .append(archiveAssociation, that.archiveAssociation)
        .isEquals();
  }

  @Override
  public int hashCode() {
    return new HashCodeBuilder(17, 37)
        .appendSuper(super.hashCode())
        .append(assignmentDeskCaseLoad)
        .append(ceilingNumber)
        .append(endDate)
        .append(identifierName)
        .append(onHold)
        .append(startDate)
        .append(fkasgUnit)
        .append(caseLoadIndicator)
        .append(county)
        .append(archiveAssociation)
        .toHashCode();
  }
}
