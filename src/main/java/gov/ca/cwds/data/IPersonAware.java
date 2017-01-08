package gov.ca.cwds.data;

/**
 * Interface defines naming standard methods for persistence classes that represent persons. Allows
 * DAO and service classes to operate on person-aware objects without knowledge of their concrete
 * 
 * @author CWDS API Team
 */
public interface IPersonAware {

  String getFirstName();

  void setFirstName(String firstName);

}
