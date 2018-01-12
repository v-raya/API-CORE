package gov.ca.cwds.data.legacy.cms.dao;

import com.google.inject.Inject;
import gov.ca.cwds.data.BaseDaoImpl;
import gov.ca.cwds.data.legacy.cms.entity.ExternalInterface;
import gov.ca.cwds.inject.CmsSessionFactory;
import org.hibernate.SessionFactory;

/**
 * @author CWDS CALS API Team
 */
public class ExternalInterfaceDao extends BaseDaoImpl<ExternalInterface> {
  @Inject
  public ExternalInterfaceDao(@CmsSessionFactory SessionFactory sessionFactory) {
    super(sessionFactory);
  }
}