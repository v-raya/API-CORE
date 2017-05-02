package gov.ca.cwds.rest.api.domain.error;

import org.junit.Test;

import static org.junit.Assert.*;

public class ErrorMessageTest {
    @Test
    public void testCreatingErrorMessage(){
        String error = "error occured";
        ErrorMessage message = new ErrorMessage(ErrorMessage.ErrorType.VALIDATION, error, "DB");
        assertEquals(message.getType(), ErrorMessage.ErrorType.VALIDATION);
        assertEquals(message.getMessage(), error);
        assertEquals(message.getSource(), "DB");
    }
}