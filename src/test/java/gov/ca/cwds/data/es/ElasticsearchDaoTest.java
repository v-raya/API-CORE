package gov.ca.cwds.data.es;

import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.startsWith;
import static org.junit.Assert.fail;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.elasticsearch.action.ListenableActionFuture;
import org.elasticsearch.action.WriteConsistencyLevel;
import org.elasticsearch.action.index.IndexRequestBuilder;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchRequestBuilder;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.search.SearchType;
import org.elasticsearch.client.Client;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
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
    when(listenSearch.actionGet()).thenReturn(respSearch);
    when(respSearch.getHits()).thenReturn(hits);
    when(hits.getHits()).thenReturn(new SearchHit[] {hit});

    // Mock up document creation:
    when(client.prepareIndex(any(String.class), any(String.class), any(String.class)))
        .thenReturn(irb);
    when(irb.setConsistencyLevel(WriteConsistencyLevel.DEFAULT)).thenReturn(irb);
    when(irb.setSource(any(String.class))).thenReturn(irb);
    when(irb.execute()).thenReturn(listenIndex);
    when(listenIndex.actionGet()).thenReturn(respIndex);
    when(respIndex.isCreated()).thenReturn(true);
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
    ElasticsearchDao target = new ElasticsearchDao(client);
    assertThat(target, notNullValue());
  }

  @Test
  public void stop_Args$() throws Exception {
    ElasticsearchDao target = new ElasticsearchDao(client);
    target.stop();
  }

  @Test(expected = IllegalArgumentException.class)
  public void autoCompletePerson_Args$String_Throws$ApiElasticSearchException() throws Exception {

    ElasticsearchDao target = new ElasticsearchDao(client);
    String searchTerm = null;
    target.autoCompletePerson(searchTerm);
    fail("Expected exception was not thrown!");
  }


  @Test
  public void autoCompletePerson_Args$String_mock_client() throws Exception {
    ElasticsearchDao target = new ElasticsearchDao(client);

    // Search EVERY field!
    // final SearchHit[] hits = client.prepareSearch(indexName).setTypes(documentType)
    // .setQuery(QueryBuilders.queryStringQuery(s)).setFrom(0).setSize(DEFAULT_MAX_RESULTS)
    // .setExplain(true).execute().actionGet().getHits().getHits();

    // when(client.prepareSearch(any())).thenReturn(srb);
    // when(srb.setTypes(any())).thenReturn(srb);
    // target.setClient(client);

    String searchTerm = "junk";
    final ElasticSearchPerson[] actual = target.autoCompletePerson(searchTerm);
    assertThat("nothing returned", actual != null);
  }
}
