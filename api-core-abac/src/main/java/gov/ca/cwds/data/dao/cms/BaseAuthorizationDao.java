package gov.ca.cwds.data.dao.cms;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

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

}
