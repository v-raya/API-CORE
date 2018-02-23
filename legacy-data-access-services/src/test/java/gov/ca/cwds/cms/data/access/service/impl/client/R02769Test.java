package gov.ca.cwds.cms.data.access.service.impl.client;


import gov.ca.cwds.data.legacy.cms.entity.Client;
import gov.ca.cwds.data.legacy.cms.entity.syscodes.NameType;
import org.junit.Test;


public class R02769Test extends BaseDocToolRulesClientImplementationTest {
    private static final String RULE_NAME = "R-02769";

    @Test
    public void nameTypeDoeAndNothingChanged() throws  Exception {
        Client client = createDoeClient();
        Client persistedClient = createDoeClient();
        clientEntityAwareDTO.setEntity(client);
        clientEntityAwareDTO.setPersistentClientState(persistedClient);

        checkRuleSatisfied(RULE_NAME);
    }

    @Test
    public void nameTypeDoeAndFirstNameChanged() throws  Exception {
        Client client = createDoeClient();
        client.setCommonFirstName("FirstName");
        Client persistedClient = createDoeClient();
        persistedClient.setCommonFirstName("NewFirstName");
        clientEntityAwareDTO.setEntity(client);
        clientEntityAwareDTO.setPersistentClientState(persistedClient);
        checkRuleViolatedOnce(RULE_NAME);
    }

    @Test
    public void nameTypeChangedAndFirstNameChanged() throws  Exception {
        Client client = createDoeClient();
        client.setCommonFirstName("FirstName");
        Client persistedClient = createClient();
        persistedClient.setCommonFirstName("NewFirstName");
        clientEntityAwareDTO.setEntity(client);
        clientEntityAwareDTO.setPersistentClientState(persistedClient);

        checkRuleSatisfied(RULE_NAME);
    }

    @Test
    public void nameTypeDoeAndLastNameChanged() throws  Exception {
        Client client = createDoeClient();
        client.setCommonLastName("LastName");
        Client persistedClient = createDoeClient();
        persistedClient.setCommonLastName("NewLastName");
        clientEntityAwareDTO.setEntity(client);
        clientEntityAwareDTO.setPersistentClientState(persistedClient);
        checkRuleViolatedOnce(RULE_NAME);
    }

    @Test
    public void nameTypeChangedAndLastNameChanged() throws  Exception {
        Client client = createDoeClient();
        client.setCommonLastName("LastName");
        Client persistedClient = createClient();
        persistedClient.setCommonLastName("NewLastName");
        clientEntityAwareDTO.setEntity(client);
        clientEntityAwareDTO.setPersistentClientState(persistedClient);
        checkRuleSatisfied(RULE_NAME);
    }

    @Test
    public void nameTypeDoeAndSuffixChanged() throws  Exception {
        Client client = createDoeClient();
        client.setSuffixTitleDescription("SuffixTitleDescription");
        Client persistedClient = createDoeClient();
        persistedClient.setCommonLastName("NewSuffixTitleDescription");
        clientEntityAwareDTO.setEntity(client);
        clientEntityAwareDTO.setPersistentClientState(persistedClient);
        checkRuleViolatedOnce(RULE_NAME);
    }

    @Test
    public void nameTypeChangedAndSuffixChanged() throws  Exception {
        Client client = createDoeClient();
        client.setCommonLastName("LastName");
        client.setCommonFirstName("FirstName");
        client.setSuffixTitleDescription("SuffixTitleDescription");
        Client persistedClient = createClient();
        persistedClient.setCommonLastName("NewSuffixTitleDescription");
        clientEntityAwareDTO.setEntity(client);
        clientEntityAwareDTO.setPersistentClientState(persistedClient);
        checkRuleSatisfied(RULE_NAME);
    }

    @Test
    public void nameTypeDoeAndAllChanged() throws  Exception {
        Client client = createDoeClient();
        client.setSuffixTitleDescription("SuffixTitleDescription");
        Client persistedClient = createDoeClient();
        persistedClient.setCommonLastName("NewLastName");
        persistedClient.setCommonFirstName("NewFirstName");
        persistedClient.setCommonLastName("NewSuffixTitleDescription");
        clientEntityAwareDTO.setEntity(client);
        clientEntityAwareDTO.setPersistentClientState(persistedClient);
        checkRuleViolatedOnce(RULE_NAME);
    }


    private static Client createDoeClient() {
        Client client = createClient();
        NameType nameType = new NameType();
        nameType.setShortDescription("Doe");
        client.setNameType(nameType);
        return client;
    }

    private static Client createClient() {
        Client client = new Client();
        client.setCommonLastName("DefaultLastName");
        client.setCommonFirstName("DefaultFirstName");
        client.setSuffixTitleDescription("DefaultSuffixDesc");
        NameType nameType = new NameType();
        nameType.setShortDescription("AKA");
        client.setNameType(nameType);
        return client;
    }



}
