package gov.ca.cwds.cms.data.access.service.impl.client;

import gov.ca.cwds.cms.data.access.service.impl.client.BaseDocToolRulesChildClientImplementationTest;
import gov.ca.cwds.data.legacy.cms.entity.ChildClient;
import gov.ca.cwds.data.legacy.cms.entity.FCEligibility;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import org.junit.Test;

/** @author CWDS TPT-3 Team */
public class R11126Test extends BaseDocToolRulesChildClientImplementationTest {
  private static final String RULE_NAME = "R-11126";
  private static final String CLIENT_ID = "1234567891";
  private static LocalDate CLIENT_BIRTH_DAY = LocalDate.of(2011, 10, 11);

  @Test
  public void testValidListofEligibilities() throws Exception {
    clientEntityAwareDTO.setEntity(createClient(CLIENT_BIRTH_DAY));

    List<FCEligibility> fcEligibilities = createListOfValidChildClientEligibilities();
    clientEntityAwareDTO.getFcEligibilities().addAll(fcEligibilities);

    checkRuleSatisfied(RULE_NAME);
  }

  @Test
  public void testInValidListOfEligibilities() throws Exception {
    ChildClient client = createClient(CLIENT_BIRTH_DAY);
    clientEntityAwareDTO.setEntity(client);

    List<FCEligibility> fcEligibilities = createListOfInvalidChildClientEligibilities();
    clientEntityAwareDTO.getFcEligibilities().addAll(fcEligibilities);

    checkRuleViolatedOnce(RULE_NAME);
  }

  @Test
  public void testEmptyListOfEligibilities() throws Exception {
    clientEntityAwareDTO.setEntity(createClient(CLIENT_BIRTH_DAY));
    checkRuleSatisfied(RULE_NAME);
  }

  @Test
  public void testEmptyClientBirthDayNotEmptyEligibilities() throws Exception {
    clientEntityAwareDTO.setEntity(createClient(null));

    List<FCEligibility> fcEligibilities = createListOfValidChildClientEligibilities();
    clientEntityAwareDTO.getFcEligibilities().addAll(fcEligibilities);

    checkRuleSatisfied(RULE_NAME);
  }

  @Test
  public void testEmptyClientBirthDayAndEmptyEligibilities() throws Exception {
    clientEntityAwareDTO.setEntity(createClient(null));
    checkRuleSatisfied(RULE_NAME);
  }

  private ChildClient createClient(LocalDate birthDay) {
    ChildClient client = new ChildClient();
    client.setIdentifier("1234567890");
    client.setBirthDate(birthDay);
    client.setAfdcFcEligibilityIndicatorVar(true);

    return client;
  }

  private static List<FCEligibility> createListOfInvalidChildClientEligibilities() {
    FCEligibility eligibility1 = createFcEligibility(CLIENT_ID, CLIENT_BIRTH_DAY.minusDays(1));
    FCEligibility eligibility2 = createFcEligibility(CLIENT_ID, CLIENT_BIRTH_DAY.minusMonths(1));
    FCEligibility eligibility3 = createFcEligibility(CLIENT_ID, CLIENT_BIRTH_DAY.minusYears(1));

    return Arrays.asList(eligibility1, eligibility2, eligibility3);
  }

  private static List<FCEligibility> createListOfValidChildClientEligibilities() {
    FCEligibility eligibility1 = createFcEligibility(CLIENT_ID, CLIENT_BIRTH_DAY);
    FCEligibility eligibility2 = createFcEligibility(CLIENT_ID, CLIENT_BIRTH_DAY.plusMonths(1));
    FCEligibility eligibility3 = createFcEligibility(CLIENT_ID, CLIENT_BIRTH_DAY.plusDays(100));
    FCEligibility eligibility4 = createFcEligibility(CLIENT_ID, CLIENT_BIRTH_DAY.plusYears(1));

    return Arrays.asList(eligibility1, eligibility2, eligibility3, eligibility4);
  }

  private static FCEligibility createFcEligibility(String childClientId, LocalDate date) {
    FCEligibility eligibility = new FCEligibility();
    eligibility.setChildClientId(childClientId);
    eligibility.setDate(date);
    return eligibility;
  }
}
