package gov.ca.cwds.rest.api;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import gov.ca.cwds.rest.api.domain.error.ErrorMessage;

/**
 * Marker indicating an object is used as a response from the API.
 * 
 * @author CWDS API Team
 */
public interface Response extends Serializable {

  /**
   * Tells whether Jersey response contains additional messages.
   *
   * @return whether Jersey response contains additional messages
   */
  default boolean hasMessages() {
    return false;
  }

  /**
   * Returns a List of additional messages from Jersey response.
   *
   * @return List of messages or empty List if none
   * @see #hasMessages()
   */
  default List<ErrorMessage> getMessages() {
    return new ArrayList<>();
  }
}
