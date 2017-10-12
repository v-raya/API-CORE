package gov.ca.cwds.data.dao.cms;

import com.google.inject.Inject;
import gov.ca.cwds.data.CrudsDaoImpl;
import gov.ca.cwds.data.persistence.cms.Referral;
import gov.ca.cwds.inject.CmsSessionFactory;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Hibernate DAO for DB2.
 *
 * @author CWDS TPT-2
 */
public class ReferralDao extends CrudsDaoImpl<Referral> {

  private static final Logger LOGGER = LoggerFactory.getLogger(ReferralDao.class);

  /**
   * Constructor
   * 
   * @param sessionFactory The session factory
   */
  @Inject
  public ReferralDao(@CmsSessionFactory SessionFactory sessionFactory) {
    super(sessionFactory);
  }

}
