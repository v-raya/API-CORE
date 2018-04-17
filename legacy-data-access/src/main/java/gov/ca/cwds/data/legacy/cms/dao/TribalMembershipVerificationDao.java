package gov.ca.cwds.data.legacy.cms.dao;

import com.google.common.collect.ImmutableList;
import com.google.inject.Inject;
import gov.ca.cwds.data.BaseDaoImpl;
import gov.ca.cwds.data.legacy.cms.entity.TribalMembershipVerification;
import gov.ca.cwds.inject.CmsSessionFactory;
import java.util.List;
import java.util.function.BiFunction;
import org.hibernate.SessionFactory;

/** @author CWDS TPT-3 Team */
public class TribalMembershipVerificationDao extends BaseDaoImpl<TribalMembershipVerification> {

  @Inject
  public TribalMembershipVerificationDao(@CmsSessionFactory SessionFactory sessionFactory) {
    super(sessionFactory);
  }

  /**
   * find TribalMembershipVerification with fkFromTribalMembershipVerification == null.
   *
   * @param clientId
   * @return
   */
  public List<TribalMembershipVerification> findByClientIdNoTribalEligFrom(String clientId) {
    return getTribals.apply(
        TribalMembershipVerification
            .FIND_TRIBAL_MEMBERSHIP_VERIFICATION_BY_CLIENT_ID_NO_TRIBAL_ELIG_FROM,
        clientId);
  }

  /**
   * find TribalMembershipVerification by ClientID.
   *
   * @param clientId
   * @return
   */
  public List<TribalMembershipVerification> findByClientId(String clientId) {
    return getTribals.apply(
        TribalMembershipVerification.FIND_TRIBAL_MEMBERSHIP_VERIFICATION_BY_CLIENT_ID, clientId);
  }

  /**
   * This method is looking for a list of TribalMembershipVerification by client id.
   * TribalMembershipVerification where Tribal Membership Verification is equal thirdID from parent
   * record. (Rule 08861 where in focus .Id = .FKTR_MBVRT and FK_CLIENT = (child) CLIENT.Id and
   * .Indian_Enrollment_Status_Type = null)
   *
   * @param clientId
   * @return
   */
  public List<TribalMembershipVerification> findTribalsThatHaveSubTribalsByClientId(
      String clientId, String parentId) {
    final List<TribalMembershipVerification> membershipVerifications =
        currentSession()
            .createNamedQuery(
                TribalMembershipVerification.FIND_TRIBAL_THAT_HAVE_SUB_TRIBALS_BY_CLIENT,
                TribalMembershipVerification.class)
            .setParameter(TribalMembershipVerification.PARAM_CLIENT_ID, clientId)
            .setParameter(TribalMembershipVerification.PARAM_PARENT_CLIENT_ID, parentId)
            .list();
    return ImmutableList.<TribalMembershipVerification>builder()
        .addAll(membershipVerifications)
        .build();
  }

  private BiFunction<String, String, List<TribalMembershipVerification>> getTribals =
      (queryName, clientId) -> {
        final List<TribalMembershipVerification> membershipVerifications =
            currentSession()
                .createNamedQuery(queryName, TribalMembershipVerification.class)
                .setParameter(TribalMembershipVerification.PARAM_CLIENT_ID, clientId)
                .list();
        return ImmutableList.<TribalMembershipVerification>builder()
            .addAll(membershipVerifications)
            .build();
      };
}
