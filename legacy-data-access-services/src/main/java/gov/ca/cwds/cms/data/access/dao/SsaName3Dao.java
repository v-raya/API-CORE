package gov.ca.cwds.cms.data.access.dao;

import com.google.inject.Inject;
import gov.ca.cwds.cms.data.access.inject.XaDasSessionFactory;
import org.hibernate.SessionFactory;

/**
 * @author CWDS CALS API Team
 */
public class SsaName3Dao extends gov.ca.cwds.data.legacy.cms.dao.SsaName3Dao {

  @Inject
  public SsaName3Dao(@XaDasSessionFactory SessionFactory sessionFactory) {
    super(sessionFactory);
  }

}

