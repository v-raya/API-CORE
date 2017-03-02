package gov.ca.cwds.data.std;

/**
 * Represents an object capable of holding multiple addresses.
 * 
 * @author CWDS API Team
 */
public interface ApiMultipleAddressesAware {

  /**
   * Get all addresses available on this object.
   * 
   * @return array of address aware objects
   */
  ApiAddressAware[] getAddresses();

}
