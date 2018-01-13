package gov.ca.cwds.authorizer.drools.configuration;

import gov.ca.cwds.data.legacy.cms.entity.Client;
import gov.ca.cwds.drools.DroolsConfiguration;

/**
 * @author CWDS TPT-3 Team
 */
public final class ClientAuthorizationDroolsConfiguration extends DroolsConfiguration<Client> {

  public static final ClientAuthorizationDroolsConfiguration INSTANCE = new ClientAuthorizationDroolsConfiguration(
      "client-authorization-session",
      "client-authorization-agenda",
      "client-authorization-rules"
  );

  private ClientAuthorizationDroolsConfiguration(String sessionName, String agendaGroup, String pathToRulesConfig) {
    super(sessionName, agendaGroup, pathToRulesConfig);
  }
}
