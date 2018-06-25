package gov.ca.cwds.data.cms;

import org.hibernate.FlushMode;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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

  private static final Logger LOGGER = LoggerFactory.getLogger(SystemCodeDao.class);

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
  @SuppressWarnings({"unchecked", "deprecation"})
  public SystemCode[] findByForeignKeyMetaTable(String foreignKeyMetaTable) {
    LOGGER.info("SystemCodeDao.findByForeignKeyMetaTable: foreignKeyMetaTable: {}",
        foreignKeyMetaTable);
    final String namedQueryName = this.getClass().getName() + ".findByForeignKeyMetaTable";

    // DRS: Don't interfere with transaction management, like XA.
    final Session session = grabSession();
    joinTransaction(session);

    try {
      final Query<SystemCode> query = session.getNamedQuery(namedQueryName)
          .setString("foreignKeyMetaTable", foreignKeyMetaTable).setReadOnly(true)
          .setCacheable(true).setHibernateFlushMode(FlushMode.MANUAL);
      return query.list().toArray(new SystemCode[0]);
    } catch (HibernateException h) {
      throw new DaoException(h);
    }
  }

  @SuppressWarnings({"unchecked", "deprecation"})
  public SystemCode findBySystemCodeId(Number systemCodeId) {
    LOGGER.info("SystemCodeDao.findBySystemCodeId: systemCodeId: {}", systemCodeId);
    final String namedQueryName = this.getClass() + ".findBySystemCodeId";
    final Session session = grabSession();
    joinTransaction(session);

    try {
      final Query<SystemCode> query =
          session.getNamedQuery(namedQueryName).setShort("systemId", systemCodeId.shortValue())
              .setReadOnly(true).setCacheable(true).setHibernateFlushMode(FlushMode.MANUAL);
      final SystemCode systemCode = query.getSingleResult();
      return systemCode;
    } catch (HibernateException h) {
      throw new DaoException(h);
    }
  }

}
