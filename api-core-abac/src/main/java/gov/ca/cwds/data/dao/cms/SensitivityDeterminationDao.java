package gov.ca.cwds.data.dao.cms;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.shiro.authz.AuthorizationException;
import org.hibernate.SessionFactory;

import com.google.inject.Inject;

import gov.ca.cwds.data.legacy.cms.entity.enums.Sensitivity;
import gov.ca.cwds.inject.CmsSessionFactory;

/**
 * Dao to determine client sensitivity.
 *
 * @author CWDS API Team
 */
public class SensitivityDeterminationDao extends BaseAuthorizationDao {

  private static final String NQ_PARAM_CLIENT_ID = "clientId";
  private static final String NQ_PARAM_CLIENT_IDS = "clientIds";

  @Inject
  public SensitivityDeterminationDao(@CmsSessionFactory SessionFactory sessionFactory) {
    super(sessionFactory);
  }

  /**
   * Method to determine client sensitivity.
   *
   * @param clientId client id
   * @return sensitivity
   */
  public Sensitivity getSensitivity(final String clientId) {
    final String nativeQuery =
        "SELECT SENSTV_IND FROM {h-schema}CLIENT_T WHERE IDENTIFIER = :" + NQ_PARAM_CLIENT_ID;
    String sensitivityCode = (String) grabSession().createNativeQuery(nativeQuery)
      .setParameter(NQ_PARAM_CLIENT_ID, clientId).getSingleResult();
    return constructSensitivity(sensitivityCode);
  }

  public Map<String, Sensitivity> getSensitivityMap(Collection<String> clientIds) {
    if (clientIds == null || clientIds.isEmpty()) {
      return new HashMap<>();
    }
    final Map<String, Sensitivity> sensitivityMap = new HashMap<>(clientIds.size());
    final String nativeQuery =
      "SELECT IDENTIFIER, SENSTV_IND FROM {h-schema}CLIENT_T WHERE IDENTIFIER IN :"
        + NQ_PARAM_CLIENT_IDS;
    @SuppressWarnings("unchecked")
    List<String[]> sensitivityResults = grabSession().createNativeQuery(nativeQuery)
      .setParameter(NQ_PARAM_CLIENT_IDS, clientIds).getResultList();
    for (String[] sensitivityResult : sensitivityResults) {
      sensitivityMap.put(sensitivityResult[0], constructSensitivity(sensitivityResult[1]));
    }
    return sensitivityMap;
  }

  private Sensitivity constructSensitivity(String code) {
    if (code == null) {
      throw new AuthorizationException("Found Client with no sensitive indicator");
    }
    return Sensitivity.fromCode(code);
  }
}
