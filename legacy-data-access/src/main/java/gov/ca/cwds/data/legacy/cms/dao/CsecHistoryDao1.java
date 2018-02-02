package gov.ca.cwds.data.legacy.cms.dao;

import com.google.common.collect.ImmutableList;
import com.google.inject.Inject;
import gov.ca.cwds.data.BaseDaoImpl;
import gov.ca.cwds.data.legacy.cms.entity.CsecHistory1;
import gov.ca.cwds.inject.CmsSessionFactory;
import java.util.List;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;

/** @author CWDS TPT-3 Team */
public class CsecHistoryDao1 extends BaseDaoImpl<CsecHistory1> {

  @Inject
  public CsecHistoryDao1(@CmsSessionFactory SessionFactory sessionFactory) {
    super(sessionFactory);
  }

  public List<CsecHistory1> findByClientId(String clientId) {
    Session session = this.getSessionFactory().getCurrentSession();
    Query<CsecHistory1> query =
        session.createNamedQuery(CsecHistory1.FIND_BY_CLIENT_ID, CsecHistory1.class);
    query.setParameter(CsecHistory1.PARAM_CLIENT_ID, clientId);
    ImmutableList.Builder<CsecHistory1> entities = new ImmutableList.Builder<>();
    entities.addAll(query.list());
    return entities.build();
  }
}
