package gov.ca.cwds.data.cms;

import com.google.inject.Inject;
import gov.ca.cwds.data.BaseDaoImpl;
import gov.ca.cwds.data.persistence.cms.OutOfStateCheck;
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
