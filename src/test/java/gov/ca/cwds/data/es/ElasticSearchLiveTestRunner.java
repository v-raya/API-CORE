package gov.ca.cwds.data.es;

import java.io.IOException;
import java.io.InputStream;
import java.net.InetAddress;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.elasticsearch.client.Client;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import com.google.inject.Inject;

import gov.ca.cwds.rest.ElasticsearchConfiguration;
import gov.ca.cwds.rest.api.ApiException;
import io.dropwizard.configuration.ConfigurationSourceProvider;
import io.dropwizard.configuration.EnvironmentVariableSubstitutor;
import io.dropwizard.configuration.FileConfigurationSourceProvider;
import io.dropwizard.configuration.SubstitutingSourceProvider;
import io.dropwizard.jackson.Jackson;

/**
 * Runs live tests of ElasticSearch modules.
 * 
 * <p>
 * Reason For Existence. Since ore Elasticsearch components are shared by CWDS sub-systems, they are
 * housed in api-core. However, api-core itself lacked its own live test runner, requiring
 * developers to repeatedly check in code to api-core in order to test on other projects. Therefore,
 * this test class was added to better test core Elasticsearch code and reduce commit churn.
 * </p>
 * 
 * @author CWDS API Team
 */
public class ElasticSearchLiveTestRunner implements Runnable {

  private static final Logger LOGGER = LogManager.getLogger(ElasticSearchLiveTestRunner.class);

  private static final ObjectMapper YAML_MAPPER = new ObjectMapper(new YAMLFactory());

  private static final ObjectMapper MAPPER = Jackson.newObjectMapper();

  private static final class AutoCloseElasticsearchDao extends ElasticsearchDao
      implements AutoCloseable {

    /**
     * Constructor. Construct from YAML configuration.
     * 
     * @param config The ES configuration
     */
    @SuppressWarnings("javadoc")
    @Inject
    public AutoCloseElasticsearchDao(Client client) {
      super(client, new ElasticsearchConfiguration());
    }

    @Override
    public void close() throws IOException {
      stop();
    }

  }

  private ElasticsearchDao dao;

  private String searchTerm;

  /**
   * Protected constructor. Only this class and its children can call it.
   * 
   * @param dao ES DAO
   */
  protected ElasticSearchLiveTestRunner(ElasticsearchDao dao, String searchTerm) {
    this.dao = dao;
    this.searchTerm = searchTerm.trim().toLowerCase();
  }

  /**
   * Let 'er rip!
   * 
   * @param args command line
   * @throws Exception Exception rises to the top
   */
  public static void main(String... args) throws Exception {

    if (args.length < 2) {
      throw new ApiException("Usage: java " + ElasticSearchLiveTestRunner.class.getName()
          + " <ES config file> <search terms>");
    }

    final String path = args[0];
    final String searchFor = args[1];

    final ConfigurationSourceProvider provider = new SubstitutingSourceProvider(
        new FileConfigurationSourceProvider(), new EnvironmentVariableSubstitutor(false));

    ElasticsearchConfiguration config;
    try (InputStream iss = provider.open(path)) {
      config = YAML_MAPPER.readValue(iss, ElasticsearchConfiguration.class);
    }

    try (AutoCloseElasticsearchDao autoCloseDao =
        new AutoCloseElasticsearchDao(elasticsearchClient(config))) {
      ElasticSearchLiveTestRunner job = new ElasticSearchLiveTestRunner(autoCloseDao, searchFor);
      job.run();
    }

  }

  @Override
  public void run() {
    try {

      final ElasticSearchPerson[] hits = dao.searchPerson(this.searchTerm);
      if (hits != null && hits.length > 0) {
        for (ElasticSearchPerson hit : hits) {
          String esp = MAPPER.writerWithDefaultPrettyPrinter().writeValueAsString(hit);
          LOGGER.info(esp);
        }
      }

    } catch (Exception e) {
      LOGGER.error("Well, this is awkward.\n******* ERROR ******* " + e.getMessage(), e);
      throw new ApiException(e);
    }
  }

  private static Client elasticsearchClient(ElasticsearchConfiguration config) throws Exception {
    Settings settings =
        Settings.settingsBuilder().put("cluster.name", config.getElasticsearchCluster()).build();
    return TransportClient.builder().settings(settings).build().addTransportAddress(
        new InetSocketTransportAddress(InetAddress.getByName(config.getElasticsearchHost()),
            Integer.parseInt(config.getElasticsearchPort())));
  }

}

