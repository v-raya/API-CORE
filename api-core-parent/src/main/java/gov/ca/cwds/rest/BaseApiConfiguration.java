package gov.ca.cwds.rest;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.dropwizard.Configuration;
import io.dropwizard.db.DataSourceFactory;
import io.dropwizard.flyway.FlywayFactory;
import java.util.HashMap;
import java.util.Map;
import javax.annotation.Nullable;
import org.hibernate.validator.constraints.NotEmpty;
import org.secnod.dropwizard.shiro.ShiroConfiguration;

public class BaseApiConfiguration extends MinimalApiConfiguration {

  @Nullable
  private DataSourceFactory nsDataSourceFactory;

  @Nullable
  private FlywayFactory flywayFactory;

  @Nullable
  private DataSourceFactory cmsDataSourceFactory;

  @Nullable
  private Map<String, ElasticsearchConfiguration> elasticsearchConfigurations = new HashMap<>();

  @Nullable
  private TriggerTablesConfiguration triggerTablesConfiguration;

  @JsonProperty
  public DataSourceFactory getNsDataSourceFactory() {
    return nsDataSourceFactory;
  }

  @JsonProperty
  public void setNsDataSourceFactory(DataSourceFactory dataSourceFactory) {
    this.nsDataSourceFactory = dataSourceFactory;
  }

  @JsonProperty
  public DataSourceFactory getCmsDataSourceFactory() {
    return cmsDataSourceFactory;
  }

  @JsonProperty
  public void setDataSourceFactoryLegacy(DataSourceFactory dataSourceFactory) {
    this.cmsDataSourceFactory = dataSourceFactory;
  }

  @JsonProperty
  public FlywayFactory getFlywayFactory() {
    return flywayFactory;
  }

  @JsonProperty
  public void setFlywayFactory(FlywayFactory flywayFactory) {
    this.flywayFactory = flywayFactory;
  }


  @JsonProperty(value = "elasticsearch")
  public Map<String, ElasticsearchConfiguration> getElasticsearchConfigurations() {
    return elasticsearchConfigurations;
  }

  @JsonProperty
  public void setElasticsearchConfigurations(
      Map<String, ElasticsearchConfiguration> elasticsearchConfigurations) {
    this.elasticsearchConfigurations = elasticsearchConfigurations;
  }

  @JsonProperty(value = "triggertables")
  public TriggerTablesConfiguration getTriggerTablesConfiguration() {
    return triggerTablesConfiguration;
  }
}
