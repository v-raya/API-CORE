package gov.ca.cwds.rest.resources;

import java.io.Serializable;
import java.lang.annotation.Annotation;

import javax.ws.rs.core.Response;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.inject.Inject;

import gov.ca.cwds.rest.api.Request;
import gov.ca.cwds.rest.services.TypedCrudsService;

/**
 * Generic, parameterized class implements the {@link ResourceDelegate} and passes work to the
 * service layer. All {@link Resource} should decorate this class. Resources will delegate to this
 * class with the decoration being swagger {@link Annotation} classes for documentation and Jersey
 * {@link Annotation} for RESTful resources.
 * 
 * @author CWDS API Team
 * @param
 *        <P>
 *        Primary key type
 * @param
 *        <Q>reQuest type
 * @param <S> reSponse type
 */
public final class TypedServiceBackedResourceDelegate<P extends Serializable, Q extends Request, S extends gov.ca.cwds.rest.api.Response>
    implements TypedResourceDelegate<P, Q> {

  private static final Logger LOGGER =
      LoggerFactory.getLogger(TypedServiceBackedResourceDelegate.class);

  /**
   * The wrapped CRUD service.
   */
  private TypedCrudsService<P, Q, S> service;

  /**
   * Constructor
   * 
   * @param crudsService The crudsService for this resource.
   */
  @Inject
  public TypedServiceBackedResourceDelegate(TypedCrudsService<P, Q, S> crudsService) {
    this.service = crudsService;
  }

  /**
   * {@inheritDoc}
   * 
   * @see ResourceDelegate#get(Serializable)
   */
  @Override
  public Response get(P id) {
    S response = service.find(id);
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
  public Response delete(P id) {
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
  public Response create(Q request) {
    return Response.status(Response.Status.CREATED).entity(service.create(request)).build();
  }

  /**
   * {@inheritDoc}
   *
   * @see ResourceDelegate#update(Serializable, Request)
   */
  @Override
  public Response update(P id, Q request) {
    return Response.status(Response.Status.OK).entity(service.update(id, request)).build();
  }

  /**
   * Exposes the wrapped {@link TypedCrudsService}.
   * 
   * @return the underlying, wrapped {@link TypedCrudsService}
   */
  public TypedCrudsService<P, Q, S> getService() {
    return service;
  }
}
