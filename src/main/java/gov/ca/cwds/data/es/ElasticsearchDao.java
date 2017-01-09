package gov.ca.cwds.data.es;

import java.net.InetAddress;

import org.apache.commons.lang3.StringUtils;
import org.elasticsearch.action.WriteConsistencyLevel;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchType;
import org.elasticsearch.client.Client;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.DummyTransportAddress;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.common.transport.TransportAddress;
import org.elasticsearch.index.query.DisMaxQueryBuilder;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.slf4j.LoggerFactory;

import com.google.inject.Inject;

import gov.ca.cwds.rest.ElasticsearchConfiguration;
import gov.ca.cwds.rest.api.domain.es.ESSearchRequest;
import gov.ca.cwds.rest.api.domain.es.ESSearchRequest.ESFieldSearchEntry;
import gov.ca.cwds.rest.api.domain.es.ESSearchRequest.ESSearchElement;
import gov.ca.cwds.rest.api.domain.es.ESSearchRequest.ElementType;

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
public class ElasticsearchDao {

  private static final org.slf4j.Logger LOGGER = LoggerFactory.getLogger(ElasticsearchDao.class);

  private static final int DEFAULT_MAX_RESULTS = 60;

  /**
   * Client is thread safe.
   */
  private Client client;

  private TransportAddress transportAddress;

  private final String host;
  private final String port;
  private final String clusterName;

  private String indexName;
  private String documentType;

  /**
   * Constructor. Construct from required fields.
   * 
   * @param host The ES host
   * @param port The port that the ES host is listening on
   * @param clusterName The ES cluster name
   */
  public ElasticsearchDao(String host, String port, String clusterName) {
    this.host = host;
    this.port = port;
    this.clusterName = clusterName;
  }

  /**
   * Constructor. Construct from YAML configuration.
   * 
   * @param config The ES configuration
   */
  @Inject
  public ElasticsearchDao(ElasticsearchConfiguration config) {
    this.host = config.getElasticsearchHost();
    this.clusterName = config.getElasticsearchCluster();
    this.port = config.getElasticsearchPort();
    this.indexName = config.getPeopleIndexName();
    this.documentType = config.getPeopleIndexType();
  }

  /**
   * Initialize ElasticSearch client once. Synchronized to prevent race conditions and multiple
   * connections.
   * 
   * @throws ApiElasticSearchException if host not found
   */
  protected synchronized void init() throws ApiElasticSearchException {
    if (this.client == null) {
      LOGGER.debug("ElasticSearchDao.init(): client is null. Initialize.");
      try {
        Settings settings = Settings.settingsBuilder().put("cluster.name", clusterName).build();
        this.client = TransportClient.builder().settings(settings).build()
            .addTransportAddress(this.transportAddress != null ? this.transportAddress
                : new InetSocketTransportAddress(InetAddress.getByName(host),
                    Integer.parseInt(port)));
      } catch (Exception e) {
        LOGGER.error("Error initializing Elasticsearch client: {}", e.getMessage(), e);
        throw new ApiElasticSearchException(
            "Error initializing Elasticsearch client: " + e.getMessage(), e);
      }
    }
  }

  /**
   * Start the ElasticSearch client, if not started already.
   * 
   * <p>
   * This method calls the synchronized method, {@link #init()}, only if the underlying
   * {@link #client} is not initialized.
   * </p>
   * 
   * @throws ApiElasticSearchException I/O error or unknown host
   * @see #init()
   */
  protected void start() throws ApiElasticSearchException {
    LOGGER.trace("ElasticSearchDao.start()");

    // Enter synchronized init method ONLY if client is not initialized.
    if (this.client == null) {
      LOGGER.debug("ElasticSearchDao.start(): call init()");
      init();
    }
  }

  /**
   * Only stop the ElasticSearch client, when the container stops or if the connection becomes
   * unhealthy.
   * 
   * <p>
   * Thread safe.
   * </p>
   * 
   * @throws ApiElasticSearchException if ElasticSearch client fails to close properly.
   */
  protected synchronized void stop() throws ApiElasticSearchException {
    if (this.client != null) {
      LOGGER.debug("ElasticSearchDao.stop()");
      this.client.close();
      setClient(null);
      setTransportAddress(null);
    }
  }

  /**
   * Stops and starts the ES client. If the client passed in is null, then {@link #init()} will
   * instantiate a new client with {@link InetSocketTransportAddress}.
   * 
   * @param client custom client, used for testing
   * @throws ApiElasticSearchException I/O error or unknown host
   */
  public void reset(Client client) throws ApiElasticSearchException {
    LOGGER.debug("ElasticSearchDao.stop()");
    stop();
    setClient(client);
    setTransportAddress(null);
    start();
  }

  /**
   * Create an ElasticSearch document with the given index and document type.
   * 
   * @param document JSON of document
   * @param id the unique identifier
   * @return true if document is indexed false if updated
   * @throws ApiElasticSearchException exception on create document
   */
  public boolean index(String document, String id) throws ApiElasticSearchException {
    LOGGER.info("ElasticSearchDao.createDocument(): " + document);
    start();
    IndexResponse response = client.prepareIndex(indexName, documentType, id)
        .setConsistencyLevel(WriteConsistencyLevel.DEFAULT).setSource(document).execute()
        .actionGet();

    LOGGER.info("Created document:\nindex: " + response.getIndex() + "\ndoc type: "
        + response.getType() + "\nid: " + response.getId() + "\nversion: " + response.getVersion()
        + "\ncreated: " + response.isCreated());

    return response.isCreated();
  }

  /**
   * Returns ALL Person documents in the target ElasticSearch index, up the maximum number of rows
   * defined by {@link #DEFAULT_MAX_RESULTS}. Initializes and starts ElasticSearch client, if not
   * started.
   * 
   * <p>
   * This method intentionally returns raw ElasticSearch {@link SearchHit}. Calling services should
   * convert data to appropriate classes.
   * </p>
   * 
   * <p>
   * SAMPLE HIT:
   * </p>
   * 
   * <blockquote>{updated_at=2016-11-23-09.09.15.930, gender=Male, birth_date=1990-04-01,
   * created_at=2016-11-23-09.09.15.953, last_name=Simpson, id=100, first_name=Bart, ssn=999551111}
   * </blockquote>
   * 
   * @return array of generic ElasticSearch {@link SearchHit}
   * @throws ApiElasticSearchException unable to connect to ElasticSearch, or client disconnects
   *         unexpectedly, etc.
   */
  public SearchHit[] fetchAllPerson() throws ApiElasticSearchException {
    start();

    return client.prepareSearch(indexName).setTypes(documentType)
        .setSearchType(SearchType.QUERY_AND_FETCH).setFrom(0).setSize(DEFAULT_MAX_RESULTS)
        .setExplain(true).execute().actionGet().getHits().getHits();
  }

  /**
   * Logical OR query for Person. Initializes and starts ElasticSearch client, if not started.
   * 
   * @param req boolean hierarchy search request
   * @return array of raw ElasticSearch hits
   * @throws ApiElasticSearchException unable to connect, disconnect, bad hair day, etc.
   */
  public SearchHit[] queryPersonOr(ESSearchRequest req) throws ApiElasticSearchException {
    start();

    String field = "";
    String value = "";
    for (ESSearchElement elem : req.getRoot().getElems()) {
      if (elem.getElementType() == ElementType.FIELD_TERM) {
        ESFieldSearchEntry fieldSearch = (ESFieldSearchEntry) elem;
        field = fieldSearch.getField();
        value = fieldSearch.getValue();
      }
    }

    QueryBuilder qb;
    if ((value.contains("*") || value.contains("?"))
        && (!value.startsWith("?") && !value.startsWith("*"))) {
      qb = QueryBuilders.wildcardQuery(field, value);
    } else {
      qb = QueryBuilders.matchQuery(field, value);
    }

    return client.prepareSearch(indexName).setTypes(documentType)
        .setSearchType(SearchType.QUERY_AND_FETCH).setQuery(qb).setFrom(0)
        .setSize(DEFAULT_MAX_RESULTS).setExplain(true).execute().actionGet().getHits().getHits();
  }

  /**
   * The Intake Auto-complete for Person takes a single search term, which is used to query
   * Elasticsearch Person documents by ALL relevant fields.
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
   * @param req ES search request
   * @return array of AutoCompletePerson
   * @throws ApiElasticSearchException unable to connect, disconnect, bad hair day, etc.
   */
  public ElasticSearchPerson[] autoCompletePerson(final String req)
      throws ApiElasticSearchException {
    start();

    final String s = req.trim().toLowerCase();

    // SAMPLE AUTO-COMPLETE RESULT: (Intake assumes all fields are potentially searchable.)
    // [{
    // "id": 1,
    // "date_of_birth": "1964-01-14",
    // "first_name": "John",
    // "gender": null,
    // "last_name": "Smith",
    // "middle_name": null,
    // "ssn": "858584561",
    // "name_suffix": null,
    // "addresses": [
    // {
    // "city": "city",
    // "id": 6,
    // "state": "IN",
    // "street_address": "876 home",
    // "zip": "66666",
    // "type": "Placement"
    // }
    // ],
    // "phone_numbers": [],
    // "languages": []
    // }]

    // SAMPLE ELASTICSEARCH PERSON DOCUMENT:
    // {
    // "first_name": "Todd",
    // "last_name": "B.",
    // "gender": "",
    // "birth_date": "",
    // "ssn": "",
    // "id": "TZDqRCG0XH",
    // "type": "gov.ca.cwds.data.persistence.cms.Reporter",
    // "source_object": {
    // "lastUpdatedId": "0XH",
    // "lastUpdatedTime": 1479394080309,
    // "referralId": "TZDqRCG0XH",
    // "badgeNumber": "",
    // "cityName": "Police",
    // "colltrClientRptrReltnshpType": 0,
    // "communicationMethodType": 410,
    // "confidentialWaiverIndicator": "Y",
    // "employerName": "test name",
    // "feedbackDate": 1479340800000,
    // "feedbackRequiredIndicator": "Y",
    // "firstName": "Todd",
    // "lastName": "B.",
    // "mandatedReporterIndicator": "Y",
    // "messagePhoneExtensionNumber": 0,
    // "messagePhoneNumber": 0,
    // "middleInitialName": "",
    // "namePrefixDescription": "Mr.",
    // "primaryPhoneNumber": 4650009886,
    // "primaryPhoneExtensionNumber": 0,
    // "stateCodeType": 1828,
    // "streetName": "Mock Plaza",
    // "streetNumber": "2323",
    // "suffixTitleDescription": "",
    // "zipNumber": 95656,
    // "zipSuffixNumber": 0,
    // "countySpecificCode": "28",
    // "primaryKey": "TZDqRCG0XH"
    // }
    // },

    // TODO: #136994539: translate county and state code to SYS ID and vice versa.

    DisMaxQueryBuilder qb = QueryBuilders.disMaxQuery();
    if (StringUtils.isNumeric(s)) {
      // Only query numeric fields.
      addNumericPersonPrefixQueries(qb, s);
    } else if (StringUtils.isAlpha(s)) {
      // Only query alphabetic fields.
      addNonNumericPersonPrefixQueries(qb, s);
    } else {
      // Query on all searchable fields.
      addNumericPersonPrefixQueries(qb, s);
      addNonNumericPersonPrefixQueries(qb, s);
    }

    final SearchHit[] hits = client.prepareSearch(indexName).setTypes(documentType).setQuery(qb)
        .setFrom(0).setSize(DEFAULT_MAX_RESULTS).setExplain(true).execute().actionGet().getHits()
        .getHits();

    final ElasticSearchPerson[] persons = new ElasticSearchPerson[hits.length];
    int counter = -1;
    for (SearchHit hit : hits) {
      persons[++counter] = ElasticSearchPerson.makeESPerson(hit);
    }

    return persons;
  }

  /**
   * Build a prefix query on numeric Person fields.
   * 
   * @param qb DisMaxQueryBuilder
   * @param s search term
   * @return DisMaxQueryBuilder
   */
  protected DisMaxQueryBuilder addNumericPersonPrefixQueries(DisMaxQueryBuilder qb, String s) {
    return qb.add(QueryBuilders.prefixQuery("birth_date", s))
        .add(QueryBuilders.prefixQuery("ssn", s))
        .add(QueryBuilders.prefixQuery("primaryPhoneNumber", s))
        .add(QueryBuilders.prefixQuery("zipNumber", s))
        .add(QueryBuilders.prefixQuery("streetNumber", s));
  }

  /**
   * Build a prefix query on non-numeric Person fields.
   * 
   * @param qb DisMaxQueryBuilder
   * @param s search term
   * @return DisMaxQueryBuilder
   */
  protected DisMaxQueryBuilder addNonNumericPersonPrefixQueries(DisMaxQueryBuilder qb, String s) {
    return qb.add(QueryBuilders.prefixQuery("first_name", s))
        .add(QueryBuilders.prefixQuery("last_name", s)).add(QueryBuilders.prefixQuery("gender", s))
        .add(QueryBuilders.prefixQuery("cityName", s))
        .add(QueryBuilders.prefixQuery("streetName", s));
  }

  // ===================
  // ACCESSORS:
  // ===================

  /**
   * @return the client
   */
  public Client getClient() {
    return client;
  }

  /**
   * @return the host
   */
  public String getHost() {
    return host;
  }

  /**
   * @return the port
   */
  public String getPort() {
    return port;
  }

  /**
   * @return the clusterName
   */
  public String getClusterName() {
    return clusterName;
  }

  /**
   * @param indexName the indexName to set
   */
  public void setIndexName(String indexName) {
    if (StringUtils.isNotBlank(indexName)) {
      this.indexName = indexName;
    } else {
      throw new ApiElasticSearchException("Elasticsearch Index Name must be provided");
    }
  }

  /**
   * Set the default index (document) type.
   * 
   * @param docType the indexType to set
   */
  public void setDocumentType(String docType) {
    if (StringUtils.isNotBlank(docType)) {
      this.documentType = docType;
    } else {
      throw new ApiElasticSearchException("Elasticsearch Index Type must be provided");
    }
  }

  /**
   * Default index name
   * 
   * @return index name
   */
  public String getIndexName() {
    return indexName;
  }

  /**
   * Default index (document) type
   * 
   * @return default document type
   */
  public String getDocumentType() {
    return documentType;
  }

  /**
   * Used for testing but can be used to set a custom ES client.
   * 
   * @param client ElasticSearch client, typically a {@link TransportClient}
   */
  public void setClient(Client client) {
    this.client = client;
  }

  /**
   * Get the {@link TransportAddress}, primarily used for testing. See
   * {@link DummyTransportAddress}.
   * 
   * @return current TransportAddress, if any, such {@link DummyTransportAddress}.
   */
  public TransportAddress getTransportAddress() {
    return transportAddress;
  }

  /**
   * Set the {@link TransportAddress}, primarily used for testing. See
   * {@link DummyTransportAddress}.
   * 
   * @param transportAddress TransportAddress, if any, such {@link DummyTransportAddress}.
   */
  public void setTransportAddress(TransportAddress transportAddress) {
    this.transportAddress = transportAddress;
  }

}
