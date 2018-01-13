package gov.ca.cwds.data.legacy.cms.dao;

import com.google.inject.Inject;
import gov.ca.cwds.data.BaseDaoImpl;
import gov.ca.cwds.data.legacy.cms.entity.PlacementHomeProfile;
import gov.ca.cwds.inject.CmsSessionFactory;
import org.hibernate.SessionFactory;

/**
 * @author CWDS CALS API Team
 */
public class PlacementHomeProfileDao extends BaseDaoImpl<PlacementHomeProfile> {
  @Inject
  public PlacementHomeProfileDao(@CmsSessionFactory SessionFactory sessionFactory) {
    super(sessionFactory);
  }
}