package gov.ca.cwds.data.legacy.cms.dao;

import static gov.ca.cwds.data.legacy.cms.entity.HealthReferral.FIND_HEALTH_REFERRAL_BY_CHILD_CLIENT_ID;
import static gov.ca.cwds.data.legacy.cms.entity.HealthReferral.PARAM_CHILD_CLIENT_ID;

import com.google.common.collect.ImmutableList;
import com.google.inject.Inject;
import gov.ca.cwds.data.BaseDaoImpl;
import gov.ca.cwds.data.legacy.cms.entity.Address;
import gov.ca.cwds.data.legacy.cms.entity.HealthReferral;
import gov.ca.cwds.inject.CmsSessionFactory;
import gov.ca.cwds.util.Require;
import java.util.List;
import org.hibernate.SessionFactory;

/**
 * DAO for {@link Address}
 *
 * @author CWDS TPT-3 Team
 */
public class HealthReferralDao extends BaseDaoImpl<HealthReferral> {


  @Inject
  public HealthReferralDao(@CmsSessionFactory final SessionFactory sessionFactory) {
    super(sessionFactory);
  }

  public List<HealthReferral> findByChildClientId(String childClientId) {

    Require.requireNotNullAndNotEmpty(childClientId);

    final List<HealthReferral> details = currentSession()
        .createNamedQuery(FIND_HEALTH_REFERRAL_BY_CHILD_CLIENT_ID, HealthReferral.class)
        .setParameter(PARAM_CHILD_CLIENT_ID, childClientId)
        .list();

    return ImmutableList.copyOf(details);
  }

}
