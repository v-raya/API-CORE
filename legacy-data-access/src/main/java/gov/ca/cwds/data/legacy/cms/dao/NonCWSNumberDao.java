package gov.ca.cwds.data.legacy.cms.dao;

import org.hibernate.SessionFactory;

import com.google.inject.Inject;

import gov.ca.cwds.data.BaseDaoImpl;
import gov.ca.cwds.data.legacy.cms.entity.NonCWSNumber;
import gov.ca.cwds.inject.CmsSessionFactory;

/**
 * DAO for {@link NonCWSNumber}.
 * 
 * @author CWDS API Team
 */
public class NonCWSNumberDao extends BaseDaoImpl<NonCWSNumber> {

  /**
   * Constructor.
   * 
   * @param sessionFactory the sessionFactory
   */
  @Inject
  public NonCWSNumberDao(@CmsSessionFactory SessionFactory sessionFactory) {
    super(sessionFactory);
  }
}
