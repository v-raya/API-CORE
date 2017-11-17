package gov.ca.cwds.data.legacy.cms.dao;

import com.google.inject.Inject;

import gov.ca.cwds.data.CrudsDaoImpl;
import gov.ca.cwds.data.legacy.cms.entity.ChildClient;
import gov.ca.cwds.inject.CmsSessionFactory;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;

/**
 * DAO for {@link ChildClient}.
 *
 * @author CWDS API Team
 */
public class ChildClientDao extends CrudsDaoImpl<ChildClient> {

  /**
   * Constructor
   *
   * @param sessionFactory The sessionFactory
   */
  @Inject
  public ChildClientDao(@CmsSessionFactory SessionFactory sessionFactory) {
    super(sessionFactory);
  }
}
