package gov.ca.cwds.cms.data.access.dao;

import com.google.inject.Inject;
import gov.ca.cwds.cms.data.access.inject.XaDasSessionFactory;
import gov.ca.cwds.data.BaseDaoImpl;
import gov.ca.cwds.data.legacy.cms.entity.EmergencyContactDetail;
import org.hibernate.SessionFactory;

/**
 * @author CWDS CALS API Team
 */
public class EmergencyContactDetailDao extends BaseDaoImpl<EmergencyContactDetail> {

  @Inject
  public EmergencyContactDetailDao(@XaDasSessionFactory SessionFactory sessionFactory) {
    super(sessionFactory);
  }

}