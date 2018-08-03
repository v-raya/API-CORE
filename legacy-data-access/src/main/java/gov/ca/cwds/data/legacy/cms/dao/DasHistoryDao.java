package gov.ca.cwds.data.legacy.cms.dao;

import static gov.ca.cwds.data.legacy.cms.entity.DasHistory.NQ_FIND_BY_CLIENT_ID;
import static gov.ca.cwds.data.legacy.cms.entity.DasHistory.NQ_PARAM_CLIENT_ID;

import java.util.Collection;
import java.util.List;

import javax.inject.Inject;

import org.hibernate.SessionFactory;

import com.google.common.collect.ImmutableList;

import gov.ca.cwds.data.BaseDaoImpl;
import gov.ca.cwds.data.legacy.cms.entity.DasHistory;
import gov.ca.cwds.inject.CmsSessionFactory;
import gov.ca.cwds.util.Require;

/**
 * @author CWDS TPT-3 Team
 */
public class DasHistoryDao extends BaseDaoImpl<DasHistory> {

  @Inject
  public DasHistoryDao(@CmsSessionFactory SessionFactory sessionFactory) {
    super(sessionFactory);
  }

  public Collection<DasHistory> findByClientId(final String clientId) {
    Require.requireNotNullAndNotEmpty(clientId);
    final List<DasHistory> histories =
        grabSession().createNamedQuery(NQ_FIND_BY_CLIENT_ID, DasHistory.class)
            .setParameter(NQ_PARAM_CLIENT_ID, clientId).list();
    return ImmutableList.copyOf(histories);
  }
}
