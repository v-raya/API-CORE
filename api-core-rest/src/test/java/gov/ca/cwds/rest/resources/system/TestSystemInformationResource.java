package gov.ca.cwds.rest.resources.system;

import gov.ca.cwds.dto.app.SystemInformationDto;
import io.dropwizard.setup.Environment;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("system-information")
@Produces(MediaType.APPLICATION_JSON)
public class TestSystemInformationResource extends AbstractSystemInformationResource {

  TestSystemInformationResource(Environment environment) {
    super(environment.healthChecks());
    super.applicationName = "app1";
    super.version = "1.0";
    super.buildNumber = "123";
    super.gitCommitHash = "5fe64cdd988f8218b3568e886064be03cb990ae8";
  }

  @GET
  @ApiResponses(
    value = {
      @ApiResponse(code = 401, message = "Not Authorized"),
      @ApiResponse(code = 404, message = "Not found"),
      @ApiResponse(code = 465, message = "CARES Service is not healthy")
    }
  )
  @ApiOperation(value = "Returns System Information", response = SystemInformationDto.class)
  public Response get() {
    return super.buildResponse();
  }
}
