package gov.ca.cwds.data.legacy.cms.entity;

import gov.ca.cwds.data.legacy.cms.entity.enums.IndividualType;
import gov.ca.cwds.data.legacy.cms.entity.syscodes.County;
import gov.ca.cwds.data.legacy.cms.entity.syscodes.ServiceContactType;
import gov.ca.cwds.data.persistence.PersistentObject;
import java.io.Serializable;
import java.time.LocalDate;
import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.NamedQuery;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

/**
 * @author CWDS TPT-3 Team
 *     <p>The identification of a specific CLIENT (who may be the CASE child), REPORTER, COLLATERAL
 *     INDIVIDUAL, SERVICE PROVIDER, ATTORNEY, and SUBSTITUTE CARE PROVIDER as the Recipient of a
 *     service which contributes to the health and welfare of a child. There must be at least one
 *     participant identified for each DELIVERED SERVICE. In addition, all non-CLIENT participants
 *     can only be involved in the 'Contact' (but not the Visit or True Service) type of DELIVERED
 *     SERVICEs.
 */
@Entity
@Table(name = "IDV_SVCT")
@NamedQuery(
  name = DeliveredService.FIND_BY_CLIENT,
  query = "FROM DeliveredService WHERE individualId = :clientId"
)
public class DeliveredService implements PersistentObject {

  public static final String FIND_BY_CLIENT = "DeliveredService.findByClient";

  @NotFound(action = NotFoundAction.IGNORE)
  @ManyToOne(fetch = FetchType.LAZY)
  @Fetch(FetchMode.SELECT)
  @JoinColumn(name = "CNTY_SPFCD", referencedColumnName = "SYS_ID")
  private County countySpecificCode;

  @Column(name = "DEL_IDV_CD")
  @Convert(converter = IndividualType.IndividualTypeConverter.class)
  private IndividualType individualType;

  @Column(name = "DEL_IDV_ID")
  private String individualId;

  @Column(name = "START_DT")
  private LocalDate startDate;

  @Column(name = "END_DT")
  private LocalDate endDate;

  @Id
  @Column(name = "FKDL_SVC_T")
  private String primaryDeliveredServiceId;

  @NotFound(action = NotFoundAction.IGNORE)
  @ManyToOne(fetch = FetchType.LAZY)
  @Fetch(FetchMode.SELECT)
  @JoinColumn(name = "SVC_CNTC", referencedColumnName = "SYS_ID")
  private ServiceContactType serviceContactType;

  public County getCountySpecificCode() {
    return countySpecificCode;
  }

  public void setCountySpecificCode(County countySpecificCode) {
    this.countySpecificCode = countySpecificCode;
  }

  public IndividualType getIndividualType() {
    return individualType;
  }

  public void setIndividualType(IndividualType individualType) {
    this.individualType = individualType;
  }

  public String getIndividualId() {
    return individualId;
  }

  public void setIndividualId(String individualId) {
    this.individualId = individualId;
  }

  public LocalDate getStartDate() {
    return startDate;
  }

  public void setStartDate(LocalDate startDate) {
    this.startDate = startDate;
  }

  public LocalDate getEndDate() {
    return endDate;
  }

  public void setEndDate(LocalDate endDate) {
    this.endDate = endDate;
  }

  public String getPrimaryDeliveredServiceId() {
    return primaryDeliveredServiceId;
  }

  public void setPrimaryDeliveredServiceId(String primaryDeliveredServiceId) {
    this.primaryDeliveredServiceId = primaryDeliveredServiceId;
  }

  public ServiceContactType getServiceContactType() {
    return serviceContactType;
  }

  public void setServiceContactType(
      ServiceContactType serviceContactType) {
    this.serviceContactType = serviceContactType;
  }

  @Override
  public Serializable getPrimaryKey() {
    return getPrimaryDeliveredServiceId();
  }
}
