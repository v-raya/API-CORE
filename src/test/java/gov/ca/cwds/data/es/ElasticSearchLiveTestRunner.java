package gov.ca.cwds.data.es;

import java.io.File;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;

import gov.ca.cwds.rest.ElasticsearchConfiguration;
import gov.ca.cwds.rest.api.ApiException;

/**
 * Runs live tests of ElasticSearch modules.
 * 
 * @author CWDS API Team
 */
public class ElasticSearchLiveTestRunner implements Runnable {

  private static final Logger LOGGER = LogManager.getLogger(ElasticSearchLiveTestRunner.class);

  private static final ObjectMapper YAML_MAPPER = new ObjectMapper(new YAMLFactory());
  private static final ObjectMapper MAPPER = new ObjectMapper();

  private ElasticsearchDao dao;

  public ElasticSearchLiveTestRunner(ElasticsearchDao dao) {
    this.dao = dao;
  }

  public static void main(String... args) throws Exception {

    if (args.length < 1) {
      throw new ApiException(
          "Usage: java " + ElasticSearchLiveTestRunner.class.getName() + " <ES config file>");
    }

    final File file = new File(args[0]);
    gov.ca.cwds.rest.ElasticsearchConfiguration configuration =
        YAML_MAPPER.readValue(file, ElasticsearchConfiguration.class);

    ElasticSearchLiveTestRunner job =
        new ElasticSearchLiveTestRunner(new ElasticsearchDao(configuration));

    try {
      job.run();
    } catch (Exception e) {
      LOGGER.error("Oops!", e);
    }
  }

  @Override
  public void run() {
    try {

      final ElasticSearchPerson[] hits = dao.autoCompletePerson("bart");
      if (hits != null && hits.length > 0) {
        for (ElasticSearchPerson hit : hits) {
          LOGGER.info(hit);
        }
      }

    } catch (Exception e) {
      throw new ApiException(e);
    } finally {
      // TODO: #136701343: exception handling in service layer.
      try {
        dao.stop();
      } catch (Exception e) {
        e.printStackTrace();
      }
    }
  }

}

