package gov.ca.cwds.rest.resources.system;

import com.codahale.metrics.health.HealthCheck.Result;
import java.util.Map;

/**
 * Interface for SystemHealthStatusStrategy.
 *
 * @author CWDS TPT-2
 */
public interface SystemHealthStatusStrategy {

  default boolean getSystemHealthStatus(Map<String, Result> healthChecks) {
    return healthChecks.values().stream().allMatch(Result::isHealthy);
  }
}
