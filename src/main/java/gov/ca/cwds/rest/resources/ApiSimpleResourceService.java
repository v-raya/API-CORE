package gov.ca.cwds.rest.resources;

import java.io.Serializable;

import gov.ca.cwds.rest.api.Request;
import gov.ca.cwds.rest.api.Response;
import gov.ca.cwds.rest.services.ServiceException;

/**
 * Typed interface for Service classes, intended for use by non-CRUD, read-only services, such as
 * ElasticSearch, NOT CRUD services.
 * 
 * @param <K> Key: uniquely identifies a record
 * @param <Q> reQuest: domain input type
 * @param <P> resPonse: domain output type
 * 
 * @author CWDS API Team
 */
public interface ApiSimpleResourceService<K extends Serializable, Q extends Request, P extends Response> {

  /**
   * Handle the incoming API{@link Request} and return an API {@link Response}.
   * 
   * <p>
   * On Exception, implementations should throw a new {@link ServiceException}, which wraps the
   * causal Exception.
   * </p>
   * 
   * @param request The API {@link Request}
   * @return The API {@link Response}
   * @throws ServiceException on disconnect or other serious error.
   */
  P handle(Q request) throws ServiceException;

  /**
   * Look up object/record by key.
   * 
   * @param key key to search for
   * @return API response
   * @throws ServiceException on disconnect or other serious error
   */
  P find(K key) throws ServiceException;

}
