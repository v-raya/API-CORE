package gov.ca.cwds.cms.data.access.service.impl.client;

import static gov.ca.cwds.cms.data.access.service.impl.client.ClientTestUtil.SOME_DATE;

import gov.ca.cwds.cms.data.access.dto.ChildClientEntityAwareDTO;
import gov.ca.cwds.data.legacy.cms.entity.ChildClient;
import gov.ca.cwds.data.legacy.cms.entity.Client;
import gov.ca.cwds.data.legacy.cms.entity.HealthScreening;
import gov.ca.cwds.data.legacy.cms.entity.syscodes.HealthScreeningType;
import java.time.LocalDate;
import java.util.Arrays;
import org.junit.Test;

public class R10326Test extends BaseDocToolRulesClientImplementationTest {

  private static final String RULE_NAME = "R-10326";

  @Test
  public void testScreeningDateGtBirthDate() throws Exception {
    ChildClient childClient = childClient(SOME_DATE);
    checkRuleSatisfied(
        dto(childClient, healthScreening(childClient, SOME_DATE.plusDays(1))),
        RULE_NAME);
  }

  @Test
  public void testScreeningDateLtBirthDate() throws Exception {
    ChildClient childClient = childClient(SOME_DATE);
    checkRuleViolatedOnce(
        dto(childClient, healthScreening(childClient, SOME_DATE.minusDays(1))),
        RULE_NAME);
  }

  @Test
  public void testTwoScreeningDatesLtBirthDate() throws Exception {
    ChildClient childClient = childClient(SOME_DATE);
    checkRuleViolated(dto(childClient,
        healthScreening(childClient, SOME_DATE.minusDays(1)),
        healthScreening(childClient, SOME_DATE.minusDays(2))),
        RULE_NAME, 2);
  }

  @Test
  public void testScreeningDateEqBirthDate() throws Exception {
    ChildClient childClient = childClient(SOME_DATE);
    checkRuleSatisfied(dto(childClient, healthScreening(childClient, SOME_DATE)),
        RULE_NAME);
  }

  @Test
  public void testScreeningDateNull() throws Exception {
    ChildClient childClient = childClient(SOME_DATE);
    checkRuleSatisfied(dto(childClient, healthScreening(childClient, null)),
        RULE_NAME);
  }

  @Test
  public void testBirthDateNull() throws Exception {
    ChildClient childClient = childClient(null);
    checkRuleSatisfied(dto(childClient, healthScreening(childClient, SOME_DATE)),
        RULE_NAME);
  }

  public static HealthScreening healthScreening(ChildClient parent, LocalDate date) {
    HealthScreening healthScreening = new HealthScreening();
    healthScreening.setChildClient(parent);

    HealthScreeningType healthScreeningType = new HealthScreeningType();
    healthScreeningType.setSystemId((short)6609);
    healthScreening.setHealthScreeningType(healthScreeningType);

    healthScreening.setScreeningDate(date);
    return healthScreening;
  }

  public static ChildClient childClient(LocalDate birthDate) {
    ChildClient childClient = new ChildClient();
    childClient.setIdentifier("ChIlDS40FG");
    childClient.setBirthDate(birthDate);
    return childClient;
  }

  public static ChildClientEntityAwareDTO dto(Client client, HealthScreening... healthScreenings) {
    ChildClientEntityAwareDTO dto = new ChildClientEntityAwareDTO();
    dto.setEntity(client);
    dto.getHealthScreenings().addAll(Arrays.asList(healthScreenings));
    return dto;
  }
}
