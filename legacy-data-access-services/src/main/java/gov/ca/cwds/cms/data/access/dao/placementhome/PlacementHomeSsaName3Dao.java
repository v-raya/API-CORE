package gov.ca.cwds.cms.data.access.dao.placementhome;

import com.google.inject.Inject;
import gov.ca.cwds.cms.data.access.inject.PlacementHomeSessionFactory;
import gov.ca.cwds.data.legacy.cms.dao.SsaName3Dao;
import org.hibernate.SessionFactory;

/**
 * @author CWDS CALS API Team
 */

public class PlacementHomeSsaName3Dao extends SsaName3Dao {

  @Inject
  public PlacementHomeSsaName3Dao(@PlacementHomeSessionFactory SessionFactory sessionFactory) {
    super(sessionFactory);
  }

}

