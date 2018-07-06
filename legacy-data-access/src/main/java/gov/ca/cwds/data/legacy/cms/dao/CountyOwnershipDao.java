package gov.ca.cwds.data.legacy.cms.dao;

import org.hibernate.SessionFactory;

import com.google.inject.Inject;

import gov.ca.cwds.data.BaseDaoImpl;
import gov.ca.cwds.data.legacy.cms.entity.CountyOwnership;
import gov.ca.cwds.inject.CmsSessionFactory;

/**
 * @author CWDS CALS API Team
 */
public class CountyOwnershipDao extends BaseDaoImpl<CountyOwnership> {

  @Inject
  public CountyOwnershipDao(@CmsSessionFactory SessionFactory sessionFactory) {
    super(sessionFactory);
  }

}
