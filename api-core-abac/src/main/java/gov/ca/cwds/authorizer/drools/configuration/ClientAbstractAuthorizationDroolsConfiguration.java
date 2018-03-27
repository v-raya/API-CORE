package gov.ca.cwds.authorizer.drools.configuration;

import gov.ca.cwds.data.legacy.cms.entity.Client;
import gov.ca.cwds.drools.DroolsConfiguration;

/**
 * Drools configuration for Client Abstract authorizer.
 *
 * @author CWDS TPT-3 Team
 */
public final class ClientAbstractAuthorizationDroolsConfiguration
    extends DroolsConfiguration<Client> implements DroolsAuthorizer {

  private static final String SESSION_NAME = "client-authorization-session";
  private static final String AGENDA_GROUP = "client-authorization-agenda";
  private static final String PATH_TO_RULES_CONFIG = "client-authorization-rules";
  private static final DroolsConfiguration INSTANCE =
      new ClientAbstractAuthorizationDroolsConfiguration();

  public ClientAbstractAuthorizationDroolsConfiguration() {
    super(SESSION_NAME, AGENDA_GROUP, PATH_TO_RULES_CONFIG);
  }

  @Override
  public DroolsConfiguration getInstance() {
    return INSTANCE;
  }
}
