package gov.ca.cwds.data.legacy.cms.dao;

import com.google.common.collect.ImmutableList;
import com.google.inject.Inject;
import gov.ca.cwds.data.BaseDaoImpl;
import gov.ca.cwds.data.legacy.cms.entity.Referral;
import gov.ca.cwds.inject.CmsSessionFactory;
import gov.ca.cwds.util.Require;
import java.time.LocalDate;
import java.util.List;
import org.hibernate.SessionFactory;

/** @author CWDS TPT-3 Team */
public class ReferralDao extends BaseDaoImpl {

  @Inject
  public ReferralDao(@CmsSessionFactory SessionFactory sessionFactory) {
    super(sessionFactory);
  }

  public List<Referral> getOpenReferralsByStaffId(String staffId, LocalDate activeDate) {
    Require.requireNotNullAndNotEmpty(staffId);

    List<Referral> referrals =
        currentSession()
            .createNamedQuery(Referral.FIND_ACTIVE_BY_STAFF_ID, Referral.class)
            .setParameter(Referral.PARAM_STAFF_ID, staffId)
            .setParameter(
                Referral.PARAM_ACTIVE_DATE, activeDate != null ? activeDate : LocalDate.now())
            .list();
    return ImmutableList.<Referral>builder().addAll(referrals).build();
  }

  public List<Referral> getActiveReferralsByClientId(String clientId) {
    return getReferralsByClientId(clientId, true);
  }

  public List<Referral> getClosedReferralsByClientId(String clientId) {
    return getReferralsByClientId(clientId, false);
  }

  public List<Referral> getReferralsByClientId(String clientId) {
    return getReferralsByClientId(clientId, null);
  }

  private List<Referral> getReferralsByClientId(String clientId, Boolean isActive) {
    String namedQuery =
        isActive == null
            ? Referral.FIND_BY_CLIENT
            : true ? Referral.FIND_ACTIVE_BY_CLIENT : Referral.FIND_CLOSED_BY_CLIENT;
    List<Referral> referrals =
        currentSession()
            .createNamedQuery(namedQuery, Referral.class)
            .setParameter(Referral.PARAM_CLIENT_ID, clientId)
            .list();
    return ImmutableList.<Referral>builder().addAll(referrals).build();
  }
}
