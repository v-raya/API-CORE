package gov.ca.cwds.rest.services;

import java.io.Serializable;

import gov.ca.cwds.rest.api.Request;
import gov.ca.cwds.rest.api.Response;
import gov.ca.cwds.rest.api.domain.DomainObject;

/**
 * Interface for business {@link Service} which perform CRUDS operations. This interface strongly
 * types a service's primary key, request, and response.
 * 
 * @author CWDS API Team
 * @param <K> primary key type
 * @param <I> request (input) type
 * @param <O> response (output) type
 */
public interface TypedSimpleService<K extends Serializable, I extends Request, O extends Response>
    extends Service {

  /**
   * Find object by primaryKey
   * 
   * @param primaryKey The primaryKey of the {@link DomainObject} to find.
   * 
   * @return The {@link Response} containing the found object, null if not found.
   */
  public O find(K primaryKey);

  /**
   * Delete object by id
   * 
   * @param primaryKey The primaryKey of the {@link DomainObject} to delete.
   * 
   * @return The {@link Response} containing the deleted object, null if not found.
   */
  public O delete(K primaryKey);

  /**
   * Create object
   * 
   * @param request {@link Request} with a {@link DomainObject} to create.
   * 
   * @return The {@link Response}
   */
  public O create(I request);

  /**
   * Update object
   * 
   * @param primaryKey The primaryKey of the {@link DomainObject} to update.
   * @param request {@link Request} with a {@link DomainObject} to update.
   * 
   * @return The {@link Response}
   */
  public O update(K primaryKey, I request);

}
