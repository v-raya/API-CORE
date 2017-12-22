package gov.ca.cwds.cms.data.access.service.rules;

import gov.ca.cwds.data.legacy.cms.entity.PlacementHome;
import gov.ca.cwds.drools.DroolsConfiguration;

/** @author CWDS CASE API Team */
public final class ClientDroolsConfiguration extends DroolsConfiguration<PlacementHome> {

  public static final ClientDroolsConfiguration INSTANCE =
      new ClientDroolsConfiguration("client-session", "client-agenda", "client-legacy-rules");

  private ClientDroolsConfiguration(String rulesSession, String rulesAgenda, String kieContainerId) {
    super(rulesSession, rulesAgenda, kieContainerId);
  }
}
