package gov.ca.cwds.rest.resources;

import static org.junit.Assert.*;

import org.junit.Test;

public class ClientExceptionTest {

  @Test(expected=ClientException.class)
  public void shouldCreateExceptionWithNoArgConstructor(){
    ClientException exception = new ClientException();
    throw exception;

  }

  @Test(expected=ClientException.class)
  public void shouldCreateExceptionWithMessage(){
    ClientException exception = new ClientException("The Client sent bad things");
    throw exception;
  }

  @Test(expected=ClientException.class)
  public void shouldCreateExceptionWithMessageAndException(){
    NullPointerException nullPointerException = new NullPointerException();
    ClientException exception = new ClientException("The Client sent bad things", nullPointerException);
    throw exception;

  }

  @Test(expected=ClientException.class)
  public void shouldCreateExceptionWithMessageExceptionSuppressionAndStackTraceFlags(){
    NullPointerException nullPointerException = new NullPointerException();
    ClientException exception = new ClientException("The Client sent bad things", nullPointerException, false, false);
    throw exception;

  }
}