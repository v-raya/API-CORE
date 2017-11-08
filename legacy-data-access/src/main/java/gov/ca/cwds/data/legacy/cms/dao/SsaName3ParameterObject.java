package gov.ca.cwds.data.legacy.cms.dao;

import java.io.Serializable;
import java.util.Date;
import org.apache.commons.lang3.StringUtils;

/**
 * @author CWDS CALS API Team
 */
public class SsaName3ParameterObject implements Serializable {

  private static final long serialVersionUID = -8461113858326860957L;

  private String tableName;
  private String crudOper;
  private String identifier;
  private String nameCd;
  private String firstName;
  private String middleName;
  private String lastName;
  private String streettNumber;
  private String streetName;
  private String updateId;
  private Short gvrEntc;

  private Date updateTimeStamp;

  public String getTableName() {
    return tableName;
  }

  public void setTableName(String tableName) {
    this.tableName = tableName;
  }

  public String getCrudOper() {
    return handleEmptyValue(crudOper);
  }

  public void setCrudOper(String crudOper) {
    this.crudOper = crudOper;
  }

  public String getIdentifier() {
    return handleEmptyValue(identifier);
  }

  public void setIdentifier(String identifier) {
    this.identifier = identifier;
  }

  public String getNameCd() {
    return handleEmptyValue(nameCd);
  }

  public void setNameCd(String nameCd) {
    this.nameCd = nameCd;
  }

  public String getFirstName() {
    return handleEmptyValue(firstName);
  }

  public void setFirstName(String firstName) {
    this.firstName = firstName;
  }

  public String getMiddleName() {
    return handleEmptyValue(middleName);
  }

  public void setMiddleName(String middleName) {
    this.middleName = middleName;
  }

  public String getLastName() {
    return handleEmptyValue(lastName);
  }

  public void setLastName(String lastName) {
    this.lastName = lastName;
  }

  public String getStreettNumber() {
    return handleEmptyValue(streettNumber);
  }

  public void setStreetNumber(String streettNumber) {
    this.streettNumber = streettNumber;
  }

  public String getStreetName() {
    return handleEmptyValue(streetName);
  }

  public void setStreetName(String streetName) {
    this.streetName = streetName;
  }

  public String getUpdateId() {
    return handleEmptyValue(updateId);
  }

  public void setUpdateId(String updateId) {
    this.updateId = updateId;
  }

  public Short getGvrEntc() {
    return gvrEntc;
  }

  public void setGvrEntc(Short gvrEntc) {
    this.gvrEntc = gvrEntc;
  }

  public Date getUpdateTimeStamp() {
    return updateTimeStamp;
  }

  public void setUpdateTimeStamp(Date updateTimeStamp) {
    this.updateTimeStamp = updateTimeStamp;
  }

  private static String handleEmptyValue(String value) {
    return StringUtils.isNotEmpty(value) ? value : " ";
  }

}
