package gov.ca.cwds.cms.data.access.service.impl.client;

import gov.ca.cwds.cms.data.access.dto.ChildClientEntityAwareDTO;
import gov.ca.cwds.data.legacy.cms.entity.ChildClient;
import gov.ca.cwds.data.legacy.cms.entity.ParentalRightsTermination;
import java.time.LocalDate;
import java.util.Arrays;
import org.junit.Ignore;
import org.junit.Test;

public class R00769Test extends BaseDocToolRulesClientImplementationTest {

  private static final String RULE_NAME = "R-00769";
  private static final LocalDate SOME_DATE = LocalDate.of(2018, 1, 8);

  @Test
  public void testTerminationStartDateGtBirthDate() throws Exception {
    checkRuleSatisfied(dto(childClient(SOME_DATE), termination(SOME_DATE.plusDays(1))), RULE_NAME);
  }

  @Test
  public void testTerminationStartDateLtBirthDate() throws Exception {
    checkRuleViolatedOnce(dto(childClient(SOME_DATE), termination(SOME_DATE.minusDays(1))), RULE_NAME);
  }

  @Ignore
  @Test
  public void testTwoTerminationStartDateLtBirthDate() throws Exception {
    checkRuleViolated(dto(childClient(SOME_DATE),
        termination(SOME_DATE.minusDays(1)),
        termination(SOME_DATE.minusDays(2))),
        RULE_NAME, 2);
  }

  @Test
  public void testTerminationStartDateEqBirthDate() throws Exception {
    checkRuleSatisfied(dto(childClient(SOME_DATE), termination(SOME_DATE)), RULE_NAME);
  }

  @Test
  public void testBirthDateNull() throws Exception {
    checkRuleSatisfied(dto(childClient(null), termination(SOME_DATE)), RULE_NAME);
  }

  private static ChildClient childClient(LocalDate birthDate) {
    ChildClient childClient = new ChildClient();
    childClient.setBirthDate(birthDate);
    return childClient;
  }

  private static ParentalRightsTermination termination(LocalDate date) {
    ParentalRightsTermination termination = new ParentalRightsTermination();
    termination.setCourtCaseNumber("");
    termination.setDate(date);
    return termination;
  }

  private static ChildClientEntityAwareDTO dto(ChildClient childClient, ParentalRightsTermination... terminations) {
    ChildClientEntityAwareDTO dto = new ChildClientEntityAwareDTO();
    dto.setEntity(childClient);
    dto.setParentalRightsTerminations(Arrays.asList(terminations));
    return dto;
  }
}
