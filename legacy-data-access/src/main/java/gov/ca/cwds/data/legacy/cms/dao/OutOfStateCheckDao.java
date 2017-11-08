package gov.ca.cwds.data.legacy.cms.dao;

import com.google.inject.Inject;
import gov.ca.cwds.data.BaseDaoImpl;
import gov.ca.cwds.data.legacy.cms.entity.OutOfStateCheck;
import gov.ca.cwds.inject.CmsSessionFactory;
import org.hibernate.SessionFactory;

/**
 * @author CWDS CALS API Team
 */
public class OutOfStateCheckDao extends BaseDaoImpl<OutOfStateCheck> {

  @Inject
  public OutOfStateCheckDao(@CmsSessionFactory SessionFactory sessionFactory) {
    super(sessionFactory);
  }
}
