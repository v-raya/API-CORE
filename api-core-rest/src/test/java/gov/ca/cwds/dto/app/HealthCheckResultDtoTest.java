package gov.ca.cwds.dto.app;

import static junit.framework.TestCase.assertNull;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import com.codahale.metrics.health.HealthCheck.Result;
import org.junit.Test;

public class HealthCheckResultDtoTest {

  @Test
  public void testHealthCheckResultDto() {
    HealthCheckResultDto dto = new HealthCheckResultDto(Result.healthy("this one is healthy"));
    assertTrue(dto.isHealthy());
    assertEquals("this one is healthy", dto.getMessage());
    assertNull(dto.getError());
    assertNull(dto.getDetails());
  }

}
