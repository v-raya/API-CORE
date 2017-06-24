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

  /**
   * Optionally configure session factory from Hibernate configuration file.
   * 
   * @param hibernateConfig location of hibernate config file
   */
  public static synchronized void init(String hibernateConfig) {
    if (sessionFactory == null) {
      sessionFactory = new SharedSessionFactory(
          new Configuration().setInterceptor(new ApiReferentialIntegrityInterceptor())
              .configure(hibernateConfig).buildSessionFactory());
    }
  }

  private static synchronized void init() {
    init(DEFAULT_HIBERNATE_CONFIG);
  }

  /**
   * Increment read lock counter per thread. {@link SharedSessionFactory#close()} releases the lock.
   * 
   * @return prepared session factory
   */
  public static SessionFactory getSessionFactory() {
    if (sessionFactory == null) {
      init();
    }

    sessionFactory.getLock().readLock().lock();
    return sessionFactory;
  }

}
