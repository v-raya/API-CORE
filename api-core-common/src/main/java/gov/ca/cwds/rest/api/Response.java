package gov.ca.cwds.rest.api;

import gov.ca.cwds.data.std.ApiMarker;
import gov.ca.cwds.rest.api.domain.error.ErrorMessage;
import java.util.ArrayList;
import java.util.List;

/**
 * Marker indicating an object is used as a response from the API.
 * 
 * @author CWDS API Team
 */
public interface Response extends ApiMarker {

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
