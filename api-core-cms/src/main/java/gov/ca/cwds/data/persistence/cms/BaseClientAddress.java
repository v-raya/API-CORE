package gov.ca.cwds.data.persistence.cms;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

import org.hibernate.annotations.ColumnTransformer;
import org.hibernate.annotations.Type;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import gov.ca.cwds.data.CmsSystemCodeDeserializer;
import gov.ca.cwds.data.SystemCodeSerializer;

/**
 * Base class for regular and replicated versions of table {@code CL_ADDRT}.
 * 
 * @author CWDS API Team
 */
@MappedSuperclass
public abstract class BaseClientAddress extends CmsPersistentObject {

  private static final long serialVersionUID = 1L;

  @Id
  @Column(name = "IDENTIFIER", nullable = false, length = CMS_ID_LEN)
  protected String id;

  @SystemCodeSerializer(logical = true, description = true)
  @JsonDeserialize(using = CmsSystemCodeDeserializer.class)
  @Type(type = "short")
  @Column(name = "ADDR_TPC", nullable = false)
  protected Short addressType;

  @Column(name = "BK_INMT_ID", nullable = false, length = CMS_ID_LEN)
  @ColumnTransformer(read = "trim(BK_INMT_ID)")
  protected String bkInmtId;

  @Type(type = "date")
  @Column(name = "EFF_END_DT", nullable = true)
  protected Date effEndDt;

  @Type(type = "date")
  @Column(name = "EFF_STRTDT", nullable = true)
  protected Date effStartDt;

  @Column(name = "FKADDRS_T", nullable = false, length = CMS_ID_LEN)
  protected String fkAddress;

  @Column(name = "FKCLIENT_T", nullable = false, length = CMS_ID_LEN)
  protected String fkClient;

  @Column(name = "HOMLES_IND", nullable = false)
  protected String homelessInd;

  @Column(name = "FKREFERL_T", nullable = true, length = CMS_ID_LEN)
  @ColumnTransformer(read = "trim(FKREFERL_T)")
  protected String fkReferral;

  /**
   * Default constructor.
   */
  public BaseClientAddress() {
    super();
  }

  /**
   * Parent constructor.
   * 
   * @param lastUpdatedId staff id who last updated this record
   */
  public BaseClientAddress(String lastUpdatedId) {
    super(lastUpdatedId);
  }

  /**
   * constructor
   * 
   * @param lastUpdatedId staff id who last updated this record
   * @param lastUpdatedTime the last updated time
   */
  public BaseClientAddress(String lastUpdatedId, Date lastUpdatedTime) {
    super(lastUpdatedId, lastUpdatedTime);
  }

  // ==================
  // IDENTIFIERS:
  // ==================

  /**
   * {@inheritDoc}
   * 
   * @see gov.ca.cwds.data.persistence.PersistentObject#getPrimaryKey()
   */
  @Override
  public String getPrimaryKey() {
    return getId();
  }

  // ==================
  // ACCESSORS:
  // ==================

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public Short getAddressType() {
    return addressType;
  }

  public void setAddressType(Short addressType) {
    this.addressType = addressType;
  }

  public String getBkInmtId() {
    return bkInmtId;
  }

  public void setBkInmtId(String bkInmtId) {
    this.bkInmtId = bkInmtId;
  }

  public String getFkAddress() {
    return fkAddress;
  }

  public void setFkAddress(String fkAddress) {
    this.fkAddress = fkAddress;
  }

  public String getFkClient() {
    return fkClient;
  }

  public void setFkClient(String fkClient) {
    this.fkClient = fkClient;
  }

  public String getHomelessInd() {
    return homelessInd;
  }

  public void setHomelessInd(String homelessInd) {
    this.homelessInd = homelessInd;
  }

  public String getFkReferral() {
    return fkReferral;
  }

  public void setFkReferral(String fkReferral) {
    this.fkReferral = fkReferral;
  }

  public Date getEffEndDt() {
    return effEndDt;
  }

  public void setEffEndDt(Date effEndDt) {
    this.effEndDt = effEndDt;
  }

  public Date getEffStartDt() {
    return effStartDt;
  }

  public void setEffStartDt(Date effStartDt) {
    this.effStartDt = effStartDt;
  }

}
