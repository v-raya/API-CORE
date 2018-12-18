package gov.ca.cwds.data.legacy.cms.dao;

import com.google.inject.Inject;
import gov.ca.cwds.data.BaseDaoImpl;
import gov.ca.cwds.data.legacy.cms.entity.PlacementFacilityTypeHistory;
import gov.ca.cwds.inject.CmsSessionFactory;
import org.hibernate.SessionFactory;

/**
 * Created by Alexander Serbin on 12/12/2018
 */
public class PlacementFacilityTypeHistoryDao extends BaseDaoImpl<PlacementFacilityTypeHistory> {

  @Inject
  public PlacementFacilityTypeHistoryDao(@CmsSessionFactory final SessionFactory sessionFactory) {
    super(sessionFactory);
  }

}
