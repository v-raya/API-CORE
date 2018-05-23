package gov.ca.cwds.data.legacy.cms.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BiFunction;

import org.hibernate.SessionFactory;

import com.google.common.collect.ImmutableList;
import com.google.inject.Inject;

import gov.ca.cwds.data.BaseDaoImpl;
import gov.ca.cwds.data.legacy.cms.entity.TribalMembershipVerification;
import gov.ca.cwds.inject.CmsSessionFactory;

/**
 * Data access object (DAO) for tribal membership verification.
 *
 * @author CWDS TPT-3 Team
 */
public class TribalMembershipVerificationDao extends BaseDaoImpl<TribalMembershipVerification> {

  @Inject
  public TribalMembershipVerificationDao(@CmsSessionFactory SessionFactory sessionFactory) {
    super(sessionFactory);
  }

  /**
   * find TribalMembershipVerification with fkFromTribalMembershipVerification == null.
   *
   * @param clientId Client id.
   * @return List of TribalMembershipVerification.
   */
  public List<TribalMembershipVerification> findByClientIdNoTribalEligFrom(String clientId) {
    return getTribals.apply(
        TribalMembershipVerification
            .FIND_TRIBAL_MEMBERSHIP_VERIFICATION_BY_CLIENT_ID_NO_TRIBAL_ELIG_FROM,
        new String[] {clientId});
  }

  /**
   * find TribalMembershipVerification by ClientID.
   *
   * @param clientId Client id.
   * @return List of TribalMembershipVerification.
   */
  public List<TribalMembershipVerification> findByClientId(String clientId) {
    return getTribals.apply(
        TribalMembershipVerification.FIND_TRIBAL_MEMBERSHIP_VERIFICATION_BY_CLIENT_IDS,
        new String[] {clientId});
  }

  /**
   * find TribalMembershipVerification by ClientIDs.
   *
   * @param clientId Client id.
   * @return List of TribalMembershipVerification.
   */
  public List<TribalMembershipVerification> findByClientIds(String... clientId) {
    return getTribals.apply(
        TribalMembershipVerification.FIND_TRIBAL_MEMBERSHIP_VERIFICATION_BY_CLIENT_IDS, clientId);
  }

  /**
   * This method is looking for a list of TribalMembershipVerification by client id.
   * TribalMembershipVerification where Tribal Membership Verification is equal thirdID from parent
   * record.
   *
   * <blockquote>
   *
   * <pre>
   * (Rule 08861 where in focus .Id = .FKTR_MBVRT and FK_CLIENT = (child) CLIENT.Id and .Indian_Enrollment_Status_Type = null)
   * </pre>
   *
   * </blockquote>
   *
   * @param clientId Client id.
   * @param parentId parent id.
   * @return List of TribalMembershipVerification.
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

  private BiFunction<String, String[], List<TribalMembershipVerification>> getTribals =
      (queryName, clientId) -> {
        if (clientId == null || clientId.length == 0) {
          return new ArrayList<>();
        }
        final List<TribalMembershipVerification> membershipVerifications =
            currentSession()
                .createNamedQuery(queryName, TribalMembershipVerification.class)
                .setParameterList(TribalMembershipVerification.PARAM_CLIENT_ID, clientId)
                .list();
        return ImmutableList.<TribalMembershipVerification>builder()
            .addAll(membershipVerifications)
            .build();
      };
}
