package gov.ca.cwds.cms.data.access.service.impl.client;

import gov.ca.cwds.data.legacy.cms.entity.Client;
import gov.ca.cwds.data.legacy.cms.entity.ClientOtherEthnicity;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.junit.Test;

public class R00742Test extends BaseDocToolRulesClientImplementationTest {

    private static final String RULE_NAME = "R-00742";
    private static final String CLIENT_IDENTIFIER = "1234567890";

    @Test
    public void testValidListofEthnicities() throws Exception {
        Client client = new Client();
        client.setIdentifier(CLIENT_IDENTIFIER);
        client.setPrimaryEthnicityCode((short) 1);

        clientEntityAwareDTO.setEntity(client);

        List<ClientOtherEthnicity> clientScpEthnicityList = createListOfDifferentClientOtherEthnicities(client);
        clientEntityAwareDTO.getOtherEthnicities().addAll(clientScpEthnicityList);

        checkRuleSatisfied(RULE_NAME);
    }

    @Test
    public void testOtherEthnicityPresentWhenNoPrimary() throws Exception {
        Client client = new Client();
        client.setIdentifier(CLIENT_IDENTIFIER);
        client.setPrimaryEthnicityCode((short) 0);

        clientEntityAwareDTO.setEntity(client);

        List<ClientOtherEthnicity> clientScpEthnicityList = createListOfDifferentClientOtherEthnicities(client);
        clientEntityAwareDTO.getOtherEthnicities().addAll(clientScpEthnicityList);

        checkRuleViolatedOnce(RULE_NAME);
    }

    @Test
    public void testEmptyListOfOtherEthnicitiesWhenPrimaryPresent() throws Exception {
        Client client = new Client();
        client.setIdentifier(CLIENT_IDENTIFIER);
        client.setPrimaryEthnicityCode((short) 1);

        clientEntityAwareDTO.setEntity(client);

        checkRuleSatisfied(RULE_NAME);
    }

    @Test
    public void testEmptyListOfOtherEthnicitiesWhenNoPrimary() throws Exception {
        Client client = new Client();
        client.setIdentifier(CLIENT_IDENTIFIER);
        client.setPrimaryEthnicityCode((short) 0);

        clientEntityAwareDTO.setEntity(client);

        checkRuleSatisfied(RULE_NAME);
    }

    @Test
    public void testNoPrimaryValidListOfOther() throws Exception {
        Client client = new Client();
        client.setIdentifier(CLIENT_IDENTIFIER);
        client.setPrimaryEthnicityCode((short) 0);

        clientEntityAwareDTO.setEntity(client);

        clientEntityAwareDTO.getOtherEthnicities().add(createClientOtherEthnicity(client, (short) 0));
        clientEntityAwareDTO.getOtherEthnicities().add(createClientOtherEthnicity(client, (short) 0));

        checkRuleSatisfied(RULE_NAME);
    }

    private static List<ClientOtherEthnicity> createListOfDifferentClientOtherEthnicities(Client client) {
        List<ClientOtherEthnicity> clientScpEthnicityList = new ArrayList<>();
        ClientOtherEthnicity clientScpEthnicity1 = createClientOtherEthnicity(client, (short) 3162);
        ClientOtherEthnicity clientScpEthnicity2 = createClientOtherEthnicity(client, (short) 3163);
        ClientOtherEthnicity clientScpEthnicity3 = createClientOtherEthnicity(client, (short) 0);

        clientScpEthnicityList.addAll(Arrays.asList(clientScpEthnicity1, clientScpEthnicity2, clientScpEthnicity3));
        return clientScpEthnicityList;
    }


    private static ClientOtherEthnicity createClientOtherEthnicity(Client client, Short ethnicityCode) {
        ClientOtherEthnicity clientScpEthnicity = new ClientOtherEthnicity();
        clientScpEthnicity.setClient(client);
        clientScpEthnicity.setEthnicityCode(ethnicityCode);
        return clientScpEthnicity;
    }

}
