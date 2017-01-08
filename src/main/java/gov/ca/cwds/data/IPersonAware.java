package gov.ca.cwds.data;

/**
 * Interface defines naming standard methods for persistence classes that represent persons. Allows
 * DAO and service classes to operate on person-aware objects without knowledge of their
 * implementation.
 * 
 * @author CWDS API Team
 */
public interface IPersonAware {

  String getFirstName();

  String getMiddleName();

  String getLastName();

  String getGender();

  String getBirthDate();

  String getSsn();

  void setFirstName(String firstName);

  void setMiddleName(String MiddleName);

  void setLastName(String lastName);

  void setGender(String gender);

  void setBirthDate(String birthDate);

  void setSsn(String ssn);

}
