package gov.ca.cwds.data;

import java.util.Date;
import java.util.List;

import org.hibernate.CacheMode;
import org.hibernate.FlushMode;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.ScrollMode;
import org.hibernate.ScrollableResults;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.resource.transaction.spi.TransactionStatus;

import com.google.common.collect.ImmutableList;

import gov.ca.cwds.data.persistence.PersistentObject;
import gov.ca.cwds.data.std.BatchBucketDao;

/**
 * Base class for DAO with some common methods.
 * 
 * @author CWDS API Team
 * @param <T> type of {@link PersistentObject}
 */
public abstract class BaseDaoImpl<T extends PersistentObject> extends CrudsDaoImpl<T>
    implements BaseDao<T>, BatchBucketDao<T> {

  /**
   * Constructor
   * 
   * @param sessionFactory The session factory
   */
  public BaseDaoImpl(SessionFactory sessionFactory) {
    super(sessionFactory);
  }

  /**
   * {@inheritDoc}
   * 
   * @see gov.ca.cwds.data.BaseDao#findAll()
   */
  @SuppressWarnings("unchecked")
  @Override
  public List<T> findAll() {
    final String namedQueryName = constructNamedQueryName("findAll");
    final Session session = getSessionFactory().getCurrentSession();

    Transaction txn = session.getTransaction();
    txn = txn != null ? txn : session.beginTransaction();

    if (TransactionStatus.NOT_ACTIVE == txn.getStatus() || !txn.isActive()) {
      txn.begin();
    }

    try {
      final Query query = session.getNamedQuery(namedQueryName);
      ImmutableList.Builder<T> entities = new ImmutableList.Builder<>();
      entities.addAll(query.list());
      txn.commit();
      return entities.build();
    } catch (HibernateException h) {
      txn.rollback();
      String message = h.getMessage() + ". Transaction Status: " + txn.getStatus();
      throw new DaoException(message, h);
    }
  }

  public List<T> queryImmutableList(String queryName) {
    Session session = this.getSessionFactory().getCurrentSession();
    org.hibernate.query.Query<T> query = session.createNamedQuery(queryName, getEntityClass());
    ImmutableList.Builder<T> entities = new ImmutableList.Builder<>();
    entities.addAll(query.list());
    return entities.build();
  }

  /**
   * {@inheritDoc}
   * 
   * @see gov.ca.cwds.data.BaseDao#findAllUpdatedAfter(java.util.Date)
   */
  @SuppressWarnings("unchecked")
  @Override
  public List<T> findAllUpdatedAfter(Date datetime) {
    final String namedQueryName = constructNamedQueryName("findAllUpdatedAfter");
    final Session session = getSessionFactory().getCurrentSession();
    final Transaction txn = session.beginTransaction();
    try {
      // Compatible with both DB2 z/OS and Linux.
      final Query query = session.getNamedQuery(namedQueryName).setCacheable(false)
          .setFlushMode(FlushMode.MANUAL).setReadOnly(true).setCacheMode(CacheMode.IGNORE)
          .setTimestamp("after", new java.sql.Timestamp(datetime.getTime()));

      // Iterate, process, flush.
      int fetchSize = 5000;
      query.setFetchSize(fetchSize);
      ScrollableResults results = query.scroll(ScrollMode.FORWARD_ONLY);
      ImmutableList.Builder<T> ret = new ImmutableList.Builder<>();
      int cnt = 0;

      while (results.next()) {
        Object[] row = results.get();
        ret.add((T) row[0]);

        if (((++cnt) % fetchSize) == 0) {
          session.flush();
        }
      }

      session.flush();
      results.close();
      txn.commit();
      return ret.build();
    } catch (HibernateException h) {
      txn.rollback();
      throw new DaoException(h);
    }
  }

  /**
   * Builds named query by the naming convention of "entity class.suffix".
   * 
   * @param suffix suffix of the named query
   * @return named query for lookup
   */
  private String constructNamedQueryName(String suffix) {
    return getEntityClass().getName() + "." + suffix;
  }

}
