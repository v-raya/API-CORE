package gov.ca.cwds.rest.validation;

import org.junit.Test;

public class ValidationExceptionTest {

  @Test(expected = ValidationException.class)
  public void shouldCreateExceptionWithNoArgConstructor() {
    ValidationException exception = new ValidationException();
    throw exception;

  }

  @Test(expected = ValidationException.class)
  public void shouldCreateExceptionWithMessage() {
    ValidationException exception = new ValidationException("The Validation sent bad things");
    throw exception;
  }

  @Test(expected = ValidationException.class)
  public void shouldCreateExceptionWithMessageAndException() {
    NullPointerException nullPointerException = new NullPointerException();
    ValidationException exception =
        new ValidationException("The Validation sent bad things", nullPointerException);
    throw exception;

  }

  @Test(expected = ValidationException.class)
  public void shouldCreateExceptionWithMessageExceptionSuppressionAndStackTraceFlags() {
    NullPointerException nullPointerException = new NullPointerException();
    ValidationException exception =
        new ValidationException("The Validation sent bad things", nullPointerException, false,
            false);
    throw exception;

  }
}
