package gov.ca.cwds.authorizer.drools.configuration;

import gov.ca.cwds.data.legacy.cms.entity.PlacementHome;
import gov.ca.cwds.drools.DroolsConfiguration;

/**
 * @author CWDS TPT-2 Team
 */
public final class SubstituteCareProviderAuthorizationDroolsConfiguration extends DroolsConfiguration<PlacementHome> implements DroolsAuthorizer {

  private static final String SESSION_NAME = "substitute-care-provider-authorization-session";
  private static final String AGENDA_GROUP = "substitute-care-provider-authorization-agenda";
  private static final String PATH_TO_RULES_CONFIG = "substitute-care-provider";
  private static final DroolsConfiguration INSTANCE = new SubstituteCareProviderAuthorizationDroolsConfiguration();

  public SubstituteCareProviderAuthorizationDroolsConfiguration() {
    super(SESSION_NAME, AGENDA_GROUP, PATH_TO_RULES_CONFIG);
  }

  @Override
  public DroolsConfiguration getInstance() {
    return INSTANCE;
  }

}
