package gov.ca.cwds.cms.data.access.dao;

import com.google.inject.Inject;
import gov.ca.cwds.cms.data.access.inject.DataAccessServicesSessionFactory;
import gov.ca.cwds.data.BaseDaoImpl;
import gov.ca.cwds.data.legacy.cms.entity.PlacementHome;
import javax.persistence.NoResultException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author CWDS CALS API Team
 */

public class PlacementHomeDao extends BaseDaoImpl<PlacementHome> {

  private static final Logger LOG = LoggerFactory.getLogger(PlacementHomeDao.class);

  @Inject
  public PlacementHomeDao(@DataAccessServicesSessionFactory SessionFactory sessionFactory) {
    super(sessionFactory);
  }

  /**
   * Returns PlacementHome by id using named query.
   *
   * @param facilityId PlacementHome identifier
   * @return PlacementHome
   */
  public PlacementHome findByFacilityId(String facilityId) {
    Session session = getSessionFactory().getCurrentSession();
    Class<PlacementHome> entityClass = getEntityClass();
    Query<PlacementHome> query =
        session.createNamedQuery(entityClass.getSimpleName() + ".find", entityClass)
            .setParameter("facilityId", facilityId);
    PlacementHome placementHome = null;
    try {
      placementHome = query.getSingleResult();
    } catch (NoResultException e) {
      LOG.warn("There is no result for facilityId = {}", facilityId);
      LOG.debug(e.getMessage(), e);
    }
    return placementHome;
  }

}
