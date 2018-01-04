package gov.ca.cwds.cms.data.access.service.impl;

import static gov.ca.cwds.util.TestUtils.localDate;

import gov.ca.cwds.data.legacy.cms.entity.Client;
import org.junit.Test;

public class R00739Test extends BaseDocToolRulesClientTest {

  private static final String RULE_NAME = "R-00739";

  @Test
  public void testDeathDateGtBirthDate() throws Exception {
    Client client = new Client();
    client.setBirthDate(localDate("2018-01-01"));
    client.setDeathDate(localDate("2098-01-03"));

    checkRuleSatisfied(client, RULE_NAME);
  }

  @Test
  public void testDeathDateLtBirthDate() throws Exception {
    Client client = new Client();
    client.setBirthDate(localDate("2098-01-03"));
    client.setDeathDate(localDate("2018-01-01"));

    checkRuleViolatedOnce(client, RULE_NAME);
  }

  @Test
  public void testDeathDateEqBirthDate() throws Exception {
    Client client = new Client();
    client.setBirthDate(localDate("2018-01-03"));
    client.setDeathDate(localDate("2018-01-03"));

    checkRuleSatisfied(client, RULE_NAME);
  }

  @Test
  public void testDeathDateNull() throws Exception {
    Client client = new Client();
    client.setBirthDate(localDate("2018-01-03"));
    client.setDeathDate(null);

    checkRuleSatisfied(client, RULE_NAME);
  }

  @Test
  public void testDeathAndBirthDatesNull() throws Exception {
    Client client = new Client();
    client.setBirthDate(null);
    client.setDeathDate(null);

    checkRuleSatisfied(client, RULE_NAME);
  }
}
