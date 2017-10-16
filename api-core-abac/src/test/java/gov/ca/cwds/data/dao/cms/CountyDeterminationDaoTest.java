package gov.ca.cwds.data.dao.cms;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;

import gov.ca.cwds.data.DaoException;
import java.util.ArrayList;
import java.util.List;
import org.hamcrest.junit.ExpectedException;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.NativeQuery;
import org.junit.Assert;
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
    List<Short> resultList = new ArrayList<>();
    resultList.add((short) 11);

    Session session = mock(Session.class);
    Transaction transaction = mock(Transaction.class);
    NativeQuery query = mock(NativeQuery.class);

    doReturn(resultList).when(query).getResultList();
    doNothing().when(transaction).commit();
    doReturn(true).when(transaction).isActive();
    doNothing().when(transaction).rollback();
    doReturn(transaction).when(session).getTransaction();
    doReturn(query).when(session).createNativeQuery(Mockito.anyString());
    doReturn(session).when(sessionFactory).getCurrentSession();
    doReturn(query).when(query).setParameter(Mockito.anyString(), Mockito.anyString());
    Whitebox.setInternalState(countyDeterminationDao, "sessionFactory",
        sessionFactory);

    assertThat(countyDeterminationDao.getClientCountyByActiveCase("testClientId").get(0),
        is(equalTo((short) 11)));

    assertThat(countyDeterminationDao.getCountyByActiveReferrals("testClientId").get(0),
        is(equalTo((short) 11)));

    assertThat(countyDeterminationDao.getClientCountyByClosedCase("testClientId").get(0),
        is(equalTo((short) 11)));

    assertThat(countyDeterminationDao.getClientCountyByClosedReferral("testClientId").get(0),
        is(equalTo((short) 11)));

    assertThat(countyDeterminationDao.getClientByClientAnyActiveCase("testClientId").get(0),
        is(equalTo((short) 11)));

    assertThat(countyDeterminationDao.getClientByClientAnyClosedCase("testClientId").get(0),
        is(equalTo((short) 11)));
  }

  @Test
  public void testSessionException() throws Exception {
    Session session = mock(Session.class);

    doThrow(HibernateException.class).when(sessionFactory).getCurrentSession();
    doReturn(session).when(sessionFactory).openSession();
    doThrow(Exception.class).when(session).getTransaction();

    Whitebox.setInternalState(countyDeterminationDao, "sessionFactory",
        sessionFactory);

    thrown.expect(Exception.class);
    countyDeterminationDao.getClientCountyByActiveCase("testClientId");
  }

  @Test
  public void testTransactionNotExist() throws Exception {
    Session session = mock(Session.class);
    Transaction transaction = mock(Transaction.class);
    NativeQuery query = mock(NativeQuery.class);

    doReturn(null).when(query).getResultList();
    doReturn(false).when(transaction).isActive();

    doReturn(transaction).when(session).getTransaction();
    doReturn(query).when(session).createNativeQuery(Mockito.anyString());
    doReturn(session).when(sessionFactory).getCurrentSession();
    doReturn(query).when(query).setParameter(Mockito.anyString(), Mockito.anyString());
    doReturn(transaction).when(session).beginTransaction();

    Whitebox.setInternalState(countyDeterminationDao, "sessionFactory",
        sessionFactory);

    Assert.assertNull(countyDeterminationDao.getClientCountyByActiveCase("testClientId"));

    doReturn(null).when(session).getTransaction();

    Whitebox.setInternalState(countyDeterminationDao, "sessionFactory",
        sessionFactory);

    Assert.assertNull(countyDeterminationDao.getClientCountyByActiveCase("testClientId"));
  }

  @Test
  public void testExecuteNativeQueryAndReturnCountyListException() throws Exception {
    Session session = mock(Session.class);
    Transaction transaction = mock(Transaction.class);

    doThrow(HibernateException.class).when(session).createNativeQuery(Mockito.anyString());
    doNothing().when(transaction).rollback();
    doReturn(true).when(transaction).isActive();
    doReturn(transaction).when(session).getTransaction();
    doReturn(session).when(sessionFactory).getCurrentSession();

    Whitebox.setInternalState(countyDeterminationDao, "sessionFactory",
        sessionFactory);

    thrown.expect(DaoException.class);
    countyDeterminationDao.getClientCountyByActiveCase("testClientId");
  }
}
