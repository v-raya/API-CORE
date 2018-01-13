package gov.ca.cwds.cms.data.access.dao;

import com.google.inject.Inject;
import gov.ca.cwds.cms.data.access.inject.DataAccessServicesSessionFactory;
import gov.ca.cwds.data.BaseDaoImpl;
import gov.ca.cwds.data.legacy.cms.entity.PlacementHomeUc;
import org.hibernate.SessionFactory;

/**
 * @author CWDS CALS API Team
 */

public class PlacementHomeUcDao extends BaseDaoImpl<PlacementHomeUc> {

  @Inject
  public PlacementHomeUcDao(@DataAccessServicesSessionFactory SessionFactory sessionFactory) {
    super(sessionFactory);
  }

}

