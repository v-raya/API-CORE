package gov.ca.cwds.cms.data.access.service.rules;

import gov.ca.cwds.data.legacy.cms.entity.TribalMembershipVerification;
import gov.ca.cwds.drools.DroolsConfiguration;

/** @author CWDS TPT-3 Team */
public class TribalMembershipVerificationConfiguration
    extends DroolsConfiguration<TribalMembershipVerification> {

  public static final TribalMembershipVerificationConfiguration INSTANCE =
      new TribalMembershipVerificationConfiguration(
          "tribal-membership-verification-session",
          "tribal-membership-verification-agenda",
          "tribal-membership-verification-rules");

  private TribalMembershipVerificationConfiguration(
      String rulesSession, String rulesAgenda, String kieContainerId) {
    super(rulesSession, rulesAgenda, kieContainerId);
  }
}
