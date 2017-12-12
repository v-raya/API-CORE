package gov.ca.cwds.data.legacy.cms.entity;

import gov.ca.cwds.data.legacy.cms.CmsPersistentObject;
import gov.ca.cwds.data.legacy.cms.entity.syscodes.County;
import java.io.Serializable;
import java.time.LocalDate;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

/**
 * Entity representing STAFF_PERSON_CASE_LOAD from legacy system
 * Identifies a particular STAFF PERSON who works on a particular CASE LOAD.
 *
 * @author CWDS TPT-3 Team
 */
@Entity
@IdClass(StaffPersonCaseLoadPK.class)
@Table(name = "STFCSLDT")
public class StaffPersonCaseLoad extends CmsPersistentObject {

  private static final long serialVersionUID = -4505513678380455273L;

  @Id
  @Column(name = "THIRD_ID", nullable = false, length = 10)
  private String thirdId;

  @Id
  @Column(name = "FKCASE_LDT", nullable = false, length = 10)
  private String fkcaseLdt;

  @Id
  @Column(name = "FKSTFPERST", nullable = false, length = 3)
  private String fkstfperst;

  @Column(name = "START_DT", nullable = false)
  private LocalDate startDate;

  @Column(name = "END_DT")
  private LocalDate endDate;

  @NotFound(action = NotFoundAction.IGNORE)
  @ManyToOne(fetch = FetchType.LAZY)
  @Fetch(FetchMode.SELECT)
  @JoinColumn(name = "CNTY_SPFCD", referencedColumnName = "SYS_ID", insertable = false, updatable = false)
  private County county;

  @NotFound(action = NotFoundAction.IGNORE)
  @ManyToOne
  @Fetch(FetchMode.SELECT)
  @JoinColumn(name = "FKSTFPERST", referencedColumnName = "IDENTIFIER")
  private StaffPerson staffPerson;

  @Override
  public Serializable getPrimaryKey() {
    return new StaffPersonCaseLoadPK(thirdId, fkcaseLdt, fkstfperst);
  }

  public String getThirdId() {
    return thirdId;
  }

  public void setThirdId(String thirdId) {
    this.thirdId = thirdId;
  }

  public LocalDate getEndDate() {
    return endDate;
  }

  public void setEndDate(LocalDate endDt) {
    this.endDate = endDt;
  }

  public LocalDate getStartDate() {
    return startDate;
  }

  public void setStartDate(LocalDate startDt) {
    this.startDate = startDt;
  }

  public String getFkcaseLdt() {
    return fkcaseLdt;
  }

  public void setFkcaseLdt(String fkcaseLdt) {
    this.fkcaseLdt = fkcaseLdt;
  }

  public String getFkstfperst() {
    return fkstfperst;
  }

  public void setFkstfperst(String fkstfperst) {
    this.fkstfperst = fkstfperst;
  }

  public County getCounty() {
    return county;
  }

  public void setCounty(County county) {
    this.county = county;
  }

  public StaffPerson getStaffPerson() {
    return staffPerson;
  }

  public void setStaffPerson(StaffPerson staffPerson) {
    this.staffPerson = staffPerson;
  }

}
