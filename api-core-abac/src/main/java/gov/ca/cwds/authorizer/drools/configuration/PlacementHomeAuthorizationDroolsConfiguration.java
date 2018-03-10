package gov.ca.cwds.authorizer.drools.configuration;

import gov.ca.cwds.data.legacy.cms.entity.PlacementHome;
import gov.ca.cwds.drools.DroolsConfiguration;

/**
 * @author CWDS TPT-2 Team
 */
public final class PlacementHomeAuthorizationDroolsConfiguration extends DroolsConfiguration<PlacementHome> implements DroolsAuthorizer {

  private static final String SESSION_NAME = "placement-home-authorization-session";
  private static final String AGENDA_GROUP = "placement-home-authorization-agenda";
  private static final String PATH_TO_RULES_CONFIG = "placement-home-authorization-rules";
  private static final DroolsConfiguration INSTANCE = new PlacementHomeAuthorizationDroolsConfiguration();

  public PlacementHomeAuthorizationDroolsConfiguration() {
    super(SESSION_NAME, AGENDA_GROUP, PATH_TO_RULES_CONFIG);
  }

  @Override
  public DroolsConfiguration getInstance() {
    return INSTANCE;
  }
}
