package gov.ca.cwds.data.legacy.cms.dao;

import com.google.common.collect.ImmutableList;
import com.google.inject.Inject;
import gov.ca.cwds.data.BaseDaoImpl;
import gov.ca.cwds.data.legacy.cms.entity.SafetyAlert;
import gov.ca.cwds.inject.CmsSessionFactory;
import gov.ca.cwds.util.Require;
import java.util.Collection;
import java.util.List;
import org.hibernate.SessionFactory;

/**
 * @author CWDS TPT-3 Team
 */
public class SafetyAlertDao extends BaseDaoImpl {

  @Inject
  public SafetyAlertDao(@CmsSessionFactory SessionFactory sessionFactory) {
    super(sessionFactory);
  }

  public Collection<SafetyAlert> findByClientId(final String clientId) {
    Require.requireNotNullAndNotEmpty(clientId);

    final List<SafetyAlert> alerts = currentSession()
        .createNamedQuery(SafetyAlert.NQ_FIND_BY_CLIENT_ID, SafetyAlert.class)
        .setParameter(SafetyAlert.NQ_PARAM_CLIENT_ID, clientId)
        .list();

    return ImmutableList.<SafetyAlert>builder().addAll(alerts).build();
  }

}
