package gov.ca.cwds.cms.data.access.service.impl.client;

import gov.ca.cwds.cms.data.access.dto.ChildClientEntityAwareDTO;
import gov.ca.cwds.data.legacy.cms.entity.Client;
import gov.ca.cwds.data.legacy.cms.entity.HealthInterventionPlan;
import java.time.LocalDate;
import java.util.Arrays;
import org.junit.Test;

public class R10354Test extends BaseDocToolRulesClientImplementationTest {

  private static final String RULE_NAME = "R-10354";
  private static final LocalDate SOME_DATE = LocalDate.of(2018, 1, 8);
  private static final String CLIENT_ID = "AdCfWS40FG";
  private static final String PLAN_THIRD_ID = "AhmDhCP0Fm";

  @Test
  public void testPlanStartDateGtBirthDate() throws Exception {
    checkRuleSatisfied(dto(client(SOME_DATE), plan(SOME_DATE.plusDays(1))), RULE_NAME);
  }

  @Test
  public void testPlanStartDateLtBirthDate() throws Exception {
    checkRuleViolatedOnce(dto(client(SOME_DATE), plan(SOME_DATE.minusDays(1))), RULE_NAME);
  }

  @Test
  public void testPlanStartDateEqBirthDate() throws Exception {
    checkRuleSatisfied(dto(client(SOME_DATE), plan(SOME_DATE)), RULE_NAME);
  }

  @Test
  public void testBirthDateNull() throws Exception {
    checkRuleSatisfied(dto(client(null), plan(SOME_DATE)), RULE_NAME);
  }

  private static Client client(LocalDate birthDate) {
    Client client = new Client();
    client.setIdentifier(CLIENT_ID);
    client.setBirthDate(birthDate);
    return client;
  }

  private static HealthInterventionPlan plan(LocalDate startDate) {
    HealthInterventionPlan plan = new HealthInterventionPlan();
    plan.setThirdId(PLAN_THIRD_ID);
    plan.setStartDate(startDate);
    return plan;
  }

  private static ChildClientEntityAwareDTO dto(Client client, HealthInterventionPlan... plans) {
    ChildClientEntityAwareDTO dto = new ChildClientEntityAwareDTO();
    dto.setEntity(client);
    dto.setActiveHealthInterventionPlans(Arrays.asList(plans));
    return dto;
  }
}
