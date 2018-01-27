package gov.ca.cwds.cms.data.access.service.impl.client;

import gov.ca.cwds.cms.data.access.service.impl.client.BaseDocToolRulesClientImplementationTest;
import gov.ca.cwds.data.legacy.cms.entity.Client;
import java.time.LocalDate;
import org.junit.Test;

public class R00739Test extends BaseDocToolRulesClientImplementationTest {

  private static final String RULE_NAME = "R-00739";

  @Test
  public void testDeathDateGtBirthDate() throws Exception {
    Client client = new Client();
    client.setBirthDate(LocalDate.of(2018, 1, 1));
    client.setDeathDate(LocalDate.of(2098, 1, 3));

    checkRuleSatisfied(client, RULE_NAME);
  }

  @Test
  public void testDeathDateLtBirthDate() throws Exception {
    Client client = new Client();
    client.setBirthDate(LocalDate.of(2098, 1, 3));
    client.setDeathDate(LocalDate.of(2018, 1, 1));

    checkRuleViolatedOnce(client, RULE_NAME);
  }

  @Test
  public void testDeathDateEqBirthDate() throws Exception {
    Client client = new Client();
    client.setBirthDate(LocalDate.of(2018, 1, 3));
    client.setDeathDate(LocalDate.of(2018, 1, 3));

    checkRuleSatisfied(client, RULE_NAME);
  }

  @Test
  public void testDeathDateNull() throws Exception {
    Client client = new Client();
    client.setBirthDate(LocalDate.of(2018, 1, 3));
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