package gov.ca.cwds.data.legacy.cms.entity;

import gov.ca.cwds.data.legacy.cms.CmsPersistentObjectVersioned;
import gov.ca.cwds.data.legacy.cms.entity.converter.NullableBooleanConverter;
import gov.ca.cwds.data.legacy.cms.entity.enums.AdoptionStatus;
import gov.ca.cwds.data.legacy.cms.entity.enums.DateOfBirthStatus;
import gov.ca.cwds.data.legacy.cms.entity.enums.Gender;
import gov.ca.cwds.data.legacy.cms.entity.enums.HispanicOrigin;
import gov.ca.cwds.data.legacy.cms.entity.enums.IncapacitatedParentStatus;
import gov.ca.cwds.data.legacy.cms.entity.enums.LiterateStatus;
import gov.ca.cwds.data.legacy.cms.entity.enums.MilitaryStatus;
import gov.ca.cwds.data.legacy.cms.entity.enums.ParentUnemployedStatus;
import gov.ca.cwds.data.legacy.cms.entity.enums.Sensitivity;
import gov.ca.cwds.data.legacy.cms.entity.enums.Soc158placementsStatus;
import gov.ca.cwds.data.legacy.cms.entity.enums.UnableToDetermineReason;
import gov.ca.cwds.data.legacy.cms.entity.syscodes.NameType;
import gov.ca.cwds.data.persistence.PersistentObject;
import org.hibernate.annotations.ColumnTransformer;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.NamedQuery;
import org.hibernate.annotations.Type;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/** @author CWDS CALS API Team */
@NamedQuery(
  name = "Client.find",
  query = Client.CHILDREN_BY_LICENSE_NUMBER_BASE_QUERY + " AND c.identifier = :childId"
)
@NamedQuery(
  name = "Client.findAll",
  query = Client.CHILDREN_BY_LICENSE_NUMBER_BASE_QUERY + " ORDER BY c.identifier "
)
@NamedQuery(
  name = "Client.findByFacilityId",
  query = Client.CHILDREN_BY_FACILITY_ID_BASE_QUERY + " ORDER BY c.identifier "
)
@NamedQuery(
  name = "Client.findByFacilityIdAndChildId",
  query = Client.CHILDREN_BY_FACILITY_ID_BASE_QUERY + " AND c.identifier = :childId"
)
@NamedQuery(name = Client.NQ_ALL, query = "FROM gov.ca.cwds.data.legacy.cms.entity.Client")
@SuppressWarnings({"squid:S3437", "squid:S2160", "common-java:DuplicatedBlocks"})
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Table(name = "CLIENT_T")
public class Client extends CmsPersistentObjectVersioned implements IClient, PersistentObject {

  private static final long serialVersionUID = 783532074047017463L;

  public static final String NQ_ALL = "Client.all";

  public static final String CHILDREN_BY_LICENSE_NUMBER_BASE_QUERY =
      "SELECT c FROM gov.ca.cwds.data.legacy.cms.entity.Client c"
          + " JOIN c.placementEpisodes pe"
          + " JOIN pe.outOfHomePlacements ohp"
          + " JOIN ohp.placementHome ph"
          + " WHERE ph.licenseNo = :licenseNumber"
          + " AND ohp.endDt is null"
          + " AND pe.plepsEndt is null";

  public static final String CHILDREN_BY_FACILITY_ID_BASE_QUERY =
      "SELECT c FROM gov.ca.cwds.data.legacy.cms.entity.Client c"
          + " JOIN c.placementEpisodes pe"
          + " JOIN pe.outOfHomePlacements ohp"
          + " JOIN ohp.placementHome ph"
          + " WHERE ph.id = :facilityId"
          + " AND ohp.endDt is null"
          + " AND pe.plepsEndt is null";

  @Id
  @Column(name = "IDENTIFIER", nullable = false, length = 10)
  @Access(AccessType.PROPERTY) // to get id without fetching entire client
  private String identifier;

  @OneToMany(fetch = FetchType.LAZY)
  @JoinColumn(
    name = "FKCLIENT_T",
    referencedColumnName = "IDENTIFIER",
    insertable = false,
    updatable = false
  )
  private Set<PlacementEpisode> placementEpisodes = new HashSet<>();

  @OneToMany(
    mappedBy = "client",
    fetch = FetchType.LAZY,
    cascade = CascadeType.ALL,
    orphanRemoval = true
  )
  private Set<ClientOtherEthnicity> otherEthnicities = new HashSet<>();

  @Column(name = "ADJDEL_IND", length = 1)
  @Convert(converter = NullableBooleanConverter.class)
  private Boolean adjudicatedDelinquentIndicator;

  @Column(name = "ADPTN_STCD", nullable = false, length = 1)
  @Convert(converter = AdoptionStatus.AdoptionStatusConverter.class)
  private AdoptionStatus adoptionStatus;

  @Column(name = "ALN_REG_NO", nullable = false, length = 12)
  @ColumnTransformer(read = "trim(ALN_REG_NO)")
  private String alienRegistrationNumber;

  @Column(name = "B_CNTRY_C", nullable = false)
  private short birthCountryCode;

  @Column(name = "B_STATE_C", nullable = false)
  private short birthStateCode;

  @Column(name = "BIRTH_CITY", nullable = false, length = 35)
  @ColumnTransformer(read = "trim(BIRTH_CITY)")
  private String birthCity;

  @Column(name = "BIRTH_DT")
  private LocalDate birthDate;

  @Type(type = "yes_no")
  @Column(name = "BP_VER_IND", nullable = false, length = 1)
  private boolean birthplaceVerifiedIndicator;

  @Column(name = "BR_FAC_NM", nullable = false, length = 35)
  @ColumnTransformer(read = "trim(BR_FAC_NM)")
  private String birthFacilityName;

  @Type(type = "yes_no")
  @Column(name = "CHLD_CLT_B", nullable = false, length = 1)
  private boolean childClientIndicator;

  @Column(name = "COM_FST_NM", nullable = false, length = 20)
  @ColumnTransformer(read = "trim(COM_FST_NM)")
  private String commonFirstName;

  @Column(name = "COM_LST_NM", nullable = false, length = 25)
  @ColumnTransformer(read = "trim(COM_LST_NM)")
  private String commonLastName;

  @Column(name = "COM_MID_NM", nullable = false, length = 20)
  @ColumnTransformer(read = "trim(COM_MID_NM)")
  private String commonMiddleName;

  @Column(name = "COMMNT_DSC", nullable = false, length = 120)
  @ColumnTransformer(read = "trim(COMMNT_DSC)")
  private String commentDescription;

  @Column(name = "CONF_ACTDT")
  private LocalDate confidentialityActionDate;

  @Type(type = "yes_no")
  @Column(name = "CONF_EFIND", nullable = false, length = 1)
  private boolean confidentialityInEffectIndicator;

  @Column(name = "COTH_DESC", nullable = false, length = 25)
  @ColumnTransformer(read = "trim(COTH_DESC)")
  private String currentlyOtherDescription;

  @Column(name = "CREATN_DT", nullable = false)
  private LocalDate creationDate;

  @Type(type = "yes_no")
  @Column(name = "CURRCA_IND", nullable = false, length = 1)
  private boolean currentCaChildrenServiceIndicator;

  @Type(type = "yes_no")
  @Column(name = "CURREG_IND", nullable = false, length = 1)
  private boolean currentlyRegionalCenterIndicator;

  @Column(name = "D_STATE_C", nullable = false)
  private short driverLicenseStateCode;

  @Column(name = "DEATH_DT")
  private LocalDate deathDate;

  @Column(name = "DEATH_PLC", length = 35)
  @ColumnTransformer(read = "trim(DEATH_PLC)")
  private String deathPlace;

  @Column(name = "DRV_LIC_NO", nullable = false, length = 20)
  @ColumnTransformer(read = "trim(DRV_LIC_NO)")
  private String driverLicenseNumber;

  @Type(type = "yes_no")
  @Column(name = "DTH_DT_IND", nullable = false, length = 1)
  private boolean deathDateVerifiedIndicator;

  @Column(name = "DTH_RN_TXT", length = 10)
  @ColumnTransformer(read = "trim(DTH_RN_TXT)")
  private String deathReasonText;

  @Column(name = "EMAIL_ADDR", length = 50)
  @ColumnTransformer(read = "trim(EMAIL_ADDR)")
  private String emailAddress;

  @Column(name = "EST_DOB_CD", nullable = false, length = 1)
  @Convert(converter = DateOfBirthStatus.DateOfBirthStatusConverter.class)
  private DateOfBirthStatus dateOfBirthStatus;

  @Column(name = "ETH_UD_CD", length = 1)
  @Convert(converter = UnableToDetermineReason.UnableToDetermineReasonConverter.class)
  private UnableToDetermineReason ethnicityUnableToDetermineReason;

  @Column(name = "FTERM_DT")
  private LocalDate fatherParentalRightTermDate;

  @Column(name = "CL_INDX_NO", length = 12)
  @ColumnTransformer(read = "trim(CL_INDX_NO)")
  private String clientIndexNumber;

  @Column(name = "GENDER_CD", nullable = false, length = 1)
  @Convert(converter = Gender.GenderConverter.class)
  private Gender gender;

  @Type(type = "yes_no")
  @Column(name = "HCARE_IND", nullable = false, length = 1)
  private boolean individualHealthCarePlanIndicator;

  @Column(name = "HEALTH_TXT", length = 10)
  @ColumnTransformer(read = "trim(HEALTH_TXT)")
  private String healthSummaryText;

  @Column(name = "HISP_CD", nullable = false, length = 1)
  @Convert(converter = HispanicOrigin.HispanicOriginConverter.class)
  private HispanicOrigin hispanicOrigin;

  @Column(name = "HISP_UD_CD", length = 1)
  @Convert(converter = UnableToDetermineReason.UnableToDetermineReasonConverter.class)
  private UnableToDetermineReason hispanicUnableToDetermineReason;

  @Column(name = "I_CNTRY_C", nullable = false)
  private short immigrationCountryCode;

  @Column(name = "IMGT_STC", nullable = false)
  private short immigrationStatusCode;

  @Column(name = "INCAPC_CD", nullable = false, length = 2)
  @ColumnTransformer(read = "trim(INCAPC_CD)")
  @Convert(converter = IncapacitatedParentStatus.IncapacitatedParentStatusConverter.class)
  private IncapacitatedParentStatus incapacitatedParentStatus;

  @Type(type = "yes_no")
  @Column(name = "LIMIT_IND", nullable = false, length = 1)
  private boolean limitationOnScpHealthIndicator;

  @Column(name = "LITRATE_CD", nullable = false, length = 1)
  @Convert(converter = LiterateStatus.LiterateStatusConverter.class)
  private LiterateStatus literateStatus;

  @Type(type = "yes_no")
  @Column(name = "MAR_HIST_B", nullable = false, length = 1)
  private boolean maritalCohabitationHistoryIndicator;

  @Column(name = "MILT_STACD", nullable = false, length = 1)
  @Convert(converter = MilitaryStatus.MilitaryStatusConverter.class)
  private MilitaryStatus militaryStatus;

  @Column(name = "MRTL_STC", nullable = false)
  private short maritalStatusCode;

  @Column(name = "MTERM_DT")
  private LocalDate motherParentalRightTermDate;

  @NotNull
  @ManyToOne(fetch = FetchType.LAZY)
  @Fetch(FetchMode.SELECT)
  @JoinColumn(name = "NAME_TPC", referencedColumnName = "SYS_ID")
  private NameType nameType;

  @Column(name = "NMPRFX_DSC", nullable = false, length = 6)
  @ColumnTransformer(read = "trim(NMPRFX_DSC)")
  private String namePrefixDescription;

  @Type(type = "yes_no")
  @Column(name = "OUTWRT_IND", nullable = false, length = 1)
  private boolean outstandingWarrantIndicator;

  @Column(name = "P_ETHNCTYC", nullable = false)
  private short primaryEthnicityCode;

  @Column(name = "P_LANG_TPC", nullable = false)
  private short primaryLanguageCode;

  @Column(name = "POTH_DESC", nullable = false, length = 25)
  @ColumnTransformer(read = "trim(POTH_DESC)")
  private String previousOtherDescription;

  @Type(type = "yes_no")
  @Column(name = "PREREG_IND", nullable = false, length = 1)
  private boolean previousRegionalCenterIndicator;

  @Type(type = "yes_no")
  @Column(name = "PREVCA_IND", nullable = false, length = 1)
  private boolean previousCaChildrenServiceIndicator;

  @Column(name = "RLGN_TPC", nullable = false)
  private short religionCode;

  @Column(name = "S_LANG_TC", nullable = false)
  private short secondaryLanguageCode;

  @Column(name = "SENSTV_IND", nullable = false, length = 1)
  @Convert(converter = Sensitivity.SensitivityConverter.class)
  private Sensitivity sensitivity;

  @Type(type = "yes_no")
  @Column(name = "SNTV_HLIND", nullable = false, length = 1)
  private boolean sensitiveHealthInfoOnFileIndicator;

  @Type(type = "yes_no")
  @Column(name = "SOC158_IND", nullable = false, length = 1)
  private boolean soc158SealedClientIndicator;

  @Column(name = "SOCPLC_CD", nullable = false, length = 1)
  @Convert(converter = Soc158placementsStatus.Soc158placementsStatusConverter.class)
  private Soc158placementsStatus soc158placementsStatus;

  @Column(name = "SS_NO", nullable = false, length = 9)
  @ColumnTransformer(read = "trim(SS_NO)")
  private String socialSecurityNumber;

  @Column(name = "SSN_CHG_CD", nullable = false, length = 1)
  private String socialSecurityNumberChangedCode;

  @Column(name = "SUFX_TLDSC", nullable = false, length = 4)
  @ColumnTransformer(read = "trim(SUFX_TLDSC)")
  private String suffixTitleDescription;

  @Type(type = "yes_no")
  @Column(name = "TR_MBVRT_B", nullable = false, length = 1)
  private boolean tribalMembershipVerifcationIndicator;

  @Type(type = "yes_no")
  @Column(name = "TRBA_CLT_B", nullable = false, length = 1)
  private boolean tribalAncestryClientIndicator;

  @Column(name = "UNEMPLY_CD", nullable = false, length = 2)
  @ColumnTransformer(read = "trim(UNEMPLY_CD)")
  @Convert(converter = ParentUnemployedStatus.ParentUnemployedStatusConverter.class)
  private ParentUnemployedStatus parentUnemployedStatus;

  @Type(type = "yes_no")
  @Column(name = "ZIPPY_IND", nullable = false, length = 1)
  private boolean zippyCreatedIndicator;

  @Column(name = "CLNT_SOC", nullable = false)
  private short sexualOrientationCode;

  @Column(name = "SO_UD_CD", length = 1)
  private String sexualOrientationUnableToDetermineCode;

  @Column(name = "SO_NL_DSC", length = 254)
  @ColumnTransformer(read = "trim(SO_NL_DSC)")
  private String sexualOrientationNotListedDescription;

  @Column(name = "CLNT_GIC", nullable = false)
  private short genderIdentityCode;

  @Column(name = "GI_NL_DSC", length = 254)
  @ColumnTransformer(read = "trim(GI_NL_DSC)")
  private String genderIdentityNotListedDescription;

  @Column(name = "CLNT_GEC", nullable = false)
  private short genderEspressionCode;

  public void addOtherEthnicity(ClientOtherEthnicity otherEthnicity) {
    otherEthnicities.add(otherEthnicity);
    otherEthnicity.setClient(this);
  }

  public void removeOtherEthnicity(ClientOtherEthnicity otherEthnicity) {
    otherEthnicities.remove(otherEthnicity);
    otherEthnicity.setClient(null);
  }

  public Set<ClientOtherEthnicity> getOtherEthnicities() {
    return otherEthnicities;
  }

  @Override
  public final boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof Client)) {
      return false;
    }
    Client client = (Client) o;
    return isCodesEqual(client)
        && isNamesAndAddressesEqual(client)
        && (getChildClientIndicator() == client.getChildClientIndicator());
  }

  private boolean isCodesEqual(Client client) {
    return (getBirthCountryCode() == client.getBirthCountryCode())
        && (getBirthStateCode() == client.getBirthStateCode());
  }

  private boolean isNamesEqual(Client client) {
    return Objects.equals(getCommonFirstName(), client.getCommonFirstName())
        && Objects.equals(getCommonLastName(), client.getCommonLastName())
        && Objects.equals(getCommonMiddleName(), client.getCommonMiddleName());
  }

  private boolean isBirthAndAddressesEqual(Client client) {
    return Objects.equals(getBirthCity(), client.getBirthCity())
        && Objects.equals(getBirthDate(), client.getBirthDate())
        && Objects.equals(getEmailAddress(), client.getEmailAddress());
  }

  private boolean isNamesAndAddressesEqual(Client client) {
    return Objects.equals(getBirthFacilityName(), client.getBirthFacilityName())
        && isBirthAndAddressesEqual(client)
        && isNamesEqual(client);
  }

  @Override
  public final int hashCode() {
    return Objects.hash(
        getBirthCountryCode(),
        getBirthStateCode(),
        getBirthCity(),
        getBirthDate(),
        getBirthFacilityName(),
        getCommonFirstName(),
        getCommonLastName(),
        getCommonMiddleName(),
        getEmailAddress(),
        getChildClientIndicator());
  }

  public String getIdentifier() {
    return identifier;
  }

  public void setIdentifier(String identifier) {
    this.identifier = identifier;
  }

  public AdoptionStatus getAdoptionStatus() {
    return adoptionStatus;
  }

  public void setAdoptionStatus(AdoptionStatus adoptionStatus) {
    this.adoptionStatus = adoptionStatus;
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

  public short getBirthStateCode() {
    return birthStateCode;
  }

  public void setBirthStateCode(short birthStateCode) {
    this.birthStateCode = birthStateCode;
  }

  public short getBirthCountryCode() {
    return birthCountryCode;
  }

  public void setBirthCountryCode(short birthCountryCode) {
    this.birthCountryCode = birthCountryCode;
  }

  public boolean getChildClientIndicator() {
    return childClientIndicator;
  }

  public void setChildClientIndicator(boolean childClientIndicator) {
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

  public boolean getConfidentialityInEffectIndicator() {
    return confidentialityInEffectIndicator;
  }

  public void setConfidentialityInEffectIndicator(boolean confidentialityInEffectIndicator) {
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

  public short getDriverLicenseStateCode() {
    return driverLicenseStateCode;
  }

  public void setDriverLicenseStateCode(short driverLicenseStateCode) {
    this.driverLicenseStateCode = driverLicenseStateCode;
  }

  public Gender getGender() {
    return gender;
  }

  public void setGender(Gender gender) {
    this.gender = gender;
  }

  public short getImmigrationCountryCode() {
    return immigrationCountryCode;
  }

  public void setImmigrationCountryCode(short immigrationCountryCode) {
    this.immigrationCountryCode = immigrationCountryCode;
  }

  public short getImmigrationStatusCode() {
    return immigrationStatusCode;
  }

  public void setImmigrationStatusCode(short immigrationStatusCode) {
    this.immigrationStatusCode = immigrationStatusCode;
  }

  public IncapacitatedParentStatus getIncapacitatedParentStatus() {
    return incapacitatedParentStatus;
  }

  public void setIncapacitatedParentStatus(IncapacitatedParentStatus incapacitatedParentStatus) {
    this.incapacitatedParentStatus = incapacitatedParentStatus;
  }

  public LiterateStatus getLiterateStatus() {
    return literateStatus;
  }

  public void setLiterateStatus(LiterateStatus literateStatus) {
    this.literateStatus = literateStatus;
  }

  public boolean getMaritalCohabitationHistoryIndicator() {
    return maritalCohabitationHistoryIndicator;
  }

  public void setMaritalCohabitationHistoryIndicator(boolean maritalCohabitationHistoryIndicator) {
    this.maritalCohabitationHistoryIndicator = maritalCohabitationHistoryIndicator;
  }

  public short getMaritalStatusCode() {
    return maritalStatusCode;
  }

  public void setMaritalStatusCode(short maritalStatusCode) {
    this.maritalStatusCode = maritalStatusCode;
  }

  public MilitaryStatus getMilitaryStatus() {
    return militaryStatus;
  }

  public void setMilitaryStatus(MilitaryStatus militaryStatus) {
    this.militaryStatus = militaryStatus;
  }

  public String getNamePrefixDescription() {
    return namePrefixDescription;
  }

  public void setNamePrefixDescription(String namePrefixDescription) {
    this.namePrefixDescription = namePrefixDescription;
  }

  public NameType getNameType() {
    return nameType;
  }

  public void setNameType(NameType nameType) {
    this.nameType = nameType;
  }

  public boolean getOutstandingWarrantIndicator() {
    return outstandingWarrantIndicator;
  }

  public void setOutstandingWarrantIndicator(boolean outstandingWarrantIndicator) {
    this.outstandingWarrantIndicator = outstandingWarrantIndicator;
  }

  public short getPrimaryEthnicityCode() {
    return primaryEthnicityCode;
  }

  public void setPrimaryEthnicityCode(short primaryEthnicityCode) {
    this.primaryEthnicityCode = primaryEthnicityCode;
  }

  public short getPrimaryLanguageCode() {
    return primaryLanguageCode;
  }

  public void setPrimaryLanguageCode(short primaryLanguageCode) {
    this.primaryLanguageCode = primaryLanguageCode;
  }

  public short getReligionCode() {
    return religionCode;
  }

  public void setReligionCode(short religionCode) {
    this.religionCode = religionCode;
  }

  public short getSecondaryLanguageCode() {
    return secondaryLanguageCode;
  }

  public void setSecondaryLanguageCode(short secondaryLanguageCode) {
    this.secondaryLanguageCode = secondaryLanguageCode;
  }

  public Sensitivity getSensitivity() {
    return sensitivity;
  }

  public void setSensitivity(Sensitivity sensitivity) {
    this.sensitivity = sensitivity;
  }

  public boolean getSensitiveHealthInfoOnFileIndicator() {
    return sensitiveHealthInfoOnFileIndicator;
  }

  public void setSensitiveHealthInfoOnFileIndicator(boolean sensitiveHealthInfoOnFileIndicator) {
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

  public ParentUnemployedStatus getParentUnemployedStatus() {
    return parentUnemployedStatus;
  }

  public void setParentUnemployedStatus(ParentUnemployedStatus parentUnemployedStatus) {
    this.parentUnemployedStatus = parentUnemployedStatus;
  }

  public String getCommentDescription() {
    return commentDescription;
  }

  public void setCommentDescription(String commentDescription) {
    this.commentDescription = commentDescription;
  }

  public DateOfBirthStatus getDateOfBirthStatus() {
    return dateOfBirthStatus;
  }

  public void setDateOfBirthStatus(DateOfBirthStatus dateOfBirthStatus) {
    this.dateOfBirthStatus = dateOfBirthStatus;
  }

  public boolean getBirthplaceVerifiedIndicator() {
    return birthplaceVerifiedIndicator;
  }

  public void setBirthplaceVerifiedIndicator(boolean birthplaceVerifiedIndicator) {
    this.birthplaceVerifiedIndicator = birthplaceVerifiedIndicator;
  }

  public HispanicOrigin getHispanicOrigin() {
    return hispanicOrigin;
  }

  public void setHispanicOrigin(HispanicOrigin hispanicOrigin) {
    this.hispanicOrigin = hispanicOrigin;
  }

  public boolean getCurrentCaChildrenServiceIndicator() {
    return currentCaChildrenServiceIndicator;
  }

  public void setCurrentCaChildrenServiceIndicator(boolean currentCaChildrenServiceIndicator) {
    this.currentCaChildrenServiceIndicator = currentCaChildrenServiceIndicator;
  }

  public boolean getCurrentlyRegionalCenterIndicator() {
    return currentlyRegionalCenterIndicator;
  }

  public void setCurrentlyRegionalCenterIndicator(boolean currentlyRegionalCenterIndicator) {
    this.currentlyRegionalCenterIndicator = currentlyRegionalCenterIndicator;
  }

  public String getCurrentlyOtherDescription() {
    return currentlyOtherDescription;
  }

  public void setCurrentlyOtherDescription(String currentlyOtherDescription) {
    this.currentlyOtherDescription = currentlyOtherDescription;
  }

  public boolean getPreviousCaChildrenServiceIndicator() {
    return previousCaChildrenServiceIndicator;
  }

  public void setPreviousCaChildrenServiceIndicator(boolean previousCaChildrenServiceIndicator) {
    this.previousCaChildrenServiceIndicator = previousCaChildrenServiceIndicator;
  }

  public boolean getPreviousRegionalCenterIndicator() {
    return previousRegionalCenterIndicator;
  }

  public void setPreviousRegionalCenterIndicator(boolean previousRegionalCenterIndicator) {
    this.previousRegionalCenterIndicator = previousRegionalCenterIndicator;
  }

  public String getPreviousOtherDescription() {
    return previousOtherDescription;
  }

  public void setPreviousOtherDescription(String previousOtherDescription) {
    this.previousOtherDescription = previousOtherDescription;
  }

  public boolean getIndividualHealthCarePlanIndicator() {
    return individualHealthCarePlanIndicator;
  }

  public void setIndividualHealthCarePlanIndicator(boolean individualHealthCarePlanIndicator) {
    this.individualHealthCarePlanIndicator = individualHealthCarePlanIndicator;
  }

  public boolean getLimitationOnScpHealthIndicator() {
    return limitationOnScpHealthIndicator;
  }

  public void setLimitationOnScpHealthIndicator(boolean limitationOnScpHealthIndicator) {
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

  public boolean getZippyCreatedIndicator() {
    return zippyCreatedIndicator;
  }

  public void setZippyCreatedIndicator(boolean zippyCreatedIndicator) {
    this.zippyCreatedIndicator = zippyCreatedIndicator;
  }

  public String getDeathPlace() {
    return deathPlace;
  }

  public void setDeathPlace(String deathPlace) {
    this.deathPlace = deathPlace;
  }

  public boolean getTribalMembershipVerifcationIndicator() {
    return tribalMembershipVerifcationIndicator;
  }

  public void setTribalMembershipVerifcationIndicator(
      boolean tribalMembershipVerifcationIndicator) {
    this.tribalMembershipVerifcationIndicator = tribalMembershipVerifcationIndicator;
  }

  public boolean getTribalAncestryClientIndicator() {
    return tribalAncestryClientIndicator;
  }

  public void setTribalAncestryClientIndicator(boolean tribalAncestryClientIndicator) {
    this.tribalAncestryClientIndicator = tribalAncestryClientIndicator;
  }

  public boolean getSoc158SealedClientIndicator() {
    return soc158SealedClientIndicator;
  }

  public void setSoc158SealedClientIndicator(boolean soc158SealedClientIndicator) {
    this.soc158SealedClientIndicator = soc158SealedClientIndicator;
  }

  public boolean getDeathDateVerifiedIndicator() {
    return deathDateVerifiedIndicator;
  }

  public void setDeathDateVerifiedIndicator(boolean deathDateVerifiedIndicator) {
    this.deathDateVerifiedIndicator = deathDateVerifiedIndicator;
  }

  public String getEmailAddress() {
    return emailAddress;
  }

  public void setEmailAddress(String emailAddress) {
    this.emailAddress = emailAddress;
  }

  /** @return adjudicatedDelinquentIndicator (Boolean value or null) */
  @SuppressWarnings("squid:S2447")
  public Boolean getAdjudicatedDelinquentIndicator() {
    return adjudicatedDelinquentIndicator;
  }

  /** @param adjudicatedDelinquentIndicator (may be null) */
  public void setAdjudicatedDelinquentIndicator(Boolean adjudicatedDelinquentIndicator) {
    this.adjudicatedDelinquentIndicator = adjudicatedDelinquentIndicator;
  }

  public UnableToDetermineReason getEthnicityUnableToDetermineReason() {
    return ethnicityUnableToDetermineReason;
  }

  public void setEthnicityUnableToDetermineReason(
      UnableToDetermineReason ethnicityUnableToDetermineReason) {
    this.ethnicityUnableToDetermineReason = ethnicityUnableToDetermineReason;
  }

  public UnableToDetermineReason getHispanicUnableToDetermineReason() {
    return hispanicUnableToDetermineReason;
  }

  public void setHispanicUnableToDetermineReason(
      UnableToDetermineReason hispanicUnableToDetermineReason) {
    this.hispanicUnableToDetermineReason = hispanicUnableToDetermineReason;
  }

  public Soc158placementsStatus getSoc158placementsStatus() {
    return soc158placementsStatus;
  }

  public void setSoc158placementsStatus(Soc158placementsStatus soc158placementsStatus) {
    this.soc158placementsStatus = soc158placementsStatus;
  }

  public short getSexualOrientationCode() {
    return sexualOrientationCode;
  }

  public void setSexualOrientationCode(short sexualOrientationCode) {
    this.sexualOrientationCode = sexualOrientationCode;
  }

  public String getSexualOrientationUnableToDetermineCode() {
    return sexualOrientationUnableToDetermineCode;
  }

  public void setSexualOrientationUnableToDetermineCode(
      String sexualOrientationUnableToDetermineCode) {
    this.sexualOrientationUnableToDetermineCode = sexualOrientationUnableToDetermineCode;
  }

  public String getSexualOrientationNotListedDescription() {
    return sexualOrientationNotListedDescription;
  }

  public void setSexualOrientationNotListedDescription(
      String sexualOrientationNotListedDescription) {
    this.sexualOrientationNotListedDescription = sexualOrientationNotListedDescription;
  }

  public short getGenderIdentityCode() {
    return genderIdentityCode;
  }

  public void setGenderIdentityCode(short genderIdentityCode) {
    this.genderIdentityCode = genderIdentityCode;
  }

  public String getGenderIdentityNotListedDescription() {
    return genderIdentityNotListedDescription;
  }

  public void setGenderIdentityNotListedDescription(String genderIdentityNotListedDescription) {
    this.genderIdentityNotListedDescription = genderIdentityNotListedDescription;
  }

  public short getGenderEspressionCode() {
    return genderEspressionCode;
  }

  public void setGenderEspressionCode(short genderEspressionCode) {
    this.genderEspressionCode = genderEspressionCode;
  }

  public String getClientIndexNumber() {
    return clientIndexNumber;
  }

  public void setClientIndexNumber(String clientIndexNumber) {
    this.clientIndexNumber = clientIndexNumber;
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
