package gov.ca.cwds.rest;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Nullable;

import com.fasterxml.jackson.annotation.JsonProperty;

import gov.ca.cwds.data.persistence.XADataSourceFactory;
import io.dropwizard.db.DataSourceFactory;
import io.dropwizard.flyway.FlywayFactory;

public class BaseApiConfiguration extends MinimalApiConfiguration {

  @Nullable
  private DataSourceFactory nsDataSourceFactory;

  @Nullable
  private FlywayFactory flywayFactory;

  @Nullable
  private DataSourceFactory cmsDataSourceFactory;

  @Nullable
  private XADataSourceFactory xaNsDataSourceFactory;

  @Nullable
  private XADataSourceFactory xaCmsDataSourceFactory;

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

  @JsonProperty
  public XADataSourceFactory getXaCmsDataSourceFactory() {
    return xaCmsDataSourceFactory;
  }

  @JsonProperty
  public void setXaCmsDataSourceFactory(XADataSourceFactory xaCmsDataSourceFactory) {
    this.xaCmsDataSourceFactory = xaCmsDataSourceFactory;
  }

  @JsonProperty
  public XADataSourceFactory getXaNsDataSourceFactory() {
    return xaNsDataSourceFactory;
  }

  @JsonProperty
  public void setXaNsDataSourceFactory(XADataSourceFactory xaNsDataSourceFactory) {
    this.xaNsDataSourceFactory = xaNsDataSourceFactory;
  }

}
