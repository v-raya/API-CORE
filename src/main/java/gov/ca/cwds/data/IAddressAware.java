package gov.ca.cwds.data;

/**
 * Interface defines naming standard methods for persistence classes that represent Address. Allows
 * DAO and service classes to operate on Address-aware objects without knowledge of their concrete
 * 
 * @author CWDS API Team
 */
public interface IAddressAware {

  String getStreetAddress();

  void setStreetAddress(String streetAddress);

  String getCity();

  void setCity(String city);

  String getState();

  void setState(String state);

  String getZip();

  void setZip(String zip);

}
