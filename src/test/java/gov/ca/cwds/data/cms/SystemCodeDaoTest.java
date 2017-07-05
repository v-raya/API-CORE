package gov.ca.cwds.data.cms;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.ArrayList;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.junit.Test;

import gov.ca.cwds.data.persistence.cms.SystemCode;

public class SystemCodeDaoTest {

  @Test
  public void type() throws Exception {
    assertThat(SystemCodeDao.class, notNullValue());
  }

  @Test
  public void instantiation() throws Exception {
    SessionFactory sessionFactory = mock(SessionFactory.class);
    SystemCodeDao target = new SystemCodeDao(sessionFactory);
    assertThat(target, notNullValue());
  }

  @Test
  public void findAll_Args__() throws Exception {
    SessionFactory sessionFactory = mock(SessionFactory.class);
    Session session = mock(Session.class);
    Transaction tx = mock(Transaction.class);
    Query query = mock(Query.class);

    when(sessionFactory.getCurrentSession()).thenReturn(session);
    when(session.getNamedQuery(any(String.class))).thenReturn(query);
    when(session.beginTransaction()).thenReturn(tx);
    when(query.list()).thenReturn(new ArrayList<SystemCode>());
    when(query.setString(any(String.class), any(String.class))).thenReturn(query);

    SystemCodeDao target = new SystemCodeDao(sessionFactory);
    SystemCode[] actual = target.findByForeignKeyMetaTable("asdf");
    SystemCode[] expected = new SystemCode[0];
    assertThat(actual, is(equalTo(expected)));
  }

}
