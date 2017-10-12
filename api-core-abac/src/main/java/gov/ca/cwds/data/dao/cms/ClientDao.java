package gov.ca.cwds.data.dao.cms;

import com.google.inject.Inject;
import gov.ca.cwds.data.BaseDaoImpl;
import gov.ca.cwds.data.cms.SystemCodeDao;
import gov.ca.cwds.data.persistence.cms.Client;
import gov.ca.cwds.inject.CmsSessionFactory;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Hibernate DAO for DB2.
 *
 * @author CWDS TPT-2
 */
public class ClientDao extends BaseDaoImpl<Client> {

  private static final Logger LOGGER = LoggerFactory.getLogger(ClientDao.class);

  /**
   * Constructor
   * 
   * @param sessionFactory The sessionFactory
   */
  @Inject
  public ClientDao(@CmsSessionFactory SessionFactory sessionFactory) {
    super(sessionFactory);
  }

  /**
   * Get the current session, if available, or open a new one.
   *
   * @return Hibernate session
   */
  protected Session getCurrentSession() {
    Session session;
    try {
      session = getSessionFactory().getCurrentSession();
    } catch (HibernateException e) { // NOSONAR
      LOGGER.warn("NO SESSION!");
      session = getSessionFactory().openSession();
    }

    return session;
  }
}
