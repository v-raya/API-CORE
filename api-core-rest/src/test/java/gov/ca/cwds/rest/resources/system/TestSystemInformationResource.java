package gov.ca.cwds.rest.resources.system;

import com.codahale.metrics.health.HealthCheckRegistry;
import gov.ca.cwds.dto.app.SystemInformationDto;

public class TestSystemInformationResource extends AbstractSystemInformationResource {

  TestSystemInformationResource(HealthCheckRegistry healthCheckRegistry) {
    super(healthCheckRegistry);
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
