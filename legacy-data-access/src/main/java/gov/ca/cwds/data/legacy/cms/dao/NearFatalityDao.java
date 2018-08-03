package gov.ca.cwds.data.legacy.cms.dao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;

import com.google.common.collect.ImmutableList;
import com.google.inject.Inject;

import gov.ca.cwds.data.BaseDaoImpl;
import gov.ca.cwds.data.legacy.cms.entity.NearFatality;
import gov.ca.cwds.inject.CmsSessionFactory;

/** @author CWDS TPT-3 Team */
public class NearFatalityDao extends BaseDaoImpl<NearFatality> {

  @Inject
  public NearFatalityDao(@CmsSessionFactory SessionFactory sessionFactory) {
    super(sessionFactory);
  }

  public List<NearFatality> findNearFatalitiesByClientId(String clientId) {

    Session session = this.grabSession();
    final Query<NearFatality> query =
        session.createNamedQuery(NearFatality.NQ_BY_CLIENT_ID, NearFatality.class)
            .setParameter("clientId", clientId);
    ImmutableList.Builder<NearFatality> entities = new ImmutableList.Builder<>();
    entities.addAll(query.list());
    return entities.build();
  }
}
