package gov.ca.cwds.cms.data.access.service.impl.client;

import gov.ca.cwds.cms.data.access.dto.ChildClientEntityAwareDTO;
import gov.ca.cwds.data.legacy.cms.entity.ChildClient;
import gov.ca.cwds.data.legacy.cms.entity.Client;
import gov.ca.cwds.data.legacy.cms.entity.ParentalRightsTermination;
import java.time.LocalDate;
import java.util.Arrays;
import org.junit.Test;

public class R00769Test extends BaseDocToolRulesClientImplementationTest {

  private static final String RULE_NAME = "R-00769";
  private static final LocalDate SOME_DATE = LocalDate.of(2018, 1, 8);
  private static final String CHILD_CLIENT_ID = "CCCCCCCCCC";

  @Test
  public void testTerminationStartDateGtBirthDate() throws Exception {
    checkRuleSatisfied(
        dto(childClient(SOME_DATE), termination(parent("AAAAAAAAAA"), SOME_DATE.plusDays(1))),
        RULE_NAME);
  }

  @Test
  public void testTerminationStartDateLtBirthDate() throws Exception {
    checkRuleViolatedOnce(
        dto(childClient(SOME_DATE), termination(parent("AAAAAAAAAA"), SOME_DATE.minusDays(1))),
        RULE_NAME);
  }

  @Test
  public void testTwoTerminationStartDateLtBirthDate() throws Exception {
    checkRuleViolated(dto(childClient(SOME_DATE),
        termination(parent("AAAAAAAAAA"), SOME_DATE.minusDays(1)),
        termination(parent("BBBBBBBBBB"), SOME_DATE.minusDays(2))),
        RULE_NAME, 2);
  }

  @Test
  public void testTerminationStartDateEqBirthDate() throws Exception {
    checkRuleSatisfied(dto(childClient(SOME_DATE), termination(parent("AAAAAAAAAA"), SOME_DATE)),
        RULE_NAME);
  }

  @Test
  public void testBirthDateNull() throws Exception {
    checkRuleSatisfied(dto(childClient(null), termination(parent("AAAAAAAAAA"), SOME_DATE)),
        RULE_NAME);
  }

  private static ChildClient childClient(LocalDate birthDate) {
    ChildClient childClient = new ChildClient();
    childClient.setIdentifier(CHILD_CLIENT_ID);
    childClient.setBirthDate(birthDate);
    return childClient;
  }

  private static Client parent(String id) {
    Client client = new Client();
    client.setIdentifier(id);
    return client;
  }

  private static ParentalRightsTermination termination(Client parent, LocalDate date) {
    ParentalRightsTermination termination = new ParentalRightsTermination();
    termination.setChild(childClient(SOME_DATE));
    termination.setParent(parent);
    termination.setDate(date);
    return termination;
  }

  private static ChildClientEntityAwareDTO dto(ChildClient childClient,
      ParentalRightsTermination... terminations) {
    ChildClientEntityAwareDTO dto = new ChildClientEntityAwareDTO();
    dto.setEntity(childClient);
    dto.getParentalRightsTerminations().addAll(Arrays.asList(terminations));
    return dto;
  }
}
