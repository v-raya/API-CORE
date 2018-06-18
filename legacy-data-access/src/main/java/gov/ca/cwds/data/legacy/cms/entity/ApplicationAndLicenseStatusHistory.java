package gov.ca.cwds.data.legacy.cms.entity;

import java.io.Serializable;
import java.time.LocalDate;
import javax.persistence.*;
import gov.ca.cwds.data.legacy.cms.CmsPersistentObject;

@Entity
@Table(name = "AL_HSTAT")
public class ApplicationAndLicenseStatusHistory extends CmsPersistentObject {
  @Id
  @Column(name = "IDENTIFIER", nullable = false, length = 10)
  private String identifier;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "FKPLC_HM_T", referencedColumnName = "IDENTIFIER")
  private PlacementHome placementHome;

  /**
   * APP_OR_LICENSE_STATUS_CODE -
   * This code defines each type of status type for which APPL_AND_LICENSE_STAT_HISTORY is maintained.
   * (e.g. Application Status Type = A, License Status Type = L, Certified Pending Licensure Type = C).
   */
  @Column(name = "AP_LIC_CD", nullable = false, length = 1)
  private String applicationOrLicenseStatusCode;

  /**
   * APP_OR_LICENSE_STATUS_TYPE - The system generated number assigned to each
   * APPLICATION STATUS TYPE or LICENSE STATUS TYPE for a specific PLACEMENT HOME.
   */
  @Column(name = "AP_LIC_STC", nullable = false)
  private Short applicationOrLicenseStatusType; //TODO sys code????

  /**
   * CLOSED_IND - This indicator tells the system if this row either closed the County License Case,
   * or was the current row in the other grid when the County License Case closed. (Y/N)
   */
  @Column(name = "CLOSED_IND", nullable = false, length = 1)
  private String closedIndicator;

  /**
   * END_DATE - The date on which this application status type or license status type becomes inactive.
   */
  @Column(name = "END_DT")
  private LocalDate endDt;

  /**
   * START_DATE - The date on which this application status type or license status type becomes active.
   */
  @Column(name = "START_DT", nullable = false)
  private LocalDate startDt;
  public String getIdentifier() {
    return identifier;
  }

  public void setIdentifier(String identifier) {
    this.identifier = identifier;
  }

  public PlacementHome getPlacementHome() {
    return placementHome;
  }

  public void setPlacementHome(PlacementHome placementHome) {
    this.placementHome = placementHome;
  }

  public String getApplicationOrLicenseStatusCode() {
    return applicationOrLicenseStatusCode;
  }

  public void setApplicationOrLicenseStatusCode(String applicationOrLicenseStatusCode) {
    this.applicationOrLicenseStatusCode = applicationOrLicenseStatusCode;
  }

  public Short getApplicationOrLicenseStatusType() {
    return applicationOrLicenseStatusType;
  }

  public void setApplicationOrLicenseStatusType(Short applicationOrLicenseStatusType) {
    this.applicationOrLicenseStatusType = applicationOrLicenseStatusType;
  }

  public String getClosedIndicator() {
    return closedIndicator;
  }

  public void setClosedIndicator(String closedIndicator) {
    this.closedIndicator = closedIndicator;
  }

  public LocalDate getEndDt() {
    return endDt;
  }

  public void setEndDt(LocalDate endDt) {
    this.endDt = endDt;
  }

  public LocalDate getStartDt() {
    return startDt;
  }

  public void setStartDt(LocalDate startDt) {
    this.startDt = startDt;
  }

  @Override
  public Serializable getPrimaryKey() {
    return getIdentifier();
  }
}
