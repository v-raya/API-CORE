package gov.ca.cwds.rest.exception;

import javax.ws.rs.core.Response.Status;

import gov.ca.cwds.rest.api.ApiException;

/**
 * @author CWDS CALS API Team
 */
public class ExpectedException extends ApiException {

  private final String message;

  private final Status responseStatus;

  public ExpectedException(String message, Status responseStatus) {
    this.message = message;
    this.responseStatus = responseStatus;
  }

  @Override
  public String getMessage() {
    return message;
  }

  public Status getResponseStatus() {
    return responseStatus;
  }

}
