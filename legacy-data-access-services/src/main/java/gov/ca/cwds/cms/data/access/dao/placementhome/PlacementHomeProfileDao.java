package gov.ca.cwds.cms.data.access.dao.placementhome;

import com.google.inject.Inject;
import gov.ca.cwds.cms.data.access.inject.PlacementHomeSessionFactory;
import gov.ca.cwds.data.BaseDaoImpl;
import gov.ca.cwds.data.legacy.cms.entity.PlacementHomeProfile;
import org.hibernate.SessionFactory;

/**
 * @author CWDS CALS API Team
 */

public class PlacementHomeProfileDao extends BaseDaoImpl<PlacementHomeProfile> {

  @Inject
  public PlacementHomeProfileDao(@PlacementHomeSessionFactory SessionFactory sessionFactory) {
    super(sessionFactory);
  }

}

