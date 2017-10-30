package gov.ca.cwds.rest.resources;

import gov.ca.cwds.rest.SwaggerConfiguration;
import gov.ca.cwds.rest.views.SwaggerView;
import io.swagger.annotations.Api;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;

import com.google.inject.Inject;

/**
 * CWDS API implementation of the API {@link Resource} marker interface to interact with Swagger.
 * 
 * @author CWDS API Team
 */
@Api(value = "swagger", hidden = true)
@Path(value = "swagger")
@Produces(MediaType.TEXT_HTML)
public class SwaggerResource implements Resource {

  private SwaggerConfiguration swaggerConfiguration;

  @Inject
  public SwaggerResource(SwaggerConfiguration swaggerConfiguration) {
    super();
    this.swaggerConfiguration = swaggerConfiguration;
  }

  @GET
  public SwaggerView get() {
    if (swaggerConfiguration.isShowSwagger()) {
      return new SwaggerView(swaggerConfiguration);
    } else {
      throw new WebApplicationException(404);
    }
  }
}
