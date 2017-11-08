package gov.ca.cwds.data.legacy.cms.entity;

import gov.ca.cwds.data.persistence.PersistentObject;
import java.io.Serializable;
import java.time.LocalDateTime;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

/**
 * @author CWDS CALS API Team
 *
 * This entity is used to trigger the external interfaces. When changes
 * occur to a database entity, which are of interest to one of the external interface systems, a row
 * is written to this entity.
 */
@Entity
@Table(name = "EXTINF_T")
@IdClass(ExternalInterfacePK.class)
@SuppressWarnings({"squid:S3437"}) //LocalDate is serializable
public class ExternalInterface implements PersistentObject {

  private static final long serialVersionUID = 3221853516853019495L;

  /**
   * INTERFACE_SEQUENCE_NUMBER - The number assigned to each row within a specific save to database.
   * This works with the Interface Timestamp to uniquely identify each row needed for external
   * interface processing.
   */
  @Id
  @Column(name = "SEQ_NO", nullable = false)
  private int seqNo;

  /**
   * INTERFACE_TIMESTAMP - The Timestamp of when the specific row being processed was saved to
   * database. This works with the Interface Sequence Number to uniquely identify each row needed
   * for external interface processing.
   */
  @Id
  @Column(name = "SUBMTL_TS", nullable = false)
  private LocalDateTime submtlTs;

  /**
   * INTERFACE_TABLE_NAME - Contains the name of the entity being processed.
   */
  @Basic
  @Column(name = "TABLE_NAME", nullable = false, length = 8)
  private String tableName;

  /**
   * INTERFACE_OPERATION_TYPE - Used to identify if the entity that is creating the EXTERNAL
   * INTERFACE row has an insert (N), change (C), or delete (D).
   */
  @Basic
  @Column(name = "OPER_TYPE", nullable = false, length = 1)
  private String operType;

  /**
   * INTERFACE_KEY_1 - This attribute contains the primary key for the entity being processed. Key 1
   * thru Key 8 are used as necessary to identify the entity.
   */
  @Basic
  @Column(name = "PRM_KEY_1", nullable = false, length = 10)
  private String prmKey1;

  /**
   * INTERFACE_KEY_2 - This attribute contains the primary key for the entity being processed. Key 1
   * thru Key 8 are used as necessary to identify the entity.
   */
  @Basic
  @Column(name = "PRM_KEY_2", nullable = false, length = 10)
  private String prmKey2;

  /**
   * INTERFACE_KEY_3 - This attribute contains the primary key for the entity being processed. Key 1
   * thru Key 8 are used as necessary to identify the entity.
   */
  @Basic
  @Column(name = "PRM_KEY_3", nullable = false, length = 10)
  private String prmKey3;

  /**
   * INTERFACE_KEY_4 - This attribute contains the primary key for the entity being processed. Key 1
   * thru Key 8 are used as necessary to identify the entity.
   */
  @Basic
  @Column(name = "PRM_KEY_4", nullable = false, length = 10)
  private String prmKey4;

  /**
   * INTERFACE_KEY_5 - This attribute contains the primary key for the entity being processed. Key 1
   * thru Key 8 are used as necessary to identify the entity.
   */
  @Basic
  @Column(name = "PRM_KEY_5", nullable = false, length = 10)
  private String prmKey5;

  /**
   * INTERFACE_KEY_6 - This attribute contains the primary key for the entity being processed. Key 1
   * thru Key 8 are used as necessary to identify the entity.
   */
  @Basic
  @Column(name = "PRM_KEY_6", nullable = false, length = 10)
  private String prmKey6;

  /**
   * INTERFACE_KEY_7 - This attribute contains the primary key for the entity being processed. Key 1
   * thru Key 8 are used as necessary to identify the entity.
   */
  @Basic
  @Column(name = "PRM_KEY_7", nullable = false, length = 10)
  private String prmKey7;

  /**
   * INTERFACE_KEY_8 - This attribute contains the primary key for the entity being processed. Key 1
   * thru Key 8 are used as necessary to identify the entity.
   */
  @Basic
  @Column(name = "PRM_KEY_8", nullable = false, length = 10)
  private String prmKey8;

  /**
   * INTERFACE_START_DATE - This attribute is populated when a STATE ID NUMBER row is inserted,
   * modified or deleted.
   */
  @Basic
  @Column(name = "START_DT", nullable = false, length = 10)
  private String startDt;

  /**
   * INTERFACE_ASSISTANT_UNIT_CODE - This attribute is populated when a STATE ID NUMBER row is
   * inserted, modified or deleted.
   */
  @Basic
  @Column(name = "AST_UNT_CD", nullable = false, length = 1)
  private String astUntCd;

  /**
   * INTERFACE_PERSON_NUMBER - This attribute is populated when a STATE ID NUMBER row is inserted,
   * modified or deleted.
   */
  @Basic
  @Column(name = "PERSON_NO", nullable = false, length = 2)
  private String personNo;

  /**
   * INTERFACE_SERIAL_NUMBER - This attribute is populated when a STATE ID NUMBER row is inserted,
   * modified or deleted.
   */
  @Basic
  @Column(name = "SERIAL_NO", nullable = false, length = 7)
  private String serialNo;

  /**
   * INTERFACE_AID_CODE - This attribute is populated when a STATE ID AID CODE row is inserted,
   * modified or deleted.
   */
  @Basic
  @Column(name = "AID_TPC", nullable = false)
  private Short aidTpc;

  /**
   * INTERFACE_GOVERNMENT_ENTITY_CODE - This attribute is populated when the Government Entity Code
   * is needed for interface processing and the trigger entity being inserted, modified or deleted
   * contains this attribute.
   */
  @Basic
  @Column(name = "GVR_ENTC", nullable = false)
  private Short gvrEntc;

  /**
   * INTERFACE_LICENSE_NUMBER - This attribute is populated when a PLACEMENT HOME row is inserted,
   * modified or deleted.
   */
  @Basic
  @Column(name = "LICENSE_NO", nullable = false, length = 9)
  private String licenseNo;

  /**
   * INTERFACE_CLEARANCE_RESPNSE_DATE - Attribute no longer in use.
   */
  @Basic
  @Column(name = "RESPONS_DT", nullable = false, length = 10)
  private String responsDt;

  /**
   * INTERFACE_RESPONSE_RECEIVED_DATE - Attribute no longer in use.
   */
  @Basic
  @Column(name = "RECEIVE_DT", nullable = false, length = 10)
  private String receiveDt;

  /**
   * INTERFACE_RAP_IDENTIFIER - Attribute no longer in use.
   */
  @Basic
  @Column(name = "RAP_ID", nullable = false, length = 10)
  private String rapId;

  /**
   * INTERFACE_FBI_INDICATOR - Attribute no longer in use.
   */
  @Basic
  @Column(name = "FBI_IND", nullable = false, length = 1)
  private String fbiInd;

  /**
   * INTERFACE_CLEARANCE_RESPNSE_TYPE - Attribute no longer in use.
   */
  @Basic
  @Column(name = "CRSP_TPC", nullable = false)
  private Short crspTpc;

  /**
   * INTERFACE_OTHER_DATA - This attribute contains non-key data that is needed to fully process the
   * interface record.
   */
  @Basic
  @Column(name = "OTHER_DATA", nullable = false, length = 254)
  private String otherData;

  /**
   * LOGON_USERID - This attribute contains the id of the logged on user who performed the action on
   * the identified external interface table.
   */
  @Id
  @Column(name = "L_USERID", nullable = false, length = 8)
  private String lUserid;

  public int getSeqNo() {
    return seqNo;
  }

  public void setSeqNo(int seqNo) {
    this.seqNo = seqNo;
  }

  public LocalDateTime getSubmtlTs() {
    return submtlTs;
  }

  public void setSubmtlTs(LocalDateTime submtlTs) {
    this.submtlTs = submtlTs;
  }

  public String getTableName() {
    return tableName;
  }

  public void setTableName(String tableName) {
    this.tableName = tableName;
  }

  public String getOperType() {
    return operType;
  }

  public void setOperType(String operType) {
    this.operType = operType;
  }

  public String getPrmKey1() {
    return prmKey1;
  }

  public void setPrmKey1(String prmKey1) {
    this.prmKey1 = prmKey1;
  }

  public String getPrmKey2() {
    return prmKey2;
  }

  public void setPrmKey2(String prmKey2) {
    this.prmKey2 = prmKey2;
  }

  public String getPrmKey3() {
    return prmKey3;
  }

  public void setPrmKey3(String prmKey3) {
    this.prmKey3 = prmKey3;
  }

  public String getPrmKey4() {
    return prmKey4;
  }

  public void setPrmKey4(String prmKey4) {
    this.prmKey4 = prmKey4;
  }

  public String getPrmKey5() {
    return prmKey5;
  }

  public void setPrmKey5(String prmKey5) {
    this.prmKey5 = prmKey5;
  }

  public String getPrmKey6() {
    return prmKey6;
  }

  public void setPrmKey6(String prmKey6) {
    this.prmKey6 = prmKey6;
  }

  public String getPrmKey7() {
    return prmKey7;
  }

  public void setPrmKey7(String prmKey7) {
    this.prmKey7 = prmKey7;
  }

  public String getPrmKey8() {
    return prmKey8;
  }

  public void setPrmKey8(String prmKey8) {
    this.prmKey8 = prmKey8;
  }

  public String getStartDt() {
    return startDt;
  }

  public void setStartDt(String startDt) {
    this.startDt = startDt;
  }

  public String getAstUntCd() {
    return astUntCd;
  }

  public void setAstUntCd(String astUntCd) {
    this.astUntCd = astUntCd;
  }

  public String getPersonNo() {
    return personNo;
  }

  public void setPersonNo(String personNo) {
    this.personNo = personNo;
  }

  public String getSerialNo() {
    return serialNo;
  }

  public void setSerialNo(String serialNo) {
    this.serialNo = serialNo;
  }

  public Short getAidTpc() {
    return aidTpc;
  }

  public void setAidTpc(Short aidTpc) {
    this.aidTpc = aidTpc;
  }

  public Short getGvrEntc() {
    return gvrEntc;
  }

  public void setGvrEntc(Short gvrEntc) {
    this.gvrEntc = gvrEntc;
  }

  public String getLicenseNo() {
    return licenseNo;
  }

  public void setLicenseNo(String licenseNo) {
    this.licenseNo = licenseNo;
  }

  public String getResponsDt() {
    return responsDt;
  }

  public void setResponsDt(String responsDt) {
    this.responsDt = responsDt;
  }

  public String getReceiveDt() {
    return receiveDt;
  }

  public void setReceiveDt(String receiveDt) {
    this.receiveDt = receiveDt;
  }

  public String getRapId() {
    return rapId;
  }

  public void setRapId(String rapId) {
    this.rapId = rapId;
  }

  public String getFbiInd() {
    return fbiInd;
  }

  public void setFbiInd(String fbiInd) {
    this.fbiInd = fbiInd;
  }

  public Short getCrspTpc() {
    return crspTpc;
  }

  public void setCrspTpc(Short crspTpc) {
    this.crspTpc = crspTpc;
  }

  public String getOtherData() {
    return otherData;
  }

  public void setOtherData(String otherData) {
    this.otherData = otherData;
  }

  public String getlUserid() {
    return lUserid;
  }

  public void setlUserid(String lUserid) {
    this.lUserid = lUserid;
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
  public Serializable getPrimaryKey() {
    return new ExternalInterfacePK(seqNo, submtlTs, lUserid);
  }
}
