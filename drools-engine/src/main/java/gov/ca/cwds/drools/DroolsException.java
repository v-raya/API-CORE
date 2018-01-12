package gov.ca.cwds.drools;

/**
 * @author CWDS CALS API Team
 */

public class DroolsException extends Exception {

  public DroolsException(String message, Exception e) {
    super(message, e);
  }

  public DroolsException(String message) {
    super(message);
  }
}
