package gov.ca.cwds.data.legacy.cms.dao;

import com.google.inject.Inject;

import gov.ca.cwds.data.CrudsDaoImpl;
import gov.ca.cwds.data.legacy.cms.entity.ChildClient;
import gov.ca.cwds.inject.CmsSessionFactory;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;

/**
 * DAO for {@link ChildClient}.
 *
 * @author CWDS API Team
 */
public class ChildClientDao extends CrudsDaoImpl<ChildClient> {

  /**
   * Constructor
   *
   * @param sessionFactory The sessionFactory
   */
  @Inject
  public ChildClientDao(@CmsSessionFactory SessionFactory sessionFactory) {
    super(sessionFactory);
  }

  /**
   * Find the victim Child Clients associated with a referral
   *
   * @param referralId the referral identifier
   * @return the Child Clients
   */
  @SuppressWarnings("unchecked")
  public ChildClient[] findVictimClients(String referralId) {
    Session session = this.getSessionFactory().getCurrentSession();
    Query<ChildClient> query = session
        .createNamedQuery(ChildClient.FIND_VICTIM_CLIENTS,
            ChildClient.class);
    query.setParameter("referralId", referralId);
    return query.list().toArray(new ChildClient[0]);
  }
}
