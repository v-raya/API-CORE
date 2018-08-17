package gov.ca.cwds.data.legacy.cms.entity;

import java.io.Serializable;
import java.time.LocalDate;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import org.hibernate.annotations.NamedQuery;
import org.hibernate.annotations.Type;

import gov.ca.cwds.data.legacy.cms.CmsPersistentObject;

/**
 * Special projects CMS entity.
 *
 * @author CWDS TPT Team
 */
@Entity
@Table(name = "SPRJ_RFT")
@NamedQuery(
  name = 
  "gov.ca.cwds.data.legacy.cms.entity.SpecialProjectReferral.findByReferralIdAndSpecialProjectId",
  query = "FROM SpecialProjectReferral "
  + "WHERE FKREFERL_T = :referralId AND FKSPC_PRJT = :specialProjectId")

public class SpecialProjectReferral extends CmsPersistentObject {

  private static final long serialVersionUID = 241170224860954003L;

  @Id
  @Column(name = "THIRD_ID", nullable = false, length = CMS_ID_LEN)
  private String id;

  @Column(name = "FKREFERL_T", nullable = false, length = CMS_ID_LEN)
  private String referralId;

  @Column(name = "FKSPC_PRJT", nullable = false, length = CMS_ID_LEN)
  private String specialProjectId;

  @Column(name = "CNTY_SPFCD", nullable = false, length = 2)
  private String countySpecificCode;

  @Column(name = "SFSURB_IND", nullable = false, length = 1)
  @Type(type = "yes_no")
  private Boolean safelySurrenderedBabiesIndicator;

  @Column(name = "PART_STRDT", nullable = false, length = 10)
  private LocalDate participationStartDate;

  @Column(name = "PART_ENDT", nullable = true, length = 10)
  private LocalDate participationEndDate;

  /**
   * No-argument constructor.
   */
  public SpecialProjectReferral() {
    super();
  }

  /**
   * Constructor
   * 
   * @param countySpecificCode - county specific code
   * @param referralId - referral ID
   * @param specialProjectId - special project ID
   * @param participationEndDate - participation end date
   * @param participationStartDate - participation start date
   * @param safelySurrenderedBabiesIndicator - safely surrendered babies indicator
   * @param id - third ID
   * 
   */
  public SpecialProjectReferral(String countySpecificCode, String referralId, String specialProjectId,
      LocalDate participationEndDate, LocalDate participationStartDate, Boolean safelySurrenderedBabiesIndicator,
      String id) {
    super();
    this.countySpecificCode = countySpecificCode;
    this.referralId = referralId;
    this.specialProjectId = specialProjectId;
    this.participationEndDate = participationEndDate;
    this.participationStartDate = participationStartDate;
    this.safelySurrenderedBabiesIndicator = safelySurrenderedBabiesIndicator;
    this.id = id;
  }
  
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

  public String getReferralId() {
    return referralId;
  }

  public void setReferralId(String referralId) {
    this.referralId = referralId;
  }

  public String getSpecialProjectId() {
    return specialProjectId;
  }

  public void setSpecialProjectId(String specialProjectId) {
    this.specialProjectId = specialProjectId;
  }

  public String getCountySpecificCode() {
    return countySpecificCode;
  }

  public void setCountySpecificCode(String countySpecificCode) {
    this.countySpecificCode = countySpecificCode;
  }

  public Boolean getSsbIndicator() {
    return safelySurrenderedBabiesIndicator;
  }

  public void setSsbIndicator(Boolean safelySurrenderedBabiesIndicator) {
    this.safelySurrenderedBabiesIndicator = safelySurrenderedBabiesIndicator;
  }

  public LocalDate getPartStartDate() {
    return participationStartDate;
  }

  public void setPartStartDate(LocalDate participationStartDate) {
    this.participationStartDate = participationStartDate;
  }

  public LocalDate getPartEndDate() {
    return participationEndDate;
  }

  public void setPartEndDate(LocalDate participationEndDate) {
    this.participationEndDate = participationEndDate;
  }
}
