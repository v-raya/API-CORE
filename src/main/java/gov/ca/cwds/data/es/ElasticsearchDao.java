package gov.ca.cwds.data.es;

import static com.google.common.base.Preconditions.checkArgument;

import java.io.ByteArrayOutputStream;
import java.io.Closeable;
import java.io.IOException;
import java.util.Optional;

import org.apache.commons.compress.utils.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.elasticsearch.action.WriteConsistencyLevel;
import org.elasticsearch.action.admin.indices.create.CreateIndexRequest;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchRequestBuilder;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.Client;
import org.elasticsearch.client.Requests;
import org.elasticsearch.cluster.metadata.IndexMetaData;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.xcontent.ToXContent;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.common.xcontent.XContentFactory;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.base.Strings;
import com.google.inject.Inject;

import gov.ca.cwds.rest.ElasticsearchConfiguration;

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

  private static final int DEFAULT_MAX_RESULTS = 100;

  private static final String DEFAULT_PERSON_MAPPING =
      "/elasticsearch/mapping/map_person_2x_snake.json";

  /**
   * Standard "people" index name.
   */
  public static final String DEFAULT_PERSON_IDX_NM = "people";

  /**
   * Standard "person" document name.
   */
  public static final String DEFAULT_PERSON_DOC_TYPE = "person";

  /**
   * Client is thread safe.
   */
  private Client client;

  private ElasticsearchConfiguration config;

  /**
   * Constructor.
   * 
   * @param client The ElasticSearch client
   * @param config ES configuration, includes default index alias and document type
   */
  @Inject
  public ElasticsearchDao(Client client, ElasticsearchConfiguration config) {
    this.client = client;
    this.config = config;
  }

  /**
   * Get the default alias, either from the configuration or the default constant.
   * 
   * @return default alias
   */
  protected String getDefaultAlias() {
    return this.config != null && StringUtils.isNotBlank(config.getElasticsearchAlias())
        ? config.getElasticsearchAlias() : DEFAULT_PERSON_IDX_NM;
  }

  /**
   * Get the default document type, either from the configuration or the default constant.
   * 
   * @return default document type
   */
  protected String getDefaultDocType() {
    return this.config != null && StringUtils.isNotBlank(config.getElasticsearchDocType())
        ? config.getElasticsearchDocType() : DEFAULT_PERSON_DOC_TYPE;
  }

  /**
   * Check whether Elasticsearch already has the chosen index.
   * 
   * @param index index name or alias
   * @return whether the index exists
   */
  public boolean doesIndexExist(final String index) {
    final IndexMetaData indexMetaData = getClient().admin().cluster()
        .state(Requests.clusterStateRequest()).actionGet().getState().getMetaData().index(index);
    return indexMetaData != null;
  }

  /**
   * Create an index and apply a mapping before blasting documents into it.
   * 
   * @param index index name or alias
   * @param numShards number of shards
   * @param numReplicas number of replicas
   * @throws IOException on disconnect, hang, etc.
   */
  public void createIndex(final String index, int numShards, int numReplicas) throws IOException {
    LOGGER.warn("CREATE ES INDEX {} with {} shards and {} replicas", index, numShards, numReplicas);
    final Settings indexSettings = Settings.settingsBuilder().put("number_of_shards", numShards)
        .put("number_of_replicas", numReplicas).build();
    CreateIndexRequest indexRequest = new CreateIndexRequest(index, indexSettings);
    getClient().admin().indices().create(indexRequest).actionGet();

    ByteArrayOutputStream out = new ByteArrayOutputStream();
    IOUtils.copy(this.getClass().getResourceAsStream(DEFAULT_PERSON_MAPPING), out);
    out.flush();
    final String mapping = out.toString();
    getClient().admin().indices().preparePutMapping(index).setType(getDefaultDocType())
        .setSource(mapping).get();
  }

  /**
   * Create an index, if needed, before blasting documents into it.
   * 
   * <p>
   * Defaults to 5 shards and 1 replica.
   * </p>
   * 
   * <p>
   * Method is intentionally synchronized to prevent race conditions and multiple attempts to create
   * the same index.
   * </p>
   * 
   * @param index index name or alias
   * @param optShards optional number of shards. Defaults to 5.
   * @param optReplicas optional number of replicas. Defaults to 1.
   * @throws InterruptedException if thread is interrupted
   * @throws IOException on disconnect, hang, etc.
   */
  public synchronized void createIndexIfNeeded(final String index,
      final Optional<Integer> optShards, final Optional<Integer> optReplicas)
      throws InterruptedException, IOException {

    final int shards = optShards.orElse(5);
    final int replicas = optReplicas.orElse(1);

    if (!doesIndexExist(index)) {
      LOGGER.warn("ES INDEX {} DOES NOT EXIST!", index);
      LOGGER.warn("Create index {} with {} shards and {} replicas", index, shards, replicas);
      createIndex(index, shards, replicas);

      // Let Elasticsearch catch its breath.
      Thread.sleep(2000);
      // Thread.currentThread().wait(2000L); // oops, thread monitor error. thanks SonarCube.
    } else {
      LOGGER.warn("INDEX {} already exists!", index);
    }
  }

  /**
   * Create an index, if missing.
   * 
   * @param index index name or alias
   * @throws InterruptedException if thread is interrupted
   * @throws IOException on disconnect, hang, etc.
   * @see #createIndexIfNeeded(String, Optional, Optional)
   */
  public synchronized void createIndexIfNeeded(final String index)
      throws InterruptedException, IOException {
    createIndexIfNeeded(index, Optional.<Integer>empty(), Optional.<Integer>empty());
  }

  /**
   * Create an ElasticSearch document with the given index and document type.
   * 
   * <p>
   * This method indexes a single document and is inefficient for bulk operations. See
   * {@link #bulkAdd(ObjectMapper, String, Object, String, String)}.
   * </p>
   * 
   * @param index to write store
   * @param documentType type to index as
   * @param document JSON of document
   * @param id the unique identifier
   * @return true if document is indexed false if updated
   * @throws ApiElasticSearchException exception on create document
   */
  public boolean index(final String index, final String documentType, final String document,
      final String id) throws ApiElasticSearchException {
    checkArgument(!Strings.isNullOrEmpty(index), "index cannot be Null or empty");
    checkArgument(!Strings.isNullOrEmpty(documentType), "documentType cannot be Null or empty");

    LOGGER.info("ElasticSearchDao.createDocument(): " + document);
    final IndexResponse response = client.prepareIndex(index, documentType, id)
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
   * @param alias index alias
   * @param docType document type
   * @return prepared IndexRequest
   * @throws JsonProcessingException if unable to serialize JSON
   */
  public IndexRequest bulkAdd(final ObjectMapper mapper, final String id, final Object obj,
      final String alias, final String docType) throws JsonProcessingException {
    return new IndexRequest(alias, docType, id).source(mapper.writeValueAsString(obj));
  }

  /**
   * Prepare an index request for bulk operations, using the default index name and person document
   * type.
   * 
   * @param mapper Jackson ObjectMapper configured for bulk operations
   * @param id ES document id
   * @param obj document object
   * @return prepared IndexRequest
   * @throws JsonProcessingException if unable to serialize JSON
   */
  public IndexRequest bulkAdd(final ObjectMapper mapper, final String id, final Object obj)
      throws JsonProcessingException {
    return bulkAdd(mapper, id, obj, getDefaultAlias(), getDefaultDocType());
  }

  /**
   * The Intake Auto-complete for Person takes a single search term, used to query Elasticsearch
   * Person documents on specific fields.
   * 
   * <p>
   * For example, search strings consisting of only digits could be phone numbers, social security
   * numbers, or street address numbers. Search strings consisting of only letters could be last
   * name, first name, city, state, language, and so forth.
   * </p>
   * 
   * <p>
   * This method calls Elasticsearch's <a href=
   * "https://www.elastic.co/guide/en/elasticsearch/guide/current/_best_fields.html#dis-max-query"
   * >"dis max"</a> query feature.
   * </p>
   * 
   * @param searchTerm ES search String
   * @param alias index alias
   * @param docType document type
   * @return array of AutoCompletePerson
   * @throws ApiElasticSearchException unable to connect, disconnect, bad hair day, etc.
   */
  public ElasticSearchPerson[] searchPerson(final String searchTerm, final String alias,
      final String docType) throws ApiElasticSearchException {
    checkArgument(!Strings.isNullOrEmpty(searchTerm), "searchTerm cannot be Null or empty");

    BoolQueryBuilder queryBuilder = buildBoolQueryFromSearchTerms(searchTerm);
    if (!queryBuilder.hasClauses()) {
      return new ElasticSearchPerson[0];
    }

    SearchRequestBuilder builder = client.prepareSearch(alias).setTypes(docType)
        .setQuery(queryBuilder).setFrom(0).setSize(DEFAULT_MAX_RESULTS)
        .addHighlightedField(ElasticSearchPerson.ESColumn.FIRST_NAME.getCol())
        .addHighlightedField(ElasticSearchPerson.ESColumn.LAST_NAME.getCol())
        .addHighlightedField(ElasticSearchPerson.ESColumn.GENDER.getCol())
        .addHighlightedField(ElasticSearchPerson.ESColumn.BIRTH_DATE.getCol())
        .addHighlightedField(ElasticSearchPerson.ESColumn.SSN.getCol())
        .setHighlighterNumOfFragments(3).setHighlighterRequireFieldMatch(true)
        .setHighlighterOrder("score").setExplain(true);

    LOGGER.warn("ES QUERY: {}", builder);
    final SearchHit[] hits = builder.execute().actionGet().getHits().getHits();

    final ElasticSearchPerson[] ret = new ElasticSearchPerson[hits.length];
    int counter = -1;
    for (SearchHit hit : hits) {
      ret[++counter] = ElasticSearchPerson.makeESPerson(hit);
    }

    return ret;
  }

  /**
   * @param searchTerm ES search String
   * @return array of AutoCompletePerson
   * @throws ApiElasticSearchException unable to connect, disconnect, bad hair day, etc.
   */
  public ElasticSearchPerson[] searchPerson(final String searchTerm)
      throws ApiElasticSearchException {
    return searchPerson(searchTerm, getDefaultAlias(), getDefaultDocType());
  }

  /**
   * Search given index by pass-through query.
   * 
   * @param index index to search
   * @param query user-provided query
   * @return JSON ES results
   */
  public String searchIndexByQuery(final String index, final String query) {
    LOGGER.warn(" index: {}", index);
    LOGGER.warn(" QUERY: {}", query);
    checkArgument(!Strings.isNullOrEmpty(query), "query cannot be Null or empty");
    checkArgument(!Strings.isNullOrEmpty(index), "index name cannot be Null or empty");
    SearchRequestBuilder builder = client.prepareSearch(index).setTypes(DEFAULT_PERSON_DOC_TYPE)
        .setQuery(QueryBuilders.wrapperQuery(query)).setFrom(0).setSize(DEFAULT_MAX_RESULTS);
    SearchResponse searchResponse = builder.execute().actionGet();
    String esResponse = "";

    try {
      XContentBuilder jsonBuilder = XContentFactory.jsonBuilder();

      jsonBuilder.startObject();
      searchResponse.toXContent(jsonBuilder, ToXContent.EMPTY_PARAMS);
      jsonBuilder.endObject();
      esResponse = jsonBuilder.string();
      jsonBuilder.close();
    } catch (IOException e) {
      final String msg = "Error in ElasticSearch json query builder: " + e.getMessage();
      LOGGER.error(msg, e);
      throw new ApiElasticSearchException(msg, e);
    }

    return esResponse;
  }

  /**
   * Builds an Elasticsearch compound query by combining multiple <b>should</b> clauses in a Bool
   * Query
   * 
   * @param searchTerm the user entered values to search for separated by space
   * @return the Elasticsearch compound query
   */
  public BoolQueryBuilder buildBoolQueryFromSearchTerms(String searchTerm) {
    final String s = searchTerm.trim().toLowerCase();
    String[] searchTerms = s.split("\\s+");
    BoolQueryBuilder queryBuilder = QueryBuilders.boolQuery();
    for (String term : searchTerms) {
      term = term.trim();
      if (StringUtils.isNotBlank(term)) {
        queryBuilder = ElasticSearchQuery.getQueryFromTerm(term).buildQuery(queryBuilder, term);
      }
    }
    return queryBuilder;
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
   * Exposed for testing. Don't abuse this.
   * 
   * @return the client
   */
  public Client getClient() {
    return client;
  }

  public ElasticsearchConfiguration getConfig() {
    return config;
  }

  public void setConfig(ElasticsearchConfiguration config) {
    this.config = config;
  }

}
