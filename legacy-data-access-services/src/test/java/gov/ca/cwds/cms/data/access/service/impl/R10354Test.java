package gov.ca.cwds.cms.data.access.service.impl;

import gov.ca.cwds.cms.data.access.dto.ClientEntityAwareDTO;
import gov.ca.cwds.data.legacy.cms.entity.Client;
import gov.ca.cwds.data.legacy.cms.entity.HealthInterventionPlan;
import java.time.LocalDate;
import java.util.Arrays;
import org.junit.Test;

public class R10354Test extends BaseDocToolRulesClientTest {

  private static final String RULE_NAME = "R-10354";
  private static final LocalDate SOME_DATE = LocalDate.of(2018, 1, 8);

  @Test
  public void testPlanStartDateGtBirthDate() throws Exception {
    Client client = client(SOME_DATE);
    HealthInterventionPlan plan = plan(SOME_DATE.plusDays(1));

    checkRuleSatisfied(dto(client, plan), RULE_NAME);
  }

  @Test
  public void testPlanStartDateLtBirthDate() throws Exception {
    Client client = client(SOME_DATE);
    HealthInterventionPlan plan = plan(SOME_DATE.minusDays(1));


    checkRuleViolatedOnce(dto(client, plan), RULE_NAME);
  }

  @Test
  public void testPlanStartDateEqBirthDate() throws Exception {
    Client client = client(SOME_DATE);
    HealthInterventionPlan plan = plan(SOME_DATE);

    checkRuleSatisfied(dto(client, plan), RULE_NAME);
  }

  @Test
  public void testBirthDateNull() throws Exception {
    Client client = client(null);
    HealthInterventionPlan plan = plan(SOME_DATE);

    checkRuleSatisfied(dto(client, plan), RULE_NAME);
  }

  private static Client client(LocalDate birthDate) {
    Client client = new Client();
    client.setBirthDate(birthDate);
    return client;
  }

  private static HealthInterventionPlan plan(LocalDate startDate) {
    HealthInterventionPlan plan = new HealthInterventionPlan();
    plan.setStartDate(startDate);
    return plan;
  }

  private static ClientEntityAwareDTO dto(Client client, HealthInterventionPlan... plans) {
    ClientEntityAwareDTO dto = new ClientEntityAwareDTO();
    dto.setEntity(client);
    dto.setActiveHealthInterventionPlans(Arrays.asList(plans));
    return dto;
  }
}
