package gov.ca.cwds.data.legacy.cms.dao;

import com.google.inject.Inject;
import gov.ca.cwds.data.BaseDaoImpl;
import gov.ca.cwds.data.legacy.cms.entity.CountyLicenseCase;
import gov.ca.cwds.data.legacy.cms.entity.SubstituteCareProvider;
import gov.ca.cwds.inject.CmsSessionFactory;
import org.hibernate.SessionFactory;

/**
 * @author CWDS CALS API Team
 */
public class CountyLicenseCaseDao extends BaseDaoImpl<CountyLicenseCase> {

  @Inject
  public CountyLicenseCaseDao(@CmsSessionFactory SessionFactory sessionFactory) {
    super(sessionFactory);
  }
}
