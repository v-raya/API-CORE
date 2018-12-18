package gov.ca.cwds.cms.data.access.dao;

import com.google.inject.Inject;
import gov.ca.cwds.cms.data.access.inject.NonXaDasSessionFactory;
import org.hibernate.SessionFactory;

/**
 * Created by Alexander Serbin on 12/12/2018
 */
public class NonXaDaoProvider extends AbstractDaoProvider {

  @Inject
  public NonXaDaoProvider(@NonXaDasSessionFactory SessionFactory sessionFactory) {
    super(sessionFactory);
  }

}
