package gov.ca.cwds.data.legacy.cms.dao;

import static gov.ca.cwds.data.legacy.cms.entity.DualAgencyServicesHistory.NQ_FIND_BY_CLIENT_ID;
import static gov.ca.cwds.data.legacy.cms.entity.DualAgencyServicesHistory.NQ_PARAM_CLIENT_ID;

import com.google.common.collect.ImmutableList;
import gov.ca.cwds.data.BaseDaoImpl;
import gov.ca.cwds.data.legacy.cms.entity.DualAgencyServicesHistory;
import gov.ca.cwds.inject.CmsSessionFactory;
import gov.ca.cwds.util.Require;
import java.util.Collection;
import java.util.List;
import javax.inject.Inject;
import org.hibernate.SessionFactory;

/**
 * @author CWDS TPT-3 Team
 */
public class DualAgencyServicesHistoryDao extends BaseDaoImpl<DualAgencyServicesHistory> {

  @Inject
  public DualAgencyServicesHistoryDao(@CmsSessionFactory SessionFactory sessionFactory) {
    super(sessionFactory);
  }

  public Collection<DualAgencyServicesHistory> findByClientId(final String clientId) {
    Require.requireNotNullAndNotEmpty(clientId);
    final List<DualAgencyServicesHistory> histories = getSessionFactory().getCurrentSession()
        .createNamedQuery(NQ_FIND_BY_CLIENT_ID, DualAgencyServicesHistory.class)
        .setParameter(NQ_PARAM_CLIENT_ID, clientId)
        .list();
    return ImmutableList.copyOf(histories);
  }
}
