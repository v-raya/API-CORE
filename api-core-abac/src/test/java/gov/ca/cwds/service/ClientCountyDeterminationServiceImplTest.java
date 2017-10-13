package gov.ca.cwds.service;

import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;

import gov.ca.cwds.data.dao.cms.CountyDeterminationDao;
import org.hamcrest.junit.ExpectedException;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import org.mockito.internal.util.reflection.Whitebox;

/**
 * @author CWDS TPT-2
 */
public class ClientCountyDeterminationServiceImplTest {

  @Rule
  public ExpectedException thrown = ExpectedException.none();

  @Mock
  private CountyDeterminationDao countyDeterminationDao;

  @Spy
  @InjectMocks
  private ClientCountyDeterminationServiceImpl countyDeterminationService; // "Class Under Test"

  @Before
  public void initMocks() {
    MockitoAnnotations.initMocks(this);
  }

  @Test
  public void type() throws Exception {
    assertThat(ClientCountyDeterminationServiceImpl.class, notNullValue());
  }

  @Test
  public void instantiation() throws Exception {
    assertThat(countyDeterminationService, notNullValue());
  }

  @Test
  public void testHandleRequest() throws Exception {
    Whitebox.setInternalState(countyDeterminationService, "countyDeterminationDao", countyDeterminationDao);

//    Whitebox.setInternalState(target, "esConfig", esConfig);
//    doReturn("fred").when(target).executionResult(Mockito.anyString(), Mockito.anyString());
//    final IndexQueryResponse actual = target.handleRequest(req);
//    final IndexQueryResponse expected = new IndexQueryResponse("fred");
//
//    assertThat(actual, is(equalTo(expected)));
  }
}
