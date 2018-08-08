package gov.ca.cwds.jobs;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.knowm.sundial.Job;
import org.knowm.sundial.SundialJobScheduler;
import org.knowm.sundial.exceptions.JobInterruptException;

import com.codahale.metrics.health.HealthCheck.Result;
import com.newrelic.api.agent.NewRelic;

import io.dropwizard.setup.Environment;

/**
 * Health check job reporting events to NewRelic.
 * 
 * @author CWDS API Team
 */
public abstract class NewRelicReportingHealthcheckJob extends Job {

  private static final String HEALTHY_ATTRIBUTE = "healthy";
  private static final String UNHEALTHY_ATTRIBUTE = "unhealthy";

  /**
   * Default no-argument constructor.
   */
  public NewRelicReportingHealthcheckJob() {
    super();
  }

  @Override
  public void doRun() throws JobInterruptException {
    Environment environment =
        (Environment) SundialJobScheduler.getServletContext().getAttribute("environment");

    final Map<String, Result> healthCheckResults = environment.healthChecks().runHealthChecks();

    // A map of event data.
    // Key: String
    // Value: Should be a String, Number, or Boolean.
    Map<String, Object> eventAttributes = new TreeMap<>();

    List<String> healthy = new ArrayList<>();
    List<String> unhealthy = new ArrayList<>();

    for (String key : healthCheckResults.keySet()) {
      Result result = healthCheckResults.get(key);
      if (result.isHealthy()) {
        healthy.add(key);
      } else {
        unhealthy.add(key);
      }
    }

    if (!healthy.isEmpty()) {
      eventAttributes.put(HEALTHY_ATTRIBUTE, healthy.toString());
    }

    if (!unhealthy.isEmpty()) {
      eventAttributes.put(UNHEALTHY_ATTRIBUTE, unhealthy.toString());
    }

    NewRelic.getAgent().getInsights().recordCustomEvent(getEventType(), eventAttributes);
  }

  /**
   * Get health check event type.
   * 
   * @return health check event type.
   */
  public abstract String getEventType();
}
