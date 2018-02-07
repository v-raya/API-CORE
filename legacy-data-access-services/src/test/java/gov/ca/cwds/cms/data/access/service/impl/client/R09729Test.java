package gov.ca.cwds.cms.data.access.service.impl.client;

import static gov.ca.cwds.cms.data.access.service.impl.client.ClientTestUtil.SOME_DATE;
import static gov.ca.cwds.cms.data.access.service.impl.client.ClientTestUtil.client;
import static gov.ca.cwds.cms.data.access.service.impl.client.ClientTestUtil.dto;
import static gov.ca.cwds.cms.data.access.service.impl.client.ClientTestUtil.safetyAlert;

import gov.ca.cwds.cms.data.access.dto.ChildClientEntityAwareDTO;
import gov.ca.cwds.data.legacy.cms.entity.Client;
import gov.ca.cwds.data.legacy.cms.entity.SafetyAlert;
import gov.ca.cwds.drools.DroolsException;
import org.junit.Test;

/**
 * @author CWDS TPT-3 Team
 */
public class R09729Test extends BaseDocToolRulesClientImplementationTest {

  private static final String RULE_NAME = "R-09729";
  private static final String THIRD_ID = "AhmDhCP0Fm";
  private static final String THIRD_ID_2 = "0hmDhCP0Fm";

  @Test
  public void rule09729_satisfied_whenNoAlerts() throws DroolsException {
    // given
    final Client client = client(SOME_DATE);
    final ChildClientEntityAwareDTO input = dto(client);

    // when + then
    checkRuleSatisfied(input, RULE_NAME);
  }

  @Test
  public void rule09729_satisfied_whenSingleAlertOnDOB() throws DroolsException {
    // given
    final Client client = client(SOME_DATE);
    final SafetyAlert safetyAlert = safetyAlert(THIRD_ID, SOME_DATE);
    final ChildClientEntityAwareDTO input = dto(client, safetyAlert);

    // when + then
    checkRuleSatisfied(input, RULE_NAME);
  }

  @Test
  public void rule09729_satisfied_whenSingleAlertAfterDOB() throws DroolsException {
    // given
    final Client client = client(SOME_DATE);
    final SafetyAlert safetyAlert = safetyAlert(THIRD_ID, SOME_DATE.plusDays(100));
    final ChildClientEntityAwareDTO input = dto(client, safetyAlert);

    // when + then
    checkRuleSatisfied(input, RULE_NAME);
  }

  @Test
  public void rule09729_satisfied_whenTwoAlertsAfterDOB() throws DroolsException {
    // given
    final Client client = client(SOME_DATE);
    final SafetyAlert safetyAlert0 = safetyAlert(THIRD_ID, SOME_DATE.plusDays(1));
    final SafetyAlert safetyAlert1 = safetyAlert(THIRD_ID_2, SOME_DATE.plusDays(100));
    final ChildClientEntityAwareDTO input = dto(client, safetyAlert0, safetyAlert1);

    // when + then
    checkRuleSatisfied(input, RULE_NAME);
  }

  @Test
  public void rule09729_violated_whenSingleAlertBeforeDOB() throws DroolsException {
    // given
    final Client client = client(SOME_DATE);
    final SafetyAlert safetyAlert = safetyAlert(THIRD_ID, SOME_DATE.minusDays(1));
    final ChildClientEntityAwareDTO input = dto(client, safetyAlert);

    // when + then
    checkRuleViolatedOnce(input, RULE_NAME);
  }

  @Test
  public void rule09729_violatedTwice_whenTwoAlertsBeforeDOB() throws DroolsException {
    // given
    final Client client = client(SOME_DATE);
    final SafetyAlert safetyAlert0 = safetyAlert(THIRD_ID, SOME_DATE.minusDays(1));
    final SafetyAlert safetyAlert1 = safetyAlert(THIRD_ID_2, SOME_DATE.minusDays(100));
    final ChildClientEntityAwareDTO input = dto(client, safetyAlert0, safetyAlert1);

    // when + then
    checkRuleViolated(input, RULE_NAME, 2);
  }

  @Test
  public void rule09729_violated_whenOneOfTwoAlertsIsBeforeDOB() throws DroolsException {
    // given
    final Client client = client(SOME_DATE);
    final SafetyAlert safetyAlert0 = safetyAlert(THIRD_ID, SOME_DATE.plusDays(100));
    final SafetyAlert safetyAlert1 = safetyAlert(THIRD_ID_2, SOME_DATE.minusDays(100));
    final ChildClientEntityAwareDTO input = dto(client, safetyAlert0, safetyAlert1);

    // when + then
    checkRuleViolatedOnce(input, RULE_NAME);
  }
}
