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

  private static final String SELECT_SENSITIVITY =
    "SELECT SENSTV_IND FROM {h-schema}CLIENT_T WHERE IDENTIFIER = :" + NQ_PARAM_CLIENT_ID;
  private static final String SELECT_SENSITIVITY_MAP =
    "SELECT IDENTIFIER, SENSTV_IND FROM {h-schema}CLIENT_T WHERE IDENTIFIER IN :"
      + NQ_PARAM_CLIENT_IDS;

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
    Object sensitivity = grabSession().createNativeQuery(SELECT_SENSITIVITY)
      .setParameter(NQ_PARAM_CLIENT_ID, clientId).getSingleResult();
    return constructSensitivity(sensitivity);
  }

  /**
   *
   * @param clientIds - collection of client id-s
   * @return map where key is a client id and value is Sensitivity
   */
  public Map<String, Sensitivity> getSensitivityMap(Collection<String> clientIds) {
    if (clientIds == null || clientIds.isEmpty()) {
      return new HashMap<>();
    }
    final Map<String, Sensitivity> sensitivityMap = new HashMap<>(clientIds.size());
    @SuppressWarnings("unchecked")
    List<Object[]> sensitivityResults = grabSession().createNativeQuery(SELECT_SENSITIVITY_MAP)
      .setParameter(NQ_PARAM_CLIENT_IDS, clientIds).getResultList();
    for (Object[] result : sensitivityResults) {
      sensitivityMap.put(result[0].toString(), constructSensitivity(result[1]));
    }
    return sensitivityMap;
  }

  private Sensitivity constructSensitivity(Object sensitivity) {
    if (sensitivity == null) {
      throw new AuthorizationException("Found Client with no sensitivity indicator");
    }
    return Sensitivity.fromCode(sensitivity.toString());
  }
}
