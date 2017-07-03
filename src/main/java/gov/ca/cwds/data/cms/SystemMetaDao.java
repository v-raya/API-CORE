package gov.ca.cwds.data.cms;

import org.hibernate.Query;
import org.hibernate.SessionFactory;

import com.google.inject.Inject;

import gov.ca.cwds.data.CrudsDaoImpl;
import gov.ca.cwds.data.persistence.cms.SystemMeta;
import gov.ca.cwds.inject.CmsSessionFactory;

/**
 * Hibernate DAO for DB2 {@link SystemMeta}.
 * 
 * @author CWDS API Team
 */
public class SystemMetaDao extends CrudsDaoImpl<SystemMeta> {

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
  @SuppressWarnings("unchecked")
  public SystemMeta[] findAll() {
    Query query = this.getSessionFactory().getCurrentSession()
        .getNamedQuery(SystemMeta.class.getName() + ".findAll");

    return (SystemMeta[]) query.list().toArray(new SystemMeta[0]);
  }

}
