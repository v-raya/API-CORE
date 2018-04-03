package gov.ca.cwds.data.legacy.cms.dao;

import com.google.inject.Inject;
import gov.ca.cwds.data.BaseDaoImpl;
import gov.ca.cwds.data.legacy.cms.entity.ClientUc;
import gov.ca.cwds.inject.CmsSessionFactory;
import org.hibernate.SessionFactory;

/** @author TPT3 team */
public class ClientUcDao extends BaseDaoImpl<ClientUc> {

  @Inject
  public ClientUcDao(@CmsSessionFactory SessionFactory sessionFactory) {
    super(sessionFactory);
  }
}
