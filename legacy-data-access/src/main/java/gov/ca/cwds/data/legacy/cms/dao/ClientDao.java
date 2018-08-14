package gov.ca.cwds.data.legacy.cms.dao;

import java.util.function.Consumer;
import java.util.stream.Stream;

import javax.persistence.NoResultException;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.inject.Inject;

import gov.ca.cwds.data.BaseDaoImpl;
import gov.ca.cwds.data.legacy.cms.entity.Client;
import gov.ca.cwds.data.stream.QueryCreator;
import gov.ca.cwds.data.stream.ScalarResultsStreamer;
import gov.ca.cwds.inject.CmsSessionFactory;

/**
 * @author CWDS CALS API Team
 */
public class ClientDao extends BaseDaoImpl<Client> {

  private static final Logger LOG = LoggerFactory.getLogger(ClientDao.class);

  @Inject
  public ClientDao(@CmsSessionFactory SessionFactory sessionFactory) {
    super(sessionFactory);
  }

  /**
   * 
   * @param facilityId facility primary key
   * @param childId child primary key
   * @return child by facility id number and child id.
   */
  public Client findByFacilityIdAndChildId(String facilityId, String childId) {
    Client client = findSingleFacility("findByFacilityIdAndChildId", query -> {
      query.setParameter("facilityId", facilityId);
      query.setParameter("childId", childId);
    });
    if (client == null) {
      LOG.warn("There is no result for facilityId = {} and childId = {}", facilityId, childId);
    }
    return client;
  }

  /**
   * @param licenseNumber license number
   * @param childId child primary key
   * @return child by license number and child id.
   */
  public Client findByLicNumAndChildId(String licenseNumber, String childId) {
    Client client = findSingleFacility("find", query -> {
      query.setParameter("licenseNumber", licenseNumber);
      query.setParameter("childId", childId);
    });
    if (client == null) {
      LOG.warn("There is no result for licenseNumber = {} and childId = {}", licenseNumber,
          childId);
    }
    return client;
  }

  /**
   * @param licenseNumber license number
   * @return stream of facilities found by string facility number.
   */
  public Stream<Client> streamByLicenseNumber(String licenseNumber) {
    QueryCreator<Client> queryCreator = (session, entityClass) -> session
        .createNamedQuery(entityClass.getSimpleName() + ".findAll", entityClass)
        .setParameter("licenseNumber", licenseNumber);
    return new ScalarResultsStreamer<>(this, queryCreator).createStream();
  }

  /**
   * @param licenseNumber license number
   * @return stream of facilities found by integer license number.
   */
  public Stream<Client> streamByLicenseNumber(Integer licenseNumber) {
    return streamByLicenseNumber(String.valueOf(licenseNumber));
  }

  /**
   * @param facilityId facility primary key
   * @return stream of facilities found by facilityId.
   */
  public Stream<Client> streamByFacilityId(String facilityId) {
    QueryCreator<Client> queryCreator = (session, entityClass) -> session
        .createNamedQuery(entityClass.getSimpleName() + ".findByFacilityId", entityClass)
        .setParameter("facilityId", facilityId);
    return new ScalarResultsStreamer<>(this, queryCreator).createStream();
  }

  private Client findSingleFacility(String queryName, Consumer<Query<Client>> setParameters) {
    Session session = grabSession();
    Class<Client> entityClass = getEntityClass();
    Query<Client> query =
        session.createNamedQuery(entityClass.getSimpleName() + "." + queryName, entityClass);
    setParameters.accept(query);
    query.setMaxResults(1);
    Client client = null;
    try {
      client = query.getSingleResult();
    } catch (NoResultException e) {
      LOG.debug(e.getMessage(), e);
    }

    return client;
  }

}
