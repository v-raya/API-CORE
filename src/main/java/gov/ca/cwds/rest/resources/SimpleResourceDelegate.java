package gov.ca.cwds.rest.resources;

import java.io.Serializable;
import java.lang.annotation.Annotation;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;
import javax.ws.rs.core.Response;

import org.apache.commons.lang3.NotImplementedException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.inject.Inject;

import gov.ca.cwds.rest.api.ApiException;
import gov.ca.cwds.rest.api.Request;
import gov.ca.cwds.rest.services.ServiceException;

/**
 * Implements the {@link ResourceDelegate} and passes work to the service layer. All
 * {@link Resource} should decorate this class. Resources will delegate to this class with the
 * decoration being swagger {@link Annotation} classes for documentation and Jersey
 * {@link Annotation} for RESTful resources.
 * 
 * @author CWDS API Team
 * @param <K> Key type
 * @param <Q> reQuest type
 * @param <P> resPonse type
 * @param <S> Service type
 * @see ISimpleResourceService
 */
public final class SimpleResourceDelegate<K extends Serializable, Q extends Request, P extends gov.ca.cwds.rest.api.Response, S extends ISimpleResourceService<K, Q, P>>
    implements ISimpleResourceDelegate<K, Q, P, S> {

  /**
   * Logger for this interface.
   */
  static final Logger LOGGER = LoggerFactory.getLogger(ISimpleResourceDelegate.class);

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
   * Find object by its key by delegating to wrapped service method,
   * {@link ISimpleResourceService#find(Serializable)}.
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
   * @param id key to search for
   * @return The API {@link Response}
   * @throws ApiException if service call fails, catch and throw an ApiException
   */
  @Override
  public Response find(K id) throws ApiException {
    Response wsResponse = null;
    try {
      wsResponse = Response.status(Response.Status.OK).entity(getService().find(id)).build();
    } catch (ServiceException e) {
      if (e.getCause() instanceof EntityNotFoundException) {
        wsResponse = Response.status(Response.Status.NOT_FOUND).entity(null).build();
      } else if (e.getCause() instanceof EntityExistsException) {
        wsResponse = Response.status(Response.Status.CONFLICT).entity(null).build();
      } else if (e.getCause() instanceof NullPointerException) {
        wsResponse = Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(null).build();
      } else if (e.getCause() instanceof ClassNotFoundException) {
        wsResponse = Response.status(Response.Status.EXPECTATION_FAILED).entity(null).build();
      } else if (e.getCause() instanceof NotImplementedException) {
        wsResponse = Response.status(Response.Status.NOT_IMPLEMENTED).entity(null).build();
      } else {
        LOGGER.error("Unable to handle request", e);
        wsResponse = Response.status(Response.Status.SERVICE_UNAVAILABLE).entity(null).build();
      }
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
  public S getService() {
    return this.service;
  }

  /**
   * <p>
   * Handle API request by delegating to service method,
   * {@link ISimpleResourceService#handle(Request)} and wrapping resulting API
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
   * @see ISimpleResourceService#handle(Request)
   */
  @Override
  public Response handle(Q req) throws ApiException {
    Response wsResponse = null;
    try {
      wsResponse = Response.status(Response.Status.OK).entity(getService().handle(req)).build();
    } catch (ServiceException e) {
      if (e.getCause() instanceof EntityNotFoundException) {
        wsResponse = Response.status(Response.Status.NOT_FOUND).entity(null).build();
      } else if (e.getCause() instanceof EntityExistsException) {
        wsResponse = Response.status(Response.Status.CONFLICT).entity(null).build();
      } else if (e.getCause() instanceof NullPointerException) {
        wsResponse = Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(null).build();
      } else if (e.getCause() instanceof ClassNotFoundException) {
        wsResponse = Response.status(Response.Status.EXPECTATION_FAILED).entity(null).build();
      } else if (e.getCause() instanceof NotImplementedException) {
        wsResponse = Response.status(Response.Status.NOT_IMPLEMENTED).entity(null).build();
      } else {
        LOGGER.error("Unable to handle request", e);
        wsResponse = Response.status(Response.Status.SERVICE_UNAVAILABLE).entity(null).build();
      }
    }
    return wsResponse;
  }

}
