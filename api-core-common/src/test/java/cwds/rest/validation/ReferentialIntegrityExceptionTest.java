package cwds.rest.validation;

import gov.ca.cwds.rest.validation.ReferentialIntegrityException;
import org.junit.Test;

public class ReferentialIntegrityExceptionTest {

  @Test(expected = ReferentialIntegrityException.class)
  public void shouldCreateExceptionWithNoArgConstructor() {
    ReferentialIntegrityException exception = new ReferentialIntegrityException();
    throw exception;

  }

  @Test(expected = ReferentialIntegrityException.class)
  public void shouldCreateExceptionWithMessage() {
    ReferentialIntegrityException exception =
        new ReferentialIntegrityException("The Referential Integrity Check sent bad things");
    throw exception;
  }

  @Test(expected = ReferentialIntegrityException.class)
  public void shouldCreateExceptionWithMessageAndException() {
    NullPointerException nullPointerException = new NullPointerException();
    ReferentialIntegrityException exception =
        new ReferentialIntegrityException("The Referential Integrity Check sent bad things",
            nullPointerException);
    throw exception;

  }

  @Test(expected = ReferentialIntegrityException.class)
  public void shouldCreateExceptionWithMessageExceptionSuppressionAndStackTraceFlags() {
    NullPointerException nullPointerException = new NullPointerException();
    ReferentialIntegrityException exception =
        new ReferentialIntegrityException("The Referential Integrity Check sent bad things",
            nullPointerException, false, false);
    throw exception;

  }
}
