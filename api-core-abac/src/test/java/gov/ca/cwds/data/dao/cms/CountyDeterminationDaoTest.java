package gov.ca.cwds.data.dao.cms;

import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;

import org.hamcrest.junit.ExpectedException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.NativeQuery;
import org.hibernate.query.Query;
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
public class CountyDeterminationDaoTest {

  @Rule
  public ExpectedException thrown = ExpectedException.none();

  @Mock
  private SessionFactory sessionFactory;

  @Spy
  @InjectMocks
  private CountyDeterminationDao countyDeterminationDao; // "Class Under Test"

  @Before
  public void initMocks() {
    MockitoAnnotations.initMocks(this);
  }

  @Test
  public void type() throws Exception {
    assertThat(CountyDeterminationDao.class, notNullValue());
  }

  @Test
  public void instantiation() throws Exception {
    assertThat(countyDeterminationDao, notNullValue());
  }

  @Test
  public void testGetClientByClientAnyActiveCase() throws Exception {
    Session session = mock(Session.class);

    NativeQuery query = mock(NativeQuery.class);

    doReturn(query).when(session).createNativeQuery(Mockito.anyString());
    doReturn(session).when(sessionFactory).getCurrentSession();
    Whitebox.setInternalState(countyDeterminationDao, "sessionFactory",
        sessionFactory);


  }
}
