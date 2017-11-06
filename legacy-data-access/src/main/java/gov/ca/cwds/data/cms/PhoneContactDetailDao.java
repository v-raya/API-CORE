package gov.ca.cwds.data.cms;

import com.google.inject.Inject;
import gov.ca.cwds.data.BaseDaoImpl;
import gov.ca.cwds.data.persistence.cms.PhoneContactDetail;
import gov.ca.cwds.inject.CmsSessionFactory;
import org.hibernate.SessionFactory;

/**
 * @author CWDS CALS API Team
 */
public class PhoneContactDetailDao extends BaseDaoImpl<PhoneContactDetail> {

  /**
   * Constructor
   *
   * @param sessionFactory The session factory
   */
  @Inject
  public PhoneContactDetailDao(@CmsSessionFactory SessionFactory sessionFactory) {
    super(sessionFactory);
  }
}
