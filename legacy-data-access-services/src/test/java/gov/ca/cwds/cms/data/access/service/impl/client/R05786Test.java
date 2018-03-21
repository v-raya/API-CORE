package gov.ca.cwds.cms.data.access.service.impl.client;

import gov.ca.cwds.cms.data.access.service.rules.ClientDroolsConfiguration;
import gov.ca.cwds.data.legacy.cms.entity.Client;
import gov.ca.cwds.drools.DroolsException;
import org.junit.Test;

public class R05786Test extends BaseDocToolRulesClientImplementationTest {
  private static final String RULE_NAME = "R-05786";

  /*
  If oldCLIENT.COMMON_LAST_NAME != newCLIENT.COMMON_LAST_NAME
  create OTHER_CLIENT_NAME from oldCLIENT.COMMON_FIRST_NAME, COMMON_LAST_NAME,
  COMMON_MIDDLE_NAME where NAME_TYPE = 'AKA'.
   */

  private static final String OLD_LAST_NAME = "OldLastName";
  private static final String NEW_LAST_NAME = "NewLastName";
  private static final String OLD_FIRST_NAME = "OldFirstName";
  private static final String NEW_FIRST_NAME = "NewFirstName";
  private static final String OLD_MIDDLE_NAME = "OldMiddleName";
  private static final String NEW_MIDDLE_NAME = "NEWMiddleName";
  private static final short AKA_NAMETYPE_SYSID = 1311; // AKA

  @Test
  public void testLastNameChanged() throws DroolsException {
    Client client = createClient(OLD_FIRST_NAME, NEW_LAST_NAME, OLD_MIDDLE_NAME);
    Client persistedClient = createPersistedClient();
    clientEntityAwareDTO.setEntity(client);
    clientEntityAwareDTO.setPersistentClientState(persistedClient);

    businessValidationService.runDataProcessing(
        clientEntityAwareDTO, principal, ClientDroolsConfiguration.INSTANCE);

    assert clientEntityAwareDTO.getOtherClientName() != null;
    assert clientEntityAwareDTO.getOtherClientName().getLastName().equals(OLD_LAST_NAME);
    assert clientEntityAwareDTO.getOtherClientName().getFirstName().equals(OLD_FIRST_NAME);
    assert clientEntityAwareDTO.getOtherClientName().getMiddleName().equals(OLD_MIDDLE_NAME);
    assert clientEntityAwareDTO.getOtherClientName().getNameType() == AKA_NAMETYPE_SYSID;
  }

  @Test
  public void testAllNamePartsChanged() throws DroolsException {
    Client client = createClient(NEW_FIRST_NAME, NEW_LAST_NAME, NEW_MIDDLE_NAME);
    Client persistedClient = createPersistedClient();
    clientEntityAwareDTO.setEntity(client);
    clientEntityAwareDTO.setPersistentClientState(persistedClient);

    businessValidationService.runDataProcessing(
        clientEntityAwareDTO, principal, ClientDroolsConfiguration.INSTANCE);

    assert clientEntityAwareDTO.getOtherClientName() != null;
    assert clientEntityAwareDTO.getOtherClientName().getLastName().equals(OLD_LAST_NAME);
    assert clientEntityAwareDTO.getOtherClientName().getFirstName().equals(OLD_FIRST_NAME);
    assert clientEntityAwareDTO.getOtherClientName().getMiddleName().equals(OLD_MIDDLE_NAME);
    assert clientEntityAwareDTO.getOtherClientName().getNameType() == AKA_NAMETYPE_SYSID;
  }

  @Test
  public void testLastNameNotChanged() throws DroolsException {
    Client client = createClient(NEW_FIRST_NAME, OLD_LAST_NAME, NEW_MIDDLE_NAME);
    Client persistedClient = createPersistedClient();
    clientEntityAwareDTO.setEntity(client);
    clientEntityAwareDTO.setPersistentClientState(persistedClient);

    businessValidationService.runDataProcessing(
        clientEntityAwareDTO, principal, ClientDroolsConfiguration.INSTANCE);

    assert clientEntityAwareDTO.getOtherClientName() == null;
  }

  private static Client createClient(String firstName, String lastName, String middleName) {
    Client client = ClientTestUtil.client();
    client.setCommonLastName(lastName);
    client.setCommonFirstName(firstName);
    client.setCommonMiddleName(middleName);
    return client;
  }

  private static Client createPersistedClient() {
    return createClient(OLD_FIRST_NAME, OLD_LAST_NAME, OLD_MIDDLE_NAME);
  }
}
