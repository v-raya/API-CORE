package gov.ca.cwds.data.dao.cms;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

/**
 * Reliable session acquisition, regardless of datasource types or transaction boundaries.
 * 
 * @author CWDS API Team
 */
public class BaseAuthorizationDao {

  private SessionFactory sessionFactory;

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
   * Default constructor.
   * 
   * @param sessionFactory Hibernate session factory
   */
  public BaseAuthorizationDao(SessionFactory sessionFactory) {
    this.sessionFactory = sessionFactory;
  }

  /**
   * Provided for <strong>convenience</strong>. Don't abuse this.
   */
  protected SessionFactory getSessionFactory() {
    return sessionFactory;
  }

}
