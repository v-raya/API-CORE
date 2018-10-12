package gov.ca.cwds.rest.resources.system;

import static gov.ca.cwds.rest.resources.system.AbstractSystemInformationResource.HTTP_STATUS_NOT_HEALTHY;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.doReturn;

import com.codahale.metrics.health.HealthCheck.Result;
import com.codahale.metrics.health.HealthCheckRegistry;
import gov.ca.cwds.dto.app.HealthCheckResultDto;
import gov.ca.cwds.dto.app.SystemInformationDto;
import io.dropwizard.setup.Environment;
import java.util.Map;
import java.util.TreeMap;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

public class AbstractSystemInformationResourceTest {

  private TestSystemInformationResource systemInformationResource;

  private HealthCheckRegistry healthCheckRegistry = Mockito.mock(HealthCheckRegistry.class);

  @Before
  public void setUp() {
    Environment environment = Mockito.mock(Environment.class);
    doReturn(healthCheckRegistry).when(environment).healthChecks();

    systemInformationResource = new TestSystemInformationResource(environment);
  }

  @Test
  public void testAbstractSystemInformationResourceHealthy() {
    final Map<String, Result> healthCheckResults = new TreeMap<>();
    healthCheckResults.put("test1_health", Result.healthy("test1 is healthy"));
    healthCheckResults.put("test2_health", Result.healthy("test2 is healthy"));
    doReturn(healthCheckResults).when(healthCheckRegistry).runHealthChecks();

    Response response = systemInformationResource.get();
    assertEquals(Status.OK.getStatusCode(), response.getStatus());

    SystemInformationDto dto = (SystemInformationDto) response.getEntity();

    assertEquals("app1", dto.getApplicationName());
    assertEquals("1.0", dto.getVersion());
    assertEquals("123", dto.getBuildNumber());
    assertEquals("5fe64cdd988f8218b3568e886064be03cb990ae8", dto.getGitCommitHash());
    assertTrue(dto.isHealthStatus());

    assertTrue(dto.getHealthCheckResults().containsKey("test1_health"));
    HealthCheckResultDto healthCheckResultDto1 = dto.getHealthCheckResults().get("test1_health");
    assertTrue(healthCheckResultDto1.isHealthy());
    assertEquals("test1 is healthy", healthCheckResultDto1.getMessage());

    assertTrue(dto.getHealthCheckResults().containsKey("test2_health"));
    HealthCheckResultDto healthCheckResultDto2 = dto.getHealthCheckResults().get("test2_health");
    assertTrue(healthCheckResultDto2.isHealthy());
    assertEquals("test2 is healthy", healthCheckResultDto2.getMessage());
  }

  @Test
  public void testAbstractSystemInformationResourceNotHealthy() {
    final Map<String, Result> healthCheckResults = new TreeMap<>();
    healthCheckResults.put("test1_health", Result.unhealthy("test1 is not healthy"));
    healthCheckResults.put("test2_health", Result.healthy("test2 is healthy"));
    doReturn(healthCheckResults).when(healthCheckRegistry).runHealthChecks();

    Response response = systemInformationResource.get();
    assertEquals(HTTP_STATUS_NOT_HEALTHY, response.getStatus());

    SystemInformationDto dto = (SystemInformationDto) response.getEntity();

    assertEquals("app1", dto.getApplicationName());
    assertEquals("1.0", dto.getVersion());
    assertEquals("123", dto.getBuildNumber());
    assertEquals("5fe64cdd988f8218b3568e886064be03cb990ae8", dto.getGitCommitHash());
    assertFalse(dto.isHealthStatus());

    assertTrue(dto.getHealthCheckResults().containsKey("test1_health"));
    HealthCheckResultDto healthCheckResultDto1 = dto.getHealthCheckResults().get("test1_health");
    assertFalse(healthCheckResultDto1.isHealthy());
    assertEquals("test1 is not healthy", healthCheckResultDto1.getMessage());

    assertTrue(dto.getHealthCheckResults().containsKey("test2_health"));
    HealthCheckResultDto healthCheckResultDto2 = dto.getHealthCheckResults().get("test2_health");
    assertTrue(healthCheckResultDto2.isHealthy());
    assertEquals("test2 is healthy", healthCheckResultDto2.getMessage());
  }
}
