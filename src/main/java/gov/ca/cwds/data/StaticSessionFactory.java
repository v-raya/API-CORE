package gov.ca.cwds.data;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class StaticSessionFactory {

  private static SharedSessionFactory sessionFactory;

  static {
    sessionFactory = new SharedSessionFactory(
        new Configuration().configure("test-cms-hibernate.cfg.xml").buildSessionFactory());
  }

  public static SessionFactory getSessionFactory() {
    sessionFactory.getLock().lock();
    sessionFactory.held = true;
    return sessionFactory;
  }

}
