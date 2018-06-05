package gov.ca.cwds.data.dao.cms;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.context.internal.ManagedSessionContext;

import com.google.inject.Inject;
import com.google.inject.name.Named;

import gov.ca.cwds.inject.CwsRsSessionFactory;

/**
 * Hibernate DAO for getting county of client from the {@code CLIENT_CNTY} table in a DB2
 * "replicated" schema, such as {@code CWSRS1}.
 *
 * @author CWDS TPT-2
 */
public class CountyDeterminationDao extends BaseAuthorizationDao {

  private static final String CLIENT_COUNTY_CACHE = "clientCountyCache";
  private static final String NQ_PARAM_CLIENT_ID = "clientId";

  protected final boolean managed;

  @Inject
  public CountyDeterminationDao(@CwsRsSessionFactory SessionFactory sessionFactory,
      @Named("managed") String managed) {
    super(sessionFactory);
    this.managed = StringUtils.isBlank(managed) || "Y".equals(managed);
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
    Session session = null;
    try {
      session = grabSession();
      if (managed) {
        ManagedSessionContext.bind(session);
      }
      return session.createNativeQuery(namedQuery).setParameter(NQ_PARAM_CLIENT_ID, clientId)
          .setCacheable(true).setCacheRegion(CLIENT_COUNTY_CACHE).getResultList();
    } finally {
      if (managed) {
        ManagedSessionContext.unbind(sessionFactory);
        if (session != null) {
          session.close();
        }
      }
    }
  }

}
