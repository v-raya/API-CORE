package gov.ca.cwds.data.cms;

import com.google.inject.Inject;
import gov.ca.cwds.data.BaseDaoImpl;
import gov.ca.cwds.data.persistence.cms.ClientScpEthnicity;
import gov.ca.cwds.inject.CmsSessionFactory;
import org.hibernate.SessionFactory;

/**
 * @author CWDS CALS API Team
 */
public class ClientScpEthnicityDao extends BaseDaoImpl<ClientScpEthnicity> {

  /**
   * Constructor
   *
   * @param sessionFactory The session factory
   */
  @Inject
  public ClientScpEthnicityDao(@CmsSessionFactory SessionFactory sessionFactory) {
    super(sessionFactory);
  }
}
