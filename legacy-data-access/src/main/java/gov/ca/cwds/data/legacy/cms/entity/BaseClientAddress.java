package gov.ca.cwds.data.legacy.cms.entity;

import gov.ca.cwds.data.legacy.cms.CmsPersistentObject;
import java.io.Serializable;
import java.time.LocalDate;
import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import org.hibernate.annotations.ColumnTransformer;

/**
 * @author CWDS TPT-3 Team
 */

@MappedSuperclass
@SuppressWarnings("squid:S3437") //LocalDate is serializable
public abstract class BaseClientAddress extends CmsPersistentObject {

  private static final long serialVersionUID = -3656767136797373958L;

  @Id
  @Column(name = "IDENTIFIER", nullable = false, length = 10)
  private String id;

  @Column(name = "ADDR_TPC", nullable = false)
  private short addressType;

  @Column(name = "BK_INMT_ID", nullable = false, length = 10)
  @ColumnTransformer(read = "trim(BK_INMT_ID)")
  private String bookingOrInmateId;

  @Column(name = "EFF_END_DT", nullable = true)
  private LocalDate effectiveEndDate;

  @Column(name = "EFF_STRTDT", nullable = true)
  private LocalDate effectiveStartDate;

  @Column(name = "HOMLES_IND", nullable = false, length = 1)
  private String homelessIndicator;

  @Column(name = "FKADDRS_T", nullable = false, length = 10)
  private String fkAddress;

  @Column(name = "FKCLIENT_T", nullable = false, length = 10)
  private String fkClient;

  @Column(name = "FKREFERL_T", nullable = true, length = 10)
  @ColumnTransformer(read = "trim(FKREFERL_T)")
  private String fkReferral;

  @Override
  public Serializable getPrimaryKey() {
    return getId();
  }

  public String getId() {
    return id;
  }

  public void setId(String identifier) {
    this.id = identifier;
  }

  public short getAddressType() {
    return addressType;
  }

  public void setAddressType(short addrTpc) {
    this.addressType = addrTpc;
  }

  public String getBookingOrInmateId() {
    return bookingOrInmateId;
  }

  public void setBookingOrInmateId(String bkInmtId) {
    this.bookingOrInmateId = bkInmtId;
  }

  public LocalDate getEffectiveEndDate() {
    return effectiveEndDate;
  }

  public void setEffectiveEndDate(LocalDate effEndDt) {
    this.effectiveEndDate = effEndDt;
  }

  public LocalDate getEffectiveStartDate() {
    return effectiveStartDate;
  }

  public void setEffectiveStartDate(LocalDate effStrtdt) {
    this.effectiveStartDate = effStrtdt;
  }

  public String getFkAddress() {
    return fkAddress;
  }

  public void setFkAddress(String fkaddrsT) {
    this.fkAddress = fkaddrsT;
  }

  public String getFkClient() {
    return fkClient;
  }

  public void setFkClient(String fkclientT) {
    this.fkClient = fkclientT;
  }

  public String getHomelessIndicator() {
    return homelessIndicator;
  }

  public void setHomelessIndicator(String homlesInd) {
    this.homelessIndicator = homlesInd;
  }

  public String getFkReferral() {
    return fkReferral;
  }

  public void setFkReferral(String fkreferlT) {
    this.fkReferral = fkreferlT;
  }

}
