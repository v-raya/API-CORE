package gov.ca.cwds.cms.data.access.service.impl;

import gov.ca.cwds.data.legacy.cms.entity.Client;
import gov.ca.cwds.data.legacy.cms.entity.ClientScpEthnicity;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class R00742Test extends BaseDocToolRulesClientImplementationTest {

    private static final String RULE_NAME = "R-00742";
    private static final String CLIENT_IDENTIFIER = "1234567890";

    @Test
    public void testValidListofEthnicities() throws Exception {
        Client client = new Client();
        client.setIdentifier(CLIENT_IDENTIFIER);
        client.setPrimaryEthnicityCode((short) 1);

        clientEntityAwareDTO.setEntity(client);

        List<ClientScpEthnicity> clientScpEthnicityList = createListOfDifferentClientScpEthnicities();
        clientEntityAwareDTO.getClientScpEthnicities().addAll(clientScpEthnicityList);

        checkRuleSatisfied(RULE_NAME);
    }

    @Test
    public void testOtherEthnicityPresentWhenNoPrimary() throws Exception {
        Client client = new Client();
        client.setIdentifier(CLIENT_IDENTIFIER);
        client.setPrimaryEthnicityCode((short) 0);

        clientEntityAwareDTO.setEntity(client);

        List<ClientScpEthnicity> clientScpEthnicityList = createListOfDifferentClientScpEthnicities();
        clientEntityAwareDTO.getClientScpEthnicities().addAll(clientScpEthnicityList);

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

        clientEntityAwareDTO.getClientScpEthnicities().add(createClientScpEthnicity("C", CLIENT_IDENTIFIER, (short) 0));
        clientEntityAwareDTO.getClientScpEthnicities().add(createClientScpEthnicity("C", CLIENT_IDENTIFIER, (short) 0));

        checkRuleSatisfied(RULE_NAME);
    }


    private static List<ClientScpEthnicity> createListOfDifferentClientScpEthnicities() {
        List<ClientScpEthnicity> clientScpEthnicityList = new ArrayList<>();
        ClientScpEthnicity clientScpEthnicity1 = createClientScpEthnicity("C", CLIENT_IDENTIFIER, (short) 3162);
        ClientScpEthnicity clientScpEthnicity2 = createClientScpEthnicity("C", CLIENT_IDENTIFIER, (short) 3163);
        ClientScpEthnicity clientScpEthnicity3 = createClientScpEthnicity("C", CLIENT_IDENTIFIER, (short) 0);

        clientScpEthnicityList.addAll(Arrays.asList(clientScpEthnicity1, clientScpEthnicity2, clientScpEthnicity3));
        return clientScpEthnicityList;
    }


    private static ClientScpEthnicity createClientScpEthnicity(String establishCode, String establishId, Short ethnicityCode) {
        ClientScpEthnicity clientScpEthnicity = new ClientScpEthnicity();
        clientScpEthnicity.setEstblshCd(establishCode);
        clientScpEthnicity.setEstblshId(establishId);
        clientScpEthnicity.setEthnctyc(ethnicityCode);
        return clientScpEthnicity;
    }


}
