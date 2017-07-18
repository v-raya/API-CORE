package gov.ca.cwds.data;

import java.io.Serializable;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Consumer;

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

  private static final Map<Class<? extends PersistentObject>, Consumer<PersistentObject>> commitHandlers =
      new ConcurrentHashMap<>();

  @Override
  public void onDelete(Object entity, Serializable id, Object[] state, String[] propertyNames,
      Type[] types) {
    LOGGER.info("Delete entity: type={}, id={}", entity.getClass().getName(), id);
  }

  /**
   * Called on entity update.
   */
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

  /**
   * Called on entity load.
   */
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

  /**
   * Called before commit to database.
   */
  @Override
  public void preFlush(@SuppressWarnings("rawtypes") Iterator iter) {
    LOGGER.info("Before commit");

    while (iter.hasNext()) {
      Object obj = iter.next();
      if (obj instanceof PersistentObject) {
        PersistentObject entity = (PersistentObject) obj;
        LOGGER.info("before commit: type={}, id={}", entity.getClass().getName(),
            entity.getPrimaryKey());

        final Class<?> klazz = entity.getClass();
        if (commitHandlers.containsKey(klazz)) {
          LOGGER.info("handler for class {}", klazz);
          commitHandlers.get(klazz).accept(entity);
        }

      }
    }
  }

  /**
   * Called after committed to database.
   */
  @Override
  public void postFlush(@SuppressWarnings("rawtypes") Iterator iterator) {
    LOGGER.info("After commit");
  }

  /**
   * Register an on-commit handler for an entity.
   * 
   * @param klass entity class to handle
   * @param consumer handler implementation
   */
  public static void addCommitHandler(Class<? extends PersistentObject> klass,
      Consumer<PersistentObject> consumer) {
    commitHandlers.put(klass, consumer);
  }

}
