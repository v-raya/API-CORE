package gov.ca.cwds.data.legacy.cms.dao;

import com.google.inject.Inject;
import gov.ca.cwds.data.BaseDaoImpl;
import gov.ca.cwds.data.legacy.cms.entity.BaseClient;
import gov.ca.cwds.data.stream.QueryCreator;
import gov.ca.cwds.data.stream.ScalarResultsStreamer;
import gov.ca.cwds.inject.CmsSessionFactory;
import java.util.stream.Stream;
import javax.persistence.NoResultException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author CWDS CALS API Team
 */
public class ClientDao extends BaseDaoImpl<BaseClient> {

  private static final Logger LOG = LoggerFactory.getLogger(ClientDao.class);

  @Inject
  public ClientDao(@CmsSessionFactory SessionFactory sessionFactory) {
    super(sessionFactory);
  }

  public BaseClient findByLicNumAndChildId(String licenseNumber, String childId) {
    Session session = getSessionFactory().getCurrentSession();
    Class<BaseClient> entityClass = getEntityClass();
    Query<BaseClient> query =
        session.createNamedQuery(entityClass.getSimpleName() + ".find", entityClass);

    query.setParameter("licenseNumber", licenseNumber);
    query.setParameter("childId", childId);
    query.setMaxResults(1);

    BaseClient client = null;
    try {
      client = query.getSingleResult();
    } catch (NoResultException e) {
      LOG.warn(
          "There is no result for licenseNumber = {} and childId = {}", licenseNumber, childId);
      LOG.debug(e.getMessage(), e);
    }

    return client;
  }

  public Stream<BaseClient> streamByLicenseNumber(String licenseNumber) {
    QueryCreator<BaseClient> queryCreator = (session, entityClass) -> session
        .createNamedQuery(entityClass.getSimpleName() + ".findAll", entityClass)
        .setParameter("licenseNumber", licenseNumber);
    return new ScalarResultsStreamer<>(this, queryCreator).createStream();
  }

  public Stream<BaseClient> streamByLicenseNumber(Integer licenseNumber) {
    return streamByLicenseNumber(String.valueOf(licenseNumber));
  }

  public Stream<BaseClient> streamByFacilityId(String facilityId) {
    QueryCreator<BaseClient> queryCreator = (session, entityClass) -> session
        .createNamedQuery(entityClass.getSimpleName() + ".findByFacilityId", entityClass)
        .setParameter("facilityId", facilityId);
    return new ScalarResultsStreamer<>(this, queryCreator).createStream();
  }
}
