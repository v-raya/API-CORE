package gov.ca.cwds.data.legacy.cms.dao;

import org.hibernate.SessionFactory;

import com.google.inject.Inject;

import gov.ca.cwds.data.BaseDaoImpl;
import gov.ca.cwds.data.legacy.cms.entity.SafelySurrenderedBabies;
import gov.ca.cwds.inject.CmsSessionFactory;

/**
 * DAO for {@link SafelySurrenderedBabies}.
 * 
 * @author CWDS API Team
 */
public class SafelySurrenderedBabiesDao extends BaseDaoImpl<SafelySurrenderedBabies> {

  /**
   * Constructor
   * 
   * @param sessionFactory the sessionFactory
   */
  @Inject
  public SafelySurrenderedBabiesDao(@CmsSessionFactory SessionFactory sessionFactory) {
    super(sessionFactory);
  }

}
