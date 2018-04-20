package gov.ca.cwds.data.legacy.cms.dao;

import gov.ca.cwds.data.legacy.cms.entity.CaseLoad;
import gov.ca.cwds.data.legacy.cms.entity.CaseLoadWeighting;
import gov.ca.cwds.data.legacy.cms.entity.ReferralAssignment;
import gov.ca.cwds.data.legacy.cms.entity.StaffPerson;
import gov.ca.cwds.data.legacy.cms.entity.facade.ReferralByStaff;
import java.time.LocalDate;
import java.util.List;

import org.hibernate.SessionFactory;

import com.google.common.collect.ImmutableList;
import com.google.inject.Inject;

import gov.ca.cwds.data.BaseDaoImpl;
import gov.ca.cwds.data.legacy.cms.entity.Referral;
import gov.ca.cwds.inject.CmsSessionFactory;
import gov.ca.cwds.util.Require;

/** @author CWDS TPT-3 Team */
public class ReferralDao extends BaseDaoImpl<Referral> {

  @Inject
  public ReferralDao(@CmsSessionFactory SessionFactory sessionFactory) {
    super(sessionFactory);
  }

  /**
   * Returns referrals which have relation to {@link StaffPerson} via {@link ReferralAssignment},
   * {@link CaseLoad} and {@link CaseLoadWeighting} entities. Also the {@link ReferralAssignment} is
   * active, which means param activeDate is between startDate and endDate of the assignment.
   *
   * @param staffId - Identifier of Staff Person who can work on the returned referrals.
   * @param activeDate - The returned referrals' assignments will be active on this date. As usual
   *     this param is a current date.
   * @return Referrals with active assignments that can be managed by requested staff person. N.B.
   *     The returned objects are not of @Entity type.
   */
  public List<ReferralByStaff> nativeFindOpenReferralsByStaffId(
      String staffId, LocalDate activeDate) {
    Require.requireNotNullAndNotEmpty(staffId);

    final LocalDate date = activeDate != null ? activeDate : LocalDate.now();
    final List<ReferralByStaff> referrals =
        currentSession()
            .getNamedNativeQuery(ReferralByStaff.NATIVE_FIND_REFERRALS_BY_STAFF_ID)
            .setResultSetMapping(ReferralByStaff.MAPPING_CASE_BY_STAFF)
            .setParameter(1, staffId)
            .setParameter(2, date)
            .setParameter(3, date)
            .getResultList();

    return ImmutableList.copyOf(referrals);
  }

  public List<Referral> getActiveReferralsByClientId(String clientId) {
    return getReferralsByClientId(clientId, Boolean.TRUE);
  }

  public List<Referral> getClosedReferralsByClientId(String clientId) {
    return getReferralsByClientId(clientId, Boolean.FALSE);
  }

  public List<Referral> getReferralsByClientId(String clientId) {
    return getReferralsByClientId(clientId, null);
  }

  private List<Referral> getReferralsByClientId(String clientId, Boolean isActive) {
    final String namedQuery =
        isActive == null ? Referral.FIND_BY_CLIENT : Referral.FIND_ACTIVE_BY_CLIENT;
    final List<Referral> referrals =
        currentSession()
            .createNamedQuery(namedQuery, Referral.class)
            .setParameter(Referral.PARAM_CLIENT_ID, clientId)
            .list();
    return ImmutableList.<Referral>builder().addAll(referrals).build();
  }
}
