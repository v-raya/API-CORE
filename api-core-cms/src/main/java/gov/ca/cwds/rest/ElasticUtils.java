package gov.ca.cwds.rest;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.transport.client.PreBuiltTransportClient;
import org.elasticsearch.xpack.client.PreBuiltXPackTransportClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Intake Team 4
 */
public class ElasticUtils {
  private static final Logger LOGGER = LoggerFactory.getLogger(ElasticUtils.class);

  private ElasticUtils(){}

  public static TransportClient makeESTransportClient(final ElasticsearchConfiguration config) {
    TransportClient client = null;
    String cluster = config.getElasticsearchCluster();
    String user = config.getUser();
    String password = config.getPassword();
    boolean secureClient = StringUtils.isNotBlank(user) && StringUtils.isNotBlank(password);
    LOGGER.info("Create NEW ES client");
    try {
      final Settings.Builder settings = Settings.builder().put("cluster.name", cluster);
      settings.put("client.transport.sniff", true);
      if (secureClient) {
        LOGGER.info("ENABLE X-PACK - cluster: {}", cluster);
        settings.put("xpack.security.user", user + ":" + password);
        client = new PreBuiltXPackTransportClient(settings.build());
      } else {
        LOGGER.info("DISABLE X-PACK - cluster: {}", cluster);
        client = new PreBuiltTransportClient(settings.build());
      }

      for (InetSocketTransportAddress address : getValidatedESNodes(config)) {
        client.addTransportAddress(address);
      }

    } catch (Exception e) {
      LOGGER.error("Error initializing Elasticsearch client: {}", e.getMessage(), e);
      if (client != null) {
        client.close();
        client = null;
      }
    }
    return client;
  }

  private static List<InetSocketTransportAddress> getValidatedESNodes(ElasticsearchConfiguration config) {
    List<InetSocketTransportAddress> nodesList = new LinkedList<>();
    String[] params;
    Map<String, String> hostPortMap = new HashMap<>();

    hostPortMap.put(config.getElasticsearchHost(), config.getElasticsearchPort());
    if (config.getNodes() != null) {
      for (String node : config.getNodes()) {
        params = node.split(":");
        hostPortMap.put(params[0], params[1]);
      }
    }

    hostPortMap.forEach((host, port) -> {
      if ((null != host) && (null != port)) {
        LOGGER.info("Adding new ES Node host:[{}] port:[{}] to elasticsearch client", host, port);
        try {
          nodesList.add(new InetSocketTransportAddress(InetAddress.getByName(host), Integer.parseInt(port)));
        } catch (UnknownHostException e) {
          LOGGER.error("Error adding Node: {}", e.getMessage(), e);
        }
      }
    });

    return nodesList;
  }
}
