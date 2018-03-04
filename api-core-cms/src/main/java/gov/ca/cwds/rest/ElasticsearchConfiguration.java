package gov.ca.cwds.rest;

import java.util.Map;
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
  @JsonProperty("elasticsearch.cluster")
  private String elasticsearchCluster;

  @NotNull
  @JsonProperty("elasticsearch.host")
  private String elasticsearchHost;

  @NotNull
  @JsonProperty("elasticsearch.port")
  private String elasticsearchPort;

  @NotNull
  @JsonProperty("elasticsearch.additional.hosts")
  private Map<String, String> additionalHosts;

  @NotNull
  @JsonProperty("elasticsearch.additional.ports")
  private Map<String, String> additionalPorts;

  @NotNull
  @JsonProperty("elasticsearch.alias")
  private String elasticsearchAlias;

  @NotNull
  @JsonProperty("elasticsearch.doctype")
  private String elasticsearchDocType;

  @JsonProperty("elasticsearch.index.setting.file")
  private String indexSettingFile;

  @JsonProperty("elasticsearch.document.mapping.file")
  private String documentMappingFile;

  @JsonProperty("elasticsearch.xpack.user")
  private String user;

  @JsonProperty("elasticsearch.xpack.password")
  private String password;

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
   * @return the list of additional elasticsearch ports for clustering support
   */
  public String getElasticsearchPort() {
    return elasticsearchPort;
  }

  /**
   * @return the list of additional elasticsearch hosts for clustering support
   */
  public Map<String, String> getAdditionalHosts() {
    return additionalHosts;
  }

  public Map<String, String> getAdditionalPorts() {
    return additionalPorts;
  }

  /**
   * @return the elasticsearchCluster
   */
  public String getElasticsearchCluster() {
    return elasticsearchCluster;
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

  /**
   * Get the xpack user
   * 
   * @return The xpack user
   */
  public String getUser() {
    return user;
  }

  /**
   * Get xpack user password
   * 
   * @return The xpack user password
   */
  public String getPassword() {
    return password;
  }

  /**
   * Get index setting file
   * 
   * @return The index setting file
   */
  public String getIndexSettingFile() {
    return indexSettingFile;
  }

  /**
   * Get the index/document mapping file
   * 
   * @return The index/document mapping file
   */
  public String getDocumentMappingFile() {
    return documentMappingFile;
  }
}
