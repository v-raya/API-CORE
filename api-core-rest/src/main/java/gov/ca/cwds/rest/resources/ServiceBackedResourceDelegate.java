package gov.ca.cwds.rest.resources;

import com.google.common.collect.ImmutableMap;
import com.google.inject.Inject;
import gov.ca.cwds.rest.api.Request;
import gov.ca.cwds.rest.api.domain.error.ErrorMessage;
import gov.ca.cwds.rest.api.domain.error.ErrorMessage.ErrorType;
import gov.ca.cwds.rest.services.CrudsService;
import gov.ca.cwds.rest.services.ServiceException;
import gov.ca.cwds.rest.validation.ValidationException;
import java.io.Serializable;
import java.lang.annotation.Annotation;
import java.util.HashSet;
import java.util.Set;
import javax.ws.rs.core.Response;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Implements the {@link ResourceDelegate} and passes work to the service layer. All
 * {@link Resource} should decorate this class. Resources will delegate to this class with the
 * decoration being swagger {@link Annotation} classes for documentation and Jersey
 * {@link Annotation} for RESTful resources.
 * 
 * @author CWDS API Team
 */
public final class ServiceBackedResourceDelegate implements ResourceDelegate {
  private static final Logger LOGGER = LoggerFactory.getLogger(ServiceBackedResourceDelegate.class);

  /**
   * The underlying, wrapped CRUD service.
   */
  private CrudsService service;

  /**
   * Constructor
   * 
   * @param crudsService The crudsService for this resource.
   */
  @Inject
  public ServiceBackedResourceDelegate(CrudsService crudsService) {
    this.service = crudsService;
  }

  /**
   * {@inheritDoc}
   * 
   * @see ResourceDelegate#get(Serializable)
   */
  @Override
  public Response get(Serializable id) {
    gov.ca.cwds.rest.api.Response response = service.find(id);
    if (response != null) {
      return Response.ok(response).build();
    } else {
      return Response.status(Response.Status.NOT_FOUND).entity(null).build();
    }
  }

  /**
   * {@inheritDoc}
   *
   * @see ResourceDelegate#delete(Serializable)
   */
  @Override
  public Response delete(Serializable id) {
    gov.ca.cwds.rest.api.Response response = service.delete(id);
    if (response != null) {
      return Response.status(Response.Status.OK).build();
    } else {
      return Response.status(Response.Status.NOT_FOUND).entity(null).build();
    }
  }

  /**
   * {@inheritDoc}
   *
   * @see ResourceDelegate#create(Request)
   */
  @Override
  public Response create(Request request) {
    Response response = null;
    try {
      gov.ca.cwds.rest.api.Response serviceResponse = service.create(request);
      Object entity;
      Response.Status responseStatus;
      if (serviceResponse.hasMessages()) {
        entity = serviceResponse.getMessages();
        responseStatus = Response.Status.BAD_REQUEST;
      } else {
        entity = serviceResponse;
        responseStatus = Response.Status.CREATED;
      }
      response = Response.status(responseStatus).entity(entity).build();
    } catch (ServiceException e) {
      if (e.getCause() instanceof ValidationException) {
        Set<ErrorMessage> errorMessages = buildErrorMessages(e, ErrorType.VALIDATION);
        response = Response.status(Response.Status.BAD_REQUEST).entity(errorMessages).build();
      } else if(e.getCause() instanceof ClientException){
        Set<ErrorMessage> errorMessages = buildErrorMessages(e, ErrorType.CLIENT_CONTRACT);
        response = Response.status(422).entity(errorMessages).build();
      } else  {
        LOGGER.error("Unable to handle request", e);
        response = Response.status(Response.Status.SERVICE_UNAVAILABLE).entity(null).build();
      }
    }
    return response;
  }

  private Set<ErrorMessage> buildErrorMessages(ServiceException e, ErrorType errorType) {
    Set<ErrorMessage> errorMessages = new HashSet<>();
    String[] messages = e.getMessage().split("&&");
    if (messages.length > 1) {
      for (int i = 0; i < messages.length; i++) {
        ErrorMessage message =
            new ErrorMessage(errorType, messages[i], "");
        errorMessages.add(message);
      }
    } else {
      ErrorMessage message =
          new ErrorMessage(errorType, messages[0], "");
      errorMessages.add(message);
    }
    return errorMessages;
  }

  /**
   * {@inheritDoc}
   *
   * @see ResourceDelegate#update(Serializable,
   *      Request)
   */
  @Override
  public Response update(Serializable id, Request request) {
    Response response = null;
    try {
      response = Response.status(Response.Status.OK).entity(service.update(id, request)).build();
    } catch (ServiceException e) {
        LOGGER.error("Unable to handle request", e);
        response = Response.status(Response.Status.SERVICE_UNAVAILABLE).entity(null).build();
    }
    return response;
  }

  /**
   * Exposes the wrapped {@link CrudsService}.
   * 
   * @return the underlying, wrapped {@link CrudsService}
   */
  public CrudsService getService() {
    return service;
  }
}
