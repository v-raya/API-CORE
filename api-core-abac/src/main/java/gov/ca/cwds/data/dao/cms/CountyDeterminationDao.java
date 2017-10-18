package gov.ca.cwds.data.dao.cms;

import com.google.inject.Inject;
import gov.ca.cwds.data.CrudsDaoImpl;
import gov.ca.cwds.data.DaoException;
import gov.ca.cwds.data.persistence.cms.CmsPersistentObject;
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
public class CountyDeterminationDao extends CrudsDaoImpl<CmsPersistentObject> {

  private static final Logger LOGGER = LoggerFactory.getLogger(CountyDeterminationDao.class);

  /**
   * Constructor
   *
   * @param sessionFactory The session factory
   */
  @Inject
  public CountyDeterminationDao(@CmsSessionFactory SessionFactory sessionFactory) {
    super(sessionFactory);
  }

  /**
   * Get the current session, if available, or open a new one.
   *
   * @return Hibernate session
   */
  private Session getCurrentSession() {
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
   * @return List of counties for Client by clientID from CLIENT_CNTY table
   */
  public List<Short> getClientCountyFromClientCountyTable(String clientId) {

    String namedQuery = "SELECT GVR_ENTC\n"
        + "  FROM CLIENT_CNTY A\n"
        + "  WHERE A.CLIENT_ID = :clientId";

    return executeNativeQueryAndReturnCountyList(namedQuery, clientId);
  }

  /**
   * @param clientId Client ID
   * @return List of counties for Client by Active Cases with whom this Client is related
   */
  public List<Short> getClientCountyByActiveCase(String clientId) {

    String namedQuery = "SELECT GVR_ENTC\n"
        + "FROM CASE_T\n"
        + "WHERE FKCHLD_CLT = :clientId\n"
        + "      AND END_DT IS NULL";

    return executeNativeQueryAndReturnCountyList(namedQuery, clientId);
  }

  /**
   * @param clientId Client ID
   * @return List of counties for Client by Active Referrals with whom this Client is related
   */
  public List<Short> getCountyByActiveReferrals(String clientId) {
    String namedQuery = "SELECT GVR_ENTC\n"
        + "FROM REFERL_T A, REFR_CLT B\n"
        + "WHERE A.IDENTIFIER = B.FKREFERL_T\n"
        + "      AND FKCLIENT_T = :clientId \n"
        + "      AND REFCLSR_DT IS NULL\n"
        + "ORDER BY REF_RCV_DT DESC";

    return executeNativeQueryAndReturnCountyList(namedQuery, clientId);
  }

  /**
   * @param clientId Client ID
   * @return List of counties for Client by Closed Cases with whom this Client is related
   */
  public List<Short> getClientCountyByClosedCase(String clientId) {
    String namedQuery = "SELECT GVR_ENTC\n"
        + "FROM CASE_T\n"
        + "WHERE FKCHLD_CLT = :clientId\n"
        + "      AND END_DT IS NOT NULL";

    return executeNativeQueryAndReturnCountyList(namedQuery, clientId);
  }

  /**
   * @param clientId Client ID
   * @return List of counties for Client by Closed Referral with whom this Client is related
   */
  public List<Short> getClientCountyByClosedReferral(String clientId) {
    String namedQuery = "SELECT GVR_ENTC\n"
        + "FROM REFERL_T A, REFR_CLT B\n"
        + "WHERE A.IDENTIFIER = B.FKREFERL_T\n"
        + "      AND FKCLIENT_T = :clientId\n"
        + "      AND REFCLSR_DT IS NOT NULL\n"
        + "ORDER BY REF_RCV_DT DESC";

    return executeNativeQueryAndReturnCountyList(namedQuery, clientId);
  }

  /**
   * @param clientId Client ID
   * @return List of counties for Client by Active Cases with whom this Client is related
   */
  public List<Short> getClientByClientAnyActiveCase(String clientId) {
    String namedQuery = "SELECT GVR_ENTC\n"
        + "FROM CASE_T A, CLN_RELT B\n"
        + "WHERE (B.FKCLIENT_0 = :clientId\n"
        + "       OR  B.FKCLIENT_T = :clientId)\n"
        + "      AND (A.FKCHLD_CLT = B.FKCLIENT_T\n"
        + "           OR  A.FKCHLD_CLT = B.FKCLIENT_0)\n"
        + "      AND  A.END_DT IS NULL ";

    return executeNativeQueryAndReturnCountyList(namedQuery, clientId);
  }

  /**
   * @param clientId Client ID
   * @return List of counties for Client by Closed Cases with whom this Client is related
   */
  public List<Short> getClientByClientAnyClosedCase(String clientId) {
    String namedQuery = "SELECT GVR_ENTC\n"
        + "FROM CASE_T A, CLN_RELT B\n"
        + "WHERE (B.FKCLIENT_0 = :clientId\n"
        + "       OR  B.FKCLIENT_T = :clientId)\n"
        + "      AND (A.FKCHLD_CLT = B.FKCLIENT_T\n"
        + "           OR  A.FKCHLD_CLT = B.FKCLIENT_0)\n"
        + "      AND  A.END_DT IS NOT NULL ";

    return executeNativeQueryAndReturnCountyList(namedQuery, clientId);
  }

  @SuppressWarnings("unchecked")
  private List<Short> executeNativeQueryAndReturnCountyList(String namedQuery, String clientId) {
    final Session session = getCurrentSession();
    Transaction txn = session.getTransaction();
    boolean transactionExists = txn != null && txn.isActive();
    txn = transactionExists ? txn : session.beginTransaction();

    try {
      final Query query = session.createNativeQuery(
          namedQuery).setParameter("clientId",
          clientId);

      List<Short> resultList = query.getResultList();

      if (!transactionExists) {
        txn.commit();
      }

      return resultList;
    } catch (HibernateException h) {
      txn.rollback();
      throw new DaoException(h);
    }
  }
}
