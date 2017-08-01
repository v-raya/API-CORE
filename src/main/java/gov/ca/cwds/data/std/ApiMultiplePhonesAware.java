package gov.ca.cwds.data.std;

/**
 * Represents an object capable of holding multiple phone numbers.
 * 
 * @author CWDS API Team
 */
@FunctionalInterface
public interface ApiMultiplePhonesAware {

  /**
   * Get all phones available on this object.
   * 
   * @return array of phones
   */
  ApiPhoneAware[] getPhones();

}
