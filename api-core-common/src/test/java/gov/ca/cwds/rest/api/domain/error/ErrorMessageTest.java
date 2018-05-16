package gov.ca.cwds.rest.api.domain.error;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import gov.ca.cwds.rest.api.domain.error.ErrorMessage.ErrorType;

public class ErrorMessageTest {

  @Test
  public void shouldCreateAnErrorMessageWithTypeValidation() {
    String error = "error occurred";
    ErrorMessage message = new ErrorMessage(ErrorType.VALIDATION, error, "DB");
    assertEquals(message.getType(), ErrorType.VALIDATION);
    assertEquals(message.getMessage(), error);
    assertEquals(message.getSource(), "DB");
  }

  @Test
  public void shouldCreateAnErrorMessageWithTypeClientContract() {
    String error = "error occurred";
    ErrorMessage message = new ErrorMessage(ErrorType.CLIENT_CONTRACT, error, "DB");
    assertEquals(message.getType(), ErrorType.CLIENT_CONTRACT);
    assertEquals(message.getMessage(), error);
    assertEquals(message.getSource(), "DB");
  }

  @Test
  public void shouldCreateAnErrorMessageWithTypeBusiness() {
    String error = "error occurred";
    ErrorMessage message = new ErrorMessage(ErrorType.BUSINESS, error, "DB");
    assertEquals(message.getType(), ErrorType.BUSINESS);
    assertEquals(message.getMessage(), error);
    assertEquals(message.getSource(), "DB");
  }

  @Test
  public void shouldCreateAnErrorMessageWithTypeDataAccess() {
    String error = "error occurred";
    ErrorMessage message = new ErrorMessage(ErrorType.BUSINESS, error, "DB");
    assertEquals(message.getType(), ErrorType.BUSINESS);
    assertEquals(message.getMessage(), error);
    assertEquals(message.getSource(), "DB");
  }

}
