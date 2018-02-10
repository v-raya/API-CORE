package gov.ca.cwds.cms.data.access.service.impl.client;

import static gov.ca.cwds.cms.data.access.service.impl.client.ClientTestUtil.SOME_DATE;
import static gov.ca.cwds.cms.data.access.service.impl.client.ClientTestUtil.childClient;
import static gov.ca.cwds.cms.data.access.service.impl.client.ClientTestUtil.client;
import static gov.ca.cwds.cms.data.access.service.impl.client.ClientTestUtil.dto;
import static gov.ca.cwds.cms.data.access.service.impl.client.ClientTestUtil.termination;

import org.junit.Test;

public class R00769Test extends BaseDocToolRulesClientImplementationTest {

  private static final String RULE_NAME = "R-00769";

  @Test
  public void testTerminationStartDateGtBirthDate() throws Exception {
    checkRuleSatisfied(
        dto(childClient(SOME_DATE), termination(client(), SOME_DATE.plusDays(1))),
        RULE_NAME);
  }

  @Test
  public void testTerminationStartDateLtBirthDate() throws Exception {
    checkRuleViolatedOnce(
        dto(childClient(SOME_DATE), termination(client(), SOME_DATE.minusDays(1))),
        RULE_NAME);
  }

  @Test
  public void testTwoTerminationStartDateLtBirthDate() throws Exception {
    checkRuleViolated(dto(childClient(SOME_DATE),
        termination(client(), SOME_DATE.minusDays(1)),
        termination(client(), SOME_DATE.minusDays(2))),
        RULE_NAME, 2);
  }

  @Test
  public void testTerminationStartDateEqBirthDate() throws Exception {
    checkRuleSatisfied(dto(childClient(SOME_DATE), termination(client(), SOME_DATE)),
        RULE_NAME);
  }

  @Test
  public void testBirthDateNull() throws Exception {
    checkRuleSatisfied(dto(childClient(), termination(client(), SOME_DATE)),
        RULE_NAME);
  }
}
