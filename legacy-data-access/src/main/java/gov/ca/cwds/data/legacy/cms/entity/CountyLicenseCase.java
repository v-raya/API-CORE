package gov.ca.cwds.data.legacy.cms.entity;

import gov.ca.cwds.data.persistence.PersistentObject;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import javax.persistence.Transient;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

/**
 * @author CWDS CALS API Team
 */
@Entity
@Table(name = "CNTY_CST")
public class CountyLicenseCase implements PersistentObject {

  private static final long serialVersionUID = 5487129105879851952L;

  private StaffPerson staffPerson;
  private LocalDate annCmpDt;
  private LocalDate annDueDt;
  private String fireInd;
  private LocalDate fireRDt;
  private Short ffhType;
  private String identifier;
  private String lstUpdId;
  private LocalDateTime lstUpdTs;
  private Short licAgeFr;
  private Short licAgeTo;
  private String licGndr;
  private String trngPlan;
  private String prtyInfo;
  private String trngCmplt;
  private LocalDate trngDt;
  private String cntySpfcd;
  private Integer clcApvc;

  private List<LicensingVisit> licensingVisits;

  private List<LicensingIssue> licensingIssues;

  @NotFound(action = NotFoundAction.IGNORE)
  @OneToMany
  @JoinColumn(name = "FKCNTY_CST", referencedColumnName = "IDENTIFIER")
  @OrderBy("visitDate DESC")
  public List<LicensingVisit> getLicensingVisits() {
    return licensingVisits;
  }

  public void setLicensingVisits(List<LicensingVisit> licensingVisits) {
    this.licensingVisits = licensingVisits;
  }

  @NotFound(action = NotFoundAction.IGNORE)
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "FKSTFPERST", referencedColumnName = "IDENTIFIER")
  public StaffPerson getStaffPerson() {
    return staffPerson;
  }

  public void setStaffPerson(StaffPerson fkstfperst) {
    this.staffPerson = fkstfperst;
  }

  @NotFound(action = NotFoundAction.IGNORE)
  @OneToMany
  @JoinColumn(name = "FKCNTY_CST", referencedColumnName = "IDENTIFIER")
  public List<LicensingIssue> getLicensingIssues() {
    return licensingIssues;
  }

  public void setLicensingIssues(
    List<LicensingIssue> licensingIssues) {
    this.licensingIssues = licensingIssues;
  }

  @Basic
  @Column(name = "ANN_CMP_DT", nullable = true)
  public LocalDate getAnnCmpDt() {
    return annCmpDt;
  }

  public void setAnnCmpDt(LocalDate annCmpDt) {
    this.annCmpDt = annCmpDt;
  }

  @Basic
  @Column(name = "ANN_DUE_DT", nullable = true)
  public LocalDate getAnnDueDt() {
    return annDueDt;
  }

  public void setAnnDueDt(LocalDate annDueDt) {
    this.annDueDt = annDueDt;
  }

  @Basic
  @Column(name = "FIRE_IND", nullable = false, length = 1)
  public String getFireInd() {
    return fireInd;
  }

  public void setFireInd(String fireInd) {
    this.fireInd = fireInd;
  }

  @Basic
  @Column(name = "FIRE_R_DT", nullable = true)
  public LocalDate getFireRDt() {
    return fireRDt;
  }

  public void setFireRDt(LocalDate fireRDt) {
    this.fireRDt = fireRDt;
  }

  @Basic
  @Column(name = "FFH_TYPE", nullable = false)
  public Short getFfhType() {
    return ffhType;
  }

  public void setFfhType(Short ffhType) {
    this.ffhType = ffhType;
  }

  @Id
  @Column(name = "IDENTIFIER", nullable = false, length = 10)
  public String getIdentifier() {
    return identifier;
  }

  public void setIdentifier(String identifier) {
    this.identifier = identifier;
  }

  @Basic
  @Column(name = "LST_UPD_ID", nullable = false, length = 3)
  public String getLstUpdId() {
    return lstUpdId;
  }

  public void setLstUpdId(String lstUpdId) {
    this.lstUpdId = lstUpdId;
  }

  @Basic
  @Column(name = "LST_UPD_TS", nullable = false)
  public LocalDateTime getLstUpdTs() {
    return lstUpdTs;
  }

  public void setLstUpdTs(LocalDateTime lstUpdTs) {
    this.lstUpdTs = lstUpdTs;
  }

  @Basic
  @Column(name = "LIC_AGE_FR", nullable = false)
  public Short getLicAgeFr() {
    return licAgeFr;
  }

  public void setLicAgeFr(Short licAgeFr) {
    this.licAgeFr = licAgeFr;
  }

  @Basic
  @Column(name = "LIC_AGE_TO", nullable = false)
  public Short getLicAgeTo() {
    return licAgeTo;
  }

  public void setLicAgeTo(Short licAgeTo) {
    this.licAgeTo = licAgeTo;
  }

  @Basic
  @Column(name = "LIC_GNDR", nullable = false, length = 1)
  public String getLicGndr() {
    return licGndr;
  }

  public void setLicGndr(String licGndr) {
    this.licGndr = licGndr;
  }

  @Basic
  @Column(name = "TRNG_PLAN", nullable = false, length = 30)
  public String getTrngPlan() {
    return trngPlan;
  }

  public void setTrngPlan(String trngPlan) {
    this.trngPlan = trngPlan;
  }

  @Basic
  @Column(name = "PRTY_INFO", nullable = false, length = 254)
  public String getPrtyInfo() {
    return prtyInfo;
  }

  public void setPrtyInfo(String prtyInfo) {
    this.prtyInfo = prtyInfo;
  }

  @Basic
  @Column(name = "TRNG_CMPLT", nullable = false, length = 254)
  public String getTrngCmplt() {
    return trngCmplt;
  }

  public void setTrngCmplt(String trngCmplt) {
    this.trngCmplt = trngCmplt;
  }

  @Basic
  @Column(name = "TRNG_DT", nullable = true)
  public LocalDate getTrngDt() {
    return trngDt;
  }

  public void setTrngDt(LocalDate trngDt) {
    this.trngDt = trngDt;
  }

  @Basic
  @Column(name = "CNTY_SPFCD", nullable = false, length = 2)
  public String getCntySpfcd() {
    return cntySpfcd;
  }

  public void setCntySpfcd(String cntySpfcd) {
    this.cntySpfcd = cntySpfcd;
  }

  @Column(name = "CLC_APVC")
  public Integer getClcApvc() {
    return clcApvc;
  }

  public void setClcApvc(Integer clcApvc) {
    this.clcApvc = clcApvc;
  }

  @Override
  public boolean equals(Object o) {
    return EqualsBuilder.reflectionEquals(this, o);
  }

  @Override
  public int hashCode() {
    return HashCodeBuilder.reflectionHashCode(this);
  }

  @Override
  @Transient
  public Serializable getPrimaryKey() {
    return getIdentifier();
  }
}
