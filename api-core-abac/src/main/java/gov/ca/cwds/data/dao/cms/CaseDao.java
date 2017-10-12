package gov.ca.cwds.data.dao.cms;

import com.google.inject.Inject;
import gov.ca.cwds.data.CrudsDaoImpl;
import gov.ca.cwds.data.DaoException;
import gov.ca.cwds.data.persistence.cms.CmsCase;
import gov.ca.cwds.inject.CmsSessionFactory;
import java.util.List;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Hibernate DAO for DB2.
 *
 * @author CWDS TPT-2
 */
public class CaseDao extends CrudsDaoImpl<CmsCase> {

  private static final Logger LOGGER = LoggerFactory.getLogger(CaseDao.class);

  /**
   * Constructor
   *
   * @param sessionFactory The sessionFactory
   */
  @Inject
  public CaseDao(@CmsSessionFactory SessionFactory sessionFactory) {
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

  /**
   * @param clientId Client ID
   * @return find all active cases
   */
  @SuppressWarnings("unchecked")
  public List<CmsCase> findAllActiveCases(String clientId) {
    final String namedQueryName = "CmsCase.findAllActiveCases";

    final Session session = getCurrentSession();
    Transaction txn = session.getTransaction();
    boolean transactionExists = txn != null && txn.isActive();
    txn = transactionExists ? txn : session.beginTransaction();

    try {
      final Query query = session.getNamedQuery(namedQueryName).setParameter("clientId",
          clientId);

      List<CmsCase> caseList = query.getResultList();

      if (!transactionExists) {
        txn.commit();
      }
      return caseList;
    } catch (HibernateException h) {
      txn.rollback();
      throw new DaoException(h);
    }
  }
}
