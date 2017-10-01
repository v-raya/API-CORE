package gov.ca.cwds.rest.resources;

import java.io.Serializable;

import javax.persistence.EntityNotFoundException;
import javax.persistence.PersistenceException;
import javax.validation.ConstraintViolationException;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.apache.commons.lang3.NotImplementedException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import gov.ca.cwds.data.std.ApiMarker;
import gov.ca.cwds.rest.api.Request;
import gov.ca.cwds.rest.services.ServiceException;

/**
 * Standard implementation of interface {@link ApiSimpleResourceService} for non-CRUD services.
 * Incoming API requests delegate work to the service layer. All Non-CRUD {@link Resource} classes
 * should extend this class or nest delegate members.
 * 
 * @param <K> Key type
 * @param <Q> reQuest type
 * @param <P> resPonse type
 * 
 * @author CWDS API Team
 * @see ApiSimpleResourceService
 */
public abstract class SimpleResourceService<K extends Serializable, Q extends Request, P extends gov.ca.cwds.rest.api.Response>
    implements ApiSimpleResourceService<K, Q, P>, ApiMarker {

  /**
   * Default serialization.
   */
  private static final long serialVersionUID = 1L;

  /**
   * Logger for this class.
   */
  protected static final Logger LOGGER = LoggerFactory.getLogger(SimpleResourceService.class);

  /**
   * Validate an incoming API request, {@link Request}, type Q.
   * 
   * @param req API request
   * @throws ConstraintViolationException if the incoming request fails validation
   */
  protected final void validateRequest(Q req) throws ServiceException {
    ResourceParamValidator.<Q>validate(req);
  }

  /**
   * Validate an incoming key, type K.
   * 
   * @param key serializable key, type K
   * @throws ConstraintViolationException if the incoming key fails validation
   */
  protected final void validateKey(K key) throws ServiceException {
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
   * Handle common exceptions uniformly and throw a wrapping {@link ServiceException}.
   * </p>
   * 
   * <p>
   * Handled Exception types:
   * </p>
   * 
   * The default method implementation handles these common Exceptions:
   * <ul>
   * <li>{@link EntityNotFoundException}</li>
   * <li>{@link PersistenceException}</li>
   * <li>{@link NullPointerException}</li>
   * <li>{@link ClassNotFoundException}</li>
   * <li>{@link NotImplementedException}</li>
   * </ul>
   * 
   * @param e Exception to handle throws ServiceException on runtime error
   * @throws ServiceException wraps incoming exception in a ServiceException
   */
  @CoverageIgnore
  private void handleException(Exception e) throws ServiceException {

    if (e.getCause() instanceof ConstraintViolationException) {
      LOGGER.error("Failed validation! {}", e.getMessage(), e);
      final ConstraintViolationException cve = (ConstraintViolationException) e.getCause();

      cve.getConstraintViolations().stream()
          .forEach(err -> LOGGER.error("validation error: {}, invalid value: {}", err.getMessage(),
              err.getInvalidValue()));

      throw new ServiceException("Failed validation! " + e.getMessage(), cve);
    } else if (e.getCause() instanceof EntityNotFoundException) {
      LOGGER.error("NOT FOUND! {}", e.getMessage(), e);
      throw new ServiceException("NOT FOUND! " + e.getMessage(), e);
    } else if (e.getCause() instanceof PersistenceException) {
      LOGGER.error("Persistence error! {}", e.getMessage(), e);
      throw new ServiceException("Persistence error! " + e.getMessage(), e);
    } else if (e.getCause() instanceof NullPointerException) {
      LOGGER.error("NPE! {}", e.getMessage(), e);
      throw new ServiceException("NPE! " + e.getMessage(), e);
    } else if (e.getCause() instanceof ClassNotFoundException) {
      LOGGER.error("Class not found! {}", e.getMessage(), e);
      throw new ServiceException("Class not found! " + e.getMessage(), e);
    } else if (e.getCause() instanceof NotImplementedException) {
      LOGGER.error("Not implemented", e);
      throw new ServiceException("Not implemented", e);
    }

    LOGGER.error("Unable to handle request", e);
    throw new ServiceException(e);
  }

  @CoverageIgnore
  @Override
  public P handle(@Valid @NotNull Q req) throws ServiceException {
    validateRequest(req);
    return handleRequest(req);
  }

  @CoverageIgnore
  @Override
  public P find(@Valid @NotNull K key) throws ServiceException {
    validateKey(key);
    return handleFind(key);
  }

  /**
   * Required implementation method to handle an incoming API {@link Request}.
   * 
   * @param req incoming API Request
   * @return API Response
   */
  protected abstract P handleRequest(@Valid @NotNull Q req);

  /**
   * Required implementation method to find a record by key.
   * 
   * @param id incoming serializable key
   * @return API Response
   */
  protected abstract P handleFind(@Valid @NotNull K id);

}
