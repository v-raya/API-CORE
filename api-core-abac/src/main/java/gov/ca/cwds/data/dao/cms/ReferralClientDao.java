package gov.ca.cwds.data.dao.cms;

import com.google.inject.Inject;
import gov.ca.cwds.data.CrudsDaoImpl;
import gov.ca.cwds.data.persistence.cms.ReferralClient;
import gov.ca.cwds.inject.CmsSessionFactory;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Hibernate DAO for DB2.
 *
 * @author CWDS TPT-2
 */
public class ReferralClientDao extends CrudsDaoImpl<ReferralClient> {

  private static final Logger LOGGER = LoggerFactory.getLogger(ReferralClientDao.class);

  /**
   * Constructor
   * 
   * @param sessionFactory The session factory
   */
  @Inject
  public ReferralClientDao(@CmsSessionFactory SessionFactory sessionFactory) {
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

  @SuppressWarnings("unchecked")
  public ReferralClient[] findByReferralId(String referralId) {
    Query query = this.getSessionFactory().getCurrentSession()
        .getNamedQuery("gov.ca.cwds.data.persistence.cms.ReferralClient.findByReferral")
        .setString("referralId", referralId);
    return (ReferralClient[]) query.list().toArray(new ReferralClient[0]);
  }

  public ReferralClient[] findByClientId(String clientId) {
    Query query = this.getSessionFactory().getCurrentSession()
        .getNamedQuery("gov.ca.cwds.data.persistence.cms.ReferralClient.findByClient")
        .setString("clientId", clientId);
    return (ReferralClient[]) query.list().toArray(new ReferralClient[0]);
  }

}
