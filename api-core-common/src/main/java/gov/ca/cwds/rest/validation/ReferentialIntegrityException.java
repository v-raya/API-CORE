package gov.ca.cwds.rest.validation;

import gov.ca.cwds.rest.api.ApiException;

/**
 * Runtime exception indicating a problem when performing a referential integrity check.
 * 
 * @author CWDS API Team
 */
@SuppressWarnings("serial")
public class ReferentialIntegrityException extends ApiException {

  public ReferentialIntegrityException() {
    super();
  }

  public ReferentialIntegrityException(String message) {
    super(message);
  }

  public ReferentialIntegrityException(Throwable cause) {
    super(cause);
  }

  public ReferentialIntegrityException(String message, Throwable cause) {
    super(message, cause);
  }

  public ReferentialIntegrityException(String message, Throwable cause, boolean enableSuppression,
      boolean writableStackTrace) {
    super(message, cause, enableSuppression, writableStackTrace);
  }

}
