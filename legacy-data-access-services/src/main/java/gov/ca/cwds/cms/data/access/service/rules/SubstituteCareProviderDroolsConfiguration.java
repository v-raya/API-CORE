package gov.ca.cwds.cms.data.access.service.rules;

import gov.ca.cwds.drools.DroolsConfiguration;

/**
 * @author CWDS CALS API Team
 */

public final class SubstituteCareProviderDroolsConfiguration extends DroolsConfiguration<Object> {


  public static final SubstituteCareProviderDroolsConfiguration INSTANCE = new SubstituteCareProviderDroolsConfiguration(
      "substitute-care-provider-session",
      "substitute-care-provider-agenda",
      "substitute-care-provider-legacy-rules"
  );

  public static final SubstituteCareProviderDroolsConfiguration DATA_PROCESSING_INSTANCE = new SubstituteCareProviderDroolsConfiguration(
          "substitute-care-provider-session",
          "substitute-care-provider-data-processing-agenda",
          "substitute-care-provider-legacy-rules"
  );

  public SubstituteCareProviderDroolsConfiguration(String rulesSession, String rulesAgenda,
      String kieContainerId) {
    super(rulesSession, rulesAgenda, kieContainerId);
  }
}
