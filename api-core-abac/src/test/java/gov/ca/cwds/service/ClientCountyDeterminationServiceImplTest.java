package gov.ca.cwds.service;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.doReturn;

import gov.ca.cwds.data.dao.cms.CountyDeterminationDao;
import java.util.ArrayList;
import java.util.List;
import org.hamcrest.junit.ExpectedException;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
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

  //test default no-arg constructor.
  @Test
  public void testDefaultConstructor() throws Exception {
    ClientCountyDeterminationService service = new ClientCountyDeterminationServiceImpl();
    assertThat(service, notNullValue());
  }

  @Test
  public void testHandleRequest() throws Exception {
    final List<Short> list = new ArrayList<>();
    list.add((short) 1);
    doReturn(list).when(countyDeterminationDao).getClientByClientAnyActiveCase(Mockito.anyString());
    Whitebox.setInternalState(countyDeterminationService, "countyDeterminationDao",
        countyDeterminationDao);

    assertThat(countyDeterminationService.getClientCountyById("testClientId"), is(equalTo((short)1)));

  }
}
