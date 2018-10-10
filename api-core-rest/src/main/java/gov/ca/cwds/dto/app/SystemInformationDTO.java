package gov.ca.cwds.dto.app;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import java.util.SortedMap;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

/**
 * @author CWDS TPT-2
 */
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
@JsonPropertyOrder({"application", "version", "build", "git_commit", "health_status",
  "health_checks"})
public class SystemInformationDTO {

  @JsonProperty(value = "application", required = true)
  private String applicationName;

  @JsonProperty(required = true)
  private String version;

  @JsonProperty("build")
  private String buildNumber;

  @JsonProperty("git_commit")
  private String gitCommitHash;

  @JsonProperty(required = true)
  private boolean healthStatus;

  @JsonProperty(value = "health_checks", required = true)
  private SortedMap<String, HealthCheckResultDTO> healthCheckResults;

  public String getApplicationName() {
    return applicationName;
  }

  public void setApplicationName(String applicationName) {
    this.applicationName = applicationName;
  }

  public String getVersion() {
    return version;
  }

  public void setVersion(String version) {
    this.version = version;
  }

  public String getBuildNumber() {
    return buildNumber;
  }

  public void setBuildNumber(String buildNumber) {
    this.buildNumber = buildNumber;
  }

  public String getGitCommitHash() {
    return gitCommitHash;
  }

  public void setGitCommitHash(String gitCommitHash) {
    this.gitCommitHash = gitCommitHash;
  }

  public boolean isHealthStatus() {
    return healthStatus;
  }

  public void setHealthStatus(boolean healthStatus) {
    this.healthStatus = healthStatus;
  }

  public SortedMap<String, HealthCheckResultDTO> getHealthCheckResults() {
    return healthCheckResults;
  }

  public void setHealthCheckResults(
    SortedMap<String, HealthCheckResultDTO> healthCheckResults) {
    this.healthCheckResults = healthCheckResults;
  }

  @Override
  public String toString() {
    return ReflectionToStringBuilder.toString(this);
  }

  @Override
  public boolean equals(Object o) {
    return EqualsBuilder.reflectionEquals(this, o);
  }

  @Override
  public int hashCode() {
    return HashCodeBuilder.reflectionHashCode(this);
  }
}
