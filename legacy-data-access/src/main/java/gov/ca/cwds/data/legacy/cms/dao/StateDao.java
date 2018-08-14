package gov.ca.cwds.data.legacy.cms.dao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;

import com.google.common.collect.ImmutableList;
import com.google.inject.Inject;

import gov.ca.cwds.data.BaseDaoImpl;
import gov.ca.cwds.data.legacy.cms.entity.syscodes.State;
import gov.ca.cwds.inject.CmsSessionFactory;

/**
 * @author CWDS CALS API Team
 */
public class StateDao extends BaseDaoImpl<State> {

  @Inject
  public StateDao(@CmsSessionFactory SessionFactory sessionFactory) {
    super(sessionFactory);
  }

  @Override
  public List<State> findAll() {
    Session session = this.grabSession();
    Query<State> query = session.createNamedQuery(State.NQ_ALL, State.class);
    ImmutableList.Builder<State> entities = new ImmutableList.Builder<>();
    entities.addAll(query.list());
    return entities.build();
  }

}
