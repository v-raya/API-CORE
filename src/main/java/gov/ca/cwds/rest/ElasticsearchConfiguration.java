package gov.ca.cwds.rest;

import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonProperty;

import gov.ca.cwds.data.es.ElasticsearchDao;

/**
 * Represents the configuration settings for {@link ElasticsearchDao}.
 * 
 * @author CWDS API Team
 */
public class ElasticsearchConfiguration {

  @NotNull
  @JsonProperty("elasticsearch.host")
  private String elasticsearchHost;

  @NotNull
  @JsonProperty("elasticsearch.cluster")
  private String elasticsearchCluster;

  @NotNull
  @JsonProperty("elasticsearch.port")
  private String elasticsearchPort;

  /**
   * @return the elasticsearchHost
   */
  public String getElasticsearchHost() {
    return elasticsearchHost;
  }

  /**
   * @return the elasticsearchCluster
   */
  public String getElasticsearchCluster() {
    return elasticsearchCluster;
  }

  /**
   * @return the elasticsearchPort
   */
  public String getElasticsearchPort() {
    return elasticsearchPort;
  }
}
