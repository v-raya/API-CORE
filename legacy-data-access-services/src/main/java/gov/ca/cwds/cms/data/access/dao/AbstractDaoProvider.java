package gov.ca.cwds.cms.data.access.dao;

import gov.ca.cwds.data.BaseDaoImpl;
import gov.ca.cwds.data.legacy.cms.dao.SsaName3Dao;
import gov.ca.cwds.data.persistence.PersistentObject;
import java.lang.reflect.Constructor;
import org.hibernate.SessionFactory;

/**
 * Provides daos for using in legacy data access services.
 * Created by Alexander Serbin on 12/12/2018
 */
abstract class AbstractDaoProvider {

  private SessionFactory sessionFactory;

  AbstractDaoProvider(SessionFactory sessionFactory) {
    this.sessionFactory = sessionFactory;
  }

  public <D extends BaseDaoImpl<E>, E extends PersistentObject> D getDao(Class<D> daoClass) {
    try {
      Constructor<D> ctor = daoClass.getConstructor(SessionFactory.class);
      return ctor.newInstance(sessionFactory);
    } catch (Exception e) {
      throw new IllegalStateException(e);
    }
  }

  public SsaName3Dao getSsaName3Dao() {
    return new SsaName3Dao(sessionFactory);
  }

}
