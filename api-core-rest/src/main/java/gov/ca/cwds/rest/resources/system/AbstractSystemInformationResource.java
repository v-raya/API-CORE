package gov.ca.cwds.rest.resources.system;

import com.codahale.metrics.health.HealthCheck.Result;
import com.codahale.metrics.health.HealthCheckRegistry;
import gov.ca.cwds.dto.app.HealthCheckResultDto;
import gov.ca.cwds.dto.app.SystemInformationDto;
import java.util.Map;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;
import javax.ws.rs.core.Response.Status;

/**
 * Abstract class for building RESTful SystemInformationResource classes in CARES Applications.
 *
 * @author CWDS TPT-2
 */
public abstract class AbstractSystemInformationResource {

  static final int HTTP_STATUS_NOT_HEALTHY = 465;

  private HealthCheckRegistry healthCheckRegistry;

  protected SystemHealthStatusStrategy systemHealthStatusStrategy;

  protected AbstractSystemInformationResource(HealthCheckRegistry healthCheckRegistry) {
    this.healthCheckRegistry = healthCheckRegistry;
    systemHealthStatusStrategy = new SystemHealthStatusStrategy() {
      // default implementation
    };
  }

  protected SystemInformationDto prepareSystemInformation() {
    final SystemInformationDto systemInformationDto = new SystemInformationDto();
    final Map<String, Result> healthCheckResults = healthCheckRegistry.runHealthChecks();
    for (Map.Entry<String, Result> resultEntry : healthCheckResults.entrySet()) {
      systemInformationDto.getHealthCheckResults().put(resultEntry.getKey(),
        new HealthCheckResultDto(resultEntry.getValue()));
    }
    boolean isSystemHealthy = systemHealthStatusStrategy.getSystemHealthStatus(healthCheckResults);
    systemInformationDto.setHealthStatus(isSystemHealthy);
    return systemInformationDto;
  }

  protected final Response buildResponse() {
    SystemInformationDto systemInformationDto = prepareSystemInformation();
    ResponseBuilder responseBuilder =
      systemInformationDto.isHealthStatus() ? Response.status(Status.OK)
        : Response.status(HTTP_STATUS_NOT_HEALTHY);
    return responseBuilder.entity(systemInformationDto).build();
  }
}
