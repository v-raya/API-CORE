package gov.ca.cwds.data.legacy.cms.dao;

import com.google.common.collect.ImmutableList;
import com.google.inject.Inject;
import gov.ca.cwds.data.BaseDaoImpl;
import gov.ca.cwds.data.legacy.cms.entity.TribalMembershipVerification;
import gov.ca.cwds.inject.CmsSessionFactory;
import java.util.List;
import org.hibernate.SessionFactory;

/** @author CWDS TPT-3 Team */
public class TribalMembershipVerificationDao extends BaseDaoImpl<TribalMembershipVerification> {

  @Inject
  public TribalMembershipVerificationDao(@CmsSessionFactory SessionFactory sessionFactory) {
    super(sessionFactory);
  }

  public List<TribalMembershipVerification> findByClientIdNoTribalReferenced(String clientId) {
    final List<TribalMembershipVerification> membershipVerifications =
        currentSession()
            .createNamedQuery(
                TribalMembershipVerification
                    .FIND_TRIBAL_MEMBERSHIP_VERIFICATION_BY_CLIENT_ID_NO_TRIBAL_ELIG_FROM,
                TribalMembershipVerification.class)
            .setParameter(TribalMembershipVerification.PARAM_CLIENT_ID, clientId)
            .list();
    return ImmutableList.<TribalMembershipVerification>builder()
        .addAll(membershipVerifications)
        .build();
  }
}
