package gov.ca.cwds.data.legacy.cms.dao;

import com.google.inject.Inject;
import gov.ca.cwds.data.BaseDaoImpl;
import gov.ca.cwds.data.legacy.cms.entity.CountyOwnership;
import gov.ca.cwds.inject.CmsSessionFactory;
import org.hibernate.SessionFactory;

/**
 * @author CWDS CALS API Team
 */
public class CountyOwnershipDao extends BaseDaoImpl<CountyOwnership> {
  @Inject
  public CountyOwnershipDao(@CmsSessionFactory SessionFactory sessionFactory) {
    super(sessionFactory);
  }
}