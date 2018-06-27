package gov.ca.cwds.data.dao.cms;

import java.util.List;

import org.apache.commons.lang3.tuple.Pair;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.context.internal.ManagedSessionContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.inject.Inject;
import com.google.inject.name.Named;

import gov.ca.cwds.inject.CwsRsSessionFactory;

/**
 * Hibernate DAO for getting county of client from the {@code CLIENT_CNTY} table in a DB2
 * "replicated" schema, such as {@code CWSRS1}.
 *
 * <p>
 * DRS, 06/27/2018. This DAO manages the session context and may even close the session in normal
 * mode. In XA transactions, do NOT commit or rollback the transaction or close the session! The
 * long-term solution is to move RequestExecutionContext into api-core.
 * </p>
 *
 * @author CWDS TPT-2
 */
public class CountyDeterminationDao extends BaseAuthorizationDao {

  private static final Logger LOGGER = LoggerFactory.getLogger(CountyDeterminationDao.class);

  private static final String CLIENT_COUNTY_CACHE = "clientCountyCache";
  private static final String NQ_PARAM_CLIENT_ID = "clientId";

  @Inject(optional = true)
  @Named("managed")
  private String managedProp;

  @Inject
  public CountyDeterminationDao(@CwsRsSessionFactory SessionFactory sessionFactory) {
    super(sessionFactory);
  }

  /**
   * List county system code id's (meta category {@code GVR_ENTC}) for the given clients.
   * 
   * @param clientId Client ID
   * @return List of counties for Client by clientID from CLIENT_CNTY table
   */
  public List<Short> getClientCounties(final String clientId) {
    final String nativeQuery =
        "SELECT GVR_ENTC FROM {h-schema}CLIENT_CNTY WHERE CLIENT_ID = :" + NQ_PARAM_CLIENT_ID;
    return executeNativeQuery(nativeQuery, clientId);
  }

  @SuppressWarnings("unchecked")
  private List<Short> executeNativeQuery(final String namedQuery, final String clientId) {
    final boolean managed = !getXaMode();
    LOGGER.info("CountyDeterminationDao.executeNativeQuery: managed: {}", managed);

    final Pair<Session, Boolean> openSession = grabSession();
    final boolean didOpenNew = openSession.getRight();

    Session session = null;
    try {
      session = openSession.getLeft();
      if (managed) {
        ManagedSessionContext.bind(session);
      }
      return session.createNativeQuery(namedQuery).setParameter(NQ_PARAM_CLIENT_ID, clientId)
          .setCacheable(true).setCacheRegion(CLIENT_COUNTY_CACHE).getResultList();
    } finally {
      if (managed) {
        ManagedSessionContext.unbind(sessionFactory);
        // if (session != null && didOpenNew) {
        // session.close();
        // }
      }
    }
  }

}
