package gov.ca.cwds.data.legacy.cms.dao;

import com.google.common.collect.ImmutableList;
import com.google.inject.Inject;
import gov.ca.cwds.data.BaseDaoImpl;
import gov.ca.cwds.data.legacy.cms.entity.BaseAssignment;
import gov.ca.cwds.data.legacy.cms.entity.ReferralAssignment;
import gov.ca.cwds.inject.CmsSessionFactory;
import java.util.ArrayList;
import java.util.List;
import org.hibernate.SessionFactory;

/** @author CWDS TPT-3 Team */
public class AssignmentDao extends BaseDaoImpl {

  @Inject
  public AssignmentDao(@CmsSessionFactory SessionFactory sessionFactory) {
    super(sessionFactory);
  }

  public List<BaseAssignment> getAssignmentsByStaffIds(List<String> referralIds) {
    if (referralIds == null || referralIds.isEmpty()) {
      return new ArrayList<>();
    }

    List<BaseAssignment> referrals =
        currentSession()
            .createNamedQuery(
                ReferralAssignment.FIND_ASSIGNMENTS_BY_REFERRALS, BaseAssignment.class)
            .setParameter(ReferralAssignment.PARAM_REFERRAL_IDS, referralIds)
            .list();
    return ImmutableList.<BaseAssignment>builder().addAll(referrals).build();
  }
}
