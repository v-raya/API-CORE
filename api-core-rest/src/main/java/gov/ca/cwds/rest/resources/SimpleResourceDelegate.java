package gov.ca.cwds.rest.resources;

import java.io.Serializable;
import java.lang.annotation.Annotation;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;
import javax.validation.ConstraintViolationException;
import javax.validation.Valid;
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
 * Standard implementation of interface {@link ApiSimpleResourceDelegate}. Incoming API requests
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
 * @see ApiSimpleResourceService
 */
public class SimpleResourceDelegate<K extends Serializable, Q extends Request, P extends gov.ca.cwds.rest.api.Response, S extends ApiSimpleResourceService<K, Q, P>>
    implements ApiSimpleResourceDelegate<K, Q, P, S> {

  /**
   * Logger for this class.
   */
  private static final Logger LOGGER = LoggerFactory.getLogger(SimpleResourceDelegate.class);

  /**
   * The wrapped CRUD service.
   */
  private S service;

  /**
   * Constructor
   *
   * @param service The {@link ApiSimpleResourceService} for this resource.
   */
  @Inject
  public SimpleResourceDelegate(S service) {
    this.service = service;
  }

  /**
   * <p>
   * Handle API request by delegating to service method,
   * {@link ApiSimpleResourceService#handle(Request)} and wrapping resulting API
   * {@link gov.ca.cwds.rest.api.Response} with a Java standard web service response.
   * </p>
   *
   * @param req input CWDS API {@link Request}
   * @return web service {@link Response}
   * @throws ApiException if service call fails, catch and throw an ApiException
   * @see ApiSimpleResourceService#handle(Request)
   */
  @Override
  public final Response handle(@Valid @NotNull Q req) throws ApiException {
    validateRequest(req);
    final P resp = execHandle(req);
    validateResponse(resp);
    return Response.status(Response.Status.OK).entity(resp).build();
  }

  /**
   * Find object by its key by delegating to wrapped service method,
   * {@link ApiSimpleResourceService#find(Serializable)}.
   *
   * @param key key to search for
   * @return The API {@link Response}
   * @throws ApiException if service call fails, catch and throw an ApiException
   */
  @Override
  public final Response find(@Valid @NotNull K key) throws ApiException {
    validateKey(key);
    final P resp = execFind(key);
    validateResponse(resp);
    return Response.status(Response.Status.OK).entity(resp).build();
  }

  /**
   * Exposes the wrapped {@link ApiSimpleResourceService}.
   *
   * <p>
   * Usually not needed, but the interface is exposed for convenience.
   * </p>
   *
   * @return the underlying, wrapped {@link ApiSimpleResourceService}
   */
  @Override
  public final S getService() {
    return this.service;
  }

  /**
   * Validate an outbound API {@link Response}, type P.
   *
   * @param resp API response
   * @throws ConstraintViolationException if the response fails validation
   */
  protected void validateResponse(P resp) throws ConstraintViolationException {
    ResourceParamValidator.<P>validate(resp);
  }

  /**
   * Validate an incoming API {@link Request}, type Q.
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
   * Convenience method. Returns the typed parameters for this class.
   *
   * @return typed parameters
   */
  public final Object[] getTypeParams() {
    return this.getClass().getTypeParameters();
  }

  /**
   * <p>
   * Handle API request by delegating to service method,
   * {@link ApiSimpleResourceService#handle(Request)} and wrapping resulting API
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
   * @see ApiSimpleResourceService#handle(Request)
   * @see ApiSimpleResourceService#find(Serializable)
   * @deprecated Moved to ServiceExceptionMapper
   */
  @Deprecated
  protected Response handleException(Exception e) throws ServiceException {
    Response ret;

    // ALREADY DONE? #136701343: Tech debt: exception handling in service layer.
    // Gold plating. Waiting for further requirements.
    if (e != null) {
      if (e.getCause() != null) {
        if (e.getCause() instanceof EntityNotFoundException) {
          throw new ServiceException("EntityNotFoundException", e.getCause());
        } else if (e.getCause() instanceof EntityExistsException) {
          throw new ServiceException("EntityExistsException", e.getCause());
        } else if (e.getCause() instanceof NullPointerException) {
          throw new ServiceException("NullPointerException", e.getCause());
        } else if (e.getCause() instanceof ClassNotFoundException) {
          LOGGER.error("Class not found! {}", e.getMessage(), e);
          throw new ServiceException("ClassNotFoundException", e.getCause());
        } else if (e.getCause() instanceof NotImplementedException) {
          LOGGER.error("Not implemented", e);
          throw new ServiceException("NotImplementedException", e.getCause());
        } else {
          LOGGER.error("Unable to handle request", e);
          throw new ServiceException("Unable to handle request", e.getCause());
        }
      } else if (e instanceof ServiceException) {
        final ServiceException svcEx = (ServiceException) e;
        LOGGER.error("ServiceException without attached cause: {}", svcEx.getMessage(), e);
        throw new ServiceException("ServiceException without attached cause", e);
      } else if (e instanceof IllegalArgumentException) {
        final IllegalArgumentException lae = (IllegalArgumentException) e;
        LOGGER.error("Argument cannot be null: {}", lae.getMessage(), e);
        throw new ServiceException("Argument cannot be null", e);
      } else if (e instanceof ConstraintViolationException) {
        final ConstraintViolationException cve = (ConstraintViolationException) e;
        LOGGER.error("ConstraintViolationException: {}", cve.getMessage(), e);
        throw new ServiceException("ConstraintViolationException: " + cve.getMessage(), e);
      } else {
        LOGGER.error("Unhandled error condition: {}", e.getMessage(), e);
        throw new ServiceException("Unhandled error condition", e);
      }
    } else {
      throw new ServiceException("No exception to handle");
    }
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
  protected P execFind(@Valid @NotNull K key) throws ServiceException {
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
  protected P execHandle(@Valid @NotNull Q req) throws ServiceException {
    return getService().handle(req);
  }

}
