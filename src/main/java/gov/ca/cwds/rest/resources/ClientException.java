package gov.ca.cwds.rest.resources;

/**
 * Runtime exception indicating a problem client system error not recoverable by the enduser.
 *
 * @author CWDS API Team
 */
public class ClientException extends RuntimeException {

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
