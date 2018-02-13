package gov.ca.cwds.data.legacy.cms.dao;

import com.google.common.collect.ImmutableList;
import com.google.inject.Inject;
import gov.ca.cwds.data.BaseDaoImpl;
import gov.ca.cwds.data.legacy.cms.entity.CreditReportHistory;
import gov.ca.cwds.inject.CmsSessionFactory;
import java.util.List;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;

/** @author CWDS TPT-3 Team */
public class CreditReportHistoryDao extends BaseDaoImpl<CreditReportHistory> {
  @Inject
  public CreditReportHistoryDao(@CmsSessionFactory SessionFactory sessionFactory) {
    super(sessionFactory);
  }

  public List<CreditReportHistory> findByClientId(String clientId) {
    Session session = this.getSessionFactory().getCurrentSession();
    Query<CreditReportHistory> query =
        session.createNamedQuery(CreditReportHistory.FIND_BY_CLIENT_ID, CreditReportHistory.class);
    query.setParameter(CreditReportHistory.PARAM_CLIENT_ID, clientId);
    ImmutableList.Builder<CreditReportHistory> entities = new ImmutableList.Builder<>();
    entities.addAll(query.list());
    return entities.build();
  }
}
