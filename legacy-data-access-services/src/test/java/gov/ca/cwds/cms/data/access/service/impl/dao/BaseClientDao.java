package gov.ca.cwds.cms.data.access.service.impl.dao;

import gov.ca.cwds.data.BaseDaoImpl;
import gov.ca.cwds.data.persistence.cms.BaseClientImpl;
import org.hibernate.SessionFactory;

/**
 * @author CWDS TPT-3 Team
 */
public class BaseClientDao extends BaseDaoImpl<BaseClientImpl> {

  /**
   * Constructor
   *
   * @param sessionFactory The session factory
   */
  public BaseClientDao(SessionFactory sessionFactory) {
    super(sessionFactory);
  }
}
