package gov.ca.cwds.data.legacy.cms.entity;

import gov.ca.cwds.data.legacy.cms.CmsPersistentObject;
import gov.ca.cwds.data.legacy.cms.entity.converter.StringToRequiredIntegerConverter;
import gov.ca.cwds.data.legacy.cms.entity.converter.StringToRequiredLongConverter;
import gov.ca.cwds.data.legacy.cms.entity.converter.ZipCodeConverter;
import gov.ca.cwds.data.legacy.cms.entity.converter.ZipExtConverter;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

/**
 * @author CWDS CALS API Team
 *
 *         All licensed and nonlicensed substitute care homes in which a child may potentially be
 *         placed. This entity includes definitions for both PLACEMENT_HOMEs and Foster Family
 *         Agencies (FFA). Attributes for each of these entities are almost identical, with the
 *         gov.ca.cwds.cals.exception of Backup Contact Person information and Unit Number for FFA.
 *         PLACEMENT_HOMEs may be licensed homes, certified foster family homes and relative homes,
 *         as well as those homes which have been certified pending licensure. The street name, city
 *         and county of the PLACEMENT_HOME must be known before the entity can be created. Once
 *         created, additional information may be added such as the phone number, capacity
 *         information, information regarding other residents in the home, and backup contact
 *         information specific to the home. A Foster Family Agency (FFA) is a CCL licensed
 *         organization which recruits foster family homes to operate under its license. The
 *         recruited foster family homes must undergo a certification process to ensure licensing
 *         standards are met, and that child placements can be successfully made at the home. The
 *         FFA is the point of contact with the social worker assigned to the child, and is often
 *         times the one to actually find the most suitable home for the child and place them in
 *         that home. In order to create an FFA in the CWS system, the agency name, street name,
 *         city, and state must be known. The remaining items in the address of the agency, contact
 *         name and phone number can be entered at a later date. Each PLACEMENT_HOME will have a
 *         Designated Payee as will the FFA. Before any payment can be established for a particular
 *         OUT_OF_HOME_PLACEMENT at a specific PLACEMENT_HOME or an FFA home, all the Payee mailing
 *         address information must be entered.
 */
@SuppressWarnings({"squid:S00104", "squid:S3437"})
// Entity can't be splited , LocalDate is serializable
/**
 * @author CWDS CALS API Team
 */
@NamedQueries({
  @NamedQuery(
    name = "PlacementHome.find",
    query = "SELECT ph FROM gov.ca.cwds.data.legacy.cms.entity.PlacementHome ph "
      + " LEFT JOIN FETCH ph.countyLicenseCase cls"
      + " LEFT JOIN FETCH cls.staffPerson sp"
      + " LEFT JOIN FETCH cls.licensingVisits lv"
      + " WHERE ph.identifier = :facilityId"
  ),
  @NamedQuery(
    name = PlacementHome.FIND_LICENSE_NUMBER_BY_FACILITY_ID_QUERY_NAME,
    query = "SELECT ph.licenseNo FROM gov.ca.cwds.data.legacy.cms.entity.PlacementHome ph "
      + " WHERE ph.identifier = :facilityId"
  )}
)
@Entity
@Table(name = "PLC_HM_T")
public class PlacementHome extends CmsPersistentObject {

  private static final long serialVersionUID = 8516376534560115438L;

  public static final String FIND_LICENSE_NUMBER_BY_FACILITY_ID_QUERY_NAME =
    "PlacementHome.findLicenseNumberByFacilityIdQueryName";

  /**
   * PLACEMENT_FACILITY_TYPE - The system generated number assigned to each type of placement
   * facility which can be used for OUT OF HOME PLACEMENT (e.g., Foster Family Agency, Licensed
   * Foster Family Home, Relative Home, Small Family Home, Group Home, etc.).
   */
  @Basic
  @Column(name = "PLC_FCLC", nullable = false)
  private Short facilityType;

  /**
   * GOVERNMENT_ENTITY_TYPE - The system generated number which represents the specific county
   * (e.g., Yolo, Butte, Fresno, etc.) within the state of California where the PLACEMENT HOME is
   * located.
   */
  @Basic
  @Column(name = "GVR_ENTC", nullable = false)
  private Short gvrEntc;

  /**
   * STATE_CODE_TYPE - The system generated number which identifies the State where the PLACEMENT
   * HOME is located (e.g., California, Texas, Nevada, etc.).
   */
  @Basic
  @Column(name = "F_STATE_C", nullable = false)
  private Short stateCode;

  @Basic
  @Column(name = "P_STATE_C")
  private Short payeeStateCode = 0;

  /**
   * LICENSE_STATUS_TYPE - The system generated number assigned to each type of license status for a
   * specific PLACEMENT HOME (e.g., Expired, Pending, Application Denied, Licensed, etc.).
   */
  @Basic
  @Column(name = "LIC_STC", nullable = false)
  private Short licStc;

  /**
   * ID - A system generated number used to uniquely identify each PLACEMENT_HOME. This ID is
   * composed of a base 62 Creation Timestamp and the STAFF_PERSON ID (a sequential 3 digit base 62
   * number generated by the system). This value eliminates the need for an additional set of
   * Creation Timestamp and Creation User ID which is needed to satisfy the Audit Trail requirement.
   */
  @Id
  @Column(name = "IDENTIFIER", nullable = false, length = 10)
  private String identifier;

  /**
   * Placement Home Profiles
   */
  @OneToMany
  @JoinColumn(name = "FKPLC_HM_T")
  private List<PlacementHomeProfile> placementHomeProfiles;

  /**
   * Additional notes for a Placement Home
   */
  @OneToMany
  @JoinColumn(name = "FKPLC_HM_T")
  private List<PlacementHomeNotes> placementHomeNotes;

  /**
   * Historic information on when a Placement Home Facility Type is changed and the dates the
   * Placement Home Facility type was of a specific type
   */
  @OneToMany
  @JoinColumn(name = "FKPLC_HM_T")
  private List<PlacementFacilityTypeHistory> placementFacilityTypeHistory;

  /**
   * LICENSE_NUMBER - The number identifying a specific License issued by either the State of
   * California (CCL) or a county to a specific PLACEMENT HOME. This number is at times referred to
   * as the facility number.
   */
  @Basic
  @Column(name = "LICENSE_NO", nullable = true, length = 9)
  private String licenseNo;


  /**
   * ACCEPTED_AGE_RANGE_FROM_NUMBER - This indicates the lower age range requirement in years for
   * child(ren) to be accepted for placement in a particular PLACEMENT HOME (e.g., a child must be
   * at least this age or older).
   */
  @Basic
  @Column(name = "AGE_FRM_NO", nullable = false)
  private Short ageFrmNo;

  /**
   * ACCEPTED_AGE_RANGE_TO_NUMBER - This indicates the upper age range requirement in years for
   * child(ren) to be accepted for placement in a particular PLACEMENT HOME (e.g., a child must be
   * at this age or younger).
   */
  @Basic
  @Column(name = "AGE_TO_NO", nullable = false)
  private Short ageToNo;

  /**
   * AT_CAPACITY_IND - This indicator is activated by the maintenance worker when the SUBSTITUTE
   * CARE PROVIDER informs them that all beds in the home are filled.
   */
  @Basic
  @Column(name = "AT_CAP_IND", nullable = false, length = 1)
  private String atCapInd;

  /**
   * BACKUP_CONTACT_PERSON_NAME - The name of the facility's backup contact person for those
   * facilities with 24 hour emergency response, or the name of the group home contact person.
   */
  @Basic
  @Column(name = "BCK_PERSNM", nullable = false, length = 35)
  private String bckPersnm;

  /**
   * BACKUP_PHONE_EXTENSION_NUMBER - The extension number associated with the phone number of the
   * backup contact person for the PLACEMENT HOME.
   */
  @Basic
  @Column(name = "BCK_EXT_NO")
  @Convert(converter = StringToRequiredIntegerConverter.class)
  private String bckExtNo;

  /**
   * BACKUP_PHONE_NUMBER - A phone number for the backup contact person for the PLACEMENT HOME.
   */
  @Basic
  @Column(name = "BCK_TEL_NO", nullable = false, precision = 0)
  @Convert(converter = StringToRequiredLongConverter.class)
  private String bckTelNo;

  /**
   * CERTIFIED_PENDING_LICENSURE_DATE - The date upon which a not yet licensed PLACEMENT HOME has
   * been certified meeting all licensing requirements, but awaiting formal License approval. This
   * process is performed when a suitable placement match has been found, but the home is not yet
   * licensed.
   */
  @Basic
  @Column(name = "CERTF_PNDT", nullable = true)
  private LocalDate certfPndt;

  /**
   * CHILD_CARE_PLAN_CODE - Indicates whether the PLACEMENT HOME has a child care plan developed.
   */
  @Basic
  @Column(name = "CHLCR_PLCD", nullable = false, length = 1)
  private String chlcrPlcd;

  /**
   * CITY_NAME - The name of the city where the PLACEMENT HOME is located.
   */
  @Basic
  @Column(name = "CITY_NM", nullable = false, length = 20)
  private String cityNm;

  /**
   * CLIENT_SERVED_TYPE - The system generated number assigned to each category of client served by
   * a PLACEMENT HOME (e.g., Children/Toddler, Infant, Medically Fragile, etc.).
   */
  @Basic
  @Column(name = "CL_SRVDC", nullable = false)
  private Short clSrvdc;

  /**
   * CONFIDENTIALITY_IN_EFFECT_IND - This indicates whether or not confidentiality has been
   * requested and is currently in effect for all of the SUBSTITUTE CARE PROVIDERs in this PLACEMENT
   * HOME.
   */
  @Basic
  @Column(name = "CONF_EFIND", nullable = false, length = 1)
  private String confEfind;

  /**
   * CURRENT_OCCUPANCY_NUMBER - The system maintained current occupancy number for each
   * PLACEMENT_HOME. It is incremented or decremented as a placement is established or terminated.
   * This number equates to the number of CWS children currently placed in the home.
   */
  @Basic
  @Column(name = "CUR_OCP_NO", nullable = false)
  private Short curOcpNo;

  /**
   * EMERGENCY_SHELTER_CODE - Indicates whether the PLACEMENT HOME can be used for emergency
   * placements (Y).
   */
  @Basic
  @Column(name = "EMR_SHLTCD", nullable = false, length = 1)
  private String emrShltcd;

  /**
   * FAX_NUMBER - A fax number associated with the PLACEMENT HOME.
   */
  @Basic
  @Column(name = "FAX_NO", nullable = false, precision = 0)
  @Convert(converter = StringToRequiredLongConverter.class)
  private String faxNo;

  /**
   * FOREIGN_ADDRESS_IND_VAR - This indicator variable is used to indicate if there are any
   * occurrences of FOREIGN_ADDRESSs related to this PLACEMENT_HOME. This will save unnecessary
   * processing time from searching for information that does not exist in the database.
   */
  @Basic
  @Column(name = "FRG_ADRT_B", nullable = false, length = 1)
  private String frgAdrtB;

  /**
   * GENDER_ACCEPTED_CODE - Indicates if the home accepts only males (M), only females (F), or
   * either gender (B) of children. This information can be entered by the maintenance worker once
   * it becomes known.
   */
  @Basic
  @Column(name = "GNDR_ACPCD", nullable = false, length = 1)
  private String gndrAcpcd;

  /**
   * GEOGRAPHIC_REGION_TEXT_CODE - Represents the geographical region (e.g., NW, SE, NE, etc.) in
   * which the PLACEMENT HOME is located. The geographic regions are county specific within each
   * county.
   */
  @Basic
  @Column(name = "GEO_RGNTCD", nullable = false, length = 2)
  private String geoRgntcd;

  /**
   * IN_HOME_VISITS_ALLOWED_CODE - Indicates whether the SUBSTITUTE CARE PROVIDER allows
   * friends/family to visit the children residing in the home.
   */
  @Basic
  @Column(name = "INHM_VSTCD", nullable = false, length = 1)
  private String inhmVstcd;

  /**
   * KNOWN_MAX_CAPACITY_NUMBER - The known maximum number of placement beds for CWS children. This
   * must be equal to or less than the maximum number stated on the license. This number may be
   * changed by the county after consulting with the SUBSTITUTE CARE PROVIDER. The number is the
   * result of a joint decision between the worker and the SUBSTITUTE CARE PROVIDER.
   */
  @Basic
  @Column(name = "MAX_CAP_NO", nullable = false)
  private Short maxCapNo;

  /**
   * LA_VENDOR_ID - A vendor id assigned by Los Angeles county (and unique within the county) to a
   * PLACEMENT HOME. This is only used for the APPS interface program.
   */
  @Basic
  @Column(name = "LA_VNDR_ID", nullable = false, length = 6)
  private String laVndrId;

  /**
   * LICENSE_APPLICATION_RECEIVE_DATE - The date that the License application for a specific
   * PLACEMENT HOME was received at the licensing office.
   */
  @Basic
  @Column(name = "LIC_APL_DT", nullable = true)
  private LocalDate licAplDt;

  /**
   * LICENSE_CAPACITY_NUMBER - The maximum number of children which may be placed within the
   * licensed facility (one of the PLACEMENT HOME).
   */
  @Basic
  @Column(name = "LIC_CAP_NO", nullable = false)
  private Short licCapNo;

  /**
   * LICENSE_EFFECTIVE_DATE - The date that a PLACEMENT HOME License first becomes effective.
   */
  @Basic
  @Column(name = "LIC_EFCTDT", nullable = true)
  private LocalDate licEfctdt;

  /**
   * LICENSE_EXPIRATION_DATE - The date on which the License expires and is no longer valid for this
   * PLACEMENT HOME.
   */
  @Basic
  @Column(name = "LIC_EXP_DT", nullable = true)
  private LocalDate licExpDt;

  /**
   * LICENSE_STATUS_LAST_UPDATED_DATE - The date that the License status was changed to its current
   * value for a specific PLACEMENT HOME.
   */
  @Basic
  @Column(name = "LIC_STATDT", nullable = true)
  private LocalDate licStatdt;

  /**
   * LICENSED_BUSINESS_TYPE - The system generated number assigned to each kind of business entity a
   * PLACEMENT HOME License was authorized to operated (e.g., Private for Profit, Individual, County
   * Owned, etc.).
   */
  @Basic
  @Column(name = "LIC_BSNC", nullable = false)
  private Short licBsnc;

  /**
   * LICENSEE_ORGANIZATION_NAME - The complete name (normally the name of an organization) of a
   * FOSTER FAMILY AGENCY licensee.
   */
  @Basic
  @Column(name = "LICNSEE_NM", nullable = false, length = 50)
  private String licnseeNm;

  /**
   * LICENSER_CODE - This indicates whether this specific PLACEMENT HOME was licensed by the
   * location county (CT), or the by the CCL (CL), or is not licensed (NA).
   */
  @Basic
  @Column(name = "LICENSR_CD", nullable = false, length = 2)
  private String licensrCd;

  /**
   * NAME - The name of the PLACEMENT HOME, if applicable (e.g., the name of a group home).
   */
  @Basic
  @Column(name = "FACLTY_NM", nullable = false, length = 50)
  private String facltyNm;

  /**
   * OPERATED_BY_ID - The logical foreign key representing the ID from the source entity to which a
   * PLACEMENT HOME is being operated by (e.g., the ID from the PLACEMENT HOME, or the GROUP HOME
   * ORGANIZATION entity type).
   */
  @Basic
  @Column(name = "OPRTD_BYID", nullable = true, length = 10)
  private String oprtdByid;

  /**
   * OPERATED_BY_CODE - This code defines each type of source entity to which a specific PLACEMENT
   * is being operated by (e.g., F = Foster Family Agency (from PLACEMENT HOME), G = GROUP HOME
   * ORGANIZATION).
   */
  @Basic
  @Column(name = "OPRTD_BYCD", nullable = true, length = 1)
  private String oprtdBycd;

  /**
   * PAYEE_CITY_NAME - The city name on the mailing address of the Designated Payee for a particular
   * PLACEMENT HOME.
   */
  @Basic
  @Column(name = "P_CITY_NM", length = 20)
  private String pCityNm;

  /**
   * PAYEE_FIRST_NAME - The first name of the Designated Payee for a particular PLACEMENT HOME.
   */
  @Basic
  @Column(name = "PYE_FSTNM", length = 20)
  private String pyeFstnm;

  /**
   * PAYEE_LAST_NAME - The last name of the Designated Payee for a particular PLACEMENT HOME.
   */
  @Basic
  @Column(name = "PYE_LSTNM", length = 25)
  private String pyeLstnm;

  /**
   * PAYEE_MIDDLE_INITIAL_NAME - The middle initial of the Designated Payee for a particular
   * PLACEMENT HOME.
   */
  @Basic
  @Column(name = "PYE_MIDNM", length = 1)
  private String pyeMidnm;

  /**
   * PAYEE_STREET_NAME - The actual name of the street associated with the Designated Payee's
   * address for a particular PLACEMENT HOME.
   */
  @Basic
  @Column(name = "PSTREET_NM", length = 40)
  private String pstreetNm;

  /**
   * PAYEE_STREET_NUMBER - The street or house number associated with the Street Name as part of the
   * Designated Payee's address. This may include the fractional or alphabetic modifier, e.g. A-17,
   * 119-10, 39.2, 100 1/2, etc.
   */
  @Basic
  @Column(name = "PSTREET_NO", length = 10)
  private String pstreetNo;

  /**
   * PAYEE_ZIP_NUMBER - The first five digits of the zip code for the Designated Payee's mailing
   * address for a particular PLACEMENT HOME.
   */
  @Basic
  @Column(name = "P_ZIP_NO")
  @Convert(converter = ZipCodeConverter.class)
  private String pZipNo;

  /**
   * PRIMARY_CONTACT_PERSON_NAME - The name of the facility's primary contact person for the
   * placement home, the Foster Family Agency, or the name of the group home contact person.
   */
  @Basic
  @Column(name = "PRM_CNCTNM", nullable = false, length = 35)
  private String prmCnctnm;

  /**
   * PRIMARY_PHONE_EXTENSION_NUMBER - The primary phone extension number of the PLACEMENT HOME or
   * Foster Family Agency.
   */
  @Basic
  @Column(name = "PRM_EXT_NO")
  @Convert(converter = StringToRequiredIntegerConverter.class)
  private String prmExtNo;

  /**
   * PRIMARY_SUBS_CARE_PROVIDER_ID - The logical foreign key representing the ID from the source
   * entity of the Primary Care Provider for a particular PLACEMENT HOME.
   *
   * <blockquote>
   * 
   * <pre>
   * &#64;Basic
   * &#64;Column(name = "PRM_SUBSID", nullable = true, length = 10)
   * private String prmSubsid;
   * </pre>
   * 
   * </blockquote>
   */
  @NotFound(action = NotFoundAction.IGNORE)
  @ManyToOne(fetch = FetchType.EAGER)
  @JoinColumn(name = "PRM_SUBSID", referencedColumnName = "IDENTIFIER")
  private SubstituteCareProvider primarySubstituteCareProvider;

  /**
   * PRIMARY_SUBS_CARE_PROVIDER_NAME - The full name of the Primary Care Provider for a particular
   * PLACEMENT HOME.
   */
  @Basic
  @Column(name = "PRM_SUBSNM", nullable = false, length = 54)
  private String prmSubsnm;

  /**
   * PRIMARY_PHONE_NUMBER - The primary phone number including area code of the PLACEMENT HOME or
   * Foster Family Agency.
   */
  @Basic
  @Column(name = "PRM_TEL_NO", nullable = false, precision = 0)
  @Convert(converter = StringToRequiredLongConverter.class)
  private String prmTelNo;

  /**
   * PROVIDES_TRANSPORT_CODE - Indicates whether the home is willing to provide transportation for
   * children in the home.
   */
  @Basic
  @Column(name = "PVD_TRNSCD", nullable = false, length = 1)
  private String pvdTrnscd;

  /**
   * PUBLIC_TRANSPORTN_AVAILABLE_CODE - Indicates whether the home is easily accessible via public
   * transportation.
   */
  @Basic
  @Column(name = "PUB_TRNSCD", nullable = false, length = 1)
  private String pubTrnscd;

  /**
   * STREET_NAME - The actual name of the street associated with the PLACEMENT HOME's address. Do
   * not abbreviate if at all possible for matching purposes.
   */
  @Basic
  @Column(name = "STREET_NM", nullable = false, length = 40)
  private String streetNm;

  /**
   * STREET_NUMBER - The street or house number associated with the street name as part of the
   * PLACEMENT HOME's address. This may include the fractional or alphabetic modifier, e.g., A-17,
   * 119-10, 39.2, 100 1/2, etc.
   */
  @Basic
  @Column(name = "STREET_NO", nullable = false, length = 10)
  private String streetNo;

  /**
   * ZIP_NUMBER - The first five digits of the zip code for the PLACEMENT HOME's address.
   */
  @Basic
  @Column(name = "ZIP_NO", nullable = false)
  @Convert(converter = ZipCodeConverter.class)
  private String zipNo;

  /**
   * ADDRESS_DESCRIPTION - Any additional information pertaining to the PLACEMENT HOME's address
   * which may include directions for getting to the PLACEMENT HOME.
   */
  @Basic
  @Column(name = "ADDR_DSC", nullable = false, length = 120)
  private String addrDsc;

  /**
   * CHILDREN_SPECIAL_CHARSTC_DESC - The freeform text field that can be used to document any other
   * special characteristics and/or conditions for all children residing in the PLACEMENT HOME.
   */
  @Basic
  @Column(name = "SPCHAR_DSC", nullable = false, length = 120)
  private String spcharDsc;

  /**
   * COUNTY_PREFERENCE_DESCRIPTION - A short description text for any County specific information
   * pertaining to the PLACEMENT HOME's preferences.
   */
  @Basic
  @Column(name = "CTYPRF_DSC", nullable = false, length = 240)
  private String ctyprfDsc;

  /**
   * EDUCATION_PROVIDER_DESCRIPTION - The free form text field which describes the schools servicing
   * the home. These schools can be elementary schools, junior high schools, high schools, and/or
   * others. This information may be used to find a suitable placement for a child when the social
   * worker determines that it is desirable to keep the child in the school he/she currently
   * attends.
   */
  @Basic
  @Column(name = "ED_PVR_DSC", nullable = false, length = 120)
  private String edPvrDsc;

  /**
   * ENVIRONMENTAL_FACTOR_DESCRIPTION - Significant environmental factors that may affect the
   * placement of a child at a facility participating in vacancy match. Examples include a facility
   * located in an area with an overabundance of pollen. This could be critical in the placement of
   * an asthmatic child.
   */
  @Basic
  @Column(name = "ENV_FCTDSC", nullable = false, length = 60)
  private String envFctdsc;

  /**
   * HAZARDS_DESCRIPTION - Describes whether the PLACEMENT HOME has a swimming pool, spa, pond, or
   * any other significant body of water or objects which may pose a hazard.
   */
  @Basic
  @Column(name = "HAZRDS_DSC", nullable = false, length = 120)
  private String hazrdsDsc;

  /**
   * LIS_PREFERENCE_DESCRIPTION - A short description text which provides placement preferences
   * summary information downloaded from the LIS interface. This is read only information and cannot
   * be modified. Any county specific comments regarding such preferences should be documented in
   * the License Waiver Description field. Many times, this preference information includes an age
   * range, or the level of care (1-4) provided by the home if the home is a Small Family Home.
   * Automated vacancy match cannot use this field. However, this information may be utilized after
   * the number of facilities has been minimized with the first cut of an automated vacancy match.
   */
  @Basic
  @Column(name = "LIS_PRFDSC", nullable = false, length = 210)
  private String lisPrfdsc;

  /**
   * PETS_DESCRIPTION - Identifies and describes whether the home allows pets or currently has pets.
   */
  @Basic
  @Column(name = "PETS_DSC", nullable = false, length = 60)
  private String petsDsc;

  /**
   * RELIGIOUS_ACTIVITY_DESCRIPTION - Description of the religious activity in the home(e.g., how
   * often the people in the home attends bible study).
   */
  @Basic
  @Column(name = "RLG_ACTDSC", nullable = false, length = 60)
  private String rlgActdsc;

  /**
   * PAYEE_ZIP_SUFFIX_NUMBER - The last four digits of the zip code for the Designated Payee's
   * mailing address for a particular PLACEMENT HOME.
   */
  @Basic
  @Column(name = "PY_ZIP_SFX", nullable = false)
  @Convert(converter = ZipExtConverter.class)
  private String pyZipSfx;

  /**
   * ZIP_SUFFIX_NUMBER - The last four digits of the zip code for a PLACEMENT HOME's address.
   */
  @Basic
  @Column(name = "ZIP_SFX_NO", nullable = false)
  @Convert(converter = ZipExtConverter.class)
  private String zipSfxNo;

  /**
   * APPLICATION_STATUS_TYPE - The system generated number which identifies the current status of
   * the application to license a PLACEMENT_HOME.
   */
  @Basic
  @Column(name = "AP_STAT_TP", nullable = false)
  private Short apStatTp;

  /**
   * CERTIFICATION_FORM_COMPLETED_IND - Indicates whether the Social Worker has completed
   * certification of the home (Y), or has not completed certification (N) for a County Licensed
   * Care.
   */
  @Basic
  @Column(name = "CERT_CMPLT", nullable = false, length = 1)
  private String certCmplt;

  /**
   * LA_PAYEE_CITY_NAME - The LA city name on the mailing address of the Designated Payee for a
   * particular PLACEMENT HOME.
   */
  @Basic
  @Column(name = "LA_P_CTYNM", nullable = false, length = 20)
  private String laPCtynm;

  /**
   * LA_PAYEE_FIRST_NAME - The first name of the Designated LA Payee for a particular PLACEMENT
   * HOME.
   */
  @Basic
  @Column(name = "LA_P_FSTNM", nullable = false, length = 20)
  private String laPFstnm;

  /**
   * LA_PAYEE_LAST_NAME - The last name of the Designated Payee for a particular PLACEMENT HOME.
   */
  @Basic
  @Column(name = "LA_P_LSTNM", nullable = false, length = 25)
  private String laPLstnm;

  /**
   * LA_PAYEE_MIDDLE_INITIAL_NAME - The middle initial of the Designated Payee for a particular
   * PLACEMENT HOME.
   */
  @Basic
  @Column(name = "LA_P_MIDNM", nullable = false, length = 1)
  private String laPMidnm;

  /**
   * LA_PAYEE_STATE_CODE_TYPE - The system generated number which identifies the State for the
   * Designated Payee's mailing address (e.g., California, Nevada, Texas, etc.).
   *
   * <blockquote>
   * 
   * <pre>
   * &#64;Basic
   * &#64;Column(name = "LP_STATE_C", nullable = false)
   * private Short lpStateC;
   * </pre>
   * 
   * </blockquote>
   */
  @Basic
  @Column(name = "LP_STATE_C")
  private Short laPayeeState;

  /**
   * LA_PAYEE_STREET_NAME - The actual name of the street associated with the Designated Payee's
   * address for a particular PLACEMENT HOME.
   */
  @Basic
  @Column(name = "LA_P_STNM", nullable = false, length = 40)
  private String laPStnm;

  /**
   * LA_PAYEE_STREET_NUMBER - The street or house number associated with the Street Name as part of
   * the Designated Payee's address. This may include the fractional or alphabetic modifier, e.g.
   * A-17, 119-10, 39.2, 100 1/2, etc.
   */
  @Basic
  @Column(name = "LA_P_STNO", nullable = false, length = 10)
  private String laPStno;

  /**
   * LA_PAYEE_ZIP_NUMBER - The first five digits of the zip code for the Designated Payee's mailing
   * address for a particular PLACEMENT HOME.
   */
  @Basic
  @Column(name = "LA_P_ZIPNO", nullable = false)
  @Convert(converter = ZipCodeConverter.class)
  private String laPZipno;

  /**
   * LA_PAYEE_ZIP_SUFFIX_NUMBER - The last four digits of the zip code for the Designated Payee's
   * mailing address for a particular PLACEMENT HOME.
   */
  @Basic
  @Column(name = "LA_P_ZPSFX", nullable = false)
  @Convert(converter = ZipExtConverter.class)
  private String laPZpsfx;

  /**
   * LA_PAYEE_BUSINESS - LA payee business
   */
  @Basic
  @Column(name = "LA_P_BSNSS", nullable = false, length = 30)
  private String laPBsnss;

  /**
   * APP_STATUS_UPDATE_DATE - This attribute holds the date that the application status was last
   * changed. It is set by the system.
   */
  @Basic
  @Column(name = "AP_STAT_DT", nullable = true)
  private LocalDate apStatDt;

  /**
   * LA_PAYEE_PHONE_NUMBER - LA phone number
   */
  @Basic
  @Column(name = "LA_P_PH_NO", nullable = false, precision = 0)
  @Convert(converter = StringToRequiredLongConverter.class)
  private String laPPhNo;

  /**
   * LA_PAYEE_PHONE_EXT - LA phone number extention
   */
  @Basic
  @Column(name = "LA_P_PH_EX")
  @Convert(converter = StringToRequiredIntegerConverter.class)
  private String laPPhEx;

  /**
   * ADOPTION_HOME_ONLY_IND - This indicates whether this home is only available to adoption workers
   * for adoptive placements and can not be viewed by social workers (Y), or it is a foster home
   * (and possible also an adoption home) and can be viewed by both social workers and adoption
   * workers (N).
   */
  @Basic
  @Column(name = "ADHMONLY", nullable = false, length = 1)
  private String adhmonly;

  /**
   * PAYEE_PHONE_EXT - Payee phone number extension.
   */
  @Basic
  @Column(name = "PYE_EXT_NO")
  @Convert(converter = StringToRequiredIntegerConverter.class)
  private String pyeExtNo;

  /**
   * PAYEE_PHONE_NUMBER - Payee phone number.
   */
  @Basic
  @Column(name = "PYE_TEL_NO", nullable = false, precision = 0)
  @Convert(converter = StringToRequiredLongConverter.class)
  private String pyeTelNo;

  /**
   * ARCHIVE_ASSOCIATION_IND - Indicates if the PLACEMENT_HOME is part of something that is
   * archived.
   */
  @Basic
  @Column(name = "ARCASS_IND", nullable = false, length = 1)
  private String arcassInd;

  /**
   * COMMUNITY_TREATMENT_FACILITY_IND - Indicates whether this is a Community Treatment Facility (Y)
   * or a Group Home (N). Code Values: Y = Yes and N = No
   */
  @Basic
  @Column(name = "COMFAC_IND", nullable = false, length = 1)
  private String comfacInd;

  /**
   * TRANSITIONAL_HOUSING_PLACEMENT_P - Indicates whether this is a Transitional Housing Placement
   * Program (Y) or a Foster Family Agency (N). Code Values: Y = Yes and N = No. Default to N.
   * Mandatory attribute.
   */
  @Basic
  @Column(name = "TRNHSG_IND", nullable = false, length = 1)
  private String trnhsgInd;

  /**
   * TRANSITIONAL_HOUSING_PLACEMENT - Indicates whether this is a Transitional Housing Placement
   * Program Facility (Y) or a Foster Family Agency Certified Home (N). Code Values: Y = Yes and N =
   * No. Default to N. Mandatory attribute.
   */
  @Basic
  @Column(name = "TRNHSG_FAC", nullable = false, length = 1)
  private String trnhsgFac;

  /**
   * NEW_LICENSE_NUMBER - This field is populated when a change of ownership or location takes place
   * for a specific Placement Facility. It identifies what the license number of the new facility
   * will be. The new License Number is issued by the State of California (CCL) or can be entered by
   * the user when processing a Placement Home Move online. After both the old facility is closed
   * and the new facility has been added to the system, any active links to the closed facility
   * (e.g. FFA-Certified Homes, SCPs, and placements) will be ended and links to the facility with
   * the new license number will be established. This field works in conjunction with
   * Process_Placement_Home_Move_Ind to show when the new license number has been added and the
   * reconnections are complete. Both the LIS Interface and the online application will set this
   * attribute.
   */
  @Basic
  @Column(name = "NEWLIC_NO", nullable = true, length = 9)
  private String newlicNo;

  /**
   * PROCESS_PLACEMENT_HOME_MOVE_IND - This field is used internally in conjunction with
   * New_License_Number for licensed homes to show when a facility has a new license number and that
   * any necessary pointers for homes and/or providers have been reset. For non licensed homes this
   * indicator is set once the Placement. Home is moved via the Process Placement Home Move
   * function. It also indicates all active out-of-home and adoptive placements have been re-pointed
   * to the new version of the home, when applicable. It will be set to Y on the old facility after
   * the updates are complete. Both the LIS Interface and the online application will set this
   * attribute.
   */
  @Basic
  @Column(name = "NEWLIC_UPD", nullable = false, length = 1)
  private String newlicUpd;

  /**
   * OLD_FACILITY_ID - This attribute is populated when a user performs a Process Placement Home
   * Move or when an FFA/Placement Home changes ownership or location. This is a foreign key to the
   * old placement home. Both the LIS Interface and the online application will set this attribute.
   */
  @Basic
  @Column(name = "OLDFAC_ID", nullable = true, length = 10)
  private String oldfacId;

  /**
   * EMERGENCY_CONTACT_IND_VAR - This indicator variable indicates whether there are any occurrences
   * of EMERGENCY CONTACT related to this PLACEMENT HOME. This will save unnecessary processing time
   * from searching for information that does not exist in the database. The valid values are Yes
   * (Y) and No (N).
   */
  @Basic
  @Column(name = "EM_CNT_B", nullable = false, length = 1)
  private String emCntB;

  /**
   * END_DATE - The Date the placement home was end dated.
   */
  @Basic
  @Column(name = "END_DT", nullable = true)
  private LocalDate endDt;

  /**
   * END_REASON_TYPE - The system generated number assigned to record why a placement home was end
   * dated. The selection choices will be provided by the Placement Home End Reason Type code table,
   * therefore this attribute will store the SysId of the chosen value.
   */
  @Basic
  @Column(name = "PH_ENDC", nullable = true)
  private Short phEndc;

  /**
   * END_COMMENT_DESCRIPTION - The field contains any comments used to describe the reason a
   * placement home was end dated.
   */
  @Basic
  @Column(name = "END_COMDSC", nullable = true, length = 254)
  private String endComdsc;

  @OneToMany
  @JoinColumn(name = "FKPLC_HM_T")
  private List<OtherAdultsInPlacementHome> otherAdultsInPlacementHomes;

  @OneToMany
  @JoinColumn(name = "FKPLC_HM_T")
  private List<OtherChildrenInPlacementHome> otherChildrenInPlacementHomes;

  @OneToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "FKCNTY_CST", referencedColumnName = "IDENTIFIER", nullable = true)
  private CountyLicenseCase countyLicenseCase;

  public CountyLicenseCase getCountyLicenseCase() {
    return countyLicenseCase;
  }

  public void setCountyLicenseCase(CountyLicenseCase countyLicenseCase) {
    this.countyLicenseCase = countyLicenseCase;
  }

  public Short getFacilityType() {
    return facilityType;
  }

  public void setFacilityType(Short facilityType) {
    this.facilityType = facilityType;
  }

  public Short getStateCode() {
    return stateCode;
  }

  public void setStateCode(Short stateCode) {
    this.stateCode = stateCode;
  }

  public Short getPayeeStateCode() {
    return payeeStateCode;
  }

  public void setPayeeStateCode(Short payeeStateCode) {
    this.payeeStateCode = payeeStateCode;
  }

  public Short getLicStc() {
    return licStc;
  }

  public void setLicStc(Short licStc) {
    this.licStc = licStc;
  }

  public String getIdentifier() {
    return identifier;
  }

  public void setIdentifier(String identifier) {
    this.identifier = identifier;
  }

  public List<PlacementHomeProfile> getPlacementHomeProfiles() {
    return placementHomeProfiles;
  }

  public void setPlacementHomeProfiles(List<PlacementHomeProfile> placementHomeProfiles) {
    this.placementHomeProfiles = placementHomeProfiles;
  }

  public List<PlacementHomeNotes> getPlacementHomeNotes() {
    return placementHomeNotes;
  }

  public void setPlacementHomeNotes(List<PlacementHomeNotes> placementHomeNotes) {
    this.placementHomeNotes = placementHomeNotes;
  }

  public List<PlacementFacilityTypeHistory> getPlacementFacilityTypeHistory() {
    return placementFacilityTypeHistory;
  }

  public void setPlacementFacilityTypeHistory(
      List<PlacementFacilityTypeHistory> placementFacilityTypeHistory) {
    this.placementFacilityTypeHistory = placementFacilityTypeHistory;
  }

  public String getLicenseNo() {
    return licenseNo;
  }

  public void setLicenseNo(String licenseNo) {
    this.licenseNo = licenseNo;
  }

  public Short getAgeFrmNo() {
    return ageFrmNo;
  }

  public void setAgeFrmNo(Short ageFrmNo) {
    this.ageFrmNo = ageFrmNo;
  }

  public Short getAgeToNo() {
    return ageToNo;
  }

  public void setAgeToNo(Short ageToNo) {
    this.ageToNo = ageToNo;
  }

  public String getAtCapInd() {
    return atCapInd;
  }

  public void setAtCapInd(String atCapInd) {
    this.atCapInd = atCapInd;
  }

  public String getBckPersnm() {
    return bckPersnm;
  }

  public void setBckPersnm(String bckPersnm) {
    this.bckPersnm = bckPersnm;
  }

  public String getBckExtNo() {
    return bckExtNo;
  }

  public void setBckExtNo(String bckExtNo) {
    this.bckExtNo = bckExtNo;
  }

  public String getBckTelNo() {
    return bckTelNo;
  }

  public void setBckTelNo(String bckTelNo) {
    this.bckTelNo = bckTelNo;
  }

  public LocalDate getCertfPndt() {
    return certfPndt;
  }

  public void setCertfPndt(LocalDate certfPndt) {
    this.certfPndt = certfPndt;
  }

  public String getChlcrPlcd() {
    return chlcrPlcd;
  }

  public void setChlcrPlcd(String chlcrPlcd) {
    this.chlcrPlcd = chlcrPlcd;
  }

  public String getCityNm() {
    return cityNm;
  }

  public void setCityNm(String cityNm) {
    this.cityNm = cityNm;
  }

  public Short getClSrvdc() {
    return clSrvdc;
  }

  public void setClSrvdc(Short clSrvdc) {
    this.clSrvdc = clSrvdc;
  }

  public String getConfEfind() {
    return confEfind;
  }

  public void setConfEfind(String confEfind) {
    this.confEfind = confEfind;
  }

  public Short getCurOcpNo() {
    return curOcpNo;
  }

  public void setCurOcpNo(Short curOcpNo) {
    this.curOcpNo = curOcpNo;
  }

  public String getEmrShltcd() {
    return emrShltcd;
  }

  public void setEmrShltcd(String emrShltcd) {
    this.emrShltcd = emrShltcd;
  }

  public String getFaxNo() {
    return faxNo;
  }

  public void setFaxNo(String faxNo) {
    this.faxNo = faxNo;
  }

  public String getFrgAdrtB() {
    return frgAdrtB;
  }

  public void setFrgAdrtB(String frgAdrtB) {
    this.frgAdrtB = frgAdrtB;
  }

  public String getGndrAcpcd() {
    return gndrAcpcd;
  }

  public void setGndrAcpcd(String gndrAcpcd) {
    this.gndrAcpcd = gndrAcpcd;
  }

  public String getGeoRgntcd() {
    return geoRgntcd;
  }

  public void setGeoRgntcd(String geoRgntcd) {
    this.geoRgntcd = geoRgntcd;
  }

  public String getInhmVstcd() {
    return inhmVstcd;
  }

  public void setInhmVstcd(String inhmVstcd) {
    this.inhmVstcd = inhmVstcd;
  }

  public Short getMaxCapNo() {
    return maxCapNo;
  }

  public void setMaxCapNo(Short maxCapNo) {
    this.maxCapNo = maxCapNo;
  }

  public String getLaVndrId() {
    return laVndrId;
  }

  public void setLaVndrId(String laVndrId) {
    this.laVndrId = laVndrId;
  }

  public LocalDate getLicAplDt() {
    return licAplDt;
  }

  public void setLicAplDt(LocalDate licAplDt) {
    this.licAplDt = licAplDt;
  }

  public Short getLicCapNo() {
    return licCapNo;
  }

  public void setLicCapNo(Short licCapNo) {
    this.licCapNo = licCapNo;
  }

  public LocalDate getLicEfctdt() {
    return licEfctdt;
  }

  public void setLicEfctdt(LocalDate licEfctdt) {
    this.licEfctdt = licEfctdt;
  }

  public LocalDate getLicExpDt() {
    return licExpDt;
  }

  public void setLicExpDt(LocalDate licExpDt) {
    this.licExpDt = licExpDt;
  }

  public LocalDate getLicStatdt() {
    return licStatdt;
  }

  public void setLicStatdt(LocalDate licStatdt) {
    this.licStatdt = licStatdt;
  }

  public Short getLicBsnc() {
    return licBsnc;
  }

  public void setLicBsnc(Short licBsnc) {
    this.licBsnc = licBsnc;
  }

  public String getLicnseeNm() {
    return licnseeNm;
  }

  public void setLicnseeNm(String licnseeNm) {
    this.licnseeNm = licnseeNm;
  }

  public String getLicensrCd() {
    return licensrCd;
  }

  public void setLicensrCd(String licensrCd) {
    this.licensrCd = licensrCd;
  }

  public String getFacltyNm() {
    return facltyNm;
  }

  public void setFacltyNm(String facltyNm) {
    this.facltyNm = facltyNm;
  }

  public String getOprtdByid() {
    return oprtdByid;
  }

  public void setOprtdByid(String oprtdByid) {
    this.oprtdByid = oprtdByid;
  }

  public String getOprtdBycd() {
    return oprtdBycd;
  }

  public void setOprtdBycd(String oprtdBycd) {
    this.oprtdBycd = oprtdBycd;
  }


  public String getpCityNm() {
    return pCityNm;
  }

  public void setpCityNm(String pCityNm) {
    this.pCityNm = pCityNm;
  }

  public String getPyeFstnm() {
    return pyeFstnm;
  }

  public void setPyeFstnm(String pyeFstnm) {
    this.pyeFstnm = pyeFstnm;
  }

  public String getPyeLstnm() {
    return pyeLstnm;
  }

  public void setPyeLstnm(String pyeLstnm) {
    this.pyeLstnm = pyeLstnm;
  }

  public String getPyeMidnm() {
    return pyeMidnm;
  }

  public void setPyeMidnm(String pyeMidnm) {
    this.pyeMidnm = pyeMidnm;
  }

  public String getPstreetNm() {
    return pstreetNm;
  }

  public void setPstreetNm(String pstreetNm) {
    this.pstreetNm = pstreetNm;
  }

  public String getPstreetNo() {
    return pstreetNo;
  }

  public void setPstreetNo(String pstreetNo) {
    this.pstreetNo = pstreetNo;
  }

  public String getpZipNo() {
    return pZipNo;
  }

  public void setpZipNo(String pZipNo) {
    this.pZipNo = pZipNo;
  }

  public String getPrmCnctnm() {
    return prmCnctnm;
  }

  public void setPrmCnctnm(String prmCnctnm) {
    this.prmCnctnm = prmCnctnm;
  }

  public String getPrmExtNo() {
    return prmExtNo;
  }

  public void setPrmExtNo(String prmExtNo) {
    this.prmExtNo = prmExtNo;
  }

  public SubstituteCareProvider getPrimarySubstituteCareProvider() {
    return primarySubstituteCareProvider;
  }

  public void setPrimarySubstituteCareProvider(
      SubstituteCareProvider primarySubstituteCareProvider) {
    this.primarySubstituteCareProvider = primarySubstituteCareProvider;
  }

  public String getPrmSubsnm() {
    return prmSubsnm;
  }

  public void setPrmSubsnm(String prmSubsnm) {
    this.prmSubsnm = prmSubsnm;
  }

  public String getPrmTelNo() {
    return prmTelNo;
  }

  public void setPrmTelNo(String prmTelNo) {
    this.prmTelNo = prmTelNo;
  }

  public String getPvdTrnscd() {
    return pvdTrnscd;
  }

  public void setPvdTrnscd(String pvdTrnscd) {
    this.pvdTrnscd = pvdTrnscd;
  }

  public String getPubTrnscd() {
    return pubTrnscd;
  }

  public void setPubTrnscd(String pubTrnscd) {
    this.pubTrnscd = pubTrnscd;
  }

  public String getStreetNm() {
    return streetNm;
  }

  public void setStreetNm(String streetNm) {
    this.streetNm = streetNm;
  }

  public String getStreetNo() {
    return streetNo;
  }

  public void setStreetNo(String streetNo) {
    this.streetNo = streetNo;
  }

  public String getZipNo() {
    return zipNo;
  }

  public void setZipNo(String zipNo) {
    this.zipNo = zipNo;
  }

  public String getAddrDsc() {
    return addrDsc;
  }

  public void setAddrDsc(String addrDsc) {
    this.addrDsc = addrDsc;
  }

  public String getSpcharDsc() {
    return spcharDsc;
  }

  public void setSpcharDsc(String spcharDsc) {
    this.spcharDsc = spcharDsc;
  }

  public String getCtyprfDsc() {
    return ctyprfDsc;
  }

  public void setCtyprfDsc(String ctyprfDsc) {
    this.ctyprfDsc = ctyprfDsc;
  }

  public String getEdPvrDsc() {
    return edPvrDsc;
  }

  public void setEdPvrDsc(String edPvrDsc) {
    this.edPvrDsc = edPvrDsc;
  }

  public String getEnvFctdsc() {
    return envFctdsc;
  }

  public void setEnvFctdsc(String envFctdsc) {
    this.envFctdsc = envFctdsc;
  }

  public String getHazrdsDsc() {
    return hazrdsDsc;
  }

  public void setHazrdsDsc(String hazrdsDsc) {
    this.hazrdsDsc = hazrdsDsc;
  }

  public String getLisPrfdsc() {
    return lisPrfdsc;
  }

  public void setLisPrfdsc(String lisPrfdsc) {
    this.lisPrfdsc = lisPrfdsc;
  }

  public String getPetsDsc() {
    return petsDsc;
  }

  public void setPetsDsc(String petsDsc) {
    this.petsDsc = petsDsc;
  }

  public String getRlgActdsc() {
    return rlgActdsc;
  }

  public void setRlgActdsc(String rlgActdsc) {
    this.rlgActdsc = rlgActdsc;
  }

  public String getPyZipSfx() {
    return pyZipSfx;
  }

  public void setPyZipSfx(String pyZipSfx) {
    this.pyZipSfx = pyZipSfx;
  }

  public String getZipSfxNo() {
    return zipSfxNo;
  }

  public void setZipSfxNo(String zipSfxNo) {
    this.zipSfxNo = zipSfxNo;
  }

  public Short getApStatTp() {
    return apStatTp;
  }

  public void setApStatTp(Short apStatTp) {
    this.apStatTp = apStatTp;
  }

  public String getCertCmplt() {
    return certCmplt;
  }

  public void setCertCmplt(String certCmplt) {
    this.certCmplt = certCmplt;
  }

  public String getLaPCtynm() {
    return laPCtynm;
  }

  public void setLaPCtynm(String laPCtynm) {
    this.laPCtynm = laPCtynm;
  }

  public String getLaPFstnm() {
    return laPFstnm;
  }

  public void setLaPFstnm(String laPFstnm) {
    this.laPFstnm = laPFstnm;
  }

  public String getLaPLstnm() {
    return laPLstnm;
  }

  public void setLaPLstnm(String laPLstnm) {
    this.laPLstnm = laPLstnm;
  }

  public String getLaPMidnm() {
    return laPMidnm;
  }

  public void setLaPMidnm(String laPMidnm) {
    this.laPMidnm = laPMidnm;
  }

  public String getLaPStnm() {
    return laPStnm;
  }

  public void setLaPStnm(String laPStnm) {
    this.laPStnm = laPStnm;
  }

  public String getLaPStno() {
    return laPStno;
  }

  public void setLaPStno(String laPStno) {
    this.laPStno = laPStno;
  }

  public String getLaPZipno() {
    return laPZipno;
  }

  public void setLaPZipno(String laPZipno) {
    this.laPZipno = laPZipno;
  }

  public String getLaPZpsfx() {
    return laPZpsfx;
  }

  public void setLaPZpsfx(String laPZpsfx) {
    this.laPZpsfx = laPZpsfx;
  }

  public String getLaPBsnss() {
    return laPBsnss;
  }

  public void setLaPBsnss(String laPBsnss) {
    this.laPBsnss = laPBsnss;
  }

  public LocalDate getApStatDt() {
    return apStatDt;
  }

  public void setApStatDt(LocalDate apStatDt) {
    this.apStatDt = apStatDt;
  }

  public String getLaPPhNo() {
    return laPPhNo;
  }

  public void setLaPPhNo(String laPPhNo) {
    this.laPPhNo = laPPhNo;
  }

  public String getLaPPhEx() {
    return laPPhEx;
  }

  public void setLaPPhEx(String laPPhEx) {
    this.laPPhEx = laPPhEx;
  }

  public String getAdhmonly() {
    return adhmonly;
  }

  public void setAdhmonly(String adhmonly) {
    this.adhmonly = adhmonly;
  }

  public String getPyeExtNo() {
    return pyeExtNo;
  }

  public void setPyeExtNo(String pyeExtNo) {
    this.pyeExtNo = pyeExtNo;
  }

  public String getPyeTelNo() {
    return pyeTelNo;
  }

  public void setPyeTelNo(String pyeTelNo) {
    this.pyeTelNo = pyeTelNo;
  }

  public String getArcassInd() {
    return arcassInd;
  }

  public void setArcassInd(String arcassInd) {
    this.arcassInd = arcassInd;
  }

  public String getComfacInd() {
    return comfacInd;
  }

  public void setComfacInd(String comfacInd) {
    this.comfacInd = comfacInd;
  }

  public String getTrnhsgInd() {
    return trnhsgInd;
  }

  public void setTrnhsgInd(String trnhsgInd) {
    this.trnhsgInd = trnhsgInd;
  }

  public String getTrnhsgFac() {
    return trnhsgFac;
  }

  public void setTrnhsgFac(String trnhsgFac) {
    this.trnhsgFac = trnhsgFac;
  }

  public String getNewlicNo() {
    return newlicNo;
  }

  public void setNewlicNo(String newlicNo) {
    this.newlicNo = newlicNo;
  }

  public String getNewlicUpd() {
    return newlicUpd;
  }

  public void setNewlicUpd(String newlicUpd) {
    this.newlicUpd = newlicUpd;
  }

  public String getOldfacId() {
    return oldfacId;
  }

  public void setOldfacId(String oldfacId) {
    this.oldfacId = oldfacId;
  }

  public String getEmCntB() {
    return emCntB;
  }

  public void setEmCntB(String emCntB) {
    this.emCntB = emCntB;
  }

  public LocalDate getEndDt() {
    return endDt;
  }

  public void setEndDt(LocalDate endDt) {
    this.endDt = endDt;
  }

  public Short getPhEndc() {
    return phEndc;
  }

  public void setPhEndc(Short phEndc) {
    this.phEndc = phEndc;
  }

  public String getEndComdsc() {
    return endComdsc;
  }

  public void setEndComdsc(String endComdsc) {
    this.endComdsc = endComdsc;
  }

  public List<OtherAdultsInPlacementHome> getOtherAdultsInPlacementHomes() {
    return otherAdultsInPlacementHomes;
  }

  public void setOtherAdultsInPlacementHomes(
      List<OtherAdultsInPlacementHome> otherAdultsInPlacementHomes) {
    this.otherAdultsInPlacementHomes = otherAdultsInPlacementHomes;
  }

  public List<OtherChildrenInPlacementHome> getOtherChildrenInPlacementHomes() {
    return otherChildrenInPlacementHomes;
  }

  public void setOtherChildrenInPlacementHomes(
      List<OtherChildrenInPlacementHome> otherChildrenInPlacementHomes) {
    this.otherChildrenInPlacementHomes = otherChildrenInPlacementHomes;
  }

  public Short getGvrEntc() {
    return gvrEntc;
  }

  public void setGvrEntc(Short gvrEntc) {
    this.gvrEntc = gvrEntc;
  }

  public Short getLaPayeeState() {
    return laPayeeState;
  }

  public void setLaPayeeState(Short laPayeeState) {
    this.laPayeeState = laPayeeState;
  }

  @Override
  public boolean equals(Object o) {
    return EqualsBuilder.reflectionEquals(this, o);
  }

  @Override
  public int hashCode() {
    return HashCodeBuilder.reflectionHashCode(this,
      "otherChildrenInPlacementHomes", "otherAdultsInPlacementHomes",
      "placementFacilityTypeHistory", "placementHomeNotes", "placementHomeProfiles");
  }

  @Override
  public Serializable getPrimaryKey() {
    return getIdentifier();
  }
}
