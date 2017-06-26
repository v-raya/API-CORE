package gov.ca.cwds.data;

import java.io.Serializable;
import java.util.Iterator;

import org.hibernate.EmptyInterceptor;
import org.hibernate.type.Type;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import gov.ca.cwds.data.persistence.PersistentObject;

/**
 * Hibernate interceptor logs activity and traps referential integrity errors.
 * 
 * @author CWDS API Team
 */
public class ApiHibernateInterceptor extends EmptyInterceptor {

  private static final long serialVersionUID = 1L;

  private static final Logger LOGGER = LoggerFactory.getLogger(ApiHibernateInterceptor.class);

  @Override
  public void onDelete(Object entity, Serializable id, Object[] state, String[] propertyNames,
      Type[] types) {
    LOGGER.info("Delete entity: type={}, id={}", entity.getClass().getName(), id);
  }

  // Called on entity update.
  @Override
  public boolean onFlushDirty(Object entity, Serializable id, Object[] currentState,
      Object[] previousState, String[] propertyNames, Type[] types) {
    LOGGER.info("Flush dirty entity: type={}, id={}", entity.getClass().getName(), id);

    // if (entity instanceof Client) {
    // LOGGER.info("Client Update Operation");
    // return true;
    // }

    return false;
  }

  // Called on entity load.
  @Override
  public boolean onLoad(Object entity, Serializable id, Object[] state, String[] propertyNames,
      Type[] types) {
    LOGGER.info("Load entity: type={}, id={}", entity.getClass().getName(), id);
    return true;
  }

  @Override
  public boolean onSave(Object entity, Serializable id, Object[] state, String[] propertyNames,
      Type[] types) {
    LOGGER.info("Save entity: type={}, id={}", entity.getClass().getName(), id);

    // if (entity instanceof Client) {
    // LOGGER.info("Client Create Operation");
    // return true;
    // }

    return false;
  }

  // Called before commit to database.
  @Override
  public void preFlush(@SuppressWarnings("rawtypes") Iterator iter) {
    LOGGER.info("Before commit");

    while (iter.hasNext()) {
      Object obj = iter.next();
      if (obj instanceof PersistentObject) {
        PersistentObject entity = (PersistentObject) obj;
        LOGGER.info("before commit: type={}, id={}", entity.getClass().getName(),
            entity.getPrimaryKey());
      }
    }
  }

  // Called after committed to database.
  @Override
  public void postFlush(@SuppressWarnings("rawtypes") Iterator iterator) {
    LOGGER.info("After commit");
  }
}
