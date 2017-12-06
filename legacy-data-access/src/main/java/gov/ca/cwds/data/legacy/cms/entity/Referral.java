package gov.ca.cwds.data.legacy.cms.entity;

import gov.ca.cwds.data.legacy.cms.CmsPersistentObject;
import java.io.Serializable;
import java.time.LocalDate;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import org.hibernate.annotations.Type;

/**
 * @author CWDS TPT-3 Team
 */
@Entity
@Table(name = "REFERL_T")

public class Referral extends CmsPersistentObject {

  public static final String FIND_ACTIVE_BY_STAFF_ID = "findActiveReferralsByStaffId";
  //public static final String

  @Id
  @Column(name = "IDENTIFIER", length = CMS_ID_LEN)
  private String id;

  @Column(name = "ADD_INF_CD")
  private Boolean additionalInfoIncludedCode;

  @Column(name = "ANRPTR_IND")
  private Boolean anonymousReporterIndicator;

  @Column(name = "PETAPL_IND")
  private Boolean applicationForPetitionIndicator;

  @Column(name = "APRVL_NO")
  private String approvalNumber;

  @Column(name = "APV_STC")
  private Short approvalStatusType;

  @Column(name = "CR_PERP_CD")
  private Boolean caretakersPerpetratorCode;

  @Column(name = "REFCLSR_DT")
  private LocalDate closureDate;

  @Column(name = "CMM_MTHC")
  private Short communicationMethodType;

  @Column(name = "CHILOC_TXT")
  private String currentLocationOfChildren;

  @Column(name = "ALGDSC_DOC")
  private String drmsAllegationDescriptionDoc;

  @Column(name = "ER_REF_DOC")
  private String drmsErReferralDoc;

  @Column(name = "INVSTG_DOC")
  private String drmsInvestigationDoc;

  @Column(name = "XRPT_LWIND")
  private String filedSuspectedChildAbuseReporttoLawEnforcementIndicator;

  @Column(name = "FMY_AW_IND")
  private String familyAwarenessIndicator;

  @Column(name = "GVR_ENTC")
  private Short govtEntityType;

  @Column(name = "LGL_DEF_CD")
  private String legalDefinitionCode;

  @Column(name = "LGLRGT_IND")
  private String legalRightsNoticeIndicator;

  @Column(name = "LMT_ACSSCD")
  private String limitedAccessCode;

  @Column(name = "XRPT_RCVDT")
  private LocalDate mandatedCrossReportReceivedDate;

  @Column(name = "REFERRL_NM")
  private String referralName;

  @Column(name = "ADQT_CS_CD")
  private String openAdequateCaseCode;

  @Column(name = "REF_RCV_DT")
  private LocalDate receivedDate;

  @Type(type = "time")
  @Column(name = "REF_RCV_TM")
  private LocalDate receivedTime;

  @Column(name = "RFR_RSPC")
  private Short referralResponseType;

  @Column(name = "RFRD_RSC")
  private Short referredToResourceType;

  @Column(name = "RSP_DTR_DT")
  private LocalDate responseDeterminationDate;

  @Type(type = "time")
  @Column(name = "RSP_DTR_TM")
  private LocalDate responseDeterminationTime;

  @Column(name = "RSP_RTNTXT")
  private String responseRationaleText;

  @Column(name = "SCN_NT_TXT")
  private String screenerNoteText;

  @Column(name = "SP_INCL_CD")
  private String specificsIncludedCode;

  @Column(name = "SFC_INF_CD")
  private String sufficientInformationCode;

  @Column(name = "UNFD_SR_CD")
  private String unfoundedSeriesCode;

  @Column(name = "FKREFERL_T", length = CMS_ID_LEN)
  private String linkToPrimaryReferralId;

  @Column(name = "FKADDRS_T", length = CMS_ID_LEN)
  private String allegesAbuseOccurredAtAddressId;

  @Column(name = "FKSTFPERS0", length = CMS_ID_LEN)
  private String firstResponseDeterminedByStaffPersonId;

  @Column(name = "FKSTFPERST", length = CMS_ID_LEN)
  private String primaryContactStaffPersonId;

  @Column(name = "CNTY_SPFCD")
  private String countySpecificCode;

  @Column(name = "SPRJRF_IND")
  private String specialProjectReferralIndicator;

  @Column(name = "ZIPPY_IND")
  private String zippyCreatedIndicator;

  @Column(name = "HOMLES_IND")
  private String homelessIndicator;

  @Column(name = "FAMREF_IND")
  private String familyRefusedServicesIndicator;

  @Column(name = "FIRST_EODT")
  private LocalDate firstEvaluatedOutApprovalDate;

  @Column(name = "RSP_AGY_CD")
  private String responsibleAgencyCode;

  @Column(name = "L_GVR_ENTC")
  private Short limitedAccessGovtAgencyType;

  @Type(type = "date")
  @Column(name = "LMT_ACS_DT")
  private LocalDate limitedAccessDate;

  @Column(name = "LMT_ACSDSC")
  private String limitedAccessDesc;

  @Column(name = "ORIGCLS_DT")
  private LocalDate originalClosureDate;

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
    this.filedSuspectedChildAbuseReporttoLawEnforcementIndicator = filedSuspectedChildAbuseReporttoLawEnforcementIndicator;
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

  public String getLimitedAccessCode() {
    return limitedAccessCode;
  }

  public void setLimitedAccessCode(String limitedAccessCode) {
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

  public String getOpenAdequateCaseCode() {
    return openAdequateCaseCode;
  }

  public void setOpenAdequateCaseCode(String openAdequateCaseCode) {
    this.openAdequateCaseCode = openAdequateCaseCode;
  }

  public LocalDate getReceivedDate() {
    return receivedDate;
  }

  public void setReceivedDate(LocalDate receivedDate) {
    this.receivedDate = receivedDate;
  }

  public LocalDate getReceivedTime() {
    return receivedTime;
  }

  public void setReceivedTime(LocalDate receivedTime) {
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

  public LocalDate getResponseDeterminationTime() {
    return responseDeterminationTime;
  }

  public void setResponseDeterminationTime(LocalDate responseDeterminationTime) {
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

  public String getSpecificsIncludedCode() {
    return specificsIncludedCode;
  }

  public void setSpecificsIncludedCode(String specificsIncludedCode) {
    this.specificsIncludedCode = specificsIncludedCode;
  }

  public String getSufficientInformationCode() {
    return sufficientInformationCode;
  }

  public void setSufficientInformationCode(String sufficientInformationCode) {
    this.sufficientInformationCode = sufficientInformationCode;
  }

  public String getUnfoundedSeriesCode() {
    return unfoundedSeriesCode;
  }

  public void setUnfoundedSeriesCode(String unfoundedSeriesCode) {
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

  public String getPrimaryContactStaffPersonId() {
    return primaryContactStaffPersonId;
  }

  public void setPrimaryContactStaffPersonId(String primaryContactStaffPersonId) {
    this.primaryContactStaffPersonId = primaryContactStaffPersonId;
  }

  public String getCountySpecificCode() {
    return countySpecificCode;
  }

  public void setCountySpecificCode(String countySpecificCode) {
    this.countySpecificCode = countySpecificCode;
  }

  public String getSpecialProjectReferralIndicator() {
    return specialProjectReferralIndicator;
  }

  public void setSpecialProjectReferralIndicator(String specialProjectReferralIndicator) {
    this.specialProjectReferralIndicator = specialProjectReferralIndicator;
  }

  public String getZippyCreatedIndicator() {
    return zippyCreatedIndicator;
  }

  public void setZippyCreatedIndicator(String zippyCreatedIndicator) {
    this.zippyCreatedIndicator = zippyCreatedIndicator;
  }

  public String getHomelessIndicator() {
    return homelessIndicator;
  }

  public void setHomelessIndicator(String homelessIndicator) {
    this.homelessIndicator = homelessIndicator;
  }

  public String getFamilyRefusedServicesIndicator() {
    return familyRefusedServicesIndicator;
  }

  public void setFamilyRefusedServicesIndicator(String familyRefusedServicesIndicator) {
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

  public Short getLimitedAccessGovtAgencyType() {
    return limitedAccessGovtAgencyType;
  }

  public void setLimitedAccessGovtAgencyType(Short limitedAccessGovtAgencyType) {
    this.limitedAccessGovtAgencyType = limitedAccessGovtAgencyType;
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
}
