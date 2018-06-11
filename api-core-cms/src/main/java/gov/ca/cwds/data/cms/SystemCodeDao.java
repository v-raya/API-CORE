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

    final Session session = grabSession();
    final Transaction txn = joinTransaction(session);
    boolean transactionExists = txn != null && txn.isActive();

    try {
      final Query query = session.getNamedQuery(namedQueryName).setString("foreignKeyMetaTable",
          foreignKeyMetaTable);
      final SystemCode[] systemCodes = (SystemCode[]) query.list().toArray(new SystemCode[0]);

      if (transactionExists) {
        txn.commit();
      }

      return systemCodes;
    } catch (HibernateException h) {
      if (txn != null && txn.getStatus().canRollback()) {
        txn.rollback();
      }
      throw new DaoException(h);
    }
  }

  @SuppressWarnings("unchecked")
  public SystemCode findBySystemCodeId(Number systemCodeId) {
    final String namedQueryName = SystemCode.class.getName() + ".findBySystemCodeId";

    final Session session = grabSession();
    final Transaction txn = joinTransaction(session);
    boolean transactionExists = txn != null && txn.isActive();

    try {
      final Query query =
          session.getNamedQuery(namedQueryName).setShort("systemId", systemCodeId.shortValue());
      final SystemCode systemCode = (SystemCode) query.getSingleResult();

      if (transactionExists) {
        txn.commit();
      }

      return systemCode;
    } catch (HibernateException h) {
      if (txn != null && txn.getStatus().canRollback()) {
        txn.rollback();
      }
      throw new DaoException(h);
    }
  }

}
