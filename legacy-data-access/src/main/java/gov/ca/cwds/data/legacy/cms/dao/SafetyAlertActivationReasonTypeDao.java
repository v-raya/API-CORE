package gov.ca.cwds.data.legacy.cms.dao;

import javax.persistence.NoResultException;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.inject.Inject;

import gov.ca.cwds.data.BaseDaoImpl;
import gov.ca.cwds.data.legacy.cms.entity.syscodes.SafetyAlertActivationReasonType;
import gov.ca.cwds.inject.CmsSessionFactory;

/**
 * @author CWDS API Team
 *
 */
public class SafetyAlertActivationReasonTypeDao
    extends BaseDaoImpl<SafetyAlertActivationReasonType> {
  private static final Logger LOGGER =
      LoggerFactory.getLogger(SafetyAlertActivationReasonTypeDao.class);

  /**
   * @param sessionFactory - sessionFactory
   */
  @Inject
  public SafetyAlertActivationReasonTypeDao(@CmsSessionFactory SessionFactory sessionFactory) {
    super(sessionFactory);
  }

  /**
   * @param systemId - systemId
   * @return the SafetyAlertActivationReasonType
   */
  public SafetyAlertActivationReasonType findBySystemId(Short systemId) {
    Session session = this.grabSession();
    Class<SafetyAlertActivationReasonType> entityClass = getEntityClass();
    Query<SafetyAlertActivationReasonType> query = session.createNamedQuery(
        entityClass.getSimpleName() + ".findBySystemId", SafetyAlertActivationReasonType.class);
    query.setParameter("systemId", systemId);
    SafetyAlertActivationReasonType safetyAlertActivationReasonType = null;
    try {
      safetyAlertActivationReasonType = query.getSingleResult();
    } catch (NoResultException e) {
      LOGGER.warn("There is no result for systemId = {}", systemId);
      LOGGER.debug(e.getMessage(), e);
    }

    return safetyAlertActivationReasonType;
  }

}
