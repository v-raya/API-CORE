package gov.ca.cwds.cms.data.access.dao.placementhome;

import com.google.inject.Inject;
import gov.ca.cwds.cms.data.access.inject.PlacementHomeSessionFactory;
import gov.ca.cwds.data.BaseDaoImpl;
import gov.ca.cwds.data.legacy.cms.entity.BackgroundCheck;
import org.hibernate.SessionFactory;

/**
 * @author CWDS CALS API Team
 */

public class BackgroundCheckDao extends BaseDaoImpl<BackgroundCheck> {

  @Inject
  public BackgroundCheckDao(@PlacementHomeSessionFactory SessionFactory sessionFactory) {
    super(sessionFactory);
  }

}

