package gov.ca.cwds.cms.data.access.dao;

import com.google.inject.Inject;
import gov.ca.cwds.cms.data.access.inject.XaDasSessionFactory;
import gov.ca.cwds.data.BaseDaoImpl;
import gov.ca.cwds.data.legacy.cms.entity.ExternalInterface;
import org.hibernate.SessionFactory;

/**
 * @author CWDS CALS API Team
 */

public class ExternalInterfaceDao extends BaseDaoImpl<ExternalInterface> {

  @Inject
  public ExternalInterfaceDao(@XaDasSessionFactory SessionFactory sessionFactory) {
    super(sessionFactory);
  }

}

