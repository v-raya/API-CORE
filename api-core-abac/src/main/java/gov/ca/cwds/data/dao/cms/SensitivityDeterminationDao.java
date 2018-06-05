package gov.ca.cwds.data.dao.cms;

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
    final String nativeQuery = "SELECT SENSTV_IND " + " FROM {h-schema}CLIENT_T "
        + " WHERE IDENTIFIER = :" + NQ_PARAM_CLIENT_ID;
    Object sensitivity = executeNativeQuery(nativeQuery, clientId);
    if (sensitivity == null) {
      throw new AuthorizationException("Found Client with no sensitive indicator");
    }
    return Sensitivity.fromCode(sensitivity.toString());
  }

  private Object executeNativeQuery(final String namedQuery, final String clientId) {
    return grabSession().createNativeQuery(namedQuery).setParameter(NQ_PARAM_CLIENT_ID, clientId)
        .getSingleResult();
  }

}
