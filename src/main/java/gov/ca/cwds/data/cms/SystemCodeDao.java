package gov.ca.cwds.data.cms;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import com.google.inject.Inject;

import gov.ca.cwds.data.CrudsDaoImpl;
import gov.ca.cwds.data.DaoException;
import gov.ca.cwds.data.persistence.cms.SystemCode;
import gov.ca.cwds.inject.CmsSessionFactory;

/**
 * Hibernate DAO for DB2 {@link SystemCode}.
 * 
 * @author CWDS API Team
 */
public class SystemCodeDao extends CrudsDaoImpl<SystemCode> {

  /**
   * Constructor
   * 
   * @param sessionFactory The session factory
   */
  @Inject
  public SystemCodeDao(@CmsSessionFactory SessionFactory sessionFactory) {
    super(sessionFactory);
  }

  /**
   * @param foreignKeyMetaTable meta group
   * @return all keys by meta table
   */
  @SuppressWarnings("unchecked")
  public SystemCode[] findByForeignKeyMetaTable(String foreignKeyMetaTable) {
    final String namedQueryName = SystemCode.class.getName() + ".findByForeignKeyMetaTable";
    Session session = getSessionFactory().getCurrentSession();

    Transaction txn = session.getTransaction();
    boolean transactionExists = txn != null;

    try {
      txn = transactionExists ? txn : session.beginTransaction();
      Query query = session.getNamedQuery(namedQueryName).setString("foreignKeyMetaTable",
          foreignKeyMetaTable);
      SystemCode[] systemCodes = (SystemCode[]) query.list().toArray(new SystemCode[0]);
      if (!transactionExists)
        txn.commit();
      return systemCodes;
    } catch (HibernateException h) {
      if (txn != null) {
        txn.rollback();
      }
      throw new DaoException(h);
    }
  }

  @SuppressWarnings("unchecked")
  public SystemCode findBySystemCodeId(Number systemCodeId) {
    final String namedQueryName = SystemCode.class.getName() + ".findBySystemCodeId";
    Session session = getSessionFactory().getCurrentSession();

    Transaction txn = null;
    try {
      txn = session.beginTransaction();
      Query query =
          session.getNamedQuery(namedQueryName).setShort("systemId", systemCodeId.shortValue());
      SystemCode systemCode = (SystemCode) query.getSingleResult();
      txn.commit();
      return systemCode;
    } catch (HibernateException h) {
      if (txn != null) {
        txn.rollback();
      }
      throw new DaoException(h);
    }
  }
}
