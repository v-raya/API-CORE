package gov.ca.cwds.rest.resources.system;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.doReturn;

import com.codahale.metrics.health.HealthCheck.Result;
import com.codahale.metrics.health.HealthCheckRegistry;
import gov.ca.cwds.dto.app.HealthCheckResultDto;
import gov.ca.cwds.dto.app.SystemInformationDto;
import java.util.Map;
import java.util.TreeMap;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

public class AbstractSystemInformationResourceTest {

  private TestSystemInformationResource systemInformationResource;

  @Before
  public void setUp() {
    final Map<String, Result> healthCheckResults = new TreeMap<>();
    healthCheckResults.put("test_health", Result.healthy("test is healthy"));
    HealthCheckRegistry healthCheckRegistry = Mockito.mock(HealthCheckRegistry.class);
    doReturn(healthCheckResults).when(healthCheckRegistry).runHealthChecks();

    systemInformationResource = new TestSystemInformationResource(healthCheckRegistry);
  }

  @Test
  public void testAbstractSystemInformationResource() {
    SystemInformationDto dto = systemInformationResource.get();
    assertEquals("app1", dto.getApplicationName());
    assertEquals("1.0", dto.getVersion());
    assertEquals("123", dto.getBuildNumber());
    assertEquals("5fe64cdd988f8218b3568e886064be03cb990ae8", dto.getGitCommitHash());
    assertTrue(dto.isHealthStatus());

    assertTrue(dto.getHealthCheckResults().containsKey("test_health"));
    HealthCheckResultDto healthCheckResultDto = dto.getHealthCheckResults().get("test_health");
    assertTrue(healthCheckResultDto.isHealthy());
    assertEquals("test is healthy", healthCheckResultDto.getMessage());
  }
}
