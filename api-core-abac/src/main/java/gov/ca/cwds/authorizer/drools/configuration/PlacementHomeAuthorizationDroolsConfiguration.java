package gov.ca.cwds.authorizer.drools.configuration;

/**
 * Drools configuration for create Placement Home authorization rules.
 *
 * @author CWDS TPT-2 Team
 */
public final class PlacementHomeAuthorizationDroolsConfiguration extends
    BasePlacementHomeAuthDroolsConfig {

  private static final String AGENDA_GROUP = "placement-home-authorization-agenda";

  public static final PlacementHomeAuthorizationDroolsConfiguration INSTANCE
      = new PlacementHomeAuthorizationDroolsConfiguration(AGENDA_GROUP);

  protected PlacementHomeAuthorizationDroolsConfiguration(String agendaGroup) {
    super(agendaGroup);
  }

}
