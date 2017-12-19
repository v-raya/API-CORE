package gov.ca.cwds.data.legacy.cms.entity;

import gov.ca.cwds.data.legacy.cms.CmsPersistentObject;
import gov.ca.cwds.data.legacy.cms.entity.enums.LimitedAccess;
import gov.ca.cwds.data.legacy.cms.entity.enums.ResponsibleAgency;
import gov.ca.cwds.data.legacy.cms.entity.syscodes.ActiveServiceComponentType;
import gov.ca.cwds.data.legacy.cms.entity.syscodes.ApprovalStatusType;
import gov.ca.cwds.data.legacy.cms.entity.syscodes.CaseClosureReasonType;
import gov.ca.cwds.data.legacy.cms.entity.syscodes.Country;
import gov.ca.cwds.data.legacy.cms.entity.syscodes.County;
import gov.ca.cwds.data.legacy.cms.entity.syscodes.State;
import gov.ca.cwds.data.persistence.PersistentObject;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import org.hibernate.annotations.ColumnTransformer;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;
import org.hibernate.annotations.Type;

/**
 * {@link PersistentObject} Class representing a Case.
 *
 * @author CASE API Team
 */
@Entity
@Table(name = "CASE_T")
@NamedQuery(
    name = Case.NQ_FIND_ACTIVE_BY_STAFF_ID,
    query = "select distinct theCase from gov.ca.cwds.data.legacy.cms.entity.CaseLoad cl"
        + " left join cl.caseAssignments assignment"
        + " left join assignment.theCase theCase "
        + " where cl.caseLoadWeighting.fkstfperst = :" + Case.NQ_PARAM_STAFF_ID
        + " and theCase.endDate is null "
        + " and assignment.startDate < :" + Case.NQ_PARAM_ACTIVE_DATE
        + " and (assignment.endDate is null or assignment.endDate > :" + Case.NQ_PARAM_ACTIVE_DATE + ")"
)
@SuppressWarnings("squid:S3437")
public class Case extends CmsPersistentObject {

  public static final String NQ_FIND_ACTIVE_BY_STAFF_ID = "gov.ca.cwds.data.legacy.cms.entity.Case.findByStaffIdAndActiveDate";
  public static final String NQ_PARAM_STAFF_ID = "staffId";
  public static final String NQ_PARAM_ACTIVE_DATE = "activeDate";

  /**
   * Default serialization.
   */
  private static final long serialVersionUID = 3310114537014283818L;

  @Id
  @Column(name = "IDENTIFIER", nullable = false, length = CMS_ID_LEN)
  private String identifier;

  @NotFound(action = NotFoundAction.IGNORE)
  @ManyToOne(fetch = FetchType.LAZY)
  @Fetch(FetchMode.SELECT)
  @JoinColumn(name = "ALERT_TXT", referencedColumnName = "IDENTIFIER")
  private LongText alertText;

  @Column(name = "APRVL_NO")
  private String approvalNumber;

  @NotFound(action = NotFoundAction.IGNORE)
  @ManyToOne(fetch = FetchType.LAZY)
  @Fetch(FetchMode.SELECT)
  @JoinColumn(name = "APV_STC", referencedColumnName = "SYS_ID")
  private ApprovalStatusType approvalStatusType;

  @NotFound(action = NotFoundAction.IGNORE)
  @ManyToOne(fetch = FetchType.LAZY)
  @Fetch(FetchMode.SELECT)
  @JoinColumn(name = "CLS_RSNC", referencedColumnName = "SYS_ID")
  private CaseClosureReasonType caseClosureReasonType;

  @Type(type = "yes_no")
  @Column(name = "CSPL_DET_B")
  private boolean caseplanChildrenDetailIndVar;

  @NotFound(action = NotFoundAction.IGNORE)
  @ManyToOne(fetch = FetchType.LAZY)
  @Fetch(FetchMode.SELECT)
  @JoinColumn(name = "CL_STM_TXT", referencedColumnName = "IDENTIFIER")
  private LongText closureStatementText;

  @NotFound(action = NotFoundAction.IGNORE)
  @ManyToOne(fetch = FetchType.LAZY)
  @Fetch(FetchMode.SELECT)
  @JoinColumn(name = "CNTRY_C", referencedColumnName = "SYS_ID")
  private Country country;

  @Column(name = "CNTY_SPFCD")
  private String countySpecificCode;

  @Column(name = "NOTES_DOC")
  @ColumnTransformer(read = "trim(NOTES_DOC)")
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
  @JoinColumn(name = "GVR_ENTC", referencedColumnName = "SYS_ID")
  private County county;

  @Type(type = "yes_no")
  @Column(name = "ICPCSTAT_B")
  private boolean icpcOutgngPlcmtStatusIndVar;

  @Type(type = "yes_no")
  @Column(name = "ICPC_RQT_B")
  private boolean icpcOutgoingRequestIndVar;

  @Column(name = "LMT_ACSSCD")
  @Convert(converter = LimitedAccess.LimitedAccessConverter.class)
  private LimitedAccess limitedAccess;

  @Column(name = "LMT_ACS_DT")
  private LocalDate limitedAccessDate;

  @Column(name = "LMT_ACSDSC")
  private String limitedAccessDesc;

  @NotFound(action = NotFoundAction.IGNORE)
  @ManyToOne(fetch = FetchType.LAZY)
  @Fetch(FetchMode.SELECT)
  @JoinColumn(name = "L_GVR_ENTC", referencedColumnName = "SYS_ID")
  private County limitedAccessCounty;

  @Column(name = "CASE_NM")
  @ColumnTransformer(read = "trim(CASE_NM)")
  private String caseName;

  @Column(name = "NXT_TILPDT")
  private LocalDate nextTilpDueDate;

  @Column(name = "PRJ_END_DT")
  private LocalDate projectedEndDate;

  @Column(name = "RSP_AGY_CD")
  @Convert(converter = ResponsibleAgency.ResponsibleAgencyConverter.class)
  private ResponsibleAgency responsibleAgency;

  @Type(type = "yes_no")
  @Column(name = "SPRJ_CST_B")
  private boolean specialProjectCaseIndVar;

  @Column(name = "START_DT")
  private LocalDate startDate;

  @NotFound(action = NotFoundAction.IGNORE)
  @ManyToOne(fetch = FetchType.LAZY)
  @Fetch(FetchMode.SELECT)
  @JoinColumn(name = "STATE_C", referencedColumnName = "SYS_ID")
  private State state;

  @NotFound(action = NotFoundAction.IGNORE)
  @ManyToOne(fetch = FetchType.LAZY)
  @Fetch(FetchMode.SELECT)
  @JoinColumn(name = "SRV_CMPC", referencedColumnName = "SYS_ID")
  private ActiveServiceComponentType activeServiceComponentType;

  @Column(name = "SRV_CMPDT")
  private LocalDate activeSvcComponentStartDate;

  @Type(type = "yes_no")
  @Column(name = "TICKLE_T_B")
  private boolean tickleIndVar;

  @NotFound(action = NotFoundAction.IGNORE)
  @OneToMany(fetch = FetchType.LAZY, mappedBy = "theCase")
  private List<CaseAssignment> assignments;

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

  public LongText getAlertText() {
    return alertText;
  }

  public void setAlertText(LongText alertText) {
    this.alertText = alertText;
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

  public CaseClosureReasonType getCaseClosureReasonType() {
    return caseClosureReasonType;
  }

  public void setCaseClosureReasonType(CaseClosureReasonType caseClosureReasonType) {
    this.caseClosureReasonType = caseClosureReasonType;
  }

  public boolean getCaseplanChildrenDetailIndVar() {
    return caseplanChildrenDetailIndVar;
  }

  public void setCaseplanChildrenDetailIndVar(boolean caseplanChildrenDetailIndVar) {
    this.caseplanChildrenDetailIndVar = caseplanChildrenDetailIndVar;
  }

  public LongText getClosureStatementText() {
    return closureStatementText;
  }

  public void setClosureStatementText(LongText closureStatementText) {
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

  public boolean getIcpcOutgngPlcmtStatusIndVar() {
    return icpcOutgngPlcmtStatusIndVar;
  }

  public void setIcpcOutgngPlcmtStatusIndVar(boolean icpcOutgngPlcmtStatusIndVar) {
    this.icpcOutgngPlcmtStatusIndVar = icpcOutgngPlcmtStatusIndVar;
  }

  public boolean getIcpcOutgoingRequestIndVar() {
    return icpcOutgoingRequestIndVar;
  }

  public void setIcpcOutgoingRequestIndVar(boolean icpcOutgoingRequestIndVar) {
    this.icpcOutgoingRequestIndVar = icpcOutgoingRequestIndVar;
  }

  public LimitedAccess getLimitedAccess() {
    return limitedAccess;
  }

  public void setLimitedAccess(LimitedAccess limitedAccess) {
    this.limitedAccess = limitedAccess;
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

  public ResponsibleAgency getResponsibleAgency() {
    return responsibleAgency;
  }

  public void setResponsibleAgency(ResponsibleAgency responsibleAgency) {
    this.responsibleAgency = responsibleAgency;
  }

  public boolean getSpecialProjectCaseIndVar() {
    return specialProjectCaseIndVar;
  }

  public void setSpecialProjectCaseIndVar(boolean specialProjectCaseIndVar) {
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

  public ActiveServiceComponentType getActiveServiceComponentType() {
    return activeServiceComponentType;
  }

  public void setActiveServiceComponentType(ActiveServiceComponentType activeServiceComponentType) {
    this.activeServiceComponentType = activeServiceComponentType;
  }

  public LocalDate getActiveSvcComponentStartDate() {
    return activeSvcComponentStartDate;
  }

  public void setActiveSvcComponentStartDate(LocalDate activeSvcComponentStartDate) {
    this.activeSvcComponentStartDate = activeSvcComponentStartDate;
  }

  public boolean getTickleIndVar() {
    return tickleIndVar;
  }

  public void setTickleIndVar(boolean tickleIndVar) {
    this.tickleIndVar = tickleIndVar;
  }

  public void setChildClient(ChildClient childClient) {
    this.childClient = childClient;
  }

  public List<CaseAssignment> getAssignments() {
    return assignments;
  }

  public void setAssignments(List<CaseAssignment> assignment) {
    this.assignments = assignment;
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
