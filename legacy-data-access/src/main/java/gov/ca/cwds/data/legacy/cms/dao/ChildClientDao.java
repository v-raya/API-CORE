package gov.ca.cwds.data.legacy.cms.dao;

import com.google.inject.Inject;

import gov.ca.cwds.data.CrudsDaoImpl;
import gov.ca.cwds.data.legacy.cms.entity.ChildClient;
import gov.ca.cwds.inject.CmsSessionFactory;
import org.hibernate.Query;
import org.hibernate.SessionFactory;

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
    Query query = this.getSessionFactory().getCurrentSession()
        .getNamedQuery("gov.ca.cwds.data.persistence.cms.ChildClient.findVictimClients")
        .setString("referralId", referralId);
    return (ChildClient[]) query.list().toArray(new ChildClient[0]);
  }

}
