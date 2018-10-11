package gov.ca.cwds.rest.resources.system;

import gov.ca.cwds.dto.app.SystemInformationDto;
import io.dropwizard.setup.Environment;

public class TestSystemInformationResource extends AbstractSystemInformationResource {

  TestSystemInformationResource(Environment environment) {
    super(environment.healthChecks());
  }

  @Override
  protected SystemInformationDto get() {
    SystemInformationDto dto = super.get();
    dto.setApplicationName("app1");
    dto.setVersion("1.0");
    dto.setBuildNumber("123");
    dto.setGitCommitHash("5fe64cdd988f8218b3568e886064be03cb990ae8");
    dto.setHealthStatus(true);
    return dto;
  }
}
