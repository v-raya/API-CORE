package gov.ca.cwds.data.legacy.cms.entity;

import gov.ca.cwds.data.legacy.cms.CmsPersistentObject;
import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import org.hibernate.annotations.ColumnTransformer;

/**
 * @author CWDS TPT-3 Team
 */

@MappedSuperclass
public abstract class BaseAddress extends CmsPersistentObject {

  private static final long serialVersionUID = 6031778171242179644L;

  @Id
  @Column(name = "IDENTIFIER", nullable = false, length = 10)
  private String id;

  @Column(name = "CITY_NM", nullable = false, length = 20)
  @ColumnTransformer(read = "trim(CITY_NM)")
  private String city;

  @Column(name = "EMRG_TELNO", nullable = false, precision = 0)
  private long emergencyPhone;

  @Column(name = "EMRG_EXTNO", nullable = false)
  private long emergencyPhoneExtension;

  @Column(name = "FRG_ADRT_B", nullable = false, length = 1)
  private String foreignAddressExists;

  @Column(name = "GVR_ENTC", nullable = false)
  private short governmentEntityCode;

  @Column(name = "MSG_TEL_NO", nullable = false, precision = 0)
  private long messagePhone;

  @Column(name = "MSG_EXT_NO", nullable = false)
  private long messagePhoneExtension;

  @Column(name = "HEADER_ADR", nullable = false, length = 35)
  @ColumnTransformer(read = "trim(HEADER_ADR)")
  private String otherHeaderAddress;

  @Column(name = "PRM_TEL_NO", nullable = false, precision = 0)
  private long primaryPhone;

  @Column(name = "PRM_EXT_NO", nullable = false)
  private long primaryPhoneExtension;

  @Column(name = "STATE_C", nullable = false)
  private short stateCode;

  @Column(name = "STREET_NM", nullable = false, length = 40)
  @ColumnTransformer(read = "trim(STREET_NM)")
  private String streetName;

  @Column(name = "STREET_NO", nullable = false, length = 10)
  @ColumnTransformer(read = "trim(STREET_NO)")
  private String streetNumber;

  @Column(name = "ZIP_NO", nullable = false)
  @ColumnTransformer(read = "trim(ZIP_NO)")
  private long zip;

  @Column(name = "ADDR_DSC", nullable = false, length = 120)
  @ColumnTransformer(read = "trim(ADDR_DSC)")
  private String description;

  @Column(name = "ZIP_SFX_NO", nullable = false)
  private short zipSuffix;

  @Column(name = "POSTDIR_CD", nullable = false, length = 2)
  @ColumnTransformer(read = "trim(POSTDIR_CD)")
  private String postDirectionTextCode;

  @Column(name = "PREDIR_CD", nullable = false, length = 2)
  @ColumnTransformer(read = "trim(PREDIR_CD)")
  private String preDirectionTextCode;

  @Column(name = "ST_SFX_C", nullable = false)
  private short streetSuffixCode;

  @Column(name = "UNT_DSGC", nullable = false)
  private short unitDesignatorCode;

  @Column(name = "UNIT_NO", nullable = false, length = 8)
  @ColumnTransformer(read = "trim(UNIT_NO)")
  private String unitNumber;

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

  public String getCity() {
    return city;
  }

  public void setCity(String cityNm) {
    this.city = cityNm;
  }

  public long getEmergencyPhone() {
    return emergencyPhone;
  }

  public void setEmergencyPhone(long emrgTelno) {
    this.emergencyPhone = emrgTelno;
  }

  public long getEmergencyPhoneExtension() {
    return emergencyPhoneExtension;
  }

  public void setEmergencyPhoneExtension(long emrgExtno) {
    this.emergencyPhoneExtension = emrgExtno;
  }

  public String getForeignAddressExists() {
    return foreignAddressExists;
  }

  public void setForeignAddressExists(String frgAdrtB) {
    this.foreignAddressExists = frgAdrtB;
  }

  public short getGovernmentEntityCode() {
    return governmentEntityCode;
  }

  public void setGovernmentEntityCode(short gvrEntc) {
    this.governmentEntityCode = gvrEntc;
  }

  public long getMessagePhone() {
    return messagePhone;
  }

  public void setMessagePhone(long msgTelNo) {
    this.messagePhone = msgTelNo;
  }

  public long getMessagePhoneExtension() {
    return messagePhoneExtension;
  }

  public void setMessagePhoneExtension(long msgExtNo) {
    this.messagePhoneExtension = msgExtNo;
  }

  public String getOtherHeaderAddress() {
    return otherHeaderAddress;
  }

  public void setOtherHeaderAddress(String headerAdr) {
    this.otherHeaderAddress = headerAdr;
  }

  public long getPrimaryPhone() {
    return primaryPhone;
  }

  public void setPrimaryPhone(long prmTelNo) {
    this.primaryPhone = prmTelNo;
  }

  public long getPrimaryPhoneExtension() {
    return primaryPhoneExtension;
  }

  public void setPrimaryPhoneExtension(long prmExtNo) {
    this.primaryPhoneExtension = prmExtNo;
  }

  public short getStateCode() {
    return stateCode;
  }

  public void setStateCode(short stateC) {
    this.stateCode = stateC;
  }

  public String getStreetName() {
    return streetName;
  }

  public void setStreetName(String streetNm) {
    this.streetName = streetNm;
  }

  public String getStreetNumber() {
    return streetNumber;
  }

  public void setStreetNumber(String streetNo) {
    this.streetNumber = streetNo;
  }

  public long getZip() {
    return zip;
  }

  public void setZip(long zipNo) {
    this.zip = zipNo;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String addrDsc) {
    this.description = addrDsc;
  }


  public short getZipSuffix() {
    return zipSuffix;
  }

  public void setZipSuffix(short zipSfxNo) {
    this.zipSuffix = zipSfxNo;
  }

  public String getPostDirectionTextCode() {
    return postDirectionTextCode;
  }

  public void setPostDirectionTextCode(String postdirCd) {
    this.postDirectionTextCode = postdirCd;
  }

  public String getPreDirectionTextCode() {
    return preDirectionTextCode;
  }

  public void setPreDirectionTextCode(String predirCd) {
    this.preDirectionTextCode = predirCd;
  }

  public short getStreetSuffixCode() {
    return streetSuffixCode;
  }

  public void setStreetSuffixCode(short stSfxC) {
    this.streetSuffixCode = stSfxC;
  }

  public short getUnitDesignatorCode() {
    return unitDesignatorCode;
  }

  public void setUnitDesignatorCode(short untDsgc) {
    this.unitDesignatorCode = untDsgc;
  }

  public String getUnitNumber() {
    return unitNumber;
  }

  public void setUnitNumber(String unitNo) {
    this.unitNumber = unitNo;
  }

}
