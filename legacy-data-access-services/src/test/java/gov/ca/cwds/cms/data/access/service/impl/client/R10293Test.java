package gov.ca.cwds.cms.data.access.service.impl.client;

import static gov.ca.cwds.cms.data.access.service.impl.client.ClientTestUtil.SOME_DATE;
import static gov.ca.cwds.cms.data.access.service.impl.client.ClientTestUtil.client;
import static gov.ca.cwds.cms.data.access.service.impl.client.ClientTestUtil.dto;
import static gov.ca.cwds.cms.data.access.service.impl.client.ClientTestUtil.schoolOriginHistory;

import gov.ca.cwds.cms.data.access.dto.ChildClientEntityAwareDTO;
import gov.ca.cwds.data.legacy.cms.entity.Client;
import gov.ca.cwds.data.legacy.cms.entity.SchoolOriginHistory;
import org.junit.Test;

/**
 * @author CWDS TPT-3 Team
 */
public class R10293Test extends BaseDocToolRulesClientImplementationTest {

  private static final String RULE_NAME = "R-10293";

  @Test
  public void rule10293_satisfied_whenNoDasHistories() {
    // given
    final Client client = client(SOME_DATE);
    final ChildClientEntityAwareDTO input = dto(client);

    // when + then
    checkRuleSatisfied(input, RULE_NAME);
  }

  @Test
  public void rule10293_satisfied_whenSingleDasHistoryOnDOB() {
    // given
    final Client client = client(SOME_DATE);
    final SchoolOriginHistory history = schoolOriginHistory(SOME_DATE);
    final ChildClientEntityAwareDTO input = dto(client, history);

    // when + then
    checkRuleSatisfied(input, RULE_NAME);
  }

  @Test
  public void rule10293_satisfied_whenSingleDasHistoryAfterDOB() {
    // given
    final Client client = client(SOME_DATE);
    final SchoolOriginHistory history = schoolOriginHistory(SOME_DATE.plusDays(100));
    final ChildClientEntityAwareDTO input = dto(client, history);

    // when + then
    checkRuleSatisfied(input, RULE_NAME);
  }

  @Test
  public void rule10293_satisfied_whenTwoDasHistoriesAfterDOB() {
    // given
    final Client client = client(SOME_DATE);
    final SchoolOriginHistory history0 = schoolOriginHistory(SOME_DATE.plusDays(1));
    final SchoolOriginHistory history1 = schoolOriginHistory(SOME_DATE.plusDays(100));
    final ChildClientEntityAwareDTO input = dto(client, history0, history1);

    // when + then
    checkRuleSatisfied(input, RULE_NAME);
  }

  @Test
  public void rule10293_violated_whenSingleDasHistoryBeforeDOB() {
    // given
    final Client client = client(SOME_DATE);
    final SchoolOriginHistory history = schoolOriginHistory(SOME_DATE.minusDays(1));
    final ChildClientEntityAwareDTO input = dto(client, history);

    // when + then
    checkRuleViolatedOnce(input, RULE_NAME);
  }

  @Test
  public void rule10293_violatedTwice_whenTwoDasHistoriesBeforeDOB() {
    // given
    final Client client = client(SOME_DATE);
    final SchoolOriginHistory history0 = schoolOriginHistory(SOME_DATE.minusDays(1));
    final SchoolOriginHistory history1 = schoolOriginHistory(SOME_DATE.minusDays(100));
    final ChildClientEntityAwareDTO input = dto(client, history0, history1);

    // when + then
    checkRuleViolated(input, RULE_NAME, 2);
  }

  @Test
  public void rule10293_violated_whenOneOfTwoDasHistoriesIsBeforeDOB() {
    // given
    final Client client = client(SOME_DATE);
    final SchoolOriginHistory history0 = schoolOriginHistory(SOME_DATE.plusDays(100));
    final SchoolOriginHistory history1 = schoolOriginHistory(SOME_DATE.minusDays(100));
    final ChildClientEntityAwareDTO input = dto(client, history0, history1);

    // when + then
    checkRuleViolatedOnce(input, RULE_NAME);
  }
}

