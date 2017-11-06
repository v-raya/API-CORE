package gov.ca.cwds.data.cms;

import com.google.inject.Inject;
import gov.ca.cwds.data.BaseDaoImpl;
import gov.ca.cwds.data.persistence.cms.PlacementHomeUc;
import gov.ca.cwds.inject.CmsSessionFactory;
import org.hibernate.SessionFactory;

/**
 * @author CWDS CALS API Team
 */
public class PlacementHomeUcDao extends BaseDaoImpl<PlacementHomeUc> {

  @Inject
  public PlacementHomeUcDao(@CmsSessionFactory SessionFactory sessionFactory) {
    super(sessionFactory);
  }
}
