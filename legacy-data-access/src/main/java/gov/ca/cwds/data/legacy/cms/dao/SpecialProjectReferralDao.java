package gov.ca.cwds.data.legacy.cms.dao;

import org.hibernate.SessionFactory;

import com.google.inject.Inject;

import gov.ca.cwds.data.BaseDaoImpl;
import gov.ca.cwds.data.legacy.cms.entity.SpecialProjectReferral;
import gov.ca.cwds.inject.CmsSessionFactory;

/**
 * DAO for {@link SpecialProjectReferral}.
 * 
 * @author CWDS API Team
 */
public class SpecialProjectReferralDao extends BaseDaoImpl<SpecialProjectReferral> {

  /**
   * Constructor
   * 
   * @param sessionFactory the sessionFactory
   */
  @Inject
  public SpecialProjectReferralDao(@CmsSessionFactory SessionFactory sessionFactory) {
    super(sessionFactory);
  }
}
