package gov.ca.cwds.cms.data.access.service.rules;

import gov.ca.cwds.data.legacy.cms.entity.ClientRelationship;
import gov.ca.cwds.drools.DroolsConfiguration;

/** @author CWDS TPT-3 Team */
public final class ClientRelationshipDroolsConfiguration
    extends DroolsConfiguration<ClientRelationship> {

  public static final ClientRelationshipDroolsConfiguration INSTANCE =
      new ClientRelationshipDroolsConfiguration(
          "client-relationship-session",
          "client-relationship-agenda",
          "client-relationship-legacy-rules");

  private ClientRelationshipDroolsConfiguration(
      String rulesSession, String rulesAgenda, String kieContainerId) {
    super(rulesSession, rulesAgenda, kieContainerId);
  }
}
