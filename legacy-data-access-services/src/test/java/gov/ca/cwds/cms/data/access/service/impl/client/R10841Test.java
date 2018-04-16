package gov.ca.cwds.cms.data.access.service.impl.client;

import gov.ca.cwds.cms.data.access.service.rules.ClientDroolsConfiguration;
import gov.ca.cwds.data.legacy.cms.entity.Client;
import org.junit.Test;

public class R10841Test extends BaseDocToolRulesClientImplementationTest {

  /*
  if adding, modifying, or deleting CLIENT.Common_First_Name, .Common_Middle_Name or .Common_Last_Name
   then add, modify, or delete corresponding CLIENT_PHONETIC_NAME and CLIENT_PHONETIC_NAME2 rows.
  */


  private static final String OLD_LAST_NAME = "OldLastName";
  private static final String NEW_LAST_NAME = "NewLastName";
  private static final String OLD_FIRST_NAME = "OldFirstName";
  private static final String NEW_FIRST_NAME = "NewFirstName";
  private static final String OLD_MIDDLE_NAME = "OldMiddleName";
  private static final String NEW_MIDDLE_NAME = "NEWMiddleName";

  @Test
  public void testLastNameChanged() {
    Client client = ClientTestUtil.withFirstLastMiddleNames(OLD_FIRST_NAME, NEW_LAST_NAME, OLD_MIDDLE_NAME);
    Client persistedClient = createPersistedClient();
    clientEntityAwareDTO.setEntity(client);
    clientEntityAwareDTO.setPersistentClientState(persistedClient);

    businessValidationService.runDataProcessing(
            clientEntityAwareDTO, principal, ClientDroolsConfiguration.INSTANCE);

    assert clientEntityAwareDTO.isUpdateClientPhoneticName();
  }

  @Test
  public void testFirstNameChanged() {
    Client client = ClientTestUtil.withFirstLastMiddleNames(NEW_FIRST_NAME, OLD_LAST_NAME, OLD_MIDDLE_NAME);
    Client persistedClient = createPersistedClient();
    clientEntityAwareDTO.setEntity(client);
    clientEntityAwareDTO.setPersistentClientState(persistedClient);

    businessValidationService.runDataProcessing(
            clientEntityAwareDTO, principal, ClientDroolsConfiguration.INSTANCE);

    assert clientEntityAwareDTO.isUpdateClientPhoneticName();
  }

  @Test
  public void testMiddleNameChanged() {
    Client client = ClientTestUtil.withFirstLastMiddleNames(OLD_FIRST_NAME, OLD_LAST_NAME, NEW_MIDDLE_NAME);
    Client persistedClient = createPersistedClient();
    clientEntityAwareDTO.setEntity(client);
    clientEntityAwareDTO.setPersistentClientState(persistedClient);

    businessValidationService.runDataProcessing(
            clientEntityAwareDTO, principal, ClientDroolsConfiguration.INSTANCE);

    assert clientEntityAwareDTO.isUpdateClientPhoneticName();
  }

  @Test
  public void testEveryNameChanged() {
    Client client = ClientTestUtil.withFirstLastMiddleNames(NEW_FIRST_NAME, NEW_LAST_NAME, NEW_MIDDLE_NAME);
    Client persistedClient = createPersistedClient();
    clientEntityAwareDTO.setEntity(client);
    clientEntityAwareDTO.setPersistentClientState(persistedClient);

    businessValidationService.runDataProcessing(
            clientEntityAwareDTO, principal, ClientDroolsConfiguration.INSTANCE);

    assert clientEntityAwareDTO.isUpdateClientPhoneticName();
  }

  @Test
  public void testNothingChanged() {
    Client client = ClientTestUtil.withFirstLastMiddleNames(OLD_FIRST_NAME, OLD_LAST_NAME, OLD_MIDDLE_NAME);
    Client persistedClient = createPersistedClient();
    clientEntityAwareDTO.setEntity(client);
    clientEntityAwareDTO.setPersistentClientState(persistedClient);

    businessValidationService.runDataProcessing(
            clientEntityAwareDTO, principal, ClientDroolsConfiguration.INSTANCE);

    assert !clientEntityAwareDTO.isUpdateClientPhoneticName();
  }


  private static Client createPersistedClient() {
    return ClientTestUtil.withFirstLastMiddleNames(OLD_FIRST_NAME, OLD_LAST_NAME, OLD_MIDDLE_NAME);
  }

}
