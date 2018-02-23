package gov.ca.cwds.cms.data.access.service.impl.client;

import static gov.ca.cwds.cms.data.access.service.impl.client.ClientTestUtil.childClient;
import static gov.ca.cwds.cms.data.access.service.impl.client.ClientTestUtil.dto;
import static gov.ca.cwds.cms.data.access.service.impl.client.ClientTestUtil.paternityDetail;

import java.time.LocalDate;
import org.junit.Test;

public class R00772_2Test extends BaseDocToolRulesClientImplementationTest {

  private static final String RULE_NAME = "R-00772";
  private static final LocalDate SOME_DATE = LocalDate.of(2018, 1, 8);
  private static final String SOME_ID = "AAAAAAAAAA";
  private static final String SOME_ID_2 = "BBBBBBBBBB";

  @Test
  public void testPaternityTestDateAndBirthDateAreNulls() throws Exception {
    checkRuleSatisfied(
        dto(childClient(), paternityDetail(SOME_ID, null)),
        RULE_NAME);
  }

  @Test
  public void testBirthDateIsNull() throws Exception {
    checkRuleSatisfied(dto(childClient(), paternityDetail(SOME_ID, SOME_DATE)),
        RULE_NAME);
  }

  @Test
  public void testPaternityTestDateIsNull() throws Exception {
    checkRuleSatisfied(
        dto(childClient(SOME_DATE), paternityDetail(SOME_ID, null)),
        RULE_NAME);
  }

  @Test
  public void testPaternityTestDateGtBirthDate() throws Exception {
    checkRuleSatisfied(
        dto(childClient(SOME_DATE), paternityDetail(SOME_ID, SOME_DATE.plusDays(1))),
        RULE_NAME);
  }

  @Test
  public void testPaternityTestDateLtBirthDate() throws Exception {
    checkRuleViolatedOnce(
        dto(childClient(SOME_DATE), paternityDetail(SOME_ID, SOME_DATE.minusDays(1))),
        RULE_NAME);
  }

  @Test
  public void testTwoPaternityTestDatesLtBirthDate() throws Exception {
    checkRuleViolated(dto(childClient(SOME_DATE),
        paternityDetail(SOME_ID, SOME_DATE.minusDays(1)),
        paternityDetail(SOME_ID_2, SOME_DATE.minusDays(2))),
        RULE_NAME, 2);
  }

  @Test
  public void testPaternityTestDateEqBirthDate() throws Exception {
    checkRuleSatisfied(dto(childClient(SOME_DATE), paternityDetail(SOME_ID, SOME_DATE)),
        RULE_NAME);
  }
}
