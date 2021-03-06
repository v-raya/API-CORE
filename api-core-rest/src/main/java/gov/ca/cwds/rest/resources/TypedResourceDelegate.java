package gov.ca.cwds.rest.resources;

import gov.ca.cwds.rest.api.Request;
import gov.ca.cwds.rest.api.domain.DomainObject;
import java.io.Serializable;
import javax.ws.rs.core.Response;

/**
 * Interface for Resource delegates, intended for use by CRUD-aware resources. All resources should
 * have at a minimum a GET, POST, PUT, and DELETE. Those implementations should be fairly generic
 * other than {@link Request} and {@link Response} types.
 * 
 * @author CWDS API Team
 * @param <P> Primary key type
 * @param <Q> reQuest (input) type
 */
public interface TypedResourceDelegate<P extends Serializable, Q extends Request> {

  /**
   * Gets a {@link DomainObject} based on the given id.
   * 
   * @param id The id of the {@link DomainObject}
   * 
   * @return The {@link Response}
   */
  public Response get(P id);

  /**
   * Delete a {@link DomainObject}
   * 
   * @param id The id of the {@link DomainObject}
   * 
   * @return {@link Response}
   */
  public Response delete(P id);

  /**
   * Create a {@link DomainObject}
   * 
   * @param request The {@link Request}
   * 
   * @return The {@link Response}
   */
  public Response create(Q request);

  /**
   * Update a {@link DomainObject}
   *
   * @param id The id of the {@link DomainObject} to update.
   * @param request The {@link Request}
   *
   * @return The {@link Response}
   */
  public Response update(P id, Q request);
}
