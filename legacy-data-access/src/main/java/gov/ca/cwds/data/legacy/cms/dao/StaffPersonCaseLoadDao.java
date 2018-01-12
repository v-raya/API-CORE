package gov.ca.cwds.data.legacy.cms.dao;

import com.google.inject.Inject;
import gov.ca.cwds.data.BaseDaoImpl;
import gov.ca.cwds.data.legacy.cms.entity.StaffPersonCaseLoad;
import gov.ca.cwds.inject.CmsSessionFactory;
import org.hibernate.SessionFactory;

/**
 * @author CWDS TPT-3 Team
 */
public class StaffPersonCaseLoadDao extends BaseDaoImpl<StaffPersonCaseLoad> {

  @Inject
  public StaffPersonCaseLoadDao(@CmsSessionFactory final SessionFactory sessionFactory) {
    super(sessionFactory);
  }

}
