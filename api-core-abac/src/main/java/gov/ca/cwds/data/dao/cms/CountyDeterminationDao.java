package gov.ca.cwds.data.dao.cms;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;

import com.google.inject.Inject;

import gov.ca.cwds.inject.CwsRsSessionFactory;

/**
 * Hibernate DAO for getting county of client from the CLIENT_CNTY table in DB2 CWSRS1 schema.
 *
 * @author CWDS TPT-2
 */
public class CountyDeterminationDao extends BaseAuthorizationDao {

  private static final String CLIENT_COUNTY_CACHE = "clientCountyCache";
  private static final String NQ_PARAM_CLIENT_ID = "clientId";

  @Inject
  public CountyDeterminationDao(@CwsRsSessionFactory SessionFactory sessionFactory) {
    super(sessionFactory);
  }

  /**
   * @param clientId Client ID
   * @return List of counties for Client by clientID from CLIENT_CNTY table
   */
  public List<Short> getClientCounties(final String clientId) {
    final String nativeQuery = "SELECT GVR_ENTC " + " FROM {h-schema}CLIENT_CNTY "
        + " WHERE CLIENT_ID = :" + NQ_PARAM_CLIENT_ID;
    return executeNativeQuery(nativeQuery, clientId);
  }

  @SuppressWarnings("unchecked")
  private List<Short> executeNativeQuery(final String namedQuery, final String clientId) {
    Session session = null;
    try {
      session = grabSession();
      // ManagedSessionContext.bind(session);
      return session.createNativeQuery(namedQuery).setParameter(NQ_PARAM_CLIENT_ID, clientId)
          .setCacheable(true).setCacheRegion(CLIENT_COUNTY_CACHE).getResultList();
    } finally {
      // ManagedSessionContext.unbind(getSessionFactory());
      // if (session != null) {
      // session.close(); // Probably don't want this.
      // }
    }
  }

}
