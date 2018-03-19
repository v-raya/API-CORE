package gov.ca.cwds.authorizer.drools.configuration;

import gov.ca.cwds.data.legacy.cms.entity.Client;
import gov.ca.cwds.drools.DroolsConfiguration;

/**
 * @author CWDS TPT-2 Team
 */
public final class ClientResultAuthorizationDroolsConfiguration extends DroolsConfiguration<Client> implements DroolsAuthorizer {

  private static final String SESSION_NAME = "client-result-authorization-session";
  private static final String AGENDA_GROUP = "client-result-authorization-agenda";
  private static final String PATH_TO_RULES_CONFIG = "client-abstract-authorization-rules";
  private static final DroolsConfiguration INSTANCE = new ClientResultAuthorizationDroolsConfiguration();

  public ClientResultAuthorizationDroolsConfiguration() {
    super(SESSION_NAME, AGENDA_GROUP, PATH_TO_RULES_CONFIG);
  }

  @Override
  public DroolsConfiguration getInstance() {
    return INSTANCE;
  }
}
