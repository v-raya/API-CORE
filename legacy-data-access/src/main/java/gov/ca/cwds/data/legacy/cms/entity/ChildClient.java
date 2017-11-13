package gov.ca.cwds.data.legacy.cms.entity;

import gov.ca.cwds.data.legacy.cms.CmsPersistentObject;
import gov.ca.cwds.data.legacy.cms.entity.enums.Adoptable;
import gov.ca.cwds.data.legacy.cms.entity.enums.AwolAbducted;
import gov.ca.cwds.data.legacy.cms.entity.enums.Disability;
import gov.ca.cwds.data.legacy.cms.entity.enums.IcwaEligibility;
import gov.ca.cwds.data.legacy.cms.entity.syscodes.DeathCircumstancesType;
import java.time.LocalDate;
import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.NamedQuery;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;
import org.hibernate.annotations.Type;

/**
 * {@link CmsPersistentObject} Class representing a Child Client.
 *
 * @author CWDS API Team
 */
@NamedQuery(name = ChildClient.FIND_VICTIM_CLIENTS,
    query = "SELECT C" + " FROM ChildClient C, ReferralClient R, Allegation A"
        + " WHERE C.victimClientId = R.clientId " + " AND A.victimClientId = R.clientId"
        + " AND R.referralId = :referralId")
@Entity
@Table(name = "CHLD_CLT")
public class ChildClient extends CmsPersistentObject {

  public static final String FIND_VICTIM_CLIENTS = "ChildClient.findVictimClients";

  /**
   * Serialization version
   */
  private static final long serialVersionUID = 1L;

  @Id
  @Column(name = "FKCLIENT_T", length = CMS_ID_LEN)
  private String victimClientId;

  @Column(name = "ADOPTBL_CD")
  @Convert(converter = Adoptable.AdoptableConverter.class)
  private Adoptable adoptable;

  @Type(type = "short")
  @Column(name = "ADPT_AGE")
  private Short adoptedAge;

  @Type(type = "yes_no")
  @Column(name = "FC_ELIGT_B")
  private Boolean afdcFcEligibilityIndicatorVar;

  @Type(type = "yes_no")
  @Column(name = "EDONFL_IND")
  private Boolean allEducationInfoOnFileIndicator;

  @Type(type = "yes_no")
  @Column(name = "HLONFL_IND")
  private Boolean allHealthInfoOnFileIndicator;

  @Column(name = "ACQ_ED_DSC")
  private String attemptToAcquireEducInfoDesc;

  @Column(name = "ACQ_HTHDSC")
  private String attemptToAcquireHlthInfoDesc;

  @Column(name = "AWOL_AB_CD")
  @Convert(converter = AwolAbducted.AwolAbductedConverter.class)
  private AwolAbducted awolAbducted;

  @Type(type = "yes_no")
  @Column(name = "BHIST_IND")
  private Boolean birthHistoryIndicatorVar;

  @Type(type = "yes_no")
  @Column(name = "INDIAN_IND")
  private Boolean childIndianAncestryIndicator;

  @Type(type = "yes_no")
  @Column(name = "CLG_IND")
  private Boolean collegeIndicator;

  @Column(name = "CURNT_CSID")
  private String currentCaseId;

  @NotFound(action = NotFoundAction.IGNORE)
  @ManyToOne(fetch = FetchType.LAZY)
  @Fetch(FetchMode.SELECT)
  @JoinColumn(name = "DTH_CIRC", referencedColumnName = "SYS_ID", insertable = false, updatable = false)
  private DeathCircumstancesType deathCircumstancesType;

  @Column(name = "DISABLD_CD")
  @Convert(converter = Disability.DisabilityConverter.class)
  private Disability disabilityDiagnosed;

  @Column(name = "HEPDOC_OLD")
  private String drmsHePassportDocOld;

  @Column(name = "DRMSHEPDOC")
  private String drmsHealthEducPassportDoc;

  @Column(name = "VOL_PLCDOC")
  private String drmsVoluntaryPlcmntAgrmntDoc;

  @Type(type = "yes_no")
  @Column(name = "FC2_APLT_B")
  private Boolean fc2EligApplicationIndicatorVar;

  @Column(name = "FSTAMP_DT")
  private LocalDate foodStampsApplicationDate;

  @Type(type = "yes_no")
  @Column(name = "FSTAMP_IND")
  private Boolean foodStampsApplicationIndicator;

  @Column(name = "ICWA_ELGCD")
  @Convert(converter = IcwaEligibility.IcwaEligibilityConverter.class)
  private IcwaEligibility icwaEligibility;

  @Type(type = "yes_no")
  @Column(name = "ICADSR_IND")
  private Boolean intercountryAdoptDisruptedIndicator;

  @Type(type = "yes_no")
  @Column(name = "ICADSL_IND")
  private Boolean intercountryAdoptDissolvedIndicator;

  @Type(type = "yes_no")
  @Column(name = "MEDELIGT_B")
  private Boolean medEligibilityApplicationIndicatorVar;

  @Type(type = "yes_no")
  @Column(name = "MNRMOM_IND")
  private Boolean minorNmdParentIndicator;

  @Type(type = "yes_no")
  @Column(name = "PRTLIM_IND")
  private Boolean parentalRightsLimitedIndicator;

  @Type(type = "yes_no")
  @Column(name = "PRG_TRMT_B")
  private Boolean parentalRightsTermintnIndicatorVar;

  @Type(type = "yes_no")
  @Column(name = "PTRN_INT_B")
  private Boolean paternityIndividualIndicatorVar;

  @Type(type = "yes_no")
  @Column(name = "PSVOC_IND")
  private Boolean postsecVocIndicator;

  @Column(name = "PREADPT_CD")
  private String previouslyAdoptedCode;

  @Type(type = "yes_no")
  @Column(name = "SFSURB_IND")
  private Boolean safelySurrendedBabiesIndicatorVar;

  @Type(type = "yes_no")
  @Column(name = "SAW1APLT_B")
  private Boolean saw1EligApplicationIndicatorVar;

  @Type(type = "integer")
  @Column(name = "SAWS_CS_NO")
  private Integer sawsCaseSerialNumber;

  @Column(name = "SIJ_INT_DT")
  private LocalDate sijsScheduledInterviewDate;

  @Column(name = "SSI_SCR_DT")
  private LocalDate siiNextScreeningDueDate;

  @Type(type = "yes_no")
  @Column(name = "SSI_SSPIND")
  private Boolean ssiSspApplicationIndicator;

  @Type(type = "yes_no")
  @Column(name = "TRBA_NOT_B")
  private Boolean tribalAncestryNotifctnIndicatorVar;

  @Column(name = "TCADPT_DT")
  private LocalDate tribalCustomaryAdoptionDate;

  @Type(type = "yes_no")
  @Column(name = "TCADPT_IND")
  private Boolean tribalCustomaryAdoptionIndicator;

  /**
   * referential integrity check. <p> Doesn't actually load the data. Just checks the existence of
   * the parent client record. </p>
   */
  @OneToOne(optional = false)
  @JoinColumn(name = "FKCLIENT_T", nullable = false, updatable = false, insertable = false)
  private Client client;

  public void setVictimClientId(String victimClientId) {
    this.victimClientId = victimClientId;
  }

  public void setAdoptable(Adoptable adoptable) {
    this.adoptable = adoptable;
  }

  public void setAdoptedAge(Short adoptedAge) {
    this.adoptedAge = adoptedAge;
  }

  public void setAfdcFcEligibilityIndicatorVar(Boolean afdcFcEligibilityIndicatorVar) {
    this.afdcFcEligibilityIndicatorVar = afdcFcEligibilityIndicatorVar;
  }

  public void setAllEducationInfoOnFileIndicator(Boolean allEducationInfoOnFileIndicator) {
    this.allEducationInfoOnFileIndicator = allEducationInfoOnFileIndicator;
  }

  public void setAllHealthInfoOnFileIndicator(Boolean allHealthInfoOnFileIndicator) {
    this.allHealthInfoOnFileIndicator = allHealthInfoOnFileIndicator;
  }

  public void setAttemptToAcquireEducInfoDesc(String attemptToAcquireEducInfoDesc) {
    this.attemptToAcquireEducInfoDesc = attemptToAcquireEducInfoDesc;
  }

  public void setAttemptToAcquireHlthInfoDesc(String attemptToAcquireHlthInfoDesc) {
    this.attemptToAcquireHlthInfoDesc = attemptToAcquireHlthInfoDesc;
  }

  public void setAwolAbductedCode(AwolAbducted awolAbducted) {
    this.awolAbducted = awolAbducted;
  }

  public void setBirthHistoryIndicatorVar(Boolean birthHistoryIndicatorVar) {
    this.birthHistoryIndicatorVar = birthHistoryIndicatorVar;
  }

  public void setChildIndianAncestryIndicator(Boolean childIndianAncestryIndicator) {
    this.childIndianAncestryIndicator = childIndianAncestryIndicator;
  }

  public void setCollegeIndicator(Boolean collegeIndicator) {
    this.collegeIndicator = collegeIndicator;
  }

  public void setCurrentCaseId(String currentCaseId) {
    this.currentCaseId = currentCaseId;
  }

  public void setDeathCircumstancesType(DeathCircumstancesType deathCircumstancesType) {
    this.deathCircumstancesType = deathCircumstancesType;
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

  public void setFc2EligApplicationIndicatorVar(Boolean fc2EligApplicationIndicatorVar) {
    this.fc2EligApplicationIndicatorVar = fc2EligApplicationIndicatorVar;
  }

  public void setFoodStampsApplicationDate(LocalDate foodStampsApplicationDate) {
    this.foodStampsApplicationDate = foodStampsApplicationDate;
  }

  public void setFoodStampsApplicationIndicator(Boolean foodStampsApplicationIndicator) {
    this.foodStampsApplicationIndicator = foodStampsApplicationIndicator;
  }

  public void setIcwaEligibilityCode(IcwaEligibility IcwaEligibility) {
    this.icwaEligibility = IcwaEligibility;
  }

  public void setIntercountryAdoptDisruptedIndicator(Boolean intercountryAdoptDisruptedIndicator) {
    this.intercountryAdoptDisruptedIndicator = intercountryAdoptDisruptedIndicator;
  }

  public void setIntercountryAdoptDissolvedIndicator(Boolean intercountryAdoptDissolvedIndicator) {
    this.intercountryAdoptDissolvedIndicator = intercountryAdoptDissolvedIndicator;
  }

  public void setMedEligibilityApplicationIndicatorVar(
      Boolean medEligibilityApplicationIndicatorVar) {
    this.medEligibilityApplicationIndicatorVar = medEligibilityApplicationIndicatorVar;
  }

  public void setMinorNmdParentIndicator(Boolean minorNmdParentIndicator) {
    this.minorNmdParentIndicator = minorNmdParentIndicator;
  }

  public void setParentalRightsLimitedIndicator(Boolean parentalRightsLimitedIndicator) {
    this.parentalRightsLimitedIndicator = parentalRightsLimitedIndicator;
  }

  public void setParentalRightsTermintnIndicatorVar(Boolean parentalRightsTermintnIndicatorVar) {
    this.parentalRightsTermintnIndicatorVar = parentalRightsTermintnIndicatorVar;
  }

  public void setPaternityIndividualIndicatorVar(Boolean paternityIndividualIndicatorVar) {
    this.paternityIndividualIndicatorVar = paternityIndividualIndicatorVar;
  }

  public void setPostsecVocIndicator(Boolean postsecVocIndicator) {
    this.postsecVocIndicator = postsecVocIndicator;
  }

  public void setPreviouslyAdoptedCode(String previouslyAdoptedCode) {
    this.previouslyAdoptedCode = previouslyAdoptedCode;
  }

  public void setSafelySurrendedBabiesIndicatorVar(Boolean safelySurrendedBabiesIndicatorVar) {
    this.safelySurrendedBabiesIndicatorVar = safelySurrendedBabiesIndicatorVar;
  }

  public void setSaw1EligApplicationIndicatorVar(Boolean saw1EligApplicationIndicatorVar) {
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

  public void setSsiSspApplicationIndicator(Boolean ssiSspApplicationIndicator) {
    this.ssiSspApplicationIndicator = ssiSspApplicationIndicator;
  }

  public void setTribalAncestryNotifctnIndicatorVar(Boolean tribalAncestryNotifctnIndicatorVar) {
    this.tribalAncestryNotifctnIndicatorVar = tribalAncestryNotifctnIndicatorVar;
  }

  public void setTribalCustomaryAdoptionDate(LocalDate tribalCustomaryAdoptionDate) {
    this.tribalCustomaryAdoptionDate = tribalCustomaryAdoptionDate;
  }

  public void setTribalCustomaryAdoptionIndicator(Boolean tribalCustomaryAdoptionIndicator) {
    this.tribalCustomaryAdoptionIndicator = tribalCustomaryAdoptionIndicator;
  }

  public void setClient(Client client) {
    this.client = client;
  }

  /*
     * (non-Javadoc)
     */
  @Override
  public String getPrimaryKey() {
    return getVictimClientId();
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
  public Boolean getAfdcFcEligibilityIndicatorVar() {
    return afdcFcEligibilityIndicatorVar;
  }

  /**
   * @return the allEducationInfoOnFileIndicator
   */
  public Boolean getAllEducationInfoOnFileIndicator() {
    return allEducationInfoOnFileIndicator;
  }

  /**
   * @return the allHealthInfoOnFileIndicator
   */
  public Boolean getAllHealthInfoOnFileIndicator() {
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
  public Boolean getBirthHistoryIndicatorVar() {
    return birthHistoryIndicatorVar;
  }

  /**
   * @return the childIndianAncestryIndicator
   */
  public Boolean getChildIndianAncestryIndicator() {
    return childIndianAncestryIndicator;
  }

  /**
   * @return the collegeIndicator
   */
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
   * @return the deathCircumstancesType
   */
  public DeathCircumstancesType getDeathCircumstancesType() {
    return deathCircumstancesType;
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
  public Boolean getFc2EligApplicationIndicatorVar() {
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
  public Boolean getFoodStampsApplicationIndicator() {
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
  public Boolean getIntercountryAdoptDisruptedIndicator() {
    return intercountryAdoptDisruptedIndicator;
  }

  /**
   * @return the intercountryAdoptDissolvedIndicator
   */
  public Boolean getIntercountryAdoptDissolvedIndicator() {
    return intercountryAdoptDissolvedIndicator;
  }

  /**
   * @return the medEligibilityApplicationIndicatorVar
   */
  public Boolean getMedEligibilityApplicationIndicatorVar() {
    return medEligibilityApplicationIndicatorVar;
  }

  /**
   * @return the minorNmdParentIndicator
   */
  public Boolean getMinorNmdParentIndicator() {
    return minorNmdParentIndicator;
  }

  /**
   * @return the parentalRightsLimitedIndicator
   */
  public Boolean getParentalRightsLimitedIndicator() {
    return parentalRightsLimitedIndicator;
  }

  /**
   * @return the parentalRightsTermintnIndicatorVar
   */
  public Boolean getParentalRightsTermintnIndicatorVar() {
    return parentalRightsTermintnIndicatorVar;
  }

  /**
   * @return the paternityIndividualIndicatorVar
   */
  public Boolean getPaternityIndividualIndicatorVar() {
    return paternityIndividualIndicatorVar;
  }

  /**
   * @return the postsecVocIndicator
   */
  public Boolean getPostsecVocIndicator() {
    return postsecVocIndicator;
  }

  /**
   * @return the previouslyAdoptedCode
   */
  public String getPreviouslyAdoptedCode() {
    return previouslyAdoptedCode;
  }

  /**
   * @return the safelySurrendedBabiesIndicatorVar
   */
  public Boolean getSafelySurrendedBabiesIndicatorVar() {
    return safelySurrendedBabiesIndicatorVar;
  }

  /**
   * @return the saw1EligApplicationIndicatorVar
   */
  public Boolean getSaw1EligApplicationIndicatorVar() {
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
  public Boolean getSsiSspApplicationIndicator() {
    return ssiSspApplicationIndicator;
  }

  /**
   * @return the tribalAncestryNotifctnIndicatorVar
   */
  public Boolean getTribalAncestryNotifctnIndicatorVar() {
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
  public Boolean getTribalCustomaryAdoptionIndicator() {
    return tribalCustomaryAdoptionIndicator;
  }
}
