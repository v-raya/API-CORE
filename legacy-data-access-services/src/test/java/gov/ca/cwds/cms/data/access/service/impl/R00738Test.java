package gov.ca.cwds.cms.data.access.service.impl;

import gov.ca.cwds.data.legacy.cms.entity.Client;
import java.time.LocalDate;
import org.junit.Test;

public class R00738Test extends BaseDocToolRulesClientTest {

  private static final String RULE_NAME = "R-00738";

  @Test
  public void testCreationDateGtBirthDate() throws Exception {
    Client client = new Client();
    client.setCreationDate(LocalDate.of(2018, 1, 3));
    client.setBirthDate(LocalDate.of(2018, 1, 1));

    checkRuleSatisfied(client, RULE_NAME);
  }

  @Test
  public void testCreationDateLtBirthDate() throws Exception {
    Client client = new Client();
    client.setCreationDate(LocalDate.of(2018, 1, 1));
    client.setBirthDate(LocalDate.of(2018, 1, 3));

    checkRuleViolatedOnce(client, RULE_NAME);
  }

  @Test
  public void testCreationDateEqBirthDate() throws Exception {
    Client client = new Client();
    client.setCreationDate(LocalDate.of(2018, 1, 3));
    client.setBirthDate(LocalDate.of(2018, 1, 3));

    checkRuleSatisfied(client, RULE_NAME);
  }

  @Test
  public void testBirthDateNull() throws Exception {
    Client client = new Client();
    client.setCreationDate(LocalDate.of(2018, 1, 3));
    client.setBirthDate(null);

    checkRuleSatisfied(client, RULE_NAME);
  }
}
