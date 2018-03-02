package gov.ca.cwds.data.legacy.cms.dao;

import static gov.ca.cwds.data.legacy.cms.entity.SchoolOriginHistory.NQ_FIND_BY_CLIENT_ID;
import static gov.ca.cwds.data.legacy.cms.entity.SchoolOriginHistory.NQ_PARAM_CLIENT_ID;

import com.google.common.collect.ImmutableList;
import gov.ca.cwds.data.BaseDaoImpl;
import gov.ca.cwds.data.legacy.cms.entity.SchoolOriginHistory;
import gov.ca.cwds.inject.CmsSessionFactory;
import gov.ca.cwds.util.Require;
import java.util.Collection;
import java.util.List;
import javax.inject.Inject;
import org.hibernate.SessionFactory;

/**
 * @author CWDS TPT-3 Team
 */
public class SchoolOriginHistoryDao extends BaseDaoImpl<SchoolOriginHistory> {

  @Inject
  public SchoolOriginHistoryDao(@CmsSessionFactory SessionFactory sessionFactory) {
    super(sessionFactory);
  }

  public Collection<SchoolOriginHistory> findByClientId(final String clientId) {
    Require.requireNotNullAndNotEmpty(clientId);
    final List<SchoolOriginHistory> histories = getSessionFactory().getCurrentSession()
        .createNamedQuery(NQ_FIND_BY_CLIENT_ID, SchoolOriginHistory.class)
        .setParameter(NQ_PARAM_CLIENT_ID, clientId)
        .list();
    return ImmutableList.copyOf(histories);
  }
}
