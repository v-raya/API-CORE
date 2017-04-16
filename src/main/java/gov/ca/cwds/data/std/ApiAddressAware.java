package gov.ca.cwds.data.std;

import java.io.Serializable;

import org.apache.commons.lang3.StringUtils;

/**
 * Interface defines naming standard methods for persistence classes that represent an Address.
 * Allows DAO and service classes to operate on Address-aware objects without knowledge of their
 * implementation.
 * 
 * @author CWDS API Team
 */
public interface ApiAddressAware extends Serializable {

  /**
   * Format zip4 as a zero-padded String.
   * 
   * @param zip4 zip4 to format
   * @return zip4 formatted as String
   */
  static String formatZip4(Short zip4) {
    return zip4 != null && zip4 != 0 ? StringUtils.leftPad(String.valueOf(zip4.intValue()), 4, '0')
        : null;
  }

  /**
   * Format zip5 as a zero-padded String.
   * 
   * @param zip5 zip5 to format
   * @return zip5 formatted as String
   */
  static String formatZip5(Short zip5) {
    return zip5 != null && zip5 != 0 ? StringUtils.leftPad(String.valueOf(zip5.intValue()), 5, '0')
        : null;
  }

  /**
   * Getter address identifier (primary key), if any.
   * 
   * @return address identifier (primary key), if any
   */
  String getAddressId();

  /**
   * Getter street name.
   * 
   * @return street name
   */
  default String getStreetName() {
    return null;
  }

  /**
   * Getter for street number.
   * 
   * @return street number
   */
  default String getStreetNumber() {
    return null;
  }

  /**
   * Getter for (first) street address. Convenient, composite street address.
   * 
   * @return street address
   */
  String getStreetAddress();

  /**
   * Getter for city.
   * 
   * @return city
   */
  String getCity();

  /**
   * Getter for state, 2-char code.
   * 
   * @return state
   */
  String getState();

  /**
   * Getter for 5-digit zip.
   * 
   * @return zip
   */
  String getZip();

  /**
   * Getter for county.
   * 
   * @return county
   */
  String getCounty();

  /**
   * Getter for underlying state system code.
   * 
   * @return legacy CMS code for this state or null
   */
  Short getStateCd();

  /**
   * Getter for optional zip 4.
   * 
   * @return zip4
   */
  default String getApiAdrZip4() {
    return null;
  }

  /**
   * Getter for optional unit number, like apartment number of suite number.
   * 
   * @return optional unit number
   */
  default String getApiAdrUnitNumber() {
    return null;
  }

  /**
   * Getter for optional address type, such as home, foster, relative, etc. Not always available in
   * legacy.
   * 
   * @return optional address type
   */
  default Short getApiAdrAddressType() {
    return null;
  }

  /**
   * Getter for optional unit type, like "apt" or "suite".
   * 
   * @return optional unit type
   */
  default Short getApiAdrUnitType() {
    return null;
  }

}
