package gov.ca.cwds.cms.data.access.domain;

/**
 * @author CWDS CALS API Team
 */

public class PhoneNumber {

  private String typeCode;
  private String number;
  private String extension;

  public PhoneNumber(String number, String extension, String typeCode) {
    this.typeCode = typeCode;
    this.number = number;
    this.extension = extension;
  }

  public String getTypeCode() {
    return typeCode;
  }

  public void setTypeCode(String typeCode) {
    this.typeCode = typeCode;
  }

  public String getNumber() {
    return number;
  }

  public void setNumber(String number) {
    this.number = number;
  }

  public String getExtension() {
    return extension;
  }

  public void setExtension(String extension) {
    this.extension = extension;
  }

}
