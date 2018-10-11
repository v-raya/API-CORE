package gov.ca.cwds.rest.resources.system;

import com.codahale.metrics.health.HealthCheck.Result;
import com.codahale.metrics.health.HealthCheckRegistry;
import gov.ca.cwds.dto.app.HealthCheckResultDto;
import gov.ca.cwds.dto.app.SystemInformationDto;
import java.util.Map;

/**
 * Abstract class for building RESTful SystemInformationResource classes in CARES Applications.
 *
 * @author CWDS TPT-2
 */
public abstract class AbstractSystemInformationResource {

  private HealthCheckRegistry healthCheckRegistry;

  protected AbstractSystemInformationResource(HealthCheckRegistry healthCheckRegistry) {
    this.healthCheckRegistry = healthCheckRegistry;
  }

  protected SystemInformationDto get() {
    final SystemInformationDto systemInformation = new SystemInformationDto();
    final Map<String, Result> healthCheckResults = healthCheckRegistry.runHealthChecks();
    for (Map.Entry<String, Result> resultEntry : healthCheckResults.entrySet()) {
      systemInformation.getHealthCheckResults().put(resultEntry.getKey(),
        new HealthCheckResultDto(resultEntry.getValue()));
    }
    return systemInformation;
  }
}
