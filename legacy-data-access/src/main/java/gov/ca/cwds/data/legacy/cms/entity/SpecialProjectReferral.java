package gov.ca.cwds.data.legacy.cms.entity;

import java.io.Serializable;
import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.Type;

import gov.ca.cwds.data.legacy.cms.CmsPersistentObject;

/**
 * Special projects CMS entity.
 *
 * @author CWDS TPT Team
 */
@Entity
@Table(name = "SPC_PRJT")
public class SpecialProjectReferral extends CmsPersistentObject {

  private static final long serialVersionUID = 241170224860954003L;

  @Id
  @Column(name = "THIRD_ID", length = CMS_ID_LEN)
  private String id;

  @Column(name = "FKREFERL_T", length = CMS_ID_LEN)
  private String referralId;

  @Column(name = "FKSPC_PRJT", length = CMS_ID_LEN)
  private String specialProjectId;

  @Column(name = "CNTY_SPFCD")
  private String countySpecificCode;

  @Column(name = "SFSURB_IND")
  @Type(type = "yes_no")
  private Boolean ssbIndicator;

  @Column(name = "PART_STRDT")
  private LocalDate partStartDate;

  @Column(name = "PART_ENDT")
  private LocalDate partEndDate;

  /**
   * No-argument constructor.
   */
  public SpecialProjectReferral() {
    super();
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
    return ssbIndicator;
  }

  public void setSsbIndicator(Boolean ssbIndicator) {
    this.ssbIndicator = ssbIndicator;
  }

  public LocalDate getPartStartDate() {
    return partStartDate;
  }

  public void setPartStartDate(LocalDate partStartDate) {
    this.partStartDate = partStartDate;
  }

  public LocalDate getPartEndDate() {
    return partEndDate;
  }

  public void setPartEndDate(LocalDate partEndDate) {
    this.partEndDate = partEndDate;
  }
}
