package gov.ca.cwds.authorizer.drools.configuration;

import gov.ca.cwds.data.legacy.cms.entity.PlacementHome;
import gov.ca.cwds.drools.DroolsConfiguration;

/**
 * @author CWDS TPT-2 Team
 */
public final class SubstituteCareProviderAuthorizationDroolsConfiguration extends DroolsConfiguration<PlacementHome> {

  public static final SubstituteCareProviderAuthorizationDroolsConfiguration INSTANCE = new SubstituteCareProviderAuthorizationDroolsConfiguration(
      "substitute-care-provider-authorization-session",
      "substitute-care-provider-authorization-agenda",
      "substitute-care-provider"
  );

  private SubstituteCareProviderAuthorizationDroolsConfiguration(String sessionName, String agendaGroup, String pathToRulesConfig) {
    super(sessionName, agendaGroup, pathToRulesConfig);
  }
}
