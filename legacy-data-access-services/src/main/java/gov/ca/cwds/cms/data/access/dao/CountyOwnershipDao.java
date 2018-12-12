package gov.ca.cwds.cms.data.access.dao;

import com.google.inject.Inject;
import gov.ca.cwds.cms.data.access.inject.XaDasSessionFactory;
import gov.ca.cwds.data.BaseDaoImpl;
import gov.ca.cwds.data.legacy.cms.entity.CountyOwnership;
import org.hibernate.SessionFactory;

/**
 * @author CWDS CALS API Team
 */
public class CountyOwnershipDao extends BaseDaoImpl<CountyOwnership> {

  @Inject
  public CountyOwnershipDao(@XaDasSessionFactory SessionFactory sessionFactory) {
    super(sessionFactory);
  }

}
