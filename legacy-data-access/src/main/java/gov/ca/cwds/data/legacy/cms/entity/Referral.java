package gov.ca.cwds.data.legacy.cms.entity;

import gov.ca.cwds.data.legacy.cms.CmsPersistentObject;
import gov.ca.cwds.data.legacy.cms.entity.enums.LimitedAccess;
import gov.ca.cwds.data.legacy.cms.entity.facade.ClientByStaff;
import gov.ca.cwds.data.legacy.cms.entity.facade.ReferralByStaff;
import gov.ca.cwds.data.legacy.cms.entity.syscodes.County;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
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
 * Referral entity with related named queries.
 *
 * @author CWDS TPT-3 Team
 */
@Entity
@Table(name = "REFERL_T")
@NamedQueries({
    @NamedQuery(
        name = Referral.FIND_ACTIVE_BY_CLIENT,
        query =
            "SELECT DISTINCT rclient.referral from gov.ca.cwds.data.legacy.cms.entity.ReferralClient rclient"
                + "  left join rclient.referral where rclient.client.identifier = :"
                + Referral.PARAM_CLIENT_ID
                + " and rclient.referral.closureDate is null"
    ),
    @NamedQuery(
        name = Referral.FIND_CLOSED_BY_CLIENT,
        query =
            "SELECT DISTINCT rclient.referral from gov.ca.cwds.data.legacy.cms.entity.ReferralClient rclient"
                + "  left join Referral referral where rclient.client.identifier = :"
                + Referral.PARAM_CLIENT_ID
                + " and rclient.referral.closureDate is not null"
    ),
    @NamedQuery(
        name = Referral.FIND_BY_CLIENT,
        query =
            "SELECT DISTINCT r.referral FROM gov.ca.cwds.data.legacy.cms.entity.ReferralClient r LEFT JOIN r.referral WHERE r.client.identifier=:"
                + Referral.PARAM_CLIENT_ID
    )
})

@NamedNativeQueries({
  @NamedNativeQuery(
    name = ReferralByStaff.NATIVE_FIND_REFERRALS_BY_STAFF_ID,
    query = "select distinct "
      + "  referral.IDENTIFIER as identifier, "
      + "  trim(referral.REFERRL_NM) AS referralName, "
      + "  referral.REF_RCV_DT AS receivedDate, "
      + "  referral.REF_RCV_TM AS receivedTime, "
      + "  trim(referral_response_type.SHORT_DSC) AS referralResponseType, "
      + "  assignment_.IDENTIFIER AS assignmentIdentifier, "
      + "  assignment_.ASGNMNT_CD AS assignmentTypeCode "
      + "from "
      + "  {h-schema}CASE_LDT caseload "
      + "  left outer join {h-schema}CSLDWGHT caseloadweight "
      + "    on caseload.IDENTIFIER = caseloadweight.FKCASE_LDT "
      + "  left outer join {h-schema}ASGNM_T assignment_ "
      + "    on caseload.IDENTIFIER=assignment_.FKCASE_LDT and assignment_.ESTBLSH_CD='R' "
      + "  left outer join {h-schema}REFERL_T referral "
      + "    on assignment_.ESTBLSH_ID=referral.IDENTIFIER "
      + "  left outer join {h-schema}SYS_CD_C referral_response_type "
      + "    on referral.RFR_RSPC = referral_response_type.SYS_ID "
      + "    and referral_response_type.FKS_META_T = 'RFR_RSPC' "
      + "where "
      + "  caseloadweight.FKSTFPERST = ?1 "
      + "  and assignment_.START_DT <= ?2 "
      + "  and (assignment_.END_DT is null or assignment_.END_DT > ?3) "
      + "  and referral.ORIGCLS_DT is null "
  ),
  @NamedNativeQuery(
    name = ClientByStaff.REFERRAL_FIND_CLIENTS_BY_STAFF_ID,
    query = "select distinct "
      + "  client.IDENTIFIER as clientIdentifier,"
      + "  trim(client.COM_FST_NM) as clientFirstName,"
      + "  trim(client.COM_MID_NM) as clientMiddleName,"
      + "  trim(client.COM_LST_NM) as clientLastName,"
      + "  trim(client.SUFX_TLDSC) as clientNameSuffix,"
      + "  client.SENSTV_IND as clientSensitivityIndicator,"
      + "  client.BIRTH_DT as clientBirthDate "
      + "from "
      + "  {h-schema}CASE_LDT caseload "
      + "  left outer join {h-schema}CSLDWGHT caseloadweight "
      + "    on caseload.IDENTIFIER = caseloadweight.FKCASE_LDT "
      + "  left outer join {h-schema}ASGNM_T assignment_ "
      + "    on caseload.IDENTIFIER=assignment_.FKCASE_LDT and assignment_.ESTBLSH_CD='R' "
      + "  left outer join {h-schema}REFERL_T referral "
      + "    on assignment_.ESTBLSH_ID=referral.IDENTIFIER "
      + "  left outer join ALLGTN_T allegation"
      + "    on allegation.FKREFERL_T = referral.IDENTIFIER"
      + "  left outer join CLIENT_T client"
      + "    on client.IDENTIFIER = allegation.FKCLIENT_T "
      + "where "
      + "  caseloadweight.FKSTFPERST = ?1 "
      + "  and assignment_.START_DT <= ?2 "
      + "  and (assignment_.END_DT is null or assignment_.END_DT > ?3) "
      + "  and referral.ORIGCLS_DT is null "
  ),
  @NamedNativeQuery(
    name = Referral.FIND_ACTIVE_IDS_BY_CLIENT,
    query =
      "SELECT  "
        + "  referral.IDENTIFIER "
        + "FROM  "
        + "  {h-schema}ALLGTN_T allegation "
        + "LEFT JOIN {h-schema}REFERL_T referral ON referral.IDENTIFIER = allegation.FKREFERL_T "
        + "WHERE "
        + "  allegation.FKCLIENT_T = ?1 "
        + "  AND (allegation.DISPSN_DT IS NULL OR allegation.DISPSN_DT > ?2) "
        + "  AND (referral.ORIGCLS_DT IS NULL OR referral.ORIGCLS_DT > ?2) "
        + "GROUP BY referral.IDENTIFIER "
        + "ORDER BY MAX(TIMESTAMP(referral.REF_RCV_DT, referral.REF_RCV_TM)) DESC ")
})
@SqlResultSetMappings({
  @SqlResultSetMapping(
    name = ReferralByStaff.MAPPING_CASE_BY_STAFF,
    classes = @ConstructorResult(
      targetClass = ReferralByStaff.class,
      columns = {
        @ColumnResult(name = "identifier"),
        @ColumnResult(name = "referralName"),
        @ColumnResult(name = "receivedDate", type = LocalDate.class),
        @ColumnResult(name = "receivedTime", type = LocalTime.class),
        @ColumnResult(name = "referralResponseType"),
        @ColumnResult(name = "assignmentIdentifier"),
        @ColumnResult(name = "assignmentTypeCode", type = String.class),
      }
    )
  ),
  @SqlResultSetMapping(
    name = ClientByStaff.MAPPING_CLIENT_FROM_REFERRAL,
    classes = @ConstructorResult(
      targetClass = ClientByStaff.class,
      columns = {
        @ColumnResult(name = "clientIdentifier", type = String.class),
        @ColumnResult(name = "clientFirstName", type = String.class),
        @ColumnResult(name = "clientMiddleName", type = String.class),
        @ColumnResult(name = "clientLastName", type = String.class),
        @ColumnResult(name = "clientNameSuffix", type = String.class),
        @ColumnResult(name = "clientSensitivityType", type = String.class),
        @ColumnResult(name = "clientBirthDate", type = LocalDate.class)
      }
    )
  )
})

@SuppressWarnings("squid:S3437")
public class Referral extends CmsPersistentObject {

  private static final long serialVersionUID = -3111967263461123486L;

  public static final String FIND_ACTIVE_BY_CLIENT = "Referral.findActiveReferralsByClient";
  public static final String FIND_ACTIVE_IDS_BY_CLIENT = "Referral.findActiveReferralIdsByClient";
  public static final String FIND_CLOSED_BY_CLIENT = "Referral.findClosedReferralsByClient";
  public static final String FIND_BY_CLIENT = "Referral.findReferralsByClient";
  public static final String PARAM_CLIENT_ID = "clientId";

  @Id
  @Column(name = "IDENTIFIER", length = CMS_ID_LEN)
  private String id;

  @Type(type = "yes_no")
  @Column(name = "ADD_INF_CD")
  private Boolean additionalInfoIncludedCode;

  @Type(type = "yes_no")
  @Column(name = "ANRPTR_IND")
  private Boolean anonymousReporterIndicator;

  @Type(type = "yes_no")
  @Column(name = "PETAPL_IND")
  private Boolean applicationForPetitionIndicator;

  @Column(name = "APRVL_NO")
  @ColumnTransformer(read = "trim(APRVL_NO)")
  private String approvalNumber;

  @Column(name = "APV_STC")
  private Short approvalStatusType;

  @Type(type = "yes_no")
  @Column(name = "CR_PERP_CD")
  private Boolean caretakersPerpetratorCode;

  @Column(name = "REFCLSR_DT")
  private LocalDate closureDate;

  @Column(name = "CMM_MTHC")
  @ColumnTransformer(read = "trim(CMM_MTHC)")
  private Short communicationMethodType;

  @Column(name = "CHILOC_TXT")
  @ColumnTransformer(read = "trim(CHILOC_TXT)")
  private String currentLocationOfChildren;

  @Column(name = "ALGDSC_DOC")
  @ColumnTransformer(read = "trim(ALGDSC_DOC)")
  private String drmsAllegationDescriptionDoc;

  @Column(name = "ER_REF_DOC")
  @ColumnTransformer(read = "trim(ER_REF_DOC)")
  private String drmsErReferralDoc;

  @Column(name = "INVSTG_DOC")
  @ColumnTransformer(read = "trim(INVSTG_DOC)")
  private String drmsInvestigationDoc;

  @Column(name = "XRPT_LWIND")
  private String filedSuspectedChildAbuseReporttoLawEnforcementIndicator;

  @Column(name = "FMY_AW_IND")
  private String familyAwarenessIndicator;

  @Column(name = "GVR_ENTC")
  private Short govtEntityType;

  @Column(name = "LGL_DEF_CD")
  @ColumnTransformer(read = "trim(LGL_DEF_CD)")
  private String legalDefinitionCode;

  @Column(name = "LGLRGT_IND")
  private String legalRightsNoticeIndicator;

  @Column(name = "LMT_ACSSCD")
  @Convert(converter = LimitedAccess.LimitedAccessConverter.class)
  private LimitedAccess limitedAccessCode;

  @Column(name = "XRPT_RCVDT")
  private LocalDate mandatedCrossReportReceivedDate;

  @Column(name = "REFERRL_NM")
  @ColumnTransformer(read = "trim(REFERRL_NM)")
  private String referralName;

  @Column(name = "ADQT_CS_CD")
  @Type(type = "yes_no")
  private Boolean openAdequateCaseCode;

  @Column(name = "REF_RCV_DT")
  private LocalDate receivedDate;

  @Column(name = "REF_RCV_TM")
  private LocalTime receivedTime;

  @Column(name = "RFR_RSPC")
  private Short referralResponseType;

  @Column(name = "RFRD_RSC")
  private Short referredToResourceType;

  @Column(name = "RSP_DTR_DT")
  private LocalDate responseDeterminationDate;

  @Column(name = "RSP_DTR_TM")
  private LocalTime responseDeterminationTime;

  @Column(name = "RSP_RTNTXT")
  @ColumnTransformer(read = "trim(RSP_RTNTXT)")
  private String responseRationaleText;

  @Column(name = "SCN_NT_TXT")
  @ColumnTransformer(read = "trim(SCN_NT_TXT)")
  private String screenerNoteText;

  @Column(name = "SP_INCL_CD")
  @Type(type = "yes_no")
  private Boolean specificsIncludedCode;

  @Column(name = "SFC_INF_CD")
  @Type(type = "yes_no")
  private Boolean sufficientInformationCode;

  @Column(name = "UNFD_SR_CD")
  @Type(type = "yes_no")
  private Boolean unfoundedSeriesCode;

  @Column(name = "FKREFERL_T", length = CMS_ID_LEN)
  @ColumnTransformer(read = "trim(FKREFERL_T)")
  private String linkToPrimaryReferralId;

  @Column(name = "FKADDRS_T", length = CMS_ID_LEN)
  @ColumnTransformer(read = "trim(FKADDRS_T)")
  private String allegesAbuseOccurredAtAddressId;

  @Column(name = "FKSTFPERS0", length = CMS_ID_LEN)
  @ColumnTransformer(read = "trim(FKSTFPERS0)")
  private String firstResponseDeterminedByStaffPersonId;

  @ManyToOne(fetch = FetchType.LAZY)
  @Fetch(FetchMode.SELECT)
  @JoinColumn(name = "FKSTFPERST", referencedColumnName = "IDENTIFIER")
  private StaffPerson primaryContactStaffPerson;

  @NotFound(action = NotFoundAction.IGNORE)
  @ManyToOne(fetch = FetchType.LAZY)
  @Fetch(FetchMode.SELECT)
  @JoinColumn(name = "CNTY_SPFCD", referencedColumnName = "SYS_ID")
  private County countySpecificCode;

  @Column(name = "SPRJRF_IND")
  @Type(type = "yes_no")
  private Boolean specialProjectReferralIndicator;

  @Column(name = "ZIPPY_IND")
  @Type(type = "yes_no")
  private Boolean zippyCreatedIndicator;

  @Column(name = "HOMLES_IND")
  @Type(type = "yes_no")
  private Boolean homelessIndicator;

  @Column(name = "FAMREF_IND")
  @Type(type = "yes_no")
  private Boolean familyRefusedServicesIndicator;

  @Column(name = "FIRST_EODT")
  private LocalDate firstEvaluatedOutApprovalDate;

  @Column(name = "RSP_AGY_CD")
  private String responsibleAgencyCode;

  @NotFound(action = NotFoundAction.IGNORE)
  @ManyToOne(fetch = FetchType.LAZY)
  @Fetch(FetchMode.SELECT)
  @JoinColumn(name = "L_GVR_ENTC", referencedColumnName = "SYS_ID")
  private County limitedAccessCounty;

  @Type(type = "date")
  @Column(name = "LMT_ACS_DT")
  private LocalDate limitedAccessDate;

  @Column(name = "LMT_ACSDSC")
  @ColumnTransformer(read = "trim(LMT_ACSDSC)")
  private String limitedAccessDesc;

  @Column(name = "ORIGCLS_DT")
  private LocalDate originalClosureDate;

  @OneToMany(fetch = FetchType.LAZY, mappedBy = "referral")
  private List<ReferralAssignment> baseAssignment;

  @Override
  public Serializable getPrimaryKey() {
    return getId();
  }

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public Boolean getAdditionalInfoIncludedCode() {
    return additionalInfoIncludedCode;
  }

  public void setAdditionalInfoIncludedCode(Boolean additionalInfoIncludedCode) {
    this.additionalInfoIncludedCode = additionalInfoIncludedCode;
  }

  public Boolean getAnonymousReporterIndicator() {
    return anonymousReporterIndicator;
  }

  public void setAnonymousReporterIndicator(Boolean anonymousReporterIndicator) {
    this.anonymousReporterIndicator = anonymousReporterIndicator;
  }

  public Boolean getApplicationForPetitionIndicator() {
    return applicationForPetitionIndicator;
  }

  public void setApplicationForPetitionIndicator(Boolean applicationForPetitionIndicator) {
    this.applicationForPetitionIndicator = applicationForPetitionIndicator;
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

  public Boolean getCaretakersPerpetratorCode() {
    return caretakersPerpetratorCode;
  }

  public void setCaretakersPerpetratorCode(Boolean caretakersPerpetratorCode) {
    this.caretakersPerpetratorCode = caretakersPerpetratorCode;
  }

  public LocalDate getClosureDate() {
    return closureDate;
  }

  public void setClosureDate(LocalDate closureDate) {
    this.closureDate = closureDate;
  }

  public Short getCommunicationMethodType() {
    return communicationMethodType;
  }

  public void setCommunicationMethodType(Short communicationMethodType) {
    this.communicationMethodType = communicationMethodType;
  }

  public String getCurrentLocationOfChildren() {
    return currentLocationOfChildren;
  }

  public void setCurrentLocationOfChildren(String currentLocationOfChildren) {
    this.currentLocationOfChildren = currentLocationOfChildren;
  }

  public String getDrmsAllegationDescriptionDoc() {
    return drmsAllegationDescriptionDoc;
  }

  public void setDrmsAllegationDescriptionDoc(String drmsAllegationDescriptionDoc) {
    this.drmsAllegationDescriptionDoc = drmsAllegationDescriptionDoc;
  }

  public String getDrmsErReferralDoc() {
    return drmsErReferralDoc;
  }

  public void setDrmsErReferralDoc(String drmsErReferralDoc) {
    this.drmsErReferralDoc = drmsErReferralDoc;
  }

  public String getDrmsInvestigationDoc() {
    return drmsInvestigationDoc;
  }

  public void setDrmsInvestigationDoc(String drmsInvestigationDoc) {
    this.drmsInvestigationDoc = drmsInvestigationDoc;
  }

  public String getFiledSuspectedChildAbuseReporttoLawEnforcementIndicator() {
    return filedSuspectedChildAbuseReporttoLawEnforcementIndicator;
  }

  public void setFiledSuspectedChildAbuseReporttoLawEnforcementIndicator(
      String filedSuspectedChildAbuseReporttoLawEnforcementIndicator) {
    this.filedSuspectedChildAbuseReporttoLawEnforcementIndicator =
        filedSuspectedChildAbuseReporttoLawEnforcementIndicator;
  }

  public String getFamilyAwarenessIndicator() {
    return familyAwarenessIndicator;
  }

  public void setFamilyAwarenessIndicator(String familyAwarenessIndicator) {
    this.familyAwarenessIndicator = familyAwarenessIndicator;
  }

  public Short getGovtEntityType() {
    return govtEntityType;
  }

  public void setGovtEntityType(Short govtEntityType) {
    this.govtEntityType = govtEntityType;
  }

  public String getLegalDefinitionCode() {
    return legalDefinitionCode;
  }

  public void setLegalDefinitionCode(String legalDefinitionCode) {
    this.legalDefinitionCode = legalDefinitionCode;
  }

  public String getLegalRightsNoticeIndicator() {
    return legalRightsNoticeIndicator;
  }

  public void setLegalRightsNoticeIndicator(String legalRightsNoticeIndicator) {
    this.legalRightsNoticeIndicator = legalRightsNoticeIndicator;
  }

  public LimitedAccess getLimitedAccessCode() {
    return limitedAccessCode;
  }

  public void setLimitedAccessCode(LimitedAccess limitedAccessCode) {
    this.limitedAccessCode = limitedAccessCode;
  }

  public LocalDate getMandatedCrossReportReceivedDate() {
    return mandatedCrossReportReceivedDate;
  }

  public void setMandatedCrossReportReceivedDate(LocalDate mandatedCrossReportReceivedDate) {
    this.mandatedCrossReportReceivedDate = mandatedCrossReportReceivedDate;
  }

  public String getReferralName() {
    return referralName;
  }

  public void setReferralName(String referralName) {
    this.referralName = referralName;
  }

  public Boolean getOpenAdequateCaseCode() {
    return openAdequateCaseCode;
  }

  public void setOpenAdequateCaseCode(Boolean openAdequateCaseCode) {
    this.openAdequateCaseCode = openAdequateCaseCode;
  }

  public LocalDate getReceivedDate() {
    return receivedDate;
  }

  public void setReceivedDate(LocalDate receivedDate) {
    this.receivedDate = receivedDate;
  }

  public LocalTime getReceivedTime() {
    return receivedTime;
  }

  public void setReceivedTime(LocalTime receivedTime) {
    this.receivedTime = receivedTime;
  }

  public Short getReferralResponseType() {
    return referralResponseType;
  }

  public void setReferralResponseType(Short referralResponseType) {
    this.referralResponseType = referralResponseType;
  }

  public Short getReferredToResourceType() {
    return referredToResourceType;
  }

  public void setReferredToResourceType(Short referredToResourceType) {
    this.referredToResourceType = referredToResourceType;
  }

  public LocalDate getResponseDeterminationDate() {
    return responseDeterminationDate;
  }

  public void setResponseDeterminationDate(LocalDate responseDeterminationDate) {
    this.responseDeterminationDate = responseDeterminationDate;
  }

  public LocalTime getResponseDeterminationTime() {
    return responseDeterminationTime;
  }

  public void setResponseDeterminationTime(LocalTime responseDeterminationTime) {
    this.responseDeterminationTime = responseDeterminationTime;
  }

  public String getResponseRationaleText() {
    return responseRationaleText;
  }

  public void setResponseRationaleText(String responseRationaleText) {
    this.responseRationaleText = responseRationaleText;
  }

  public String getScreenerNoteText() {
    return screenerNoteText;
  }

  public void setScreenerNoteText(String screenerNoteText) {
    this.screenerNoteText = screenerNoteText;
  }

  public Boolean getSpecificsIncludedCode() {
    return specificsIncludedCode;
  }

  public void setSpecificsIncludedCode(Boolean specificsIncludedCode) {
    this.specificsIncludedCode = specificsIncludedCode;
  }

  public Boolean getSufficientInformationCode() {
    return sufficientInformationCode;
  }

  public void setSufficientInformationCode(Boolean sufficientInformationCode) {
    this.sufficientInformationCode = sufficientInformationCode;
  }

  public Boolean getUnfoundedSeriesCode() {
    return unfoundedSeriesCode;
  }

  public void setUnfoundedSeriesCode(Boolean unfoundedSeriesCode) {
    this.unfoundedSeriesCode = unfoundedSeriesCode;
  }

  public String getLinkToPrimaryReferralId() {
    return linkToPrimaryReferralId;
  }

  public void setLinkToPrimaryReferralId(String linkToPrimaryReferralId) {
    this.linkToPrimaryReferralId = linkToPrimaryReferralId;
  }

  public String getAllegesAbuseOccurredAtAddressId() {
    return allegesAbuseOccurredAtAddressId;
  }

  public void setAllegesAbuseOccurredAtAddressId(String allegesAbuseOccurredAtAddressId) {
    this.allegesAbuseOccurredAtAddressId = allegesAbuseOccurredAtAddressId;
  }

  public String getFirstResponseDeterminedByStaffPersonId() {
    return firstResponseDeterminedByStaffPersonId;
  }

  public void setFirstResponseDeterminedByStaffPersonId(
      String firstResponseDeterminedByStaffPersonId) {
    this.firstResponseDeterminedByStaffPersonId = firstResponseDeterminedByStaffPersonId;
  }

  public StaffPerson getPrimaryContactStaffPerson() {
    return primaryContactStaffPerson;
  }

  public void setPrimaryContactStaffPerson(StaffPerson primaryContactStaffPerson) {
    this.primaryContactStaffPerson = primaryContactStaffPerson;
  }

  public County getCountySpecificCode() {
    return countySpecificCode;
  }

  public void setCountySpecificCode(County countySpecificCode) {
    this.countySpecificCode = countySpecificCode;
  }

  public Boolean getSpecialProjectReferralIndicator() {
    return specialProjectReferralIndicator;
  }

  public void setSpecialProjectReferralIndicator(Boolean specialProjectReferralIndicator) {
    this.specialProjectReferralIndicator = specialProjectReferralIndicator;
  }

  public Boolean getZippyCreatedIndicator() {
    return zippyCreatedIndicator;
  }

  public void setZippyCreatedIndicator(Boolean zippyCreatedIndicator) {
    this.zippyCreatedIndicator = zippyCreatedIndicator;
  }

  public Boolean getHomelessIndicator() {
    return homelessIndicator;
  }

  public void setHomelessIndicator(Boolean homelessIndicator) {
    this.homelessIndicator = homelessIndicator;
  }

  public Boolean getFamilyRefusedServicesIndicator() {
    return familyRefusedServicesIndicator;
  }

  public void setFamilyRefusedServicesIndicator(Boolean familyRefusedServicesIndicator) {
    this.familyRefusedServicesIndicator = familyRefusedServicesIndicator;
  }

  public LocalDate getFirstEvaluatedOutApprovalDate() {
    return firstEvaluatedOutApprovalDate;
  }

  public void setFirstEvaluatedOutApprovalDate(LocalDate firstEvaluatedOutApprovalDate) {
    this.firstEvaluatedOutApprovalDate = firstEvaluatedOutApprovalDate;
  }

  public String getResponsibleAgencyCode() {
    return responsibleAgencyCode;
  }

  public void setResponsibleAgencyCode(String responsibleAgencyCode) {
    this.responsibleAgencyCode = responsibleAgencyCode;
  }

  public County getLimitedAccessCounty() {
    return limitedAccessCounty;
  }

  public void setLimitedAccessCounty(County limitedAccessCounty) {
    this.limitedAccessCounty = limitedAccessCounty;
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

  public LocalDate getOriginalClosureDate() {
    return originalClosureDate;
  }

  public void setOriginalClosureDate(LocalDate originalClosureDate) {
    this.originalClosureDate = originalClosureDate;
  }

  public List<ReferralAssignment> getBaseAssignment() {
    return baseAssignment;
  }

  public void setBaseAssignment(List<ReferralAssignment> baseAssignment) {
    this.baseAssignment = baseAssignment;
  }
}
