package gov.ca.cwds.rest.resources;

import java.io.Serializable;
import java.lang.annotation.Annotation;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;
import javax.validation.ConstraintViolationException;
import javax.validation.constraints.NotNull;
import javax.ws.rs.core.Response;

import org.apache.commons.lang3.NotImplementedException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.inject.Inject;

import gov.ca.cwds.rest.api.ApiException;
import gov.ca.cwds.rest.api.Request;
import gov.ca.cwds.rest.services.ServiceException;

/**
 * Standard implementation of interface {@link ISimpleResourceDelegate}. Incoming API requests
 * delegate work to the service layer. All Non-CRUD {@link Resource} classes should extend this
 * class or nest delegate members.
 * 
 * <p>
 * Non-CRUD resources delegate to this class with the decoration being Swagger {@link Annotation}
 * classes for documentation and Jersey {@link Annotation} for RESTful resources.
 * </p>
 * 
 * <p>
 * NOTE: CRUD resources should use {@link TypedResourceDelegate} instead, which provides methods to
 * Create, Read, Update, and Delete underlying persistence objects. This class is intended for
 * resources which do not fall cleanly into the CRUD model.
 * </p>
 *
 * @param <K> Key type
 * @param <Q> reQuest type
 * @param <P> resPonse type
 * @param <S> Service type
 * 
 * @author CWDS API Team
 * @see ISimpleResourceService
 */
public class SimpleResourceDelegate<K extends Serializable, Q extends Request, P extends gov.ca.cwds.rest.api.Response, S extends ISimpleResourceService<K, Q, P>>
    implements ISimpleResourceDelegate<K, Q, P, S> {

  /**
   * Logger for this class.
   */
  private static final Logger LOGGER = LoggerFactory.getLogger(ISimpleResourceDelegate.class);

  /**
   * The wrapped CRUD service.
   */
  private S service;

  /**
   * Constructor
   * 
   * @param service The {@link ISimpleResourceService} for this resource.
   */
  @Inject
  public SimpleResourceDelegate(S service) {
    this.service = service;
  }

  /**
   * <p>
   * Handle API request by delegating to service method,
   * {@link ISimpleResourceService#handle(Request)} and wrapping resulting API
   * {@link gov.ca.cwds.rest.api.Response} with a Java standard web service response.
   * </p>
   * 
   * <p>
   * See method {@link #handleException(Exception)} for the list of possible HTTP Response Codes.
   * </p>
   * 
   * @param req input CWDS API {@link Request}
   * @return web service {@link Response}
   * @throws ApiException if service call fails, catch and throw an ApiException
   * @see ISimpleResourceService#handle(Request)
   */
  @Override
  public final Response handle(@NotNull Q req) throws ApiException {
    Response wsResponse = null;
    try {
      validateRequest(req);
      wsResponse = Response.status(Response.Status.OK).entity(execHandle(req)).build();
    } catch (Exception e) {
      wsResponse = handleException(e);
    }
    return wsResponse;
  }

  /**
   * Find object by its key by delegating to wrapped service method,
   * {@link ISimpleResourceService#find(Serializable)}.
   * 
   * <p>
   * See method {@link #handleException(Exception)} for the list of possible HTTP Response Codes.
   * </p>
   * 
   * @param key key to search for
   * @return The API {@link Response}
   * @throws ApiException if service call fails, catch and throw an ApiException
   */
  @Override
  public final Response find(@NotNull K key) throws ApiException {
    Response wsResponse = null;
    try {
      validateKey(key);
      wsResponse = Response.status(Response.Status.OK).entity(execFind(key)).build();
    } catch (Exception e) {
      wsResponse = handleException(e);
    }
    return wsResponse;
  }

  /**
   * Exposes the wrapped {@link ISimpleResourceService}.
   * 
   * <p>
   * Usually you don't need to call this, but the interface is exposed for convenience.
   * </p>
   * 
   * @return the underlying, wrapped {@link ISimpleResourceService}
   */
  @Override
  public final S getService() {
    return this.service;
  }

  /**
   * Validate an incoming API request, {@link Request}, type Q.
   * 
   * @param req API request
   * @throws ConstraintViolationException if the request fails validation
   */
  protected void validateRequest(Q req) throws ConstraintViolationException {
    ResourceParamValidator.<Q>validate(req);
  }

  /**
   * Validate an incoming key, type K.
   * 
   * @param key key to validate
   * @throws ConstraintViolationException if the key fails validation
   */
  protected void validateKey(K key) throws ConstraintViolationException {
    ResourceParamValidator.<K>validate(key);
  }

  /**
   * <p>
   * Handle API request by delegating to service method,
   * {@link ISimpleResourceService#handle(Request)} and wrapping resulting API
   * {@link gov.ca.cwds.rest.api.Response} with a Java standard web service response.
   * </p>
   * 
   * <p>
   * Web Service Response, not a CWDS API Response.
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
   * @param e {@link ServiceException} to inspect
   * @return web service {@link Response}
   * @see ISimpleResourceService#handle(Request)
   * @see ISimpleResourceService#find(Serializable)
   */
  protected Response handleException(Exception e) {
    Response ret;

    if (e.getCause() != null) {
      if (e.getCause() instanceof EntityNotFoundException) {
        ret = Response.status(Response.Status.NOT_FOUND).entity(null).build();
      } else if (e.getCause() instanceof EntityExistsException) {
        ret = Response.status(Response.Status.CONFLICT).entity(null).build();
      } else if (e.getCause() instanceof NullPointerException) {
        ret = Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(null).build();
      } else if (e.getCause() instanceof ClassNotFoundException) {
        LOGGER.error("Class not found! {}", e.getMessage(), e);
        ret = Response.status(Response.Status.EXPECTATION_FAILED).entity(null).build();
      } else if (e.getCause() instanceof NotImplementedException) {
        LOGGER.error("Not implemented", e);
        ret = Response.status(Response.Status.NOT_IMPLEMENTED).entity(null).build();
      } else {
        LOGGER.error("Unable to handle request", e);
        ret = Response.status(Response.Status.SERVICE_UNAVAILABLE).entity(null).build();
      }
    } else if (e instanceof ServiceException) {
      final ServiceException svcEx = (ServiceException) e;
      LOGGER.error("ServiceException without attached cause: {}", svcEx.getMessage(), e);
      ret = Response.status(Response.Status.SERVICE_UNAVAILABLE).entity(null).build();
    } else {
      LOGGER.error("Unhandled error condition: {}", e.getMessage(), e);
      ret = Response.status(Response.Status.SERVICE_UNAVAILABLE).entity(null).build();
    }

    return ret;
  }

  /**
   * Default behavior for {@link #find(Serializable)} method. Delegates to service.
   * 
   * <p>
   * Implementations are free to override this behavior, as needed.
   * </p>
   * 
   * @param key Serializable, unique key
   * @return API Response
   * @throws ServiceException on service error
   */
  protected P execFind(@NotNull K key) throws ServiceException {
    return getService().find(key);
  }

  /**
   * Default behavior for {@link #handle(Request)} method. Delegates to service.
   * 
   * <p>
   * Implementations are free to override this behavior, as needed.
   * </p>
   * 
   * @param req API Request
   * @return API Response
   * @throws ServiceException on service error
   */
  protected P execHandle(@NotNull Q req) throws ServiceException {
    return getService().handle(req);
  }

}
