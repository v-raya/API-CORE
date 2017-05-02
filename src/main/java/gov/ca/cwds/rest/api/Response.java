package gov.ca.cwds.rest.api;

import java.util.Set;

/**
 * Marker indicating an object is used as a response from the API.
 * 
 * @author CWDS API Team
 */
public interface Response {
    public boolean hasMessages();
    public Set getMessages();
}
