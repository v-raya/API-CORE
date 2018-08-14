package gov.ca.cwds.data.cms;

import org.hibernate.FlushMode;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.inject.Inject;

import gov.ca.cwds.data.CaresStackUtils;
import gov.ca.cwds.data.CrudsDaoImpl;
import gov.ca.cwds.data.DaoException;
import gov.ca.cwds.data.persistence.cms.SystemMeta;
import gov.ca.cwds.inject.CmsSessionFactory;

/**
 * Hibernate DAO for DB2 {@link SystemMeta}.
 * 
 * @author CWDS API Team
 */
public class SystemMetaDao extends CrudsDaoImpl<SystemMeta> {

  private static final Logger LOGGER = LoggerFactory.getLogger(SystemMetaDao.class);

  /**
   * Constructor
   * 
   * @param sessionFactory The session factory
   */
  @Inject
  public SystemMetaDao(@CmsSessionFactory SessionFactory sessionFactory) {
    super(sessionFactory);
  }

  /**
   * @return all meta data records
   */
  @SuppressWarnings({"unchecked", "deprecation"})
  public SystemMeta[] findAll() {
    LOGGER.info("SystemMetaDao.findAll");
    CaresStackUtils.logStack();
    final String namedQueryName = SystemMeta.class.getName() + ".findAll";
    final Session session = grabSession();
    joinTransaction(session);

    try {
      final Query<?> query = session.getNamedQuery(namedQueryName);
      query.setReadOnly(true);
      query.setCacheable(true);
      query.setHibernateFlushMode(FlushMode.MANUAL);
      return query.list().toArray(new SystemMeta[0]);
    } catch (HibernateException h) {
      LOGGER.info("SystemMetaDao.findAll: ERROR! {}", h.getMessage(), h);
      throw new DaoException(h);
    }
  }

}
