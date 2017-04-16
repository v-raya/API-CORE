package gov.ca.cwds.data.std;

import java.io.Serializable;

/**
 * Interface defines naming standard methods for persistence classes that represent an Address.
 * Allows DAO and service classes to operate on Address-aware objects without knowledge of their
 * implementation.
 * 
 * @author CWDS API Team
 */
public interface ApiAddressAware extends Serializable {

  /**
   * Getter address identifier (primary key), if any.
   * 
   * @return address identifier (primary key), if any
   */
  String getAddressId();

  /**
   * Getter for (first) street address.
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
   * Getter for optional zip 4.
   * 
   * @return zip4
   */
  default String getZip4() {
    return null;
  }

  /**
   * Getter for optional unit type, like "apt" or "suite".
   * 
   * @return optional unit type
   */
  default String getUnitType() {
    return null;
  }

  /**
   * Getter for optional unit number, like apartment number of suite number.
   * 
   * @return optional unit number
   */
  default String getUnitNumber() {
    return null;
  }

  /**
   * Getter for optional address type, such as home, foster, relative, etc. Not always available in
   * legacy.
   * 
   * @return optional address type
   */
  default String getAddressType() {
    return null;
  }

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

}
