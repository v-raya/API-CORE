package gov.ca.cwds.data.dao.cms;

import org.apache.commons.lang3.tuple.Pair;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

/**
 * Reliable session acquisition, regardless of datasource or transaction boundaries.
 * 
 * @author CWDS API Team
 */
public class BaseAuthorizationDao {

  private static final ThreadLocal<Boolean> bound = new ThreadLocal<>();

  protected final SessionFactory sessionFactory;

  /**
   * Default constructor.
   * 
   * @param sessionFactory Hibernate session factory
   */
  public BaseAuthorizationDao(SessionFactory sessionFactory) {
    this.sessionFactory = sessionFactory;
  }

  /**
   * Get the current session, if any, or open a new one.
   * 
   * @return active session, true if opened a new session
   */
  protected Pair<Session, Boolean> grabSession() {
    boolean openedNew = false;
    Session session;
    try {
      session = sessionFactory.getCurrentSession();
    } catch (HibernateException e) {
      session = sessionFactory.openSession();
      openedNew = true;
    }

    return Pair.of(session, openedNew);
  }

  public static void setXaMode(boolean xaMode) {
    bound.set(xaMode);
  }

  public static void clearXaMode() {
    bound.remove();
  }

  protected boolean getXaMode() {
    final Boolean ret = bound.get();
    return ret != null && ret;
  }

}
