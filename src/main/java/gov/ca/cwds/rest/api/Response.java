package gov.ca.cwds.rest.api;

import java.util.Set;
import java.util.concurrent.ConcurrentSkipListSet;

/**
 * Marker indicating an object is used as a response from the API.
 * 
 * @author CWDS API Team
 */
public interface Response {

  /**
   * Tells whether Jersey response contains additional messages,
   * 
   * @return whether Jersey response contains additional messages
   */
  default boolean hasMessages() {
    return false;
  }

  /**
   * Returns Set of additional messages from Jersey response.
   * 
   * @return Set of messages or empty set if none
   * @see #hasMessages()
   */
  default Set<String> getMessages() {
    return new ConcurrentSkipListSet<>();
  }

}
