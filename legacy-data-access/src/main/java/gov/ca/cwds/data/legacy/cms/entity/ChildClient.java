package gov.ca.cwds.data.legacy.cms.entity;

import gov.ca.cwds.data.legacy.cms.CmsPersistentObject;
import gov.ca.cwds.data.legacy.cms.entity.converter.NullableBooleanConverter;
import gov.ca.cwds.data.legacy.cms.entity.enums.Adoptable;
import gov.ca.cwds.data.legacy.cms.entity.enums.AwolAbducted;
import gov.ca.cwds.data.legacy.cms.entity.enums.Disability;
import gov.ca.cwds.data.legacy.cms.entity.enums.IcwaEligibility;
import gov.ca.cwds.data.legacy.cms.entity.enums.PreviouslyAdopted;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;
import org.hibernate.annotations.ColumnTransformer;
import org.hibernate.annotations.Type;

/**
 * {@link CmsPersistentObject} Class representing a Child Client.
 *
 * @author CWDS API Team
 */
@SuppressWarnings({"squid:S3437", "squid:S2160"})
@Entity
@PrimaryKeyJoinColumn(name = "FKCLIENT_T")
@Table(name = "CHLD_CLT")
public class ChildClient extends Client {

  /**
   * Serialization version
   */
  private static final long serialVersionUID = -109941247950464170L;

  @Column(name = "FKCLIENT_T", length = CMS_ID_LEN, insertable = false, updatable = false)
  private String victimClientId;

  @Column(name = "ADOPTBL_CD")
  @Convert(converter = Adoptable.AdoptableConverter.class)
  private Adoptable adoptable;

  @Type(type = "short")
  @Column(name = "ADPT_AGE")
  private Short adoptedAge;

  @Type(type = "yes_no")
  @Column(name = "FC_ELIGT_B")
  private boolean afdcFcEligibilityIndicatorVar;

  @Type(type = "yes_no")
  @Column(name = "EDONFL_IND")
  private boolean allEducationInfoOnFileIndicator;

  @Type(type = "yes_no")
  @Column(name = "HLONFL_IND")
  private boolean allHealthInfoOnFileIndicator;

  @Column(name = "ACQ_ED_DSC")
  @ColumnTransformer(read = "trim(ACQ_ED_DSC)")
  private String attemptToAcquireEducInfoDesc;

  @Column(name = "ACQ_HTHDSC")
  @ColumnTransformer(read = "trim(ACQ_HTHDSC)")
  private String attemptToAcquireHlthInfoDesc;

  @Column(name = "AWOL_AB_CD")
  @Convert(converter = AwolAbducted.AwolAbductedConverter.class)
  private AwolAbducted awolAbducted;

  @Type(type = "yes_no")
  @Column(name = "BHIST_IND")
  private boolean birthHistoryIndicatorVar;

  @Type(type = "yes_no")
  @Column(name = "INDIAN_IND")
  private boolean childIndianAncestryIndicator;

  @Column(name = "CLG_IND")
  @Convert(converter = NullableBooleanConverter.class)
  private Boolean collegeIndicator;

  @Column(name = "CURNT_CSID")
  private String currentCaseId;

  @Column(name = "DTH_CIRC", nullable = false)
  private short deathCircumstancesTypeCode;

  @Column(name = "DISABLD_CD")
  @Convert(converter = Disability.DisabilityConverter.class)
  private Disability disabilityDiagnosed;

  @Column(name = "HEPDOC_OLD")
  @ColumnTransformer(read = "trim(HEPDOC_OLD)")
  private String drmsHePassportDocOld;

  @Column(name = "DRMSHEPDOC")
  @ColumnTransformer(read = "trim(DRMSHEPDOC)")
  private String drmsHealthEducPassportDoc;

  @Column(name = "VOL_PLCDOC")
  @ColumnTransformer(read = "trim(VOL_PLCDOC)")
  private String drmsVoluntaryPlcmntAgrmntDoc;

  @Type(type = "yes_no")
  @Column(name = "FC2_APLT_B")
  private boolean fc2EligApplicationIndicatorVar;

  @Column(name = "FSTAMP_DT")
  private LocalDate foodStampsApplicationDate;

  @Type(type = "yes_no")
  @Column(name = "FSTAMP_IND")
  private boolean foodStampsApplicationIndicator;

  @Column(name = "ICWA_ELGCD")
  @Convert(converter = IcwaEligibility.IcwaEligibilityConverter.class)
  private IcwaEligibility icwaEligibility;

  @Type(type = "yes_no")
  @Column(name = "ICADSR_IND")
  private boolean intercountryAdoptDisruptedIndicator;

  @Type(type = "yes_no")
  @Column(name = "ICADSL_IND")
  private boolean intercountryAdoptDissolvedIndicator;

  @Type(type = "yes_no")
  @Column(name = "MEDELIGT_B")
  private boolean medEligibilityApplicationIndicatorVar;

  @Type(type = "yes_no")
  @Column(name = "MNRMOM_IND")
  private boolean minorNmdParentIndicator;

  @Type(type = "yes_no")
  @Column(name = "PRTLIM_IND")
  private boolean parentalRightsLimitedIndicator;

  @Type(type = "yes_no")
  @Column(name = "PRG_TRMT_B")
  private boolean parentalRightsTermintnIndicatorVar;

  @Type(type = "yes_no")
  @Column(name = "PTRN_INT_B")
  private boolean paternityIndividualIndicatorVar;

  @Column(name = "PSVOC_IND")
  @Convert(converter = NullableBooleanConverter.class)
  private Boolean postsecVocIndicator;

  @Column(name = "PREADPT_CD")
  @Convert(converter = PreviouslyAdopted.PreviouslyAdoptedConverter.class)
  private PreviouslyAdopted previouslyAdopted;

  @Type(type = "yes_no")
  @Column(name = "SFSURB_IND")
  private boolean safelySurrendedBabiesIndicatorVar;

  @Type(type = "yes_no")
  @Column(name = "SAW1APLT_B")
  private boolean saw1EligApplicationIndicatorVar;

  @Type(type = "integer")
  @Column(name = "SAWS_CS_NO")
  private Integer sawsCaseSerialNumber;

  @Column(name = "SIJ_INT_DT")
  private LocalDate sijsScheduledInterviewDate;

  @Column(name = "SSI_SCR_DT")
  private LocalDate siiNextScreeningDueDate;

  @Type(type = "yes_no")
  @Column(name = "SSI_SSPIND")
  private boolean ssiSspApplicationIndicator;

  @Type(type = "yes_no")
  @Column(name = "TRBA_NOT_B")
  private boolean tribalAncestryNotifctnIndicatorVar;

  @Column(name = "TCADPT_DT")
  private LocalDate tribalCustomaryAdoptionDate;

  @Type(type = "yes_no")
  @Column(name = "TCADPT_IND")
  private boolean tribalCustomaryAdoptionIndicator;

  @Column(name = "LST_UPD_ID", nullable = false, length = 3)
  private String childClientLastUpdateId;

  @Column(name = "LST_UPD_TS", nullable = false)
  private LocalDateTime childClientLastUpdateTime;

  public void setVictimClientId(String victimClientId) {
    this.victimClientId = victimClientId;
  }

  public void setAdoptable(Adoptable adoptable) {
    this.adoptable = adoptable;
  }

  public void setAdoptedAge(Short adoptedAge) {
    this.adoptedAge = adoptedAge;
  }

  public void setAfdcFcEligibilityIndicatorVar(boolean afdcFcEligibilityIndicatorVar) {
    this.afdcFcEligibilityIndicatorVar = afdcFcEligibilityIndicatorVar;
  }

  public void setAllEducationInfoOnFileIndicator(boolean allEducationInfoOnFileIndicator) {
    this.allEducationInfoOnFileIndicator = allEducationInfoOnFileIndicator;
  }

  public void setAllHealthInfoOnFileIndicator(boolean allHealthInfoOnFileIndicator) {
    this.allHealthInfoOnFileIndicator = allHealthInfoOnFileIndicator;
  }

  public void setAttemptToAcquireEducInfoDesc(String attemptToAcquireEducInfoDesc) {
    this.attemptToAcquireEducInfoDesc = attemptToAcquireEducInfoDesc;
  }

  public void setAttemptToAcquireHlthInfoDesc(String attemptToAcquireHlthInfoDesc) {
    this.attemptToAcquireHlthInfoDesc = attemptToAcquireHlthInfoDesc;
  }

  public void setAwolAbducted(AwolAbducted awolAbducted) {
    this.awolAbducted = awolAbducted;
  }

  public void setBirthHistoryIndicatorVar(boolean birthHistoryIndicatorVar) {
    this.birthHistoryIndicatorVar = birthHistoryIndicatorVar;
  }

  public void setChildIndianAncestryIndicator(boolean childIndianAncestryIndicator) {
    this.childIndianAncestryIndicator = childIndianAncestryIndicator;
  }

  /**
   *
   * @param collegeIndicator (may be null)
   */
  public void setCollegeIndicator(Boolean collegeIndicator) {
    this.collegeIndicator = collegeIndicator;
  }

  public void setCurrentCaseId(String currentCaseId) {
    this.currentCaseId = currentCaseId;
  }

  public void setDeathCircumstancesTypeCode(short deathCircumstancesTypeCode) {
    this.deathCircumstancesTypeCode = deathCircumstancesTypeCode;
  }

  public void setDisabilityDiagnosed(Disability disabilityDiagnosed) {
    this.disabilityDiagnosed = disabilityDiagnosed;
  }

  public void setDrmsHePassportDocOld(String drmsHePassportDocOld) {
    this.drmsHePassportDocOld = drmsHePassportDocOld;
  }

  public void setDrmsHealthEducPassportDoc(String drmsHealthEducPassportDoc) {
    this.drmsHealthEducPassportDoc = drmsHealthEducPassportDoc;
  }

  public void setDrmsVoluntaryPlcmntAgrmntDoc(String drmsVoluntaryPlcmntAgrmntDoc) {
    this.drmsVoluntaryPlcmntAgrmntDoc = drmsVoluntaryPlcmntAgrmntDoc;
  }

  public void setFc2EligApplicationIndicatorVar(boolean fc2EligApplicationIndicatorVar) {
    this.fc2EligApplicationIndicatorVar = fc2EligApplicationIndicatorVar;
  }

  public void setFoodStampsApplicationDate(LocalDate foodStampsApplicationDate) {
    this.foodStampsApplicationDate = foodStampsApplicationDate;
  }

  public void setFoodStampsApplicationIndicator(boolean foodStampsApplicationIndicator) {
    this.foodStampsApplicationIndicator = foodStampsApplicationIndicator;
  }

  public void setIcwaEligibility(IcwaEligibility icwaEligibility) {
    this.icwaEligibility = icwaEligibility;
  }

  public void setIntercountryAdoptDisruptedIndicator(boolean intercountryAdoptDisruptedIndicator) {
    this.intercountryAdoptDisruptedIndicator = intercountryAdoptDisruptedIndicator;
  }

  public void setIntercountryAdoptDissolvedIndicator(boolean intercountryAdoptDissolvedIndicator) {
    this.intercountryAdoptDissolvedIndicator = intercountryAdoptDissolvedIndicator;
  }

  public void setMedEligibilityApplicationIndicatorVar(
      boolean medEligibilityApplicationIndicatorVar) {
    this.medEligibilityApplicationIndicatorVar = medEligibilityApplicationIndicatorVar;
  }

  public void setMinorNmdParentIndicator(boolean minorNmdParentIndicator) {
    this.minorNmdParentIndicator = minorNmdParentIndicator;
  }

  public void setParentalRightsLimitedIndicator(boolean parentalRightsLimitedIndicator) {
    this.parentalRightsLimitedIndicator = parentalRightsLimitedIndicator;
  }

  public void setParentalRightsTermintnIndicatorVar(boolean parentalRightsTermintnIndicatorVar) {
    this.parentalRightsTermintnIndicatorVar = parentalRightsTermintnIndicatorVar;
  }

  public void setPaternityIndividualIndicatorVar(boolean paternityIndividualIndicatorVar) {
    this.paternityIndividualIndicatorVar = paternityIndividualIndicatorVar;
  }

  /**
   *
   * @param postsecVocIndicator (may be null)
   */
  public void setPostsecVocIndicator(Boolean postsecVocIndicator) {
    this.postsecVocIndicator = postsecVocIndicator;
  }

  public void setPreviouslyAdopted(PreviouslyAdopted previouslyAdopted) {
    this.previouslyAdopted = previouslyAdopted;
  }

  public void setSafelySurrendedBabiesIndicatorVar(boolean safelySurrendedBabiesIndicatorVar) {
    this.safelySurrendedBabiesIndicatorVar = safelySurrendedBabiesIndicatorVar;
  }

  public void setSaw1EligApplicationIndicatorVar(boolean saw1EligApplicationIndicatorVar) {
    this.saw1EligApplicationIndicatorVar = saw1EligApplicationIndicatorVar;
  }

  public void setSawsCaseSerialNumber(Integer sawsCaseSerialNumber) {
    this.sawsCaseSerialNumber = sawsCaseSerialNumber;
  }

  public void setSijsScheduledInterviewDate(LocalDate sijsScheduledInterviewDate) {
    this.sijsScheduledInterviewDate = sijsScheduledInterviewDate;
  }

  public void setSiiNextScreeningDueDate(LocalDate siiNextScreeningDueDate) {
    this.siiNextScreeningDueDate = siiNextScreeningDueDate;
  }

  public void setSsiSspApplicationIndicator(boolean ssiSspApplicationIndicator) {
    this.ssiSspApplicationIndicator = ssiSspApplicationIndicator;
  }

  public void setTribalAncestryNotifctnIndicatorVar(boolean tribalAncestryNotifctnIndicatorVar) {
    this.tribalAncestryNotifctnIndicatorVar = tribalAncestryNotifctnIndicatorVar;
  }

  public void setTribalCustomaryAdoptionDate(LocalDate tribalCustomaryAdoptionDate) {
    this.tribalCustomaryAdoptionDate = tribalCustomaryAdoptionDate;
  }

  public void setTribalCustomaryAdoptionIndicator(boolean tribalCustomaryAdoptionIndicator) {
    this.tribalCustomaryAdoptionIndicator = tribalCustomaryAdoptionIndicator;
  }

  /**
   * @return the victimClientId
   */
  public String getVictimClientId() {
    return victimClientId;
  }

  /**
   * @return the adoptableCode
   */
  public Adoptable getAdoptable() {
    return adoptable;
  }

  /**
   * @return the adoptedAge
   */
  public Short getAdoptedAge() {
    return adoptedAge;
  }

  /**
   * @return the afdcFcEligibilityIndicatorVar
   */
  public boolean getAfdcFcEligibilityIndicatorVar() {
    return afdcFcEligibilityIndicatorVar;
  }

  /**
   * @return the allEducationInfoOnFileIndicator
   */
  public boolean getAllEducationInfoOnFileIndicator() {
    return allEducationInfoOnFileIndicator;
  }

  /**
   * @return the allHealthInfoOnFileIndicator
   */
  public boolean getAllHealthInfoOnFileIndicator() {
    return allHealthInfoOnFileIndicator;
  }

  /**
   * @return the attemptToAcquireEducInfoDesc
   */
  public String getAttemptToAcquireEducInfoDesc() {
    return attemptToAcquireEducInfoDesc;
  }

  /**
   * @return the attemptToAcquireHlthInfoDesc
   */
  public String getAttemptToAcquireHlthInfoDesc() {
    return attemptToAcquireHlthInfoDesc;
  }

  /**
   * @return the awolAbductedCode
   */
  public AwolAbducted getAwolAbducted() {
    return awolAbducted;
  }

  /**
   * @return the birthHistoryIndicatorVar
   */
  public boolean getBirthHistoryIndicatorVar() {
    return birthHistoryIndicatorVar;
  }

  /**
   * @return the childIndianAncestryIndicator
   */
  public boolean getChildIndianAncestryIndicator() {
    return childIndianAncestryIndicator;
  }

  /**
   * @return the collegeIndicator (Boolean value or null)
   */
  @SuppressWarnings("squid:S2447")
  public Boolean getCollegeIndicator() {
    return collegeIndicator;
  }

  /**
   * @return the currentCaseId
   */
  public String getCurrentCaseId() {
    return currentCaseId;
  }

  /**
   * @return the deathCircumstancesTypeCode
   */
  public short getDeathCircumstancesTypeCode() {
    return deathCircumstancesTypeCode;
  }

  /**
   * @return the disabilityDiagnosedCode
   */
  public Disability getDisabilityDiagnosed() {
    return disabilityDiagnosed;
  }

  /**
   * @return the drmsHePassportDocOld
   */
  public String getDrmsHePassportDocOld() {
    return drmsHePassportDocOld;
  }

  /**
   * @return the drmsHealthEducPassportDoc
   */
  public String getDrmsHealthEducPassportDoc() {
    return drmsHealthEducPassportDoc;
  }

  /**
   * @return the drmsVoluntaryPlcmntAgrmntDoc
   */
  public String getDrmsVoluntaryPlcmntAgrmntDoc() {
    return drmsVoluntaryPlcmntAgrmntDoc;
  }

  /**
   * @return the fc2EligApplicationIndicatorVar
   */
  public boolean getFc2EligApplicationIndicatorVar() {
    return fc2EligApplicationIndicatorVar;
  }

  /**
   * @return the foodStampsApplicationDate
   */
  public LocalDate getFoodStampsApplicationDate() {
    return foodStampsApplicationDate;
  }

  /**
   * @return the foodStampsApplicationIndicator
   */
  public boolean getFoodStampsApplicationIndicator() {
    return foodStampsApplicationIndicator;
  }

  /**
   * @return the icwaEligibilityCode
   */
  public IcwaEligibility getIcwaEligibility() {
    return icwaEligibility;
  }

  /**
   * @return intercountryAdoptDisruptedIndicator
   */
  public boolean getIntercountryAdoptDisruptedIndicator() {
    return intercountryAdoptDisruptedIndicator;
  }

  /**
   * @return the intercountryAdoptDissolvedIndicator
   */
  public boolean getIntercountryAdoptDissolvedIndicator() {
    return intercountryAdoptDissolvedIndicator;
  }

  /**
   * @return the medEligibilityApplicationIndicatorVar
   */
  public boolean getMedEligibilityApplicationIndicatorVar() {
    return medEligibilityApplicationIndicatorVar;
  }

  /**
   * @return the minorNmdParentIndicator
   */
  public boolean getMinorNmdParentIndicator() {
    return minorNmdParentIndicator;
  }

  /**
   * @return the parentalRightsLimitedIndicator
   */
  public boolean getParentalRightsLimitedIndicator() {
    return parentalRightsLimitedIndicator;
  }

  /**
   * @return the parentalRightsTermintnIndicatorVar
   */
  public boolean getParentalRightsTermintnIndicatorVar() {
    return parentalRightsTermintnIndicatorVar;
  }

  /**
   * @return the paternityIndividualIndicatorVar
   */
  public boolean getPaternityIndividualIndicatorVar() {
    return paternityIndividualIndicatorVar;
  }

  /**
   * @return the postsecVocIndicator (Boolean value or null)
   */
  @SuppressWarnings("squid:S2447")
  public Boolean getPostsecVocIndicator() {
    return postsecVocIndicator;
  }

  /**
   * @return the previouslyAdoptedCode
   */
  public PreviouslyAdopted getPreviouslyAdopted() {
    return previouslyAdopted;
  }

  /**
   * @return the safelySurrendedBabiesIndicatorVar
   */
  public boolean getSafelySurrendedBabiesIndicatorVar() {
    return safelySurrendedBabiesIndicatorVar;
  }

  /**
   * @return the saw1EligApplicationIndicatorVar
   */
  public boolean getSaw1EligApplicationIndicatorVar() {
    return saw1EligApplicationIndicatorVar;
  }

  /**
   * @return the sawsCaseSerialNumber
   */
  public Integer getSawsCaseSerialNumber() {
    return sawsCaseSerialNumber;
  }

  /**
   * @return the sijsScheduledInterviewDate
   */
  public LocalDate getSijsScheduledInterviewDate() {
    return sijsScheduledInterviewDate;
  }

  /**
   * @return the siiNextScreeningDueDate
   */
  public LocalDate getSiiNextScreeningDueDate() {
    return siiNextScreeningDueDate;
  }

  /**
   * @return the ssiSspApplicationIndicator
   */
  public boolean getSsiSspApplicationIndicator() {
    return ssiSspApplicationIndicator;
  }

  /**
   * @return the tribalAncestryNotifctnIndicatorVar
   */
  public boolean getTribalAncestryNotifctnIndicatorVar() {
    return tribalAncestryNotifctnIndicatorVar;
  }

  /**
   * @return the tribalCustomaryAdoptionDate
   */
  public LocalDate getTribalCustomaryAdoptionDate() {
    return tribalCustomaryAdoptionDate;
  }

  /**
   * @return the tribalCustomaryAdoptionIndicator
   */
  public boolean getTribalCustomaryAdoptionIndicator() {
    return tribalCustomaryAdoptionIndicator;
  }

  /**
   * @return the ID (a sequential 3 digit base 62 number generated by the system) of the STAFF
   * PERSON or batch program which made the last update to an occurrence of this entity type.
   */
  public String getChildClientLastUpdateId() {
    return childClientLastUpdateId;
  }

  /**
   * @return the time and date of the most recent update to an occurrence of this entity type.
   */
  public LocalDateTime getChildClientLastUpdateTime() {
    return childClientLastUpdateTime;
  }

  @SuppressWarnings("javadoc")
  public void setChildClientLastUpdateId(String lastUpdatedId) {
    this.childClientLastUpdateId = lastUpdatedId;
  }

  @SuppressWarnings("javadoc")
  public void setChildClientLastUpdateTime(LocalDateTime lastUpdatedTime) {
    this.childClientLastUpdateTime = lastUpdatedTime;
  }
}
