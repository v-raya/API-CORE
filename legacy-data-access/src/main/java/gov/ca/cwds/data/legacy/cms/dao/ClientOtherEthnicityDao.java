package gov.ca.cwds.data.legacy.cms.dao;

import com.google.inject.Inject;
import gov.ca.cwds.data.BaseDaoImpl;
import gov.ca.cwds.data.legacy.cms.entity.ClientOtherEthnicity;
import gov.ca.cwds.inject.CmsSessionFactory;
import org.hibernate.SessionFactory;

/**
 * @author CWDS TPT-3 Team.
 */
public class ClientOtherEthnicityDao extends BaseDaoImpl<ClientOtherEthnicity> {

  /**
   * Constructor
   *
   * @param sessionFactory The session factory
   */
  @Inject
  public ClientOtherEthnicityDao(@CmsSessionFactory SessionFactory sessionFactory) {
    super(sessionFactory);
  }
}
