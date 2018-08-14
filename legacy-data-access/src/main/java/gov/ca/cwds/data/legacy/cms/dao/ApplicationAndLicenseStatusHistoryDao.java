package gov.ca.cwds.data.legacy.cms.dao;

import com.google.inject.Inject;
import gov.ca.cwds.data.BaseDaoImpl;
import gov.ca.cwds.data.legacy.cms.entity.ApplicationAndLicenseStatusHistory;
import gov.ca.cwds.inject.CmsSessionFactory;
import org.hibernate.SessionFactory;

public class ApplicationAndLicenseStatusHistoryDao extends
  BaseDaoImpl<ApplicationAndLicenseStatusHistory> {

  /**
   * Constructor.
   *
   * @param sessionFactory The session factory
   */
  @Inject
  public ApplicationAndLicenseStatusHistoryDao(@CmsSessionFactory SessionFactory sessionFactory) {
    super(sessionFactory);
  }
}
