package gov.ca.cwds.data.legacy.cms.entity;

import gov.ca.cwds.data.legacy.cms.CmsPersistentObject;
import gov.ca.cwds.data.persistence.PersistentObject;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import javax.persistence.Transient;
import org.hibernate.annotations.NamedQuery;

/**
 * @author CWDS CALS API Team
 */
@NamedQuery(
    name = "Client.find",
    query = "SELECT c FROM Client c"
        + " JOIN c.placementEpisodes pe"
        + " JOIN pe.outOfHomePlacements ohp"
        + " JOIN ohp.placementHome ph"
        + " WHERE ph.licenseNo = :licenseNumber AND c.identifier = :childId"
)
@NamedQuery(
    name = "Client.findAll",
    query = "SELECT c FROM Client c" +
        " JOIN c.placementEpisodes pe" +
        " JOIN pe.outOfHomePlacements ohp" +
        " JOIN ohp.placementHome ph" +
        " WHERE ph.licenseNo = :licenseNumber" +
        " ORDER BY c.identifier "
)
@NamedQuery(
    name = "Client.findByFacilityId",
    query = "SELECT c FROM Client c" +
        " JOIN c.placementEpisodes pe" +
        " JOIN pe.outOfHomePlacements ohp" +
        " JOIN ohp.placementHome ph" +
        " WHERE ph.id = :facilityId"
)
@SuppressWarnings({"squid:S3437", "squid:S2160"})
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Table(name = "CLIENT_T")
public class Client extends CmsPersistentObject implements IClient, PersistentObject {

  private static final long serialVersionUID = 783532074047017463L;

  @Id
  @Column(name = "IDENTIFIER", nullable = false, length = 10)
  private String identifier;

  @OneToMany(fetch = FetchType.LAZY)
  @JoinColumn(name = "FKCLIENT_T", referencedColumnName = "IDENTIFIER")
  @OrderBy("removalDt DESC")
  private Set<PlacementEpisode> placementEpisodes = new HashSet<>();

  @Column(name = "ADJDEL_IND", nullable = true, length = 1)
  private String adjudicatedDelinquentIndicator;

  @Column(name = "ADPTN_STCD", nullable = false, length = 1)
  private String adoptionStatusCode;

  @Column(name = "ALN_REG_NO", nullable = false, length = 12)
  private String alienRegistrationNumber;

  @Column(name = "B_CNTRY_C", nullable = false)
  private Short birthCountryCodeType;

  @Column(name = "B_STATE_C", nullable = false)
  private Short birthStateCodeType;

  @Column(name = "BIRTH_CITY", nullable = false, length = 35)
  private String birthCity;

  @Column(name = "BIRTH_DT", nullable = true)
  private LocalDate birthDate;

  @Column(name = "BP_VER_IND", nullable = false, length = 1)
  private String birthplaceVerifiedIndicator;

  @Column(name = "BR_FAC_NM", nullable = false, length = 35)
  private String birthFacilityName;

  @Column(name = "CHLD_CLT_B", nullable = false, length = 1)
  private String childClientIndicator;

  @Column(name = "COM_FST_NM", nullable = false, length = 20)
  private String commonFirstName;

  @Column(name = "COM_LST_NM", nullable = false, length = 25)
  private String commonLastName;

  @Column(name = "COM_MID_NM", nullable = false, length = 20)
  private String commonMiddleName;

  @Column(name = "COMMNT_DSC", nullable = false, length = 120)
  private String commentDescription;

  @Column(name = "CONF_ACTDT", nullable = true)
  private LocalDate confidentialityActionDate;

  @Column(name = "CONF_EFIND", nullable = false, length = 1)
  private String confidentialityInEffectIndicator;

  @Column(name = "COTH_DESC", nullable = false, length = 25)
  private String currentlyOtherDescription;

  @Column(name = "CREATN_DT", nullable = false)
  private LocalDate creationDate;

  @Column(name = "CURRCA_IND", nullable = false, length = 1)
  private String currentCaChildrenServiceIndicator;

  @Column(name = "CURREG_IND", nullable = false, length = 1)
  private String currentlyRegionalCenterIndicator;

  @Column(name = "D_STATE_C", nullable = false)
  private Short driverLicenseStateCodeType;

  @Column(name = "DEATH_DT", nullable = true)
  private LocalDate deathDate;

  @Column(name = "DEATH_PLC", nullable = true, length = 35)
  private String deathPlace;

  @Column(name = "DRV_LIC_NO", nullable = false, length = 20)
  private String driverLicenseNumber;

  @Column(name = "DTH_DT_IND", nullable = false, length = 1)
  private String deathDateVerifiedIndicator;

  @Column(name = "DTH_RN_TXT", nullable = true, length = 10)
  private String deathReasonText;

  @Column(name = "EMAIL_ADDR", nullable = true, length = 50)
  private String emailAddress;

  @Column(name = "EST_DOB_CD", nullable = false, length = 1)
  private String estimatedDobCode;

  @Column(name = "ETH_UD_CD", nullable = true, length = 1)
  private String ethnicityUnableToDetermineReasonCode;

  @Column(name = "FTERM_DT", nullable = true)
  private LocalDate fatherParentalRightTermDate;

  @Column(name = "GENDER_CD", nullable = false, length = 1)
  private String genderCode;

  @Column(name = "HCARE_IND", nullable = false, length = 1)
  private String individualHealthCarePlanIndicator;

  @Column(name = "HEALTH_TXT", nullable = true, length = 10)
  private String healthSummaryText;

  @Column(name = "HISP_CD", nullable = false, length = 1)
  private String hispanicOriginCode;

  @Column(name = "HISP_UD_CD", nullable = true, length = 1)
  private String hispanicUnableToDetermineReasonCode;

  @Column(name = "I_CNTRY_C", nullable = false)
  private Short immigrationCountryCodeType;

  @Column(name = "IMGT_STC", nullable = false)
  private Short immigrationStatusType;

  @Column(name = "INCAPC_CD", nullable = false, length = 2)
  private String incapacitatedParentCode;

  @Column(name = "LIMIT_IND", nullable = false, length = 1)
  private String limitationOnScpHealthIndicator;

  @Column(name = "LITRATE_CD", nullable = false, length = 1)
  private String literateCode;

  @Column(name = "MAR_HIST_B", nullable = false, length = 1)
  private String maritalCohabitatnHistoryIndicator;

  @Column(name = "MILT_STACD", nullable = false, length = 1)
  private String militaryStatusCode;

  @Column(name = "MRTL_STC", nullable = false)
  private Short maritalStatusType;

  @Column(name = "MTERM_DT", nullable = true)
  private LocalDate motherParentalRightTermDate;

  @Column(name = "NAME_TPC", nullable = false)
  private Short nameType;

  @Column(name = "NMPRFX_DSC", nullable = false, length = 6)
  private String namePrefixDescription;

  @Column(name = "OUTWRT_IND", nullable = false, length = 1)
  private String outstandingWarrantIndicator;

  @Column(name = "P_ETHNCTYC", nullable = false)
  private Short primaryEthnicityType;

  @Column(name = "P_LANG_TPC", nullable = false)
  private Short primaryLanguageType;

  @Column(name = "POTH_DESC", nullable = false, length = 25)
  private String previousOtherDescription;

  @Column(name = "PREREG_IND", nullable = false, length = 1)
  private String previousRegionalCenterIndicator;

  @Column(name = "PREVCA_IND", nullable = false, length = 1)
  private String previousCaChildrenServiceIndicator;

  @Column(name = "RLGN_TPC", nullable = false)
  private Short religionType;

  @Column(name = "S_LANG_TC", nullable = false)
  private Short secondaryLanguageType;

  @Column(name = "SENSTV_IND", nullable = false, length = 1)
  private String sensitivityIndicator;

  @Column(name = "SNTV_HLIND", nullable = false, length = 1)
  private String sensitiveHealthInfoOnFileIndicator;

  @Column(name = "SOC158_IND", nullable = false, length = 1)
  private String soc158SealedClientIndicator;

  @Column(name = "SOCPLC_CD", nullable = false, length = 1)
  private String soc158PlacementCode;

  @Column(name = "SS_NO", nullable = false, length = 9)
  private String socialSecurityNumber;

  @Column(name = "SSN_CHG_CD", nullable = false, length = 1)
  private String socialSecurityNumberChangedCode;

  @Column(name = "SUFX_TLDSC", nullable = false, length = 4)
  private String suffixTitleDescription;

  @Column(name = "TR_MBVRT_B", nullable = false, length = 1)
  private String tribalMembershipVerifcationIndicator;

  @Column(name = "TRBA_CLT_B", nullable = false, length = 1)
  private String tribalAncestryClientIndicator;

  @Column(name = "UNEMPLY_CD", nullable = false, length = 2)
  private String unemployedParentCode;

  @Column(name = "ZIPPY_IND", nullable = false, length = 1)
  private String zippyCreatedIndicator;

  @Column(name = "CL_INDX_NO", nullable = true, length = 12)
  private String clIndxNo;

  public String getIdentifier() {
    return identifier;
  }

  public void setIdentifier(String identifier) {
    this.identifier = identifier;
  }


  public String getAdoptionStatusCode() {
    return adoptionStatusCode;
  }

  public void setAdoptionStatusCode(String adoptionStatusCode) {
    this.adoptionStatusCode = adoptionStatusCode;
  }


  public String getAlienRegistrationNumber() {
    return alienRegistrationNumber;
  }

  public void setAlienRegistrationNumber(String alienRegistrationNumber) {
    this.alienRegistrationNumber = alienRegistrationNumber;
  }

  public LocalDate getBirthDate() {
    return birthDate;
  }

  public void setBirthDate(LocalDate birthDate) {
    this.birthDate = birthDate;
  }


  public String getBirthFacilityName() {
    return birthFacilityName;
  }

  public void setBirthFacilityName(String birthFacilityName) {
    this.birthFacilityName = birthFacilityName;
  }

  public Short getBirthStateCodeType() {
    return birthStateCodeType;
  }

  public void setBirthStateCodeType(Short birthStateCodeType) {
    this.birthStateCodeType = birthStateCodeType;
  }


  public Short getBirthCountryCodeType() {
    return birthCountryCodeType;
  }

  public void setBirthCountryCodeType(Short birthCountryCodeType) {
    this.birthCountryCodeType = birthCountryCodeType;
  }


  public String getChildClientIndicator() {
    return childClientIndicator;
  }

  public void setChildClientIndicator(String childClientIndicator) {
    this.childClientIndicator = childClientIndicator;
  }


  public String getCommonFirstName() {
    return commonFirstName;
  }

  public void setCommonFirstName(String commonFirstName) {
    this.commonFirstName = commonFirstName;
  }


  public String getCommonLastName() {
    return commonLastName;
  }

  public void setCommonLastName(String commonLastName) {
    this.commonLastName = commonLastName;
  }


  public String getCommonMiddleName() {
    return commonMiddleName;
  }

  public void setCommonMiddleName(String commonMiddleName) {
    this.commonMiddleName = commonMiddleName;
  }


  public String getConfidentialityInEffectIndicator() {
    return confidentialityInEffectIndicator;
  }

  public void setConfidentialityInEffectIndicator(String confidentialityInEffectIndicator) {
    this.confidentialityInEffectIndicator = confidentialityInEffectIndicator;
  }


  public LocalDate getConfidentialityActionDate() {
    return confidentialityActionDate;
  }

  public void setConfidentialityActionDate(LocalDate confidentialityActionDate) {
    this.confidentialityActionDate = confidentialityActionDate;
  }


  public LocalDate getCreationDate() {
    return creationDate;
  }

  public void setCreationDate(LocalDate creationDate) {
    this.creationDate = creationDate;
  }


  public LocalDate getDeathDate() {
    return deathDate;
  }

  public void setDeathDate(LocalDate deathDate) {
    this.deathDate = deathDate;
  }


  public String getDeathReasonText() {
    return deathReasonText;
  }

  public void setDeathReasonText(String deathReasonText) {
    this.deathReasonText = deathReasonText;
  }


  public String getDriverLicenseNumber() {
    return driverLicenseNumber;
  }

  public void setDriverLicenseNumber(String driverLicenseNumber) {
    this.driverLicenseNumber = driverLicenseNumber;
  }


  public Short getDriverLicenseStateCodeType() {
    return driverLicenseStateCodeType;
  }

  public void setDriverLicenseStateCodeType(Short driverLicenseStateCodeType) {
    this.driverLicenseStateCodeType = driverLicenseStateCodeType;
  }


  public String getGenderCode() {
    return genderCode;
  }

  public void setGenderCode(String genderCode) {
    this.genderCode = genderCode;
  }


  public Short getImmigrationCountryCodeType() {
    return immigrationCountryCodeType;
  }

  public void setImmigrationCountryCodeType(Short immigrationCountryCodeType) {
    this.immigrationCountryCodeType = immigrationCountryCodeType;
  }

  public Short getImmigrationStatusType() {
    return immigrationStatusType;
  }

  public void setImmigrationStatusType(Short immigrationStatusType) {
    this.immigrationStatusType = immigrationStatusType;
  }

  public String getIncapacitatedParentCode() {
    return incapacitatedParentCode;
  }

  public void setIncapacitatedParentCode(String incapacitatedParentCode) {
    this.incapacitatedParentCode = incapacitatedParentCode;
  }


  public String getLiterateCode() {
    return literateCode;
  }

  public void setLiterateCode(String literateCode) {
    this.literateCode = literateCode;
  }


  public String getMaritalCohabitatnHistoryIndicator() {
    return maritalCohabitatnHistoryIndicator;
  }

  public void setMaritalCohabitatnHistoryIndicator(String maritalCohabitatnHistoryIndicator) {
    this.maritalCohabitatnHistoryIndicator = maritalCohabitatnHistoryIndicator;
  }

  public Short getMaritalStatusType() {
    return maritalStatusType;
  }

  public void setMaritalStatusType(Short maritalStatusType) {
    this.maritalStatusType = maritalStatusType;
  }

  public String getMilitaryStatusCode() {
    return militaryStatusCode;
  }

  public void setMilitaryStatusCode(String militaryStatusCode) {
    this.militaryStatusCode = militaryStatusCode;
  }

  public String getNamePrefixDescription() {
    return namePrefixDescription;
  }

  public void setNamePrefixDescription(String namePrefixDescription) {
    this.namePrefixDescription = namePrefixDescription;
  }


  public Short getNameType() {
    return nameType;
  }

  public void setNameType(Short nameType) {
    this.nameType = nameType;
  }

  public String getOutstandingWarrantIndicator() {
    return outstandingWarrantIndicator;
  }

  public void setOutstandingWarrantIndicator(String outstandingWarrantIndicator) {
    this.outstandingWarrantIndicator = outstandingWarrantIndicator;
  }

  public Short getPrimaryEthnicityType() {
    return primaryEthnicityType;
  }

  public void setPrimaryEthnicityType(Short primaryEthnicityType) {
    this.primaryEthnicityType = primaryEthnicityType;
  }


  public Short getPrimaryLanguageType() {
    return primaryLanguageType;
  }

  public void setPrimaryLanguageType(Short primaryLanguageType) {
    this.primaryLanguageType = primaryLanguageType;
  }


  public Short getReligionType() {
    return religionType;
  }

  public void setReligionType(Short religionType) {
    this.religionType = religionType;
  }


  public Short getSecondaryLanguageType() {
    return secondaryLanguageType;
  }

  public void setSecondaryLanguageType(Short secondaryLanguageType) {
    this.secondaryLanguageType = secondaryLanguageType;
  }

  public String getSensitivityIndicator() {
    return sensitivityIndicator;
  }

  public void setSensitivityIndicator(String sensitivityIndicator) {
    this.sensitivityIndicator = sensitivityIndicator;
  }


  public String getSensitiveHealthInfoOnFileIndicator() {
    return sensitiveHealthInfoOnFileIndicator;
  }

  public void setSensitiveHealthInfoOnFileIndicator(String sensitiveHealthInfoOnFileIndicator) {
    this.sensitiveHealthInfoOnFileIndicator = sensitiveHealthInfoOnFileIndicator;
  }


  public String getSocialSecurityNumber() {
    return socialSecurityNumber;
  }

  public void setSocialSecurityNumber(String socialSecurityNumber) {
    this.socialSecurityNumber = socialSecurityNumber;
  }


  public String getSocialSecurityNumberChangedCode() {
    return socialSecurityNumberChangedCode;
  }

  public void setSocialSecurityNumberChangedCode(String socialSecurityNumberChangedCode) {
    this.socialSecurityNumberChangedCode = socialSecurityNumberChangedCode;
  }

  public String getSuffixTitleDescription() {
    return suffixTitleDescription;
  }

  public void setSuffixTitleDescription(String suffixTitleDescription) {
    this.suffixTitleDescription = suffixTitleDescription;
  }


  public String getUnemployedParentCode() {
    return unemployedParentCode;
  }

  public void setUnemployedParentCode(String unemployedParentCode) {
    this.unemployedParentCode = unemployedParentCode;
  }

  public String getCommentDescription() {
    return commentDescription;
  }

  public void setCommentDescription(String commentDescription) {
    this.commentDescription = commentDescription;
  }

  public String getEstimatedDobCode() {
    return estimatedDobCode;
  }

  public void setEstimatedDobCode(String estimatedDobCode) {
    this.estimatedDobCode = estimatedDobCode;
  }

  public String getBirthplaceVerifiedIndicator() {
    return birthplaceVerifiedIndicator;
  }

  public void setBirthplaceVerifiedIndicator(String birthplaceVerifiedIndicator) {
    this.birthplaceVerifiedIndicator = birthplaceVerifiedIndicator;
  }

  public String getHispanicOriginCode() {
    return hispanicOriginCode;
  }

  public void setHispanicOriginCode(String hispanicOriginCode) {
    this.hispanicOriginCode = hispanicOriginCode;
  }

  public String getCurrentCaChildrenServiceIndicator() {
    return currentCaChildrenServiceIndicator;
  }

  public void setCurrentCaChildrenServiceIndicator(String currentCaChildrenServiceIndicator) {
    this.currentCaChildrenServiceIndicator = currentCaChildrenServiceIndicator;
  }

  public String getCurrentlyRegionalCenterIndicator() {
    return currentlyRegionalCenterIndicator;
  }

  public void setCurrentlyRegionalCenterIndicator(String currentlyRegionalCenterIndicator) {
    this.currentlyRegionalCenterIndicator = currentlyRegionalCenterIndicator;
  }

  public String getCurrentlyOtherDescription() {
    return currentlyOtherDescription;
  }

  public void setCurrentlyOtherDescription(String currentlyOtherDescription) {
    this.currentlyOtherDescription = currentlyOtherDescription;
  }

  public String getPreviousCaChildrenServiceIndicator() {
    return previousCaChildrenServiceIndicator;
  }

  public void setPreviousCaChildrenServiceIndicator(String previousCaChildrenServiceIndicator) {
    this.previousCaChildrenServiceIndicator = previousCaChildrenServiceIndicator;
  }

  public String getPreviousRegionalCenterIndicator() {
    return previousRegionalCenterIndicator;
  }

  public void setPreviousRegionalCenterIndicator(String previousRegionalCenterIndicator) {
    this.previousRegionalCenterIndicator = previousRegionalCenterIndicator;
  }

  public String getPreviousOtherDescription() {
    return previousOtherDescription;
  }

  public void setPreviousOtherDescription(String previousOtherDescription) {
    this.previousOtherDescription = previousOtherDescription;
  }

  public String getIndividualHealthCarePlanIndicator() {
    return individualHealthCarePlanIndicator;
  }

  public void setIndividualHealthCarePlanIndicator(String individualHealthCarePlanIndicator) {
    this.individualHealthCarePlanIndicator = individualHealthCarePlanIndicator;
  }

  public String getLimitationOnScpHealthIndicator() {
    return limitationOnScpHealthIndicator;
  }

  public void setLimitationOnScpHealthIndicator(String limitationOnScpHealthIndicator) {
    this.limitationOnScpHealthIndicator = limitationOnScpHealthIndicator;
  }

  public String getBirthCity() {
    return birthCity;
  }

  public void setBirthCity(String birthCity) {
    this.birthCity = birthCity;
  }


  public String getHealthSummaryText() {
    return healthSummaryText;
  }

  public void setHealthSummaryText(String healthSummaryText) {
    this.healthSummaryText = healthSummaryText;
  }

  public LocalDate getMotherParentalRightTermDate() {
    return motherParentalRightTermDate;
  }

  public void setMotherParentalRightTermDate(LocalDate motherParentalRightTermDate) {
    this.motherParentalRightTermDate = motherParentalRightTermDate;
  }


  public LocalDate getFatherParentalRightTermDate() {
    return fatherParentalRightTermDate;
  }

  public void setFatherParentalRightTermDate(LocalDate fatherParentalRightTermDate) {
    this.fatherParentalRightTermDate = fatherParentalRightTermDate;
  }


  public String getZippyCreatedIndicator() {
    return zippyCreatedIndicator;
  }

  public void setZippyCreatedIndicator(String zippyCreatedIndicator) {
    this.zippyCreatedIndicator = zippyCreatedIndicator;
  }

  public String getDeathPlace() {
    return deathPlace;
  }

  public void setDeathPlace(String deathPlace) {
    this.deathPlace = deathPlace;
  }


  public String getTribalMembershipVerifcationIndicator() {
    return tribalMembershipVerifcationIndicator;
  }

  public void setTribalMembershipVerifcationIndicator(String tribalMembershipVerifcationIndicator) {
    this.tribalMembershipVerifcationIndicator = tribalMembershipVerifcationIndicator;
  }

  public String getTribalAncestryClientIndicator() {
    return tribalAncestryClientIndicator;
  }

  public void setTribalAncestryClientIndicator(String tribalAncestryClientIndicator) {
    this.tribalAncestryClientIndicator = tribalAncestryClientIndicator;
  }


  public String getSoc158SealedClientIndicator() {
    return soc158SealedClientIndicator;
  }

  public void setSoc158SealedClientIndicator(String soc158SealedClientIndicator) {
    this.soc158SealedClientIndicator = soc158SealedClientIndicator;
  }


  public String getDeathDateVerifiedIndicator() {
    return deathDateVerifiedIndicator;
  }

  public void setDeathDateVerifiedIndicator(String deathDateVerifiedIndicator) {
    this.deathDateVerifiedIndicator = deathDateVerifiedIndicator;
  }


  public String getEmailAddress() {
    return emailAddress;
  }

  public void setEmailAddress(String emailAddress) {
    this.emailAddress = emailAddress;
  }

  public String getAdjudicatedDelinquentIndicator() {
    return adjudicatedDelinquentIndicator;
  }

  public void setAdjudicatedDelinquentIndicator(String adjudicatedDelinquentIndicator) {
    this.adjudicatedDelinquentIndicator = adjudicatedDelinquentIndicator;
  }

  public String getEthnicityUnableToDetermineReasonCode() {
    return ethnicityUnableToDetermineReasonCode;
  }

  public void setEthnicityUnableToDetermineReasonCode(String ethnicityUnableToDetermineReasonCode) {
    this.ethnicityUnableToDetermineReasonCode = ethnicityUnableToDetermineReasonCode;
  }


  public String getHispanicUnableToDetermineReasonCode() {
    return hispanicUnableToDetermineReasonCode;
  }

  public void setHispanicUnableToDetermineReasonCode(String hispanicUnableToDetermineReasonCode) {
    this.hispanicUnableToDetermineReasonCode = hispanicUnableToDetermineReasonCode;
  }


  public String getSoc158PlacementCode() {
    return soc158PlacementCode;
  }

  public void setSoc158PlacementCode(String soc158PlacementCode) {
    this.soc158PlacementCode = soc158PlacementCode;
  }

  public String getClIndxNo() {
    return clIndxNo;
  }

  public void setClIndxNo(String clIndxNo) {
    this.clIndxNo = clIndxNo;
  }

  @Override
  @Transient
  public Serializable getPrimaryKey() {
    return getIdentifier();
  }



  @Override
  public Set<PlacementEpisode> getPlacementEpisodes() {
    return placementEpisodes;
  }

  public void setPlacementEpisodes(Set<PlacementEpisode> placementEpisodes) {
    this.placementEpisodes = placementEpisodes;
  }

}
