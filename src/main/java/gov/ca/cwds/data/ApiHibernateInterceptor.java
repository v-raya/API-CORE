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
 * Hibernate interceptor that logs SQL activity and traps referential integrity (RI) errors as a
 * <strong>last resort</strong>.
 * 
 * <p>
 * Validate any other "last ditch" constraints or business rules here before committing a
 * transaction to the database. Register handlers with method {@link #addHandler(Class, Consumer)}.
 * </p>
 * 
 * <p>
 * Enforce foreign key constraints using "normal" Hibernate mechanisms, such as this typical FK:
 * </p>
 * <blockquote>
 * 
 * <pre>
 * &#64;ManyToOne(optional = false)
 * &#64;JoinColumn(name = "FKCLIENT_T", nullable = false, updatable = false, insertable = false)
 * private Client client;
 * </pre>
 * 
 * </blockquote>
 * 
 * <p>
 * Boolean return methods should return true <i>only</i> if the interceptor changes the object,
 * which it <i>does not yet</i> do.
 * </p>
 * 
 * @author CWDS API Team
 * @see ApiReferentialCheck
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
    if (LOGGER.isDebugEnabled()) {
      LOGGER.debug("onDelete -> id={}, entity={}", id, entity);
    } else {
      LOGGER.info("onDelete -> id={}, entityClass={}", id, entity.getClass().getName());
    }
  }

  /**
   * Called on entity update.
   */
  @Override
  public boolean onFlushDirty(Object entity, Serializable id, Object[] currentState,
      Object[] previousState, String[] propertyNames, Type[] types) {
    if (LOGGER.isDebugEnabled()) {
      LOGGER.debug("onFlushDirty -> id={}, entity={}", id, entity);
    } else {
      LOGGER.info("onFlushDirty -> id={}, entityClass={}", id, entity.getClass().getName());
    }
    return false;
  }

  /**
   * Called on entity load.
   */
  @Override
  public boolean onLoad(Object entity, Serializable id, Object[] state, String[] propertyNames,
      Type[] types) {
    if (LOGGER.isDebugEnabled()) {
      LOGGER.debug("onLoad -> id={}, entity={}", id, entity);
    } else {
      LOGGER.info("onLoad -> id={}, entityClass={}", id, entity.getClass().getName());
    }
    return false;
  }

  @Override
  public boolean onSave(Object entity, Serializable id, Object[] state, String[] propertyNames,
      Type[] types) {
    if (LOGGER.isDebugEnabled()) {
      LOGGER.debug("onSave -> id={}, entity={}", id, entity);
    } else {
      LOGGER.info("onSave -> id={}, entityClass={}", id, entity.getClass().getName());
    }
    return false;
  }

  /**
   * Called <strong>before</strong> the transaction commits.
   */
  @Override
  public void preFlush(@SuppressWarnings("rawtypes") Iterator iter) {
    while (iter.hasNext()) {
      Object obj = iter.next();
      if (obj instanceof PersistentObject) {

        PersistentObject entity = (PersistentObject) obj;
        if (LOGGER.isTraceEnabled()) {
          LOGGER.trace("preFlush -> id={}, entity={}", entity.getPrimaryKey(), entity);
        } else {
          LOGGER.info("preFlush -> id={}, entityClass={}", entity.getPrimaryKey(),
              entity.getClass().getName());
        }

        final Class<?> klazz = entity.getClass();
        if (handlers.containsKey(klazz)) {
          LOGGER.debug("handler for class {}", klazz);
          handlers.get(klazz).accept(entity);
        }

      }
    }
  }

  /**
   * Called <strong>after</strong> the transaction commits.
   */
  @Override
  public void postFlush(@SuppressWarnings("rawtypes") Iterator iterator) {
    LOGGER.info("postFlush -> after commit");
  }

  /**
   * Register an RI handler by entity.
   * 
   * @param klass entity class to handle
   * @param consumer handler implementation
   */
  public static void addHandler(Class<? extends PersistentObject> klass,
      Consumer<PersistentObject> consumer) {
    LOGGER.trace("addHandler -> class={}", klass.getName());
    handlers.put(klass, consumer);
  }

  @Override
  public void afterTransactionBegin(Transaction tx) {
    LOGGER.trace("afterTransactionBegin -> txt status={}", tx.getStatus());
    super.afterTransactionBegin(tx);
  }

  @Override
  public void beforeTransactionCompletion(Transaction tx) {
    LOGGER.info("beforeTransactionCompletion -> txt status={}", tx.getStatus());
    super.beforeTransactionCompletion(tx);
  }

  @Override
  public void afterTransactionCompletion(Transaction tx) {
    LOGGER.info("afterTransactionCompletion -> txt status={}", tx.getStatus());
    super.afterTransactionCompletion(tx);
  }

  @Override
  public Object instantiate(String entityName, EntityMode entityMode, Serializable id) {
    LOGGER.trace("preFlush -> id={}, entityClass={}", id, entityName);
    return super.instantiate(entityName, entityMode, id);
  }

}
