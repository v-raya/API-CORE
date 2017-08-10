package gov.ca.cwds.rest.resources;

import java.io.Serializable;
import java.lang.annotation.Annotation;

import javax.validation.constraints.NotNull;
import javax.ws.rs.core.Response;

import gov.ca.cwds.rest.api.ApiException;
import gov.ca.cwds.rest.api.Request;

/**
 * <p>
 * Implements the {@link ResourceDelegate} and passes work to the service layer. All non-CRUD
 * {@link Resource}'s may implement this interface multiple times, once for each distinct
 * Request/Response type. Resources will delegate to this class with the decoration being swagger
 * {@link Annotation} classes for documentation and Jersey {@link Annotation} for RESTful resources.
 * </p>
 * 
 * @param <K> Key type
 * @param <Q> reQuest type
 * @param <P> resPonse type
 * @param <S> Service type
 * 
 * @author CWDS API Team
 * @see ApiSimpleResourceService
 */
public interface ApiSimpleResourceDelegate<K extends Serializable, Q extends Request, P extends gov.ca.cwds.rest.api.Response, S extends ApiSimpleResourceService<K, Q, P>> {

  /**
   * Find object by its key by delegating to wrapped service method,
   * {@link ApiSimpleResourceService#find(Serializable)}.
   * 
   * @param key key to search for
   * @return The API {@link Response}
   * @throws ApiException if service call fails, catch and throw an ApiException
   */
  Response find(@NotNull K key) throws ApiException;

  /**
   * Exposes the wrapped {@link ApiSimpleResourceService}.
   * 
   * <p>
   * Usually you don't need to call this, but the interface is exposed for convenience.
   * </p>
   * 
   * @return the underlying, wrapped {@link ApiSimpleResourceService}
   */
  S getService();

  /**
   * <p>
   * Handle API request by delegating to service method,
   * {@link ApiSimpleResourceService#handle(Request)} and wrapping resulting API
   * {@link gov.ca.cwds.rest.api.Response} with a Java standard web service response.
   * </p>
   * 
   * <p>
   * HTTP Response Codes
   * </p>
   * 
   * The default method implementation may return the following HTTP response codes:
   * <ul>
   * <li>{@link javax.ws.rs.core.Response.Status#OK}</li>
   * <li>{@link javax.ws.rs.core.Response.Status#NOT_FOUND}</li>
   * <li>{@link javax.ws.rs.core.Response.Status#CONFLICT}</li>
   * <li>{@link javax.ws.rs.core.Response.Status#INTERNAL_SERVER_ERROR}</li>
   * <li>{@link javax.ws.rs.core.Response.Status#EXPECTATION_FAILED}</li>
   * <li>{@link javax.ws.rs.core.Response.Status#NOT_IMPLEMENTED}</li>
   * <li>{@link javax.ws.rs.core.Response.Status#SERVICE_UNAVAILABLE}</li>
   * </ul>
   * 
   * @param req input CWDS API {@link Request}
   * @return web service {@link Response}
   * @throws ApiException if service call fails, catch and throw an ApiException
   * @see ApiSimpleResourceService#handle(Request)
   */
  Response handle(@NotNull Q req) throws ApiException;
}
