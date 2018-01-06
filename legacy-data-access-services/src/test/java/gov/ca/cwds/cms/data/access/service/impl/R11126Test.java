package gov.ca.cwds.cms.data.access.service.impl;

import gov.ca.cwds.data.legacy.cms.entity.ChildClient;
import gov.ca.cwds.data.legacy.cms.entity.FсEligibility;
import java.util.Arrays;
import java.util.List;
import org.junit.Test;

/**
 * @author CWDS TPT-3 Team
 */
public class R11126Test extends BaseDocToolRulesChildClientImplementationTest {
  private static final String RULE_NAME = "R-11126";

  @Test
  public void testValidListofElegebilities() throws Exception {
    ChildClient client = new ChildClient();
    client.setIdentifier("1234567890");
    client.setAfdcFcEligibilityIndicatorVar(true);

    clientEntityAwareDTO.setEntity(client);

    List<FсEligibility> fсEligibilities = createListOfDifferentChildClientEligibilities();
    clientEntityAwareDTO.getFcFсEligibilities().addAll(fсEligibilities);

    checkRuleSatisfied(RULE_NAME);
  }

//  @Test
//  public void testOtherEthnicityPresentWhenNoPrimary() throws Exception {
//    Client client = new Client();
//    client.setIdentifier("1234567890");
//    client.setPrimaryEthnicityCode((short) 0);
//
//    clientEntityAwareDTO.setEntity(client);
//
//    List<ClientScpEthnicity> clientScpEthnicityList = createListOfDifferentClientScpEthnicities();
//    clientEntityAwareDTO.getClientScpEthnicities().addAll(clientScpEthnicityList);
//
//    checkRuleViolatedOnce(RULE_NAME);
//  }
//
//  @Test
//  public void testEmptyListOfOtherEthnicitiesWhenPrimaryPresent() throws Exception {
//    Client client = new Client();
//    client.setIdentifier("1234567890");
//    client.setPrimaryEthnicityCode((short) 1);
//
//    clientEntityAwareDTO.setEntity(client);
//
//    checkRuleSatisfied(RULE_NAME);
//  }
//
//  @Test
//  public void testEmptyListOfOtherEthnicitiesWhenNoPrimary() throws Exception {
//    Client client = new Client();
//    client.setIdentifier("1234567890");
//    client.setPrimaryEthnicityCode((short) 0);
//
//    clientEntityAwareDTO.setEntity(client);
//
//    checkRuleSatisfied(RULE_NAME);
//  }
//
//  @Test
//  public void testNoPrimaryValidListOfOther() throws Exception {
//    Client client = new Client();
//    client.setIdentifier("1234567890");
//    client.setPrimaryEthnicityCode((short) 0);
//
//    clientEntityAwareDTO.setEntity(client);
//
//    clientEntityAwareDTO.getClientScpEthnicities().add(createClientScpEthnicity("C", "1234567890", (short) 0));
//    clientEntityAwareDTO.getClientScpEthnicities().add(createClientScpEthnicity("C", "1234567890", (short) 0));
//
//    checkRuleSatisfied(RULE_NAME);
//  }

  private static List<FсEligibility> createListOfDifferentChildClientEligibilities() {
    FсEligibility eligibility1 = createFcEligibility("1234567890");
    FсEligibility eligibility2 = createFcEligibility("1234567890");
    FсEligibility eligibility3 = createFcEligibility("1234567890");

    return Arrays.asList(eligibility1, eligibility2, eligibility3);
  }


  private static FсEligibility createFcEligibility(String childClientId) {
    FсEligibility eligibility = new FсEligibility();
    eligibility.setChildClientId(childClientId);
    return eligibility;
  }


}
