package gov.ca.cwds.data.dao.cms;

import com.google.inject.Inject;
import gov.ca.cwds.inject.CwsRsSessionFactory;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.tuple.Pair;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

/**
 * Hibernate DAO for getting county of client from the {@code CLIENT_CNTY} table in a DB2
 * "replicated" schema, such as {@code CWSRS1}.
 *
 * <p>
 * DRS, 06/27/2018. This DAO manages the session context and may even close the session in normal
 * mode. In XA transactions, do NOT commit or rollback the transaction or close the session! The
 * long-term solution is to move RequestExecutionContext into
 * <a href="https://github.com/ca-cwds/api-core">api-core</a>.
 * </p>
 *
 * @author CWDS TPT-2
 */
public class CountyDeterminationDao extends BaseAuthorizationDao {

  private static final String CLIENT_COUNTY_CACHE = "clientCountyCache";
  private static final String NQ_PARAM_CLIENT_ID = "clientId";
  private static final String NQ_PARAM_CLIENT_IDS = "clientIds";

  private static final String SELECT_CLIENT_COUNTIES =
    "SELECT GVR_ENTC FROM {h-schema}CLIENT_CNTY WHERE CLIENT_ID = :" + NQ_PARAM_CLIENT_ID;
  private static final String SELECT_CLIENT_COUNTIES_MAP =
    "SELECT CLIENT_ID, GVR_ENTC FROM {h-schema}CLIENT_CNTY WHERE CLIENT_ID IN :"
      + NQ_PARAM_CLIENT_IDS;

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
  @SuppressWarnings("unchecked")
  public List<Short> getClientCounties(final String clientId) {
    final Pair<Session, Boolean> pair = grabSession();
    Session session = pair.getLeft();
    try {
      return session.createNativeQuery(SELECT_CLIENT_COUNTIES)
        .setParameter(NQ_PARAM_CLIENT_ID, clientId).setCacheable(true)
        .setCacheRegion(CLIENT_COUNTY_CACHE).getResultList();
    } finally {
      finalizeSession();
    }
  }

  /**
   *
   * @param clientIds - collection of client id-s
   * @return map where key is a client id and value is a list of county codes
   */
  @SuppressWarnings({"unchecked", "fb-contrib:CLI_CONSTANT_LIST_INDEX"})
  public Map<String, List<Short>> getClientCountiesMap(final Collection<String> clientIds) {
    if (clientIds == null || clientIds.isEmpty()) {
      return new HashMap<>();
    }
    final Map<String, List<Short>> clientCountiesMap = new HashMap<>(clientIds.size());
    final Pair<Session, Boolean> pair = grabSession();
    Session session = pair.getLeft();

    List<Object[]> clientCountiesResults;
    try {
      clientCountiesResults = session.createNativeQuery(SELECT_CLIENT_COUNTIES_MAP)
        .setParameter(NQ_PARAM_CLIENT_IDS, clientIds).getResultList();
    } finally {
      finalizeSession();
    }
    for (Object[] result : clientCountiesResults) {
      String clientId = (String)result[0];
      Short county = (Short)result[1];
      if (!clientCountiesMap.containsKey(clientId)) {
        clientCountiesMap.put(clientId, new ArrayList<>());
      }
      clientCountiesMap.get(clientId).add(county);
    }
    return clientCountiesMap;
  }

}
