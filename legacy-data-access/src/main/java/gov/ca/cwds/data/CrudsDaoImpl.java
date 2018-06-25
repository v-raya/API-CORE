package gov.ca.cwds.data;

import java.io.Serializable;
import java.text.MessageFormat;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.resource.transaction.spi.TransactionStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import gov.ca.cwds.data.persistence.PersistentObject;
import io.dropwizard.hibernate.AbstractDAO;

/**
 * An implementation of {@link CrudsDao}. Class is final and is expected that other {@link Dao} will
 * contain this implementation and delegate.
 * 
 * @author CWDS API Team
 *
 * @param <T> The {@link PersistentObject} to perform CRUDS operations on
 */
public class CrudsDaoImpl<T extends PersistentObject> extends AbstractDAO<T>
    implements CrudsDao<T> {

  private static final Logger LOGGER = LoggerFactory.getLogger(CrudsDaoImpl.class);

  private SessionFactory sessionFactory;

  /**
   * Grab a session! If a current session is available, return it, else open a new session.
   * 
   * @return usable session
   */
  public Session grabSession() {
    Session session;
    try {
      session = sessionFactory.getCurrentSession();
    } catch (HibernateException e) {
      session = sessionFactory.openSession();
    }

    return session;
  }

  public Transaction joinTransaction(Session session) {
    Transaction txn = session.getTransaction();
    txn = txn != null ? txn : session.beginTransaction();

    if (!txn.getRollbackOnly() && !txn.isActive() && txn.getStatus() != TransactionStatus.COMMITTING
        && txn.getStatus() != TransactionStatus.COMMITTED
        && txn.getStatus() != TransactionStatus.FAILED_COMMIT
        && txn.getStatus() != TransactionStatus.MARKED_ROLLBACK
        && txn.getStatus() != TransactionStatus.ROLLED_BACK
        && txn.getStatus() != TransactionStatus.ROLLING_BACK) {
      LOGGER.warn("\n\t ******* Begin new transaction ******* \n");
      txn.begin();
    }

    return txn;
  }

  /**
   * Default constructor.
   * 
   * @param sessionFactory Hibernate session factory
   */
  public CrudsDaoImpl(SessionFactory sessionFactory) {
    super(sessionFactory);
    this.sessionFactory = sessionFactory;
  }

  /**
   * Provided for <strong>convenience</strong>. Don't abuse this.
   */
  @Override
  public SessionFactory getSessionFactory() {
    return sessionFactory;
  }

  /**
   * {@inheritDoc}
   * 
   * @see gov.ca.cwds.data.CrudsDao#find(java.io.Serializable)
   */
  @Override
  public T find(Serializable primaryKey) {
    grabSession();
    return get(primaryKey);
  }

  /**
   * {@inheritDoc}
   * 
   * @see CrudsDao#delete(java.io.Serializable)
   */
  @Override
  public T delete(Serializable id) {
    final Session session = grabSession();
    T object = find(id);
    if (object != null) {
      session.delete(object);
    }
    return object;
  }

  /**
   * {@inheritDoc}
   * 
   * @see CrudsDao#create(gov.ca.cwds.data.persistence.PersistentObject)
   */
  @Override
  public T create(T object) {
    grabSession();
    if (object.getPrimaryKey() != null) {
      T databaseObject = find(object.getPrimaryKey());
      if (databaseObject != null) {
        String msg = MessageFormat.format("entity with id={0} already exists", object);
        LOGGER.error(msg);
        throw new EntityExistsException(msg);
      }
    }
    return persist(object);
  }

  /**
   * {@inheritDoc}
   * 
   * @see CrudsDao#update(gov.ca.cwds.data.persistence.PersistentObject)
   */
  @Override
  public T update(T object) {
    final Session session = grabSession();
    T databaseObject = find(object.getPrimaryKey());
    if (databaseObject == null) {
      String msg =
          MessageFormat.format("Unable to find entity with id={0}", object.getPrimaryKey());
      throw new EntityNotFoundException(msg);
    }
    session.evict(databaseObject);
    return persist(object);
  }

}
