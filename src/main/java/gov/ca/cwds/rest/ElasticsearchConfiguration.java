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

  @NotNull
  @JsonProperty("elasticsearch.alias")
  private String elasticsearchAlias;

  @NotNull
  @JsonProperty("elasticsearch.doctype")
  private String elasticsearchDocType;

  /**
   * Default constructor.
   */
  public ElasticsearchConfiguration() {
    // Default.
  }

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

  /**
   * @return the elasticsearchAlias
   */
  public String getElasticsearchAlias() {
    return elasticsearchAlias;
  }

  /**
   * @return the elasticsearchDocType
   */
  public String getElasticsearchDocType() {
    return elasticsearchDocType;
  }
}
