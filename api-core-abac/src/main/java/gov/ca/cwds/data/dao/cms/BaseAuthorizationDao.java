package gov.ca.cwds.data.dao.cms;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

/**
 * Reliable session acquisition, regardless of datasource or transaction boundaries.
 * 
 * @author CWDS API Team
 */
public class BaseAuthorizationDao {

  protected final SessionFactory sessionFactory;

  /**
   * Default constructor.
   * 
   * @param sessionFactory Hibernate session factory
   */
  public BaseAuthorizationDao(SessionFactory sessionFactory) {
    this.sessionFactory = sessionFactory;
  }

  protected Session grabSession() {
    Session session;
    try {
      session = sessionFactory.getCurrentSession();
    } catch (HibernateException e) {
      session = sessionFactory.openSession();
    }

    return session;
  }

  /**
   * Join an existing transaction or create a new one, as needed.
   * 
   * @param session live session
   * @return active transaction
   */
  public Transaction joinTransaction(Session session) {
    Transaction txn = session.getTransaction();
    txn = txn != null ? txn : session.beginTransaction();

    if (!txn.isActive()) {
      txn.begin();
    }

    return txn;
  }

}
