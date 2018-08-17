package gov.ca.cwds.data.legacy.cms.dao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;

import com.google.common.collect.ImmutableList;
import com.google.inject.Inject;

import gov.ca.cwds.data.BaseDaoImpl;
import gov.ca.cwds.data.legacy.cms.entity.syscodes.DeathCircumstancesType;
import gov.ca.cwds.inject.CmsSessionFactory;

/**
 * @author CWDS CASE API Team
 */
public class DeathCircumstancesTypeDao extends BaseDaoImpl<DeathCircumstancesType> {

  @Inject
  public DeathCircumstancesTypeDao(@CmsSessionFactory SessionFactory sessionFactory) {
    super(sessionFactory);
  }

  @Override
  public List<DeathCircumstancesType> findAll() {
    Session session = this.grabSession();
    Query<DeathCircumstancesType> query =
        session.createNamedQuery(DeathCircumstancesType.NQ_ALL, DeathCircumstancesType.class);
    ImmutableList.Builder<DeathCircumstancesType> entities = new ImmutableList.Builder<>();
    entities.addAll(query.list());
    return entities.build();
  }
}
