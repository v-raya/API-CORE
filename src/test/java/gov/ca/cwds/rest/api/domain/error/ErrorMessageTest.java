package gov.ca.cwds.rest.api.domain.error;

import gov.ca.cwds.rest.api.domain.error.ErrorMessage.ErrorType;
import org.junit.Test;

import static org.junit.Assert.*;

public class ErrorMessageTest {
  @Test
  public void shouldCreateAnErrorMessageWithTypeValidation(){
    String error = "error occured";
    ErrorMessage message = new ErrorMessage(ErrorMessage.ErrorType.VALIDATION, error, "DB");
    assertEquals(message.getType(), ErrorMessage.ErrorType.VALIDATION);
    assertEquals(message.getMessage(), error);
    assertEquals(message.getSource(), "DB");
  }

  @Test
  public void shouldCreateAnErrorMessageWithTypeClientContract(){
    String error = "error occured";
    ErrorMessage message = new ErrorMessage(ErrorType.CLIENT_CONTRACT, error, "DB");
    assertEquals(message.getType(), ErrorType.CLIENT_CONTRACT);
    assertEquals(message.getMessage(), error);
    assertEquals(message.getSource(), "DB");
  }

  @Test
  public void shouldCreateAnErrorMessageWithTypeBusiness(){
    String error = "error occured";
    ErrorMessage message = new ErrorMessage(ErrorType.BUSINESS, error, "DB");
    assertEquals(message.getType(), ErrorType.BUSINESS);
    assertEquals(message.getMessage(), error);
    assertEquals(message.getSource(), "DB");
  }

  @Test
  public void shouldCreateAnErrorMessageWithTypeDataAccess(){
    String error = "error occured";
    ErrorMessage message = new ErrorMessage(ErrorType.BUSINESS, error, "DB");
    assertEquals(message.getType(), ErrorType.BUSINESS);
    assertEquals(message.getMessage(), error);
    assertEquals(message.getSource(), "DB");
  }
}