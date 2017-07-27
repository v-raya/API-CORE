package gov.ca.cwds.data;

import java.io.Serializable;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Consumer;

import org.hibernate.EmptyInterceptor;
import org.hibernate.EntityMode;
import org.hibernate.Transaction;
import org.hibernate.type.Type;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import gov.ca.cwds.data.persistence.PersistentObject;

/**
 * Hibernate interceptor logs activity and traps referential integrity errors.
 * 
 * <p>
 * Methods that return a boolean should return true if the interceptor changed the object, which it
 * does not <i>yet</i> do.
 * </p>
 * 
 * @author CWDS API Team
 */
public class ApiHibernateInterceptor extends EmptyInterceptor {

  private static final long serialVersionUID = 1L;

  private static final Logger LOGGER = LoggerFactory.getLogger(ApiHibernateInterceptor.class);

  /**
   * Types of Hibernate events to handle.
   */
  public enum ApiHibernateEvent {
    BEFORE_COMMIT, AFTER_COMMIT, SAVE, LOAD, DELETE, AFTER_TXN_BEGIN, BEFORE_TXN_COMPLETE, AFTER_TXN_COMPLETE;
  }

  private static final Map<Class<? extends PersistentObject>, Consumer<PersistentObject>> handlers =
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
    return false;
  }

  /**
   * Called on entity load.
   */
  @Override
  public boolean onLoad(Object entity, Serializable id, Object[] state, String[] propertyNames,
      Type[] types) {
    LOGGER.info("Load entity: type={}, id={}", entity.getClass().getName(), id);
    return false;
  }

  @Override
  public boolean onSave(Object entity, Serializable id, Object[] state, String[] propertyNames,
      Type[] types) {
    LOGGER.info("Save entity: type={}, id={}", entity.getClass().getName(), id);
    return false;
  }

  /**
   * Called before commit to database.
   */
  @Override
  public void preFlush(@SuppressWarnings("rawtypes") Iterator iter) {
    LOGGER.info("preFlush");

    while (iter.hasNext()) {
      Object obj = iter.next();
      if (obj instanceof PersistentObject) {
        PersistentObject entity = (PersistentObject) obj;
        LOGGER.info("preFlush: type={}, id={}", entity.getClass().getName(),
            entity.getPrimaryKey());

        final Class<?> klazz = entity.getClass();
        if (handlers.containsKey(klazz)) {
          LOGGER.info("handler for class {}", klazz);
          handlers.get(klazz).accept(entity);
        }

      }
    }
  }

  /**
   * Called after the transaction commits.
   */
  @Override
  public void postFlush(@SuppressWarnings("rawtypes") Iterator iterator) {
    LOGGER.info("postFlush, After commit");
  }

  /**
   * Register an RI handler by entity.
   * 
   * @param klass entity class to handle
   * @param consumer handler implementation
   */
  public static void addHandler(Class<? extends PersistentObject> klass,
      Consumer<PersistentObject> consumer) {
    handlers.put(klass, consumer);
  }

  @Override
  public void afterTransactionBegin(Transaction tx) {
    super.afterTransactionBegin(tx);
    LOGGER.info("afterTransactionBegin: txt status={}", tx.getStatus());
  }

  @Override
  public void beforeTransactionCompletion(Transaction tx) {
    super.beforeTransactionCompletion(tx);
    LOGGER.info("beforeTransactionCompletion: txt status={}", tx.getStatus());
  }

  @Override
  public void afterTransactionCompletion(Transaction tx) {
    super.afterTransactionCompletion(tx);
    LOGGER.info("afterTransactionCompletion: txt status={}", tx.getStatus());
  }

  @Override
  public Object instantiate(String entityName, EntityMode entityMode, Serializable id) {
    LOGGER.info("instantiate: entityName={}, id={}", entityName, id);
    return super.instantiate(entityName, entityMode, id);
  }

}
