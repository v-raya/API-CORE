package gov.ca.cwds.data.es;

import static com.google.common.base.Preconditions.checkArgument;

import java.io.Closeable;
import java.io.IOException;

import org.elasticsearch.action.WriteConsistencyLevel;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.client.Client;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.base.Strings;
import com.google.inject.Inject;

/**
 * A DAO for Elasticsearch.
 * 
 * <p>
 * OPTION: In order to avoid minimize connections to Elasticsearch, this DAO class should either be
 * final, so that other classes cannot instantiate a client or else the ES client should be injected
 * by the framework.
 * </p>
 * 
 * <p>
 * OPTION: allow child DAO classes to connect to a configured index of choice and read specified
 * document type(s).
 * </p>
 * 
 * @author CWDS API Team
 */
public class ElasticsearchDao implements Closeable {

  private static final org.slf4j.Logger LOGGER = LoggerFactory.getLogger(ElasticsearchDao.class);

  private static final int DEFAULT_MAX_RESULTS = 60;

  public static final String DEFAULT_PERSON_IDX_NM = "people";
  public static final String DEFAULT_PERSON_DOC_TYPE = "person";

  /**
   * Client is thread safe.
   */
  private Client client;

  /**
   * Constructor.
   * 
   * @param client The elasticsearch client
   */
  @Inject
  public ElasticsearchDao(Client client) {
    this.client = client;
  }

  /**
   * Create an ElasticSearch document with the given index and document type.
   * 
   * @param index to write store
   * @param documentType type to index as
   * @param document JSON of document
   * @param id the unique identifier
   * @return true if document is indexed false if updated
   * @throws ApiElasticSearchException exception on create document
   */
  public boolean index(String index, String documentType, String document, String id)
      throws ApiElasticSearchException {
    checkArgument(!Strings.isNullOrEmpty(index), "index cannot be Null or empty");
    checkArgument(!Strings.isNullOrEmpty(documentType), "documentType cannot be Null or empty");

    LOGGER.info("ElasticSearchDao.createDocument(): " + document);

    IndexResponse response = client.prepareIndex(index, documentType, id)
        .setConsistencyLevel(WriteConsistencyLevel.DEFAULT).setSource(document).execute()
        .actionGet();

    boolean created = response.isCreated();
    if (created) {
      LOGGER.info("Created document:\nindex: " + response.getIndex() + "\ndoc type: "
          + response.getType() + "\nid: " + response.getId() + "\nversion: " + response.getVersion()
          + "\ncreated: " + response.isCreated());
      LOGGER.info("Created document --- index:{}, doc type:{},id:{},version:{},created:{}",
          response.getIndex(), response.getType(), response.getId(), response.getVersion(),
          response.isCreated());
    } else {
      LOGGER.warn("Document not created --- index:{}, doc type:{},id:{},version:{},created:{}",
          response.getIndex(), response.getType(), response.getId(), response.getVersion(),
          response.isCreated());
    }

    return created;
  }

  /**
   * Prepare an index request for bulk operations.
   * 
   * @param mapper Jackson ObjectMapper
   * @param id ES document id
   * @param obj document object
   * @return prepared IndexRequest
   * @throws JsonProcessingException if unable to serialize JSON
   */
  public IndexRequest bulkAdd(final ObjectMapper mapper, final String id, final Object obj)
      throws JsonProcessingException {
    return new IndexRequest(ElasticsearchDao.DEFAULT_PERSON_IDX_NM,
        ElasticsearchDao.DEFAULT_PERSON_DOC_TYPE, id).source(mapper.writeValueAsString(obj));
  }

  /**
   * The Intake Auto-complete for Person takes a single search term, used to query Elasticsearch
   * Person documents on ALL relevant fields.
   * 
   * <p>
   * For example, search strings consisting of only digits could be phone numbers, social security
   * numbers, or street address numbers. Search strings consisting of only letters could be last
   * name, first name, city, state, language, and so forth.
   * </p>
   * 
   * <p>
   * This method calls Elasticsearch's <a href=
   * "https://www.elastic.co/guide/en/elasticsearch/guide/current/_best_fields.html#dis-max-query">"dis
   * max"</a> query feature.
   * </p>
   * 
   * @param searchTerm ES search String
   * @return array of AutoCompletePerson
   * @throws ApiElasticSearchException unable to connect, disconnect, bad hair day, etc.
   */
  // TODO : #139105623
  public ElasticSearchPerson[] searchPerson(final String searchTerm)
      throws ApiElasticSearchException {
    checkArgument(!Strings.isNullOrEmpty(searchTerm), "searchTerm cannot be Null or empty");
    final String s = searchTerm.trim().toLowerCase();

    final SearchHit[] hits = client.prepareSearch(DEFAULT_PERSON_IDX_NM)
        .setTypes(DEFAULT_PERSON_DOC_TYPE).setQuery(QueryBuilders.queryStringQuery(s)).setFrom(0)
        .setSize(DEFAULT_MAX_RESULTS).setExplain(true).execute().actionGet().getHits().getHits();

    final ElasticSearchPerson[] ret = new ElasticSearchPerson[hits.length];
    int counter = -1;
    for (SearchHit hit : hits) {
      ret[++counter] = ElasticSearchPerson.makeESPerson(hit);
    }

    return ret;
  }

  // TODO : #139105623
  public IndexRequest prepareIndexRequest(String document, String id) {
    return client.prepareIndex(DEFAULT_PERSON_IDX_NM, DEFAULT_PERSON_DOC_TYPE, id)
        .setConsistencyLevel(WriteConsistencyLevel.DEFAULT).setSource(document).request();
  }

  /**
   * Stop the ES client, if started.
   */
  public void stop() {
    if (client != null) {
      this.client.close();
    }
  }

  @Override
  public void close() throws IOException {
    try {
      stop();
    } catch (Exception e) {
      final String msg = "Error closing ElasticSearch DAO: " + e.getMessage();
      LOGGER.error(msg, e);
      throw new IOException(msg, e);
    }
  }

  /**
   * @return the client
   */
  public Client getClient() {
    return client;
  }

}
