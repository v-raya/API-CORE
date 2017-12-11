package gov.ca.cwds.data.legacy.cms.entity;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

/** @author CWDS TPT-3 Team */
@Entity
@DiscriminatorValue("R")
@NamedQuery(
  name = ReferralAssignment.FIND_ASSIGNMENTS_BY_REFERRALS,
  query =
      "SELECT assignment FROM ReferralAssignment assignment where "
          + "assignment.referral.id in (:" + ReferralAssignment.PARAM_REFERRAL_IDS + ")"
)
public class ReferralAssignment extends BaseAssignment {

  private static final long serialVersionUID = 5323366175816010628L;

  public static final String FIND_ASSIGNMENTS_BY_REFERRALS =
      "ReferralAssignment.findAssignmentsByReferrals";
  public static final String PARAM_REFERRAL_IDS = "referralIds";

  @NotFound(action = NotFoundAction.IGNORE)
  @ManyToOne(fetch = FetchType.LAZY)
  private Referral referral;

  public static long getSerialVersionUID() {
    return serialVersionUID;
  }

  public Referral getReferral() {
    return referral;
  }

  public void setReferral(Referral referral) {
    this.referral = referral;
  }
}
