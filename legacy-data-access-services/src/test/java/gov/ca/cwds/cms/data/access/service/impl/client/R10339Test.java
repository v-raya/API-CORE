package gov.ca.cwds.cms.data.access.service.impl.client;

import static gov.ca.cwds.cms.data.access.service.impl.client.ClientTestUtil.SOME_DATE;

import gov.ca.cwds.cms.data.access.dto.ChildClientEntityAwareDTO;
import gov.ca.cwds.data.legacy.cms.entity.ChildClient;
import gov.ca.cwds.data.legacy.cms.entity.Client;
import gov.ca.cwds.data.legacy.cms.entity.HealthReferral;
import java.time.LocalDate;
import java.util.Arrays;
import org.junit.Test;

public class R10339Test extends BaseDocToolRulesClientImplementationTest {

  private static final String RULE_NAME = "R-10339";

  @Test
  public void testTerminationStartDateGtBirthDate() throws Exception {
    checkRuleSatisfied(
        dto(childClient(SOME_DATE), healthReferral(childClient(SOME_DATE), SOME_DATE.plusDays(1))),
        RULE_NAME);
  }

  @Test
  public void testTerminationStartDateLtBirthDate() throws Exception {
    checkRuleViolatedOnce(
        dto(childClient(SOME_DATE), healthReferral(childClient(SOME_DATE), SOME_DATE.minusDays(1))),
        RULE_NAME);
  }

  @Test
  public void testTwoTerminationStartDateLtBirthDate() throws Exception {
    checkRuleViolated(dto(childClient(SOME_DATE),
        healthReferral(childClient(SOME_DATE), SOME_DATE.minusDays(1)),
        healthReferral(childClient(SOME_DATE), SOME_DATE.minusDays(2))),
        RULE_NAME, 2);
  }

  @Test
  public void testTerminationStartDateEqBirthDate() throws Exception {
    checkRuleSatisfied(dto(childClient(SOME_DATE), healthReferral(childClient(SOME_DATE), SOME_DATE)),
        RULE_NAME);
  }

  @Test
  public void testBirthDateNull() throws Exception {
    checkRuleSatisfied(dto(childClient(SOME_DATE), healthReferral(childClient(SOME_DATE), SOME_DATE)),
        RULE_NAME);
  }

  public static HealthReferral healthReferral(ChildClient parent, LocalDate date) {
    HealthReferral healthReferral = new HealthReferral();
    healthReferral.setChildClient(parent);
    healthReferral.setReferralDate(date);
    return healthReferral;
  }

  public static ChildClient childClient(LocalDate birthDate) {
    ChildClient childClient = new ChildClient();
    childClient.setIdentifier("ChIlDS40FG");
    childClient.setBirthDate(birthDate);
    return childClient;
  }

  public static ChildClientEntityAwareDTO dto(Client client, HealthReferral... healthReferrals) {
    ChildClientEntityAwareDTO dto = new ChildClientEntityAwareDTO();
    dto.setEntity(client);
    dto.getHealthReferrals().addAll(Arrays.asList(healthReferrals));
    return dto;
  }
}
