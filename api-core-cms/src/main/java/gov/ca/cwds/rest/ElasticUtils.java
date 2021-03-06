package gov.ca.cwds.rest;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import gov.ca.cwds.rest.api.ApiException;
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

  private ElasticUtils() {
  }

  public static TransportClient buildElasticsearchClient(final ElasticsearchConfiguration config) {
    TransportClient client = null;
    try {
      client = makeESTransportClient(config);

      for (InetSocketTransportAddress address : getValidatedESNodes(config)) {
        client.addTransportAddress(address);
      }
      return client;
    } catch (Exception e) {
      LOGGER.error("Error initializing Elasticsearch client: {}", e.getMessage(), e);
      if (client != null) {
        client.close();
      }
      throw new ApiException(e);
    }
  }


  private static TransportClient makeESTransportClient(final ElasticsearchConfiguration config) {
    TransportClient client;
    final String cluster = config.getElasticsearchCluster();
    final String user = config.getUser();
    final String password = config.getPassword();
    final boolean secureClient = StringUtils.isNotBlank(user) && StringUtils.isNotBlank(password);

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

    return client;
  }

  @SuppressWarnings("fb-contrib:CLI_CONSTANT_LIST_INDEX")
  private static List<InetSocketTransportAddress> getValidatedESNodes(ElasticsearchConfiguration config) {
    final List<InetSocketTransportAddress> addressList = new ArrayList<>();
    final List<String> nodesList = new ArrayList<>();

    //If provided use Host and Port as a first node. For backward compatibility
    if (config.getElasticsearchHost() != null && config.getElasticsearchPort() != null) {
      nodesList.add(config.getElasticsearchHost().concat(":").concat(config.getElasticsearchPort()));
    }

    //Comma "," separated List of host:port pairs provided in configuration file.
    //Example: host1:port1,host2:port2,...etc.
    if (config.getElasticsearchNodes() != null) {
      nodesList.addAll(Arrays.asList(config.getElasticsearchNodes().split(",")));
    }

    //remove duplicates if any.
    final Map<String, String[]> nodesMap = new HashMap<>(nodesList.size());
    for (String node : nodesList) {
      nodesMap.put(node, node.split(":"));
    }

    for (Map.Entry<String, String[]> entry : nodesMap.entrySet()) {
      String[] hostPort = entry.getValue();
      if (hostPort.length >= 2) {
        LOGGER.info("Adding new ES Node host:[{}] port:[{}] to elasticsearch client", hostPort[0], hostPort[1]);
        try {
          addressList.add(new InetSocketTransportAddress(InetAddress.getByName(hostPort[0]), Integer.parseInt(hostPort[1])));
        } catch (UnknownHostException e) {
          LOGGER.error("Error adding Node: {}", e.getMessage(), e);
        }

      }
    }


    return addressList;
  }


}
