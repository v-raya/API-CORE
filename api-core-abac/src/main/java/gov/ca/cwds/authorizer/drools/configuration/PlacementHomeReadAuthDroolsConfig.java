package gov.ca.cwds.authorizer.drools.configuration;

/**
 * Drools configuration for the Read Authorization for a Placement Home.
 *
 * @author CWDS TPT-2 Team
 */
public class PlacementHomeReadAuthDroolsConfig extends BasePlacementHomeAuthDroolsConfig {

  private static final String AGENDA_GROUP = "placement-home-read-authorization-agenda";

  public static final PlacementHomeReadAuthDroolsConfig INSTANCE
      = new PlacementHomeReadAuthDroolsConfig(AGENDA_GROUP);

  private PlacementHomeReadAuthDroolsConfig(String agendaGroup) {
    super(agendaGroup);
  }
}
