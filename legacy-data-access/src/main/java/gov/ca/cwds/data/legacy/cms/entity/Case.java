package gov.ca.cwds.data.legacy.cms.entity;

import gov.ca.cwds.data.legacy.cms.CmsPersistentObject;
import gov.ca.cwds.data.legacy.cms.entity.syscodes.Country;
import gov.ca.cwds.data.legacy.cms.entity.syscodes.County;
import gov.ca.cwds.data.legacy.cms.entity.syscodes.State;
import gov.ca.cwds.data.persistence.PersistentObject;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;
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
 * {@link PersistentObject} Class representing a Case.
 *
 * @author CWDS API Team
 */
@Entity
@Table(name = "CASE_T")
public class Case extends CmsPersistentObject {

  /**
   * Default serialization.
   */
  private static final long serialVersionUID = 1L;

  @Id
  @Column(name = "IDENTIFIER", nullable = false, length = CMS_ID_LEN)
  private String identifier;

  @Column(name = "ALERT_TXT")
  private String alertText;

  @Column(name = "APRVL_NO")
  private String approvalNumber;

  @Type(type = "short")
  @Column(name = "APV_STC")
  private Short approvalStatusType;

  @Type(type = "short")
  @Column(name = "CLS_RSNC")
  private Short caseClosureReasonType;

  @Type(type = "yes_no")
  @Column(name = "CSPL_DET_B")
  private Boolean caseplanChildrenDetailIndVar;

  @Column(name = "CL_STM_TXT")
  private String closureStatementText;

  @NotFound(action = NotFoundAction.IGNORE)
  @ManyToOne(fetch = FetchType.LAZY)
  @Fetch(FetchMode.SELECT)
  @JoinColumn(name = "CNTRY_C", referencedColumnName = "SYS_ID", insertable = false, updatable = false)
  private Country country;

  @Column(name = "CNTY_SPFCD")
  private String countySpecificCode;

  @Column(name = "NOTES_DOC")
  private String drmsNotesDoc;

  @Column(name = "EMANCPN_DT")
  private LocalDate emancipationDate;

  @Column(name = "END_DT")
  private LocalDate endDate;

  @NotFound(action = NotFoundAction.IGNORE)
  @ManyToOne
  @JoinColumn(name = "FKCHLD_CLT", referencedColumnName = "FKCLIENT_T")
  private ChildClient childClient;

  @Column(name = "FKREFERL_T")
  private String referralId;

  @NotFound(action = NotFoundAction.IGNORE)
  @ManyToOne(fetch = FetchType.LAZY)
  @Fetch(FetchMode.SELECT)
  @JoinColumn(name = "FKSTFPERST", referencedColumnName = "IDENTIFIER")
  private StaffPerson staffPerson;

  @NotFound(action = NotFoundAction.IGNORE)
  @ManyToOne(fetch = FetchType.LAZY)
  @Fetch(FetchMode.SELECT)
  @JoinColumn(name = "GVR_ENTC", referencedColumnName = "SYS_ID", insertable = false, updatable = false)
  private County county;

  @Type(type = "yes_no")
  @Column(name = "ICPCSTAT_B")
  private Boolean icpcOutgngPlcmtStatusIndVar;

  @Type(type = "yes_no")
  @Column(name = "ICPC_RQT_B")
  private Boolean icpcOutgoingRequestIndVar;

  @Column(name = "LMT_ACSSCD")
  private String limitedAccessCode;

  @Column(name = "LMT_ACS_DT")
  private LocalDate limitedAccessDate;

  @Column(name = "LMT_ACSDSC")
  private String limitedAccessDesc;

  @NotFound(action = NotFoundAction.IGNORE)
  @ManyToOne(fetch = FetchType.LAZY)
  @Fetch(FetchMode.SELECT)
  @JoinColumn(name = "L_GVR_ENTC", referencedColumnName = "SYS_ID", insertable = false, updatable = false)
  private County limitedAccessCounty;

  @Column(name = "CASE_NM")
  private String caseName;

  @Column(name = "NXT_TILPDT")
  private LocalDate nextTilpDueDate;

  @Column(name = "PRJ_END_DT")
  private LocalDate projectedEndDate;

  @Column(name = "RSP_AGY_CD")
  private String responsibleAgencyCode;

  @Type(type = "yes_no")
  @Column(name = "SPRJ_CST_B")
  private Boolean specialProjectCaseIndVar;

  @Column(name = "START_DT")
  private LocalDate startDate;

  @NotFound(action = NotFoundAction.IGNORE)
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "STATE_C", referencedColumnName = "SYS_ID", insertable = false, updatable = false)
  private State state;

  @Type(type = "short")
  @Column(name = "SRV_CMPC")
  private Short activeServiceComponentType;

  @Column(name = "SRV_CMPDT")
  private LocalDate activeSvcComponentStartDate;

  @Type(type = "yes_no")
  @Column(name = "TICKLE_T_B")
  private Boolean tickleIndVar;

  @Override
  public Serializable getPrimaryKey() {
    return getIdentifier();
  }

  public String getIdentifier() {
    return this.identifier;
  }

  public void setIdentifier(String identifier) {
    this.identifier = identifier;
  }

  public String getAlertText() {
    return alertText;
  }

  public void setAlertText(String alertText) {
    this.alertText = alertText;
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

  public Short getCaseClosureReasonType() {
    return caseClosureReasonType;
  }

  public void setCaseClosureReasonType(Short caseClosureReasonType) {
    this.caseClosureReasonType = caseClosureReasonType;
  }

  public Boolean getCaseplanChildrenDetailIndVar() {
    return caseplanChildrenDetailIndVar;
  }

  public void setCaseplanChildrenDetailIndVar(Boolean caseplanChildrenDetailIndVar) {
    this.caseplanChildrenDetailIndVar = caseplanChildrenDetailIndVar;
  }

  public String getClosureStatementText() {
    return closureStatementText;
  }

  public void setClosureStatementText(String closureStatementText) {
    this.closureStatementText = closureStatementText;
  }

  public Country getCountry() {
    return country;
  }

  public void setCountry(Country country) {
    this.country = country;
  }

  public String getCountySpecificCode() {
    return countySpecificCode;
  }

  public void setCountySpecificCode(String countySpecificCode) {
    this.countySpecificCode = countySpecificCode;
  }

  public String getDrmsNotesDoc() {
    return drmsNotesDoc;
  }

  public void setDrmsNotesDoc(String drmsNotesDoc) {
    this.drmsNotesDoc = drmsNotesDoc;
  }

  public LocalDate getEmancipationDate() {
    return emancipationDate;
  }

  public void setEmancipationDate(LocalDate emancipationDate) {
    this.emancipationDate = emancipationDate;
  }

  public LocalDate getEndDate() {
    return endDate;
  }

  public void setEndDate(LocalDate endDate) {
    this.endDate = endDate;
  }

  public ChildClient getChildClient() {
    return childClient;
  }

  public void setChildClientId(ChildClient childClient) {
    this.childClient = childClient;
  }

  public String getReferralId() {
    return referralId;
  }

  public void setReferralId(String referralId) {
    this.referralId = referralId;
  }

  public StaffPerson getStaffPerson() {
    return staffPerson;
  }

  public void setStaffPerson(StaffPerson staffPerson) {
    this.staffPerson = staffPerson;
  }

  public County getCounty() {
    return county;
  }

  public void setCounty(County county) {
    this.county = county;
  }

  public Boolean getIcpcOutgngPlcmtStatusIndVar() {
    return icpcOutgngPlcmtStatusIndVar;
  }

  public void setIcpcOutgngPlcmtStatusIndVar(Boolean icpcOutgngPlcmtStatusIndVar) {
    this.icpcOutgngPlcmtStatusIndVar = icpcOutgngPlcmtStatusIndVar;
  }

  public Boolean getIcpcOutgoingRequestIndVar() {
    return icpcOutgoingRequestIndVar;
  }

  public void setIcpcOutgoingRequestIndVar(Boolean icpcOutgoingRequestIndVar) {
    this.icpcOutgoingRequestIndVar = icpcOutgoingRequestIndVar;
  }

  public String getLimitedAccessCode() {
    return limitedAccessCode;
  }

  public void setLimitedAccessCode(String limitedAccessCode) {
    this.limitedAccessCode = limitedAccessCode;
  }

  public LocalDate getLimitedAccessDate() {
    return limitedAccessDate;
  }

  public void setLimitedAccessDate(LocalDate limitedAccessDate) {
    this.limitedAccessDate = limitedAccessDate;
  }

  public String getLimitedAccessDesc() {
    return limitedAccessDesc;
  }

  public void setLimitedAccessDesc(String limitedAccessDesc) {
    this.limitedAccessDesc = limitedAccessDesc;
  }

  public County getLimitedAccessCounty() {
    return limitedAccessCounty;
  }

  public void setLimitedAccessCounty(County limitedAccessCounty) {
    this.limitedAccessCounty = limitedAccessCounty;
  }

  public String getCaseName() {
    return caseName;
  }

  public void setCaseName(String caseName) {
    this.caseName = caseName;
  }

  public LocalDate getNextTilpDueDate() {
    return nextTilpDueDate;
  }

  public void setNextTilpDueDate(LocalDate nextTilpDueDate) {
    this.nextTilpDueDate = nextTilpDueDate;
  }

  public LocalDate getProjectedEndDate() {
    return projectedEndDate;
  }

  public void setProjectedEndDate(LocalDate projectedEndDate) {
    this.projectedEndDate = projectedEndDate;
  }

  public String getResponsibleAgencyCode() {
    return responsibleAgencyCode;
  }

  public void setResponsibleAgencyCode(String responsibleAgencyCode) {
    this.responsibleAgencyCode = responsibleAgencyCode;
  }

  public Boolean getSpecialProjectCaseIndVar() {
    return specialProjectCaseIndVar;
  }

  public void setSpecialProjectCaseIndVar(Boolean specialProjectCaseIndVar) {
    this.specialProjectCaseIndVar = specialProjectCaseIndVar;
  }

  public LocalDate getStartDate() {
    return startDate;
  }

  public void setStartDate(LocalDate startDate) {
    this.startDate = startDate;
  }

  public State getState() {
    return state;
  }

  public void setState(State state) {
    this.state = state;
  }

  public Short getActiveServiceComponentType() {
    return activeServiceComponentType;
  }

  public void setActiveServiceComponentType(Short activeServiceComponentType) {
    this.activeServiceComponentType = activeServiceComponentType;
  }

  public LocalDate getActiveSvcComponentStartDate() {
    return activeSvcComponentStartDate;
  }

  public void setActiveSvcComponentStartDate(LocalDate activeSvcComponentStartDate) {
    this.activeSvcComponentStartDate = activeSvcComponentStartDate;
  }

  public Boolean getTickleIndVar() {
    return tickleIndVar;
  }

  public void setTickleIndVar(Boolean tickleIndVar) {
    this.tickleIndVar = tickleIndVar;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Case aCase = (Case) o;
    return
        Objects.equals(childClient.getVictimClientId(), aCase.getChildClient().getVictimClientId())
            && Objects.equals(startDate, aCase.startDate);
  }

  @Override
  public int hashCode() {
    return Objects.hash(childClient.getVictimClientId(), startDate);
  }
}
