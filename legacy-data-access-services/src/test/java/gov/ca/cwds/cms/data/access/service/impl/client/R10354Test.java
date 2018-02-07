package gov.ca.cwds.cms.data.access.service.impl.client;

import static gov.ca.cwds.cms.data.access.service.impl.client.ClientTestUtil.client;
import static gov.ca.cwds.cms.data.access.service.impl.client.ClientTestUtil.dto;
import static gov.ca.cwds.cms.data.access.service.impl.client.ClientTestUtil.plan;

import java.time.LocalDate;
import org.junit.Test;

public class R10354Test extends BaseDocToolRulesClientImplementationTest {

  private static final String RULE_NAME = "R-10354";
  private static final LocalDate SOME_DATE = LocalDate.of(2018, 1, 8);
  private static final String PLAN_THIRD_ID = "AhmDhCP0Fm";

  @Test
  public void testPlanStartDateGtBirthDate() throws Exception {
    checkRuleSatisfied(
        dto(client(SOME_DATE), plan(PLAN_THIRD_ID, SOME_DATE.plusDays(1))),
        RULE_NAME
    );
  }

  @Test
  public void testPlanStartDateLtBirthDate() throws Exception {
    checkRuleViolatedOnce(dto(client(SOME_DATE), plan(PLAN_THIRD_ID, SOME_DATE.minusDays(1))), RULE_NAME);
  }

  @Test
  public void testPlanStartDateEqBirthDate() throws Exception {
    checkRuleSatisfied(dto(client(SOME_DATE), plan(PLAN_THIRD_ID, SOME_DATE)), RULE_NAME);
  }

  @Test
  public void testBirthDateNull() throws Exception {
    checkRuleSatisfied(dto(client(null), plan(PLAN_THIRD_ID, SOME_DATE)), RULE_NAME);
  }

}
