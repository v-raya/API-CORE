package gov.ca.cwds.cms.data.access.dao;

import com.google.inject.Inject;
import gov.ca.cwds.cms.data.access.inject.DataAccessServicesSessionFactory;
import gov.ca.cwds.data.BaseDaoImpl;
import gov.ca.cwds.data.legacy.cms.entity.ClientScpEthnicity;
import org.hibernate.SessionFactory;

/**
 * @author CWDS CALS API Team
 */

public class ClientScpEthnicityDao extends BaseDaoImpl<ClientScpEthnicity> {

  @Inject
  public ClientScpEthnicityDao(@DataAccessServicesSessionFactory SessionFactory sessionFactory) {
    super(sessionFactory);
  }

}
