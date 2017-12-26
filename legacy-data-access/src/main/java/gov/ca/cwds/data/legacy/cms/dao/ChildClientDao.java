package gov.ca.cwds.data.legacy.cms.dao;

import com.google.inject.Inject;

import gov.ca.cwds.data.BaseDaoImpl;
import gov.ca.cwds.data.legacy.cms.entity.ChildClient;
import gov.ca.cwds.inject.CmsSessionFactory;
import org.hibernate.SessionFactory;

/**
 * DAO for {@link ChildClient}.
 *
 * @author CWDS API Team
 */
public class ChildClientDao extends BaseDaoImpl<ChildClient> {

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
