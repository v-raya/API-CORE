package gov.ca.cwds.data.dao.cms;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.context.internal.ManagedSessionContext;

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
  BaseAuthorizationDao(SessionFactory sessionFactory) {
    this.sessionFactory = sessionFactory;
  }

  Session grabSession() {
    Session session;
    try {
      session = sessionFactory.getCurrentSession();
    } catch (HibernateException e) {
      session = sessionFactory.openSession();
    }
    if (isManaged()) {
      ManagedSessionContext.bind(session);
    }

    return session;
  }

  void finalizeSession(Session session) {
    if (isManaged()) {
      ManagedSessionContext.unbind(sessionFactory);
      if (session != null) {
        session.close();
      }
    }
  }

  protected boolean isManaged() {
    return false;
  }
}
