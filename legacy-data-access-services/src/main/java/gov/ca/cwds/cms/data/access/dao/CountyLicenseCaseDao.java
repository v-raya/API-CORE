package gov.ca.cwds.cms.data.access.dao;

import com.google.inject.Inject;
import gov.ca.cwds.cms.data.access.inject.XaDasSessionFactory;
import gov.ca.cwds.data.BaseDaoImpl;
import gov.ca.cwds.data.legacy.cms.entity.CountyLicenseCase;
import org.hibernate.SessionFactory;

/**
 * CountyLicenseCaseDao.
 * @author CWDS CALS API Team
 */
public class CountyLicenseCaseDao extends BaseDaoImpl<CountyLicenseCase> {

  @Inject
  public CountyLicenseCaseDao(@XaDasSessionFactory SessionFactory sessionFactory) {
    super(sessionFactory);
  }
}
