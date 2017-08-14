package gov.ca.cwds.auth;

@SuppressWarnings("serial")
public class InvalidTokenException extends ApiAuthenticationException {

  public InvalidTokenException() {
    super();
  }

  public InvalidTokenException(String message, Throwable cause) {
    super(message, cause);
  }

  public InvalidTokenException(String message) {
    super(message);
  }

  public InvalidTokenException(Throwable cause) {
    super(cause);
  }

}
