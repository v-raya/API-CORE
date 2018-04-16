package gov.ca.cwds.authorizer.drools.configuration;

import gov.ca.cwds.data.legacy.cms.entity.PlacementHome;
import gov.ca.cwds.drools.DroolsConfiguration;

/**
 * @author CWDS TPT-2 Team
 */
public abstract class BasePlacementHomeAuthDroolsConfig extends DroolsConfiguration<PlacementHome> implements DroolsAuthorizer {

  private static final String SESSION_NAME = "placement-home-authorization-session";
  private static final String PATH_TO_RULES_CONFIG = "placement-home-authorization-rules";

  protected BasePlacementHomeAuthDroolsConfig(String agendaGroup) {
    super(SESSION_NAME, agendaGroup, PATH_TO_RULES_CONFIG);
  }

  @Override
  public DroolsConfiguration getInstance() {
    return this;
  }
}
