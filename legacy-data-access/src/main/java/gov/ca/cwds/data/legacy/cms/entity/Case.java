package gov.ca.cwds.data.legacy.cms.entity;

import gov.ca.cwds.data.legacy.cms.CmsPersistentObject;
import gov.ca.cwds.data.legacy.cms.entity.enums.LimitedAccess;
import gov.ca.cwds.data.legacy.cms.entity.enums.ResponsibleAgency;
import gov.ca.cwds.data.legacy.cms.entity.facade.CaseByStaff;
import gov.ca.cwds.data.legacy.cms.entity.facade.ClientByStaff;
import gov.ca.cwds.data.legacy.cms.entity.syscodes.ActiveServiceComponentType;
import gov.ca.cwds.data.legacy.cms.entity.syscodes.ApprovalStatusType;
import gov.ca.cwds.data.legacy.cms.entity.syscodes.Country;
import gov.ca.cwds.data.legacy.cms.entity.syscodes.County;
import gov.ca.cwds.data.persistence.PersistentObject;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.ColumnResult;
import javax.persistence.ConstructorResult;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedNativeQueries;
import javax.persistence.NamedNativeQuery;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.SqlResultSetMapping;
import javax.persistence.SqlResultSetMappings;
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
@NamedQueries({
  @NamedQuery(
    name = Case.NQ_FIND_ACTIVE_BY_CLIENT_ID,
    query =
        "select distinct c from gov.ca.cwds.data.legacy.cms.entity.Case c"
            + " left join c.childClient ch"
            + " where ch.victimClientId = :"
            + Case.NQ_PARAM_CLIENT_ID
            + " and c.endDate is null "
  ),
  @NamedQuery(
    name = Case.NQ_FIND_CLOSED_BY_CLIENT_ID,
    query =
        "select distinct c from gov.ca.cwds.data.legacy.cms.entity.Case c"
            + " left join c.childClient ch"
            + " where ch.victimClientId = :"
            + Case.NQ_PARAM_CLIENT_ID
            + " and c.endDate is not null "
  ),
})

@NamedNativeQueries({
  @NamedNativeQuery(
    name = CaseByStaff.NATIVE_FIND_CASES_BY_STAFF_ID,
    query = "select distinct "

      + "  case_.IDENTIFIER as identifier, "
      + "  trim(case_.CASE_NM) as caseName, "
      + "  client.IDENTIFIER as clientIdentifier, "
      + "  trim(client.COM_FST_NM) as clientFirstName, "
      + "  trim(client.COM_LST_NM) as clientLastName, "
      + "  trim(service_component_type.SHORT_DSC) as activeServiceComponent, "
      + "  case_assignment.IDENTIFIER as assignmentIdentifier, "
      + "  case_assignment.ASGNMNT_CD as assignmentTypeCode, "
      + "  case_assignment.START_DT as assignmentStartDate "
      + "from "
      + "  {h-schema}CASE_LDT caseload "
      + "  left outer join {h-schema}CSLDWGHT caseloadweight "
      + "    on caseload.IDENTIFIER = caseloadweight.FKCASE_LDT "
      + "  left outer join {h-schema}ASGNM_T case_assignment "
      + "    on caseload.IDENTIFIER=case_assignment.FKCASE_LDT "
      + "    and case_assignment.ESTBLSH_CD='C' "
      + "  left outer join {h-schema}CASE_T case_ "
      + "    on case_assignment.ESTBLSH_ID=case_.IDENTIFIER "
      + "  left outer join {h-schema}CLIENT_T client "
      + "    on case_.FKCHLD_CLT = client.IDENTIFIER "
      + "  left outer join {h-schema}SYS_CD_C service_component_type "
      + "    on case_.SRV_CMPC = service_component_type.SYS_ID "
      + "    and service_component_type.FKS_META_T = 'SRV_CMPC' "
      + "where "
      + "  caseloadweight.FKSTFPERST = ?1"
      + "  and case_.END_DT is null "
      + "  and case_assignment.START_DT <= ?2"
      + "  and (case_assignment.END_DT is null or case_assignment.END_DT > ?3)"
  ),
  @NamedNativeQuery(
    name = ClientByStaff.NATIVE_FIND_CLIENTS_BY_STAFF_ID,
    query = "select client.IDENTIFIER as clientIdentifier,"
      + "  max(trim(client.COM_FST_NM)) as clientFirstName,"
      + "  max(trim(client.COM_MID_NM)) as clientMiddleName,"
      + "  max(trim(client.COM_LST_NM)) as clientLastName,"
      + "  max(trim(client.SUFX_TLDSC)) as clientNameSuffix,"
      + "  max(client.SENSTV_IND) as clientSensitivityType,"
      + "  max(client.BIRTH_DT) as clientBirthDate,"
      + "  max(family_case_plan_episode.RVW_DUE_DT) as casePlanReviewDueDate "
      + "from "
      + "  {h-schema}CASE_LDT caseload "
      + "  left outer join {h-schema}CSLDWGHT caseloadweight "
      + "    on caseload.IDENTIFIER = caseloadweight.FKCASE_LDT "
      + "  left outer join {h-schema}ASGNM_T case_assignment "
      + "    on caseload.IDENTIFIER=case_assignment.FKCASE_LDT "
      + "    and case_assignment.ESTBLSH_CD='C' "
      + "  left outer join {h-schema}CASE_T case_ "
      + "    on case_assignment.ESTBLSH_ID=case_.IDENTIFIER "
      + "  left outer join {h-schema}CLIENT_T client "
      + "    on case_.FKCHLD_CLT = client.IDENTIFIER "
      + "  left outer join {h-schema}CSPL_DET case_plan_children_detail on case_.IDENTIFIER = case_plan_children_detail.FKCASE_T"
      + "  left outer join {h-schema}FCPL_EPT family_case_plan_episode on case_plan_children_detail.FKFCPL_EPT = family_case_plan_episode.IDENTIFIER "
      + "where "
      + "  caseloadweight.FKSTFPERST = ?1"
      + "  and case_.END_DT is null "
      + "  and case_assignment.START_DT <= ?2"
      + "  and (case_assignment.END_DT is null or case_assignment.END_DT > ?3) "
      + "group by client.IDENTIFIER"
  )
})

@SqlResultSetMappings({
  @SqlResultSetMapping(
    name = CaseByStaff.MAPPING_CASE_BY_STAFF,
    classes = @ConstructorResult(
      targetClass = CaseByStaff.class,
      columns = {
        @ColumnResult(name = "identifier"),
        @ColumnResult(name = "caseName"),
        @ColumnResult(name = "clientIdentifier"),
        @ColumnResult(name = "clientFirstName"),
        @ColumnResult(name = "clientLastName"),
        @ColumnResult(name = "activeServiceComponent"),
        @ColumnResult(name = "assignmentIdentifier"),
        @ColumnResult(name = "assignmentStartDate", type = LocalDate.class),
        @ColumnResult(name = "assignmentTypeCode", type = String.class),
        @ColumnResult(name = "assignmentStartDate", type = LocalDate.class)
      }
    )
  ),
  @SqlResultSetMapping(
    name = ClientByStaff.MAPPING_CLIENT_BY_STAFF,
    classes = @ConstructorResult(
      targetClass = ClientByStaff.class,
      columns = {
        @ColumnResult(name = "clientIdentifier", type = String.class),
        @ColumnResult(name = "clientFirstName", type = String.class),
        @ColumnResult(name = "clientMiddleName", type = String.class),
        @ColumnResult(name = "clientLastName", type = String.class),
        @ColumnResult(name = "clientNameSuffix", type = String.class),
        @ColumnResult(name = "clientSensitivityType", type = String.class),
        @ColumnResult(name = "clientBirthDate", type = LocalDate.class),
        @ColumnResult(name = "casePlanReviewDueDate", type = LocalDate.class)
      }
    )
  )
})

@SuppressWarnings({"squid:S3437", "squid:S2160"})
public class Case extends CmsPersistentObject {

  public static final String NQ_FIND_ACTIVE_BY_CLIENT_ID =
      "gov.ca.cwds.data.legacy.cms.entity.Case.findActiveByClient";
  public static final String NQ_FIND_CLOSED_BY_CLIENT_ID =
      "gov.ca.cwds.data.legacy.cms.entity.Case.findClosedByClient";
  public static final String NQ_PARAM_CLIENT_ID = "clientId";

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

  @Column(name = "CLS_RSNC", nullable = false)
  private short caseClosureReasonTypeCode;

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
  @ColumnTransformer(read = "trim(LMT_ACSDSC)")
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

  @Column(name = "STATE_C", nullable = false)
  private short stateCode;

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

  public short getCaseClosureReasonTypeCode() {
    return caseClosureReasonTypeCode;
  }

  public void setCaseClosureReasonTypeCode(short caseClosureReasonTypeCode) {
    this.caseClosureReasonTypeCode = caseClosureReasonTypeCode;
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

  public short getStateCode() {
    return stateCode;
  }

  public void setStateCode(short stateCode) {
    this.stateCode = stateCode;
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
  public final boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof Case)) {
      return false;
    }
    Case aCase = (Case) o;
    return Objects.equals(getChildClient(), aCase.getChildClient())
        && Objects.equals(getStartDate(), aCase.getStartDate());
  }

  @Override
  public final int hashCode() {
    return Objects.hash(getChildClient(), getStartDate());
  }
}
