package gov.ca.cwds.rest.resources.system;

import gov.ca.cwds.dto.app.SystemInformationDto;
import io.dropwizard.setup.Environment;
import javax.ws.rs.core.Response;

public class TestSystemInformationResource extends AbstractSystemInformationResource {

  TestSystemInformationResource(Environment environment) {
    super(environment.healthChecks());
  }

  @Override
  protected SystemInformationDto prepareSystemInformation() {
    SystemInformationDto systemInformationDto = super.prepareSystemInformation();
    systemInformationDto.setApplicationName("app1");
    systemInformationDto.setVersion("1.0");
    systemInformationDto.setBuildNumber("123");
    systemInformationDto.setGitCommitHash("5fe64cdd988f8218b3568e886064be03cb990ae8");
    return systemInformationDto;
  }

  public Response get() {
    return super.buildResponse();
  }
}
