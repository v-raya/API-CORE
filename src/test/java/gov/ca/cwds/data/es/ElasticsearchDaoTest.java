package gov.ca.cwds.data.es;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.startsWith;
import static org.junit.Assert.fail;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.io.IOException;

import org.elasticsearch.action.ListenableActionFuture;
import org.elasticsearch.action.WriteConsistencyLevel;
import org.elasticsearch.action.index.IndexRequestBuilder;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchRequestBuilder;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.search.SearchType;
import org.elasticsearch.client.Client;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;

/**
 * Class under test: {@link ElasticsearchDao}.
 * 
 * @author CWDS API Team
 */
public final class ElasticsearchDaoTest {

  private static final String TEST_INDEXNAME = "people";
  private static final String TEST_INDEXTYPE = "person";

  @Mock
  private Client client;

  private ElasticsearchDao target;

  @InjectMocks
  @Spy
  private ElasticsearchDao cut = new ElasticsearchDao(client);

  @Mock
  private TransportClient.Builder clientBuilder;


  @Mock
  private SearchRequestBuilder srb;

  @Mock
  private IndexRequestBuilder irb;

  @Mock
  private ListenableActionFuture<SearchResponse> listenSearch;

  @Mock
  private ListenableActionFuture<IndexResponse> listenIndex;

  @Mock
  private SearchResponse respSearch;

  @Mock
  private IndexResponse respIndex;

  @Mock
  private SearchHits hits;

  @Mock
  private SearchHit hit;

  @Rule
  public ExpectedException thrown = ExpectedException.none();

  @Before
  public void setUp() throws Exception {
    mock(TransportClient.class);
    mock(SearchRequestBuilder.class);
    MockitoAnnotations.initMocks(this);
    target = new ElasticsearchDao(client);

    // To run queries against a fake transport and client, use this:
    // cut.setTransportAddress(DummyTransportAddress.INSTANCE);

    // Mock up searches:
    when(client.prepareSearch(any())).thenReturn(srb);
    when(srb.setTypes(any())).thenReturn(srb);
    when(srb.setSearchType(any(SearchType.class))).thenReturn(srb);
    when(srb.setFrom(0)).thenReturn(srb);
    when(srb.setSize(any(Integer.class))).thenReturn(srb);
    when(srb.setExplain(any(Boolean.class))).thenReturn(srb);
    when(srb.execute()).thenReturn(listenSearch);
    when(srb.setQuery(any(QueryBuilder.class))).thenReturn(srb);
    when(srb.addHighlightedField(any(String.class))).thenReturn(srb);
    when(srb.setHighlighterNumOfFragments(any(Integer.class))).thenReturn(srb);
    when(listenSearch.actionGet()).thenReturn(respSearch);
    when(respSearch.getHits()).thenReturn(hits);
    when(hits.getHits()).thenReturn(new SearchHit[] {hit});

    // Mock up document creation:
    when(client.prepareIndex(any(String.class), any(String.class), any(String.class))).thenReturn(
        irb);
    when(irb.setConsistencyLevel(WriteConsistencyLevel.DEFAULT)).thenReturn(irb);
    when(irb.setSource(any(String.class))).thenReturn(irb);
    when(irb.execute()).thenReturn(listenIndex);
    when(listenIndex.actionGet()).thenReturn(respIndex);
    when(respIndex.isCreated()).thenReturn(true);
  }

  @After
  public void teardown() throws IOException {
    target.close();
  }

  @Test
  public void testIndexNameEmptyFails() throws Exception {
    thrown.expect(IllegalArgumentException.class);
    thrown.expectMessage(startsWith("index cannot be Null or empty"));
    cut.index("", "some_document_type", "some_document", "some_id");
  }

  @Test
  public void testDocumentTypeEmptyFails() throws Exception {
    thrown.expect(IllegalArgumentException.class);
    thrown.expectMessage(startsWith("documentType cannot be Null or empty"));
    cut.index("some_index", "", "some_document", "some_id");
  }

  @Test
  public void testIndexDoc() throws Exception {
    assertThat("index doc", cut.index("some_index", "some_document_type", "fred", "1234"));
  }

  @Test
  public void type() throws Exception {
    assertThat(ElasticsearchDao.class, notNullValue());
  }

  @Test
  public void instantiation() throws Exception {
    assertThat(target, notNullValue());
  }

  @Test
  public void stop_Args$() throws Exception {
    target.stop();
  }

  @Test(expected = IllegalArgumentException.class)
  public void autoCompletePerson_Args$String_Throws$ApiElasticSearchException() throws Exception {
    String searchTerm = null;
    target.searchPerson(searchTerm);
    fail("Expected exception was not thrown!");
  }

  @Test
  public void autoCompletePerson_Args$String_mock_client() throws Exception {
    String searchTerm = "junk";
    final ElasticSearchPerson[] actual = target.searchPerson(searchTerm);
    assertThat("nothing returned", actual != null);
  }

  @Test
  public void buildBoolQueryFromSearchTermsBuildsExpectedQuery() {
    BoolQueryBuilder createdQuery =
        target.buildBoolQueryFromSearchTerms("john smith 9/1/1990 123456789   ");
    QueryBuilder expectedQuery =
        QueryBuilders.boolQuery().should(QueryBuilders.matchQuery("firstName", "john"))
            .should(QueryBuilders.matchQuery("lastName", "john"))
            .should(QueryBuilders.matchQuery("firstName", "smith"))
            .should(QueryBuilders.matchQuery("lastName", "smith"))
            .should(QueryBuilders.matchQuery("dateOfBirth", "1990-09-01"))
            .should(QueryBuilders.matchQuery("ssn", "123456789"));
    org.junit.Assert.assertThat(createdQuery.toString(), is(equalTo(expectedQuery.toString())));
  }

  @Test
  public void buildBoolQueryFromMalformedSearchTermsBuildsQueryWithNoClauses() {
    BoolQueryBuilder createdQuery = target.buildBoolQueryFromSearchTerms("a-#4 df$ jk-/+ ");
    org.junit.Assert.assertThat(createdQuery.hasClauses(), is(equalTo(false)));


  }

  @Test
  public void buildBoolQueryFromMalformedDateBuildsQueryWithNoClauses() {
    BoolQueryBuilder createdQuery = target.buildBoolQueryFromSearchTerms("9/8-9000");
    org.junit.Assert.assertThat(createdQuery.hasClauses(), is(equalTo(false)));


  }

  @Test
  public void buildBoolQueryFromMalformedSsnBuildsQueryWithNoClauses() {
    BoolQueryBuilder createdQuery = target.buildBoolQueryFromSearchTerms("111-1090/0905 ");
    org.junit.Assert.assertThat(createdQuery.hasClauses(), is(equalTo(false)));
  }

  @Test
  public void buildBoolQueryFromMoreThanNineDigitSsnBuildsQueryWithNoClauses() {
    BoolQueryBuilder createdQuery = target.buildBoolQueryFromSearchTerms("111223333111 ");
    org.junit.Assert.assertThat(createdQuery.hasClauses(), is(equalTo(false)));
  }

  @Test
  public void buildBoolQueryFromTwoDateBuildsQueryWithTwoDateClauses() {
    BoolQueryBuilder createdQuery = target.buildBoolQueryFromSearchTerms("1989-01-01 9/1/1990   ");
    QueryBuilder expectedQuery =
        QueryBuilders.boolQuery().should(QueryBuilders.matchQuery("dateOfBirth", "1989-01-01"))
            .should(QueryBuilders.matchQuery("dateOfBirth", "1990-09-01"));
    org.junit.Assert.assertThat(createdQuery.toString(), is(equalTo(expectedQuery.toString())));

  }

  @Test
  public void buildBoolQueryFromTwoSsnBuildsQueryWithTwoSsnClauses() {
    BoolQueryBuilder createdQuery = target.buildBoolQueryFromSearchTerms("123456789   111223333 ");
    QueryBuilder expectedQuery =
        QueryBuilders.boolQuery().should(QueryBuilders.matchQuery("ssn", "123456789"))
            .should(QueryBuilders.matchQuery("ssn", "111223333"));
    org.junit.Assert.assertThat(createdQuery.toString(), is(equalTo(expectedQuery.toString())));

  }
}
