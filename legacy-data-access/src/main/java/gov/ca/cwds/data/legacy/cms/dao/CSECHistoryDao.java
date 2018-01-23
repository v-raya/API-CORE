package gov.ca.cwds.data.legacy.cms.dao;

import com.google.common.collect.ImmutableList;
import com.google.inject.Inject;
import gov.ca.cwds.data.BaseDaoImpl;
import gov.ca.cwds.data.legacy.cms.entity.CSECHistory;
import gov.ca.cwds.inject.CmsSessionFactory;
import java.util.List;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;

/** @author CWDS TPT-3 Team */
public class CSECHistoryDao extends BaseDaoImpl<CSECHistory> {

  @Inject
  public CSECHistoryDao(@CmsSessionFactory SessionFactory sessionFactory) {
    super(sessionFactory);
  }

  public List<CSECHistory> findByClientId(String clientId) {
    Session session = this.getSessionFactory().getCurrentSession();
    Query<CSECHistory> query =
        session.createNamedQuery(CSECHistory.FIND_BY_CLIENT_ID, CSECHistory.class);
    query.setParameter(CSECHistory.PARAM_CLIENT_ID, clientId);
    ImmutableList.Builder<CSECHistory> entities = new ImmutableList.Builder<>();
    entities.addAll(query.list());
    return entities.build();
  }
}
