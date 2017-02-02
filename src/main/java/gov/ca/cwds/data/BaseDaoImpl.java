package gov.ca.cwds.data;

import java.util.Date;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import com.google.common.collect.ImmutableList;

import gov.ca.cwds.data.persistence.PersistentObject;

/**
 * Base class for DAO with some common methods.
 * 
 * @author CWDS API Team
 * @param <T> type of {@link PersistentObject}
 */
public abstract class BaseDaoImpl<T extends PersistentObject> extends CrudsDaoImpl<T>
    implements BaseDao<T> {

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
    Session session = getSessionFactory().getCurrentSession();

    Transaction txn = null;
    try {
      txn = session.beginTransaction();
      Query query = session.getNamedQuery(namedQueryName);
      ImmutableList.Builder<T> entities = new ImmutableList.Builder<>();
      entities.addAll(query.list());
      txn.commit();
      return entities.build();
    } catch (HibernateException h) {
      if (txn != null) {
        txn.rollback();
      }
      throw new DaoException(h);
    }
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
    Session session = getSessionFactory().getCurrentSession();

    Transaction txn = null;
    try {
      txn = session.beginTransaction();
      Query query = session.getNamedQuery(namedQueryName).setDate("after", datetime);
      ImmutableList.Builder<T> results = new ImmutableList.Builder<>();
      results.addAll(query.list());
      txn.commit();
      return results.build();
    } catch (HibernateException h) {
      if (txn != null) {
        txn.rollback();
      }
      throw new DaoException(h);
    }
  }


  /**
   * Retrieve all records for batch processing for a single bucket. PostgreSQL queries would likely
   * rely on the <a href="https://www.postgresql.org/docs/9.6/static/functions-window.html">NTILE
   * analytic function</a>, whereas DB2 10.5, lacking modern analytics, would likely rely on nested
   * or correlated queries. Note that DB2 doesn't even provide basic pseudo-columns, like ROWNUM,
   * without enabling <a href=
   * "http://www.ibm.com/support/knowledgecenter/SSEPGG_10.5.0/com.ibm.db2.luw.apdv.porting.doc/doc/r0052867.html">"compatibility
   * vectors"</a> or writing a
   * <a href="http://hoteljavaopensource.blogspot.com/2011/06/ntile-and-db2.html">user-defined
   * function</a>.
   * 
   * <p>
   * The following DB2 SQL demonstrates how to simulate the NTILE analytic without installing
   * additional packages or non-standard functions.
   * </p>
   * 
   * <pre>
   * select z.identifier, z.bucket, z.rn, row_number() over (partition by z.bucket order by 1) as bucket_rn 
   * from ( 
   *    select mod(y.rn,10) + 1 as bucket, y.rn, y.identifier 
   *    from (
   *       select row_number() over (order by 1) as rn, x.identifier 
   *       from ( select c.identifier from cwsint.client_t c ) x 
   *    ) y 
   * ) z 
   * where z.bucket = 3 for read only;
   * </pre>
   * 
   * <p>
   * Most batch bucket queries are defined Hibernate named queries in the persistence class itself.
   * </p>
   * 
   * @param bucketNum current bucket for this batch
   * @param totalBuckets total buckets in batch run
   * @return ordered list of referral/client document records
   */
  @SuppressWarnings("unchecked")
  public List<T> bucketList(long bucketNum, long totalBuckets) {
    this.getSessionFactory().getCurrentSession().beginTransaction();
    return this.getSessionFactory().getCurrentSession()
        .getNamedQuery(constructNamedQueryName("findAllByBucket")).setLong("bucket_num", bucketNum)
        .setLong("total_buckets", totalBuckets).list();
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
