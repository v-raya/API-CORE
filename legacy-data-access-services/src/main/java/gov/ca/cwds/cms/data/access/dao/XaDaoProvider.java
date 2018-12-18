package gov.ca.cwds.cms.data.access.dao;

import com.google.inject.Inject;
import gov.ca.cwds.cms.data.access.inject.XaDasSessionFactory;
import org.hibernate.SessionFactory;

/**
 * Created by Alexander Serbin on 12/12/2018
 */
public class XaDaoProvider extends AbstractDaoProvider {

  @Inject
  public XaDaoProvider(@XaDasSessionFactory SessionFactory sessionFactory) {
    super(sessionFactory);
  }

}
