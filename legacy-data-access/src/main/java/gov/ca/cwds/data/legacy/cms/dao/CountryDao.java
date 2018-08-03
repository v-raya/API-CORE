package gov.ca.cwds.data.legacy.cms.dao;

import java.util.List;

import javax.persistence.NoResultException;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.collect.ImmutableList;
import com.google.inject.Inject;

import gov.ca.cwds.data.BaseDaoImpl;
import gov.ca.cwds.data.legacy.cms.entity.syscodes.Country;
import gov.ca.cwds.inject.CmsSessionFactory;

/** @author CWDS CASE API Team */
public class CountryDao extends BaseDaoImpl<Country> {
  private static final Logger LOG = LoggerFactory.getLogger(CountryDao.class);

  @Inject
  public CountryDao(@CmsSessionFactory SessionFactory sessionFactory) {
    super(sessionFactory);
  }

  @Override
  public List<Country> findAll() {
    Session session = this.grabSession();
    Query<Country> query = session.createNamedQuery(Country.NQ_ALL, Country.class);
    ImmutableList.Builder<Country> entities = new ImmutableList.Builder<>();
    entities.addAll(query.list());
    return entities.build();
  }

  public Country findByLogicalId(String logicalId) {
    Session session = this.grabSession();
    Class<Country> entityClass = getEntityClass();
    Query<Country> query =
        session.createNamedQuery(entityClass.getSimpleName() + ".findByLogicalId", Country.class);
    query.setParameter("logicalId", logicalId);
    Country country = null;
    try {
      country = query.getSingleResult();
    } catch (NoResultException e) {
      LOG.warn("There is no result for logicalId = {}", logicalId);
      LOG.debug("error has occured for logicalId = {}", logicalId, e);
    }

    return country;
  }
}
