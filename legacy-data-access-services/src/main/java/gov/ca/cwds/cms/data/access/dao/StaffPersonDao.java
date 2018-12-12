package gov.ca.cwds.cms.data.access.dao;

import com.google.inject.Inject;
import gov.ca.cwds.cms.data.access.inject.XaDasSessionFactory;
import gov.ca.cwds.data.BaseDaoImpl;
import gov.ca.cwds.data.legacy.cms.entity.StaffPerson;
import org.hibernate.SessionFactory;

/**
 * Created by Alexander Serbin on 12/12/2018
 */
public class StaffPersonDao extends BaseDaoImpl<StaffPerson> {

  @Inject
  public StaffPersonDao(@XaDasSessionFactory SessionFactory sessionFactory) {
    super(sessionFactory);
  }

}
