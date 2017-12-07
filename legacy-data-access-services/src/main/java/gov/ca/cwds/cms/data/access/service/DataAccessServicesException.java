package gov.ca.cwds.cms.data.access.service;

import gov.ca.cwds.drools.DroolsException;

/**
 * @author CWDS CALS API Team
 */

public class DataAccessServicesException extends Exception {

  public DataAccessServicesException(DroolsException exception) {
    super(exception);
  }
}
