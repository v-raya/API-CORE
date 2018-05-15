package gov.ca.cwds.rest.api.domain.error;

import org.slf4j.event.Level;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import gov.ca.cwds.rest.serializer.ErrorMessageSerializer;

/**
 * JSON-aware error message, used to transmit errors from services back to the domain response.
 * 
 * @author CWDS API Team
 */
@JsonSerialize(using = ErrorMessageSerializer.class)
public class ErrorMessage {

  private final ErrorType type;
  private final String message;
  private final String source;

  public ErrorType getType() {
    return type;
  }

  public String getMessage() {
    return message;
  }

  public String getSource() {
    return source;
  }

  public ErrorMessage(ErrorType type, String message, String source) {
    this.type = type;
    this.message = message;
    this.source = source;
  }

  public static enum ErrorType {

    //@formatter:off
    CLIENT_AUTHORIZATION("Client Authorization Error"), 

    /**
     * User is not authorized to view a client, typically in passing, such as a child's half-sister's foster sibling. Show a warning, not an error.
     */
    CLIENT_AUTHORIZATION_WARNING("Client Authorization Warning", Level.WARN), 
    
    CLIENT_CONTRACT("Client Contract Error"), 
    
    VALIDATION("Validation Error"), 
    
    BUSINESS("Buiness Rules"), 
    
    DATA_ACCESS("Data Access");
    //@formatter:on 

    private final String label;
    private final Level level;

    private ErrorType(String label) {
      this.label = label;
      this.level = Level.ERROR;
    }

    private ErrorType(String label, Level level) {
      this.label = label;
      this.level = level;
    }

    public String getLabel() {
      return label;
    }

    public Level getLevel() {
      return level;
    }
  }

}
