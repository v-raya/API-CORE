package gov.ca.cwds.data.legacy.cms.dao;

import com.google.common.collect.ImmutableList;
import com.google.inject.Inject;
import gov.ca.cwds.data.BaseDaoImpl;
import gov.ca.cwds.data.legacy.cms.entity.CsecHistory;
import gov.ca.cwds.inject.CmsSessionFactory;
import java.util.List;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;

/** @author CWDS TPT-3 Team */
public class CsecHistoryDao extends BaseDaoImpl<CsecHistory> {

  @Inject
  public CsecHistoryDao(@CmsSessionFactory SessionFactory sessionFactory) {
    super(sessionFactory);
  }

  public List<CsecHistory> findByClientId(String clientId) {
    Session session = this.getSessionFactory().getCurrentSession();
    Query<CsecHistory> query =
        session.createNamedQuery(CsecHistory.FIND_BY_CLIENT_ID, CsecHistory.class);
    query.setParameter(CsecHistory.PARAM_CLIENT_ID, clientId);
    ImmutableList.Builder<CsecHistory> entities = new ImmutableList.Builder<>();
    entities.addAll(query.list());
    return entities.build();
  }
}
