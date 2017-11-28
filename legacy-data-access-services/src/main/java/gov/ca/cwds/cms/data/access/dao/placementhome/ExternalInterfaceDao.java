package gov.ca.cwds.cms.data.access.dao.placementhome;

import com.google.inject.Inject;
import gov.ca.cwds.cms.data.access.inject.PlacementHomeSessionFactory;
import gov.ca.cwds.data.BaseDaoImpl;
import gov.ca.cwds.data.legacy.cms.entity.ExternalInterface;
import org.hibernate.SessionFactory;

/**
 * @author CWDS CALS API Team
 */

public class ExternalInterfaceDao extends BaseDaoImpl<ExternalInterface> {

  @Inject
  public ExternalInterfaceDao(@PlacementHomeSessionFactory SessionFactory sessionFactory) {
    super(sessionFactory);
  }

}

