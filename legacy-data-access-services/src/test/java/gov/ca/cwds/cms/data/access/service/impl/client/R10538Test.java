package gov.ca.cwds.cms.data.access.service.impl.client;

import static gov.ca.cwds.cms.data.access.service.impl.client.ClientTestUtil.SOME_DATE;
import static gov.ca.cwds.cms.data.access.service.impl.client.ClientTestUtil.client;
import static gov.ca.cwds.cms.data.access.service.impl.client.ClientTestUtil.dasHistory;
import static gov.ca.cwds.cms.data.access.service.impl.client.ClientTestUtil.dto;

import gov.ca.cwds.cms.data.access.dto.ChildClientEntityAwareDTO;
import gov.ca.cwds.data.legacy.cms.entity.Client;
import gov.ca.cwds.data.legacy.cms.entity.DasHistory;
import org.junit.Test;

/**
 * @author CWDS TPT-3 Team
 */
public class R10538Test  extends BaseDocToolRulesClientImplementationTest {

  private static final String RULE_NAME = "R-10538";

  @Test
  public void rule10538_satisfied_whenNoDasHistories() {
    // given
    final Client client = client(SOME_DATE);
    final ChildClientEntityAwareDTO input = dto(client);

    // when + then
    checkRuleSatisfied(input, RULE_NAME);
  }

  @Test
  public void rule10538_satisfied_whenSingleDasHistoryOnDOB() {
    // given
    final Client client = client(SOME_DATE);
    final DasHistory dasHistory = dasHistory(SOME_DATE);
    final ChildClientEntityAwareDTO input = dto(client, dasHistory);

    // when + then
    checkRuleSatisfied(input, RULE_NAME);
  }

  @Test
  public void rule10538_satisfied_whenSingleDasHistoryAfterDOB() {
    // given
    final Client client = client(SOME_DATE);
    final DasHistory dasHistory = dasHistory(SOME_DATE.plusDays(100));
    final ChildClientEntityAwareDTO input = dto(client, dasHistory);

    // when + then
    checkRuleSatisfied(input, RULE_NAME);
  }

  @Test
  public void rule10538_satisfied_whenTwoDasHistoriesAfterDOB() {
    // given
    final Client client = client(SOME_DATE);
    final DasHistory dasHistory0 = dasHistory(SOME_DATE.plusDays(1));
    final DasHistory dasHistory1 = dasHistory(SOME_DATE.plusDays(100));
    final ChildClientEntityAwareDTO input = dto(client, dasHistory0, dasHistory1);

    // when + then
    checkRuleSatisfied(input, RULE_NAME);
  }

  @Test
  public void rule10538_violated_whenSingleDasHistoryBeforeDOB() {
    // given
    final Client client = client(SOME_DATE);
    final DasHistory dasHistory = dasHistory(SOME_DATE.minusDays(1));
    final ChildClientEntityAwareDTO input = dto(client, dasHistory);

    // when + then
    checkRuleViolatedOnce(input, RULE_NAME);
  }

  @Test
  public void rule10538_violatedTwice_whenTwoDasHistoriesBeforeDOB() {
    // given
    final Client client = client(SOME_DATE);
    final DasHistory dasHistory0 = dasHistory(SOME_DATE.minusDays(1));
    final DasHistory dasHistory1 = dasHistory(SOME_DATE.minusDays(100));
    final ChildClientEntityAwareDTO input = dto(client, dasHistory0, dasHistory1);

    // when + then
    checkRuleViolated(input, RULE_NAME, 2);
  }

  @Test
  public void rule10538_violated_whenOneOfTwoDasHistoriesIsBeforeDOB() {
    // given
    final Client client = client(SOME_DATE);
    final DasHistory dasHistory0 = dasHistory(SOME_DATE.plusDays(100));
    final DasHistory dasHistory1 = dasHistory(SOME_DATE.minusDays(100));
    final ChildClientEntityAwareDTO input = dto(client, dasHistory0, dasHistory1);

    // when + then
    checkRuleViolatedOnce(input, RULE_NAME);
  }
}

