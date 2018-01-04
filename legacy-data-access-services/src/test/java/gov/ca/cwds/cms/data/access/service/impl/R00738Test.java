package gov.ca.cwds.cms.data.access.service.impl;

import static gov.ca.cwds.util.TestUtils.localDate;

import gov.ca.cwds.data.legacy.cms.entity.Client;
import org.junit.Test;

public class R00738Test extends BaseDocToolRulesClientTest {

  private static final String RULE_NAME = "R-00738";

  @Test
  public void testCreationDateGtBirthDate() throws Exception {
    Client client = new Client();
    client.setCreationDate(localDate("2018-01-03"));
    client.setBirthDate(localDate("2018-01-01"));

    checkRuleSatisfied(client, RULE_NAME);
  }

  @Test
  public void testCreationDateLtBirthDate() throws Exception {
    Client client = new Client();
    client.setCreationDate(localDate("2018-01-01"));
    client.setBirthDate(localDate("2018-01-03"));

    checkRuleViolatedOnce(client, RULE_NAME);
  }

  @Test
  public void testCreationDateEqBirthDate() throws Exception {
    Client client = new Client();
    client.setCreationDate(localDate("2018-01-03"));
    client.setBirthDate(localDate("2018-01-03"));

    checkRuleSatisfied(client, RULE_NAME);
  }

  @Test
  public void testBirthDateNull() throws Exception {
    Client client = new Client();
    client.setCreationDate(localDate("2018-01-03"));
    client.setBirthDate(null);

    checkRuleSatisfied(client, RULE_NAME);
  }
}
