package gov.ca.cwds.cms.data.access.service.rules;

import gov.ca.cwds.data.legacy.cms.entity.TribalMembershipVerification;
import gov.ca.cwds.drools.DroolsConfiguration;

/**
 * Drools configuration for Tribal membership verification.
 *
 * @author CWDS TPT-3 Team
 * */
public class TribalMembershipVerificationConfiguration
    extends DroolsConfiguration<TribalMembershipVerification> {

  public static final TribalMembershipVerificationConfiguration INSTANCE =
      new TribalMembershipVerificationConfiguration(
          "tribal-membership-verification-session",
          "tribal-membership-verification-agenda",
        "tribal-membership-verification-legacy-rules");

  public static final TribalMembershipVerificationConfiguration DATA_PROCESSING_INSTANCE =
    new TribalMembershipVerificationConfiguration(
      "tribal-membership-verification-session",
      "tribal-membership-verification-data-processing-agenda",
      "tribal-membership-verification-legacy-rules");


  private TribalMembershipVerificationConfiguration(
      String rulesSession, String rulesAgenda, String kieContainerId) {
    super(rulesSession, rulesAgenda, kieContainerId);
  }
}
