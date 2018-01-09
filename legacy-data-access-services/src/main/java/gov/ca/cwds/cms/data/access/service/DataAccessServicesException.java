package gov.ca.cwds.cms.data.access.service;

/** @author CWDS CALS API Team */
public class DataAccessServicesException extends Exception {

  private static final long serialVersionUID = 1531891281265710299L;

  public DataAccessServicesException(Exception exception) {
    super(exception);
  }
}
