package gov.ca.cwds.data.cms;

import org.hibernate.Query;
import org.hibernate.SessionFactory;

import com.google.inject.Inject;

import gov.ca.cwds.data.CrudsDaoImpl;
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
    Query query = this.getSessionFactory().getCurrentSession()
        .getNamedQuery(SystemCode.class.getName() + ".findByForeignKeyMetaTable")
        .setString("foreignKeyMetaTable", foreignKeyMetaTable);
    return (SystemCode[]) query.list().toArray(new SystemCode[0]);
  }

  @SuppressWarnings("unchecked")
  public SystemCode findBySystemCodeId(Short systemCodeId) {
    Query query = this.getSessionFactory().getCurrentSession()
        .getNamedQuery(SystemCode.class.getName() + ".findBySystemCodeId")
        .setShort("systemId", systemCodeId);
    return (SystemCode) query.getSingleResult();

  }

}
