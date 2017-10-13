package gov.ca.cwds.rest.resources;

import gov.ca.cwds.rest.api.ApiException;

/**
 * Runtime exception indicating a problem client system error, which is not recoverable by the end
 * user.
 *
 * @author CWDS API Team
 */
public class ClientException extends ApiException {

  public ClientException() {
    super();
  }

  public ClientException(String message) {
    super(message);
  }

  public ClientException(Throwable cause) {
    super(cause);
  }

  public ClientException(String message, Throwable cause) {
    super(message, cause);
  }

  public ClientException(String message, Throwable cause, boolean enableSuppression,
      boolean writableStackTrace) {
    super(message, cause, enableSuppression, writableStackTrace);
  }

}
