package gov.ca.cwds.authorizer.drools.configuration;

import gov.ca.cwds.data.legacy.cms.entity.PlacementHome;
import gov.ca.cwds.drools.DroolsConfiguration;

/**
 * @author CWDS TPT-2 Team
 */
public final class PlacementHomeAuthorizationDroolsConfiguration extends DroolsConfiguration<PlacementHome> {

  public static final PlacementHomeAuthorizationDroolsConfiguration INSTANCE = new PlacementHomeAuthorizationDroolsConfiguration(
      "placement-home-authorization-session",
      "placement-home-authorization-agenda",
      "placement-home-authorization-rules"
  );

  private PlacementHomeAuthorizationDroolsConfiguration(String sessionName, String agendaGroup, String pathToRulesConfig) {
    super(sessionName, agendaGroup, pathToRulesConfig);
  }
}
