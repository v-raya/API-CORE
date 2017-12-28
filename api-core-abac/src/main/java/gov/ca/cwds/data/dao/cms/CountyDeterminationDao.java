package gov.ca.cwds.data.dao.cms;

import com.google.inject.Inject;
import gov.ca.cwds.inject.CwsRsSessionFactory;
import java.util.List;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.context.internal.ManagedSessionContext;

/**
 * Hibernate DAO for getting county of client from GVR_ENTC table in DB2 CWSRS1 schema
 *
 * @author CWDS TPT-2
 */
public class CountyDeterminationDao {

  private static final String CLIENT_COUNTY_CACHE = "clientCountyCache";
  private static final String NQ_PARAM_CLIENT_ID = "clientId";

  private final SessionFactory sessionFactory;

  @Inject
  public CountyDeterminationDao(@CwsRsSessionFactory SessionFactory sessionFactory) {
    this.sessionFactory = sessionFactory;
  }

  /**
   * @param clientId Client ID
   * @return List of counties for Client by clientID from CLIENT_CNTY table
   */
  public List<Short> getClientCounties(final String clientId) {
    final String nativeQuery = "SELECT GVR_ENTC "
        + " FROM {h-schema}CLIENT_CNTY A "
        + " WHERE A.CLIENT_ID = :" + NQ_PARAM_CLIENT_ID;
    return executeNativeQuery(nativeQuery, clientId);
  }

  @SuppressWarnings("unchecked")
  private List<Short> executeNativeQuery(final String namedQuery, final String clientId) {
    Session session = null;
    try {
      session = sessionFactory.openSession();
      ManagedSessionContext.bind(session);
      return session.createNativeQuery(namedQuery)
          .setParameter(NQ_PARAM_CLIENT_ID, clientId)
          .setCacheable(true)
          .setCacheRegion(CLIENT_COUNTY_CACHE)
          .getResultList();
    } finally {
      ManagedSessionContext.unbind(sessionFactory);
      if (session != null) {
        session.close();
      }
    }
  }
}
