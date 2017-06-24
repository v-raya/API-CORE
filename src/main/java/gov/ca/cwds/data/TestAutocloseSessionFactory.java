package gov.ca.cwds.data;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

/**
 * Reduce connection pressure on test databases by constructing a single data source (connection
 * pool) for all test cases.
 * 
 * Add to JUnit classes:
 * 
 * <blockquote>
 * 
 * <pre>
 * private static ReplicatedAttorneyDao attorneyDao;
 * private static SessionFactory sessionFactory;
 * private Session session;
 * 
 * &#64;BeforeClass
 * public static void beforeClass() {
 *   sessionFactory = TestAutocloseSessionFactory.getSessionFactory();
 *   attorneyDao = new ReplicatedAttorneyDao(sessionFactory);
 * }
 * 
 * &#64;AfterClass
 * public static void afterClass() {
 *   sessionFactory.close();
 * }
 * 
 * &#64;AfterClass
 * public static void afterClass() {
 *   sessionFactory.close();
 * }
 * 
 * &#64;Before
 * public void setup() {
 *   session = sessionFactory.getCurrentSession();
 *   session.beginTransaction();
 * }
 * 
 * &#64;After
 * public void teardown() {
 *   session.getTransaction().rollback();
 * }
 * 
 * </pre>
 * 
 * </blockquote>
 * 
 * @author CWDS API Team
 * @see SharedSessionFactory
 */
public final class TestAutocloseSessionFactory {

  private static final String DEFAULT_HIBERNATE_CONFIG = "hibernate.cfg.xml";

  private static SharedSessionFactory sessionFactory;

  private TestAutocloseSessionFactory() {
    // Statics only.
  }

  private static synchronized void init(String hibernateConfig) {
    if (sessionFactory == null) {
      sessionFactory = new SharedSessionFactory(
          new Configuration().configure(hibernateConfig).buildSessionFactory());
    }
  }

  private static synchronized void init() {
    init(DEFAULT_HIBERNATE_CONFIG);
  }

  /**
   * @return prepared session factory
   */
  public static SessionFactory getSessionFactory() {
    try {
      if (sessionFactory == null) {
        init();
      }

      sessionFactory.getLock().readLock().lock();
      sessionFactory.held = true;
    } finally {
      sessionFactory.getLock().readLock().unlock(); // Always unlock.
      sessionFactory.held = false;
      sessionFactory.getCondition().notifyAll();
    }

    return sessionFactory;
  }

}
