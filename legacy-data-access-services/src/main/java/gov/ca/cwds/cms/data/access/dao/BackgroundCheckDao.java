package gov.ca.cwds.cms.data.access.dao;

import com.google.inject.Inject;
import gov.ca.cwds.cms.data.access.inject.DataAccessServicesSessionFactory;
import gov.ca.cwds.data.BaseDaoImpl;
import gov.ca.cwds.data.legacy.cms.entity.BackgroundCheck;
import org.hibernate.SessionFactory;

/**
 * @author CWDS CALS API Team
 */

public class BackgroundCheckDao extends BaseDaoImpl<BackgroundCheck> {

  @Inject
  public BackgroundCheckDao(@DataAccessServicesSessionFactory SessionFactory sessionFactory) {
    super(sessionFactory);
  }

}

