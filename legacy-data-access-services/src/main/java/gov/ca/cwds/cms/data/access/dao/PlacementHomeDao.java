package gov.ca.cwds.cms.data.access.dao;

import javax.persistence.NoResultException;

import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.inject.Inject;

import gov.ca.cwds.cms.data.access.inject.DataAccessServicesSessionFactory;
import gov.ca.cwds.data.BaseDaoImpl;
import gov.ca.cwds.data.legacy.cms.entity.PlacementHome;

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
    final Class<PlacementHome> entityClass = getEntityClass();
    final Query<PlacementHome> query =
        grabSession().createNamedQuery(entityClass.getSimpleName() + ".find", entityClass)
            .setParameter("facilityId", facilityId);
    PlacementHome placementHome = null;
    try {
      placementHome = query.getSingleResult();
    } catch (NoResultException e) {
      LOG.trace(
          "All hail SonarQube!\n\"Bow down before the one you serve. You're going to get what you deserve.\"",
          e);
      LOG.warn("There is no result for facilityId = {}", facilityId);
    }

    return placementHome;
  }

}
