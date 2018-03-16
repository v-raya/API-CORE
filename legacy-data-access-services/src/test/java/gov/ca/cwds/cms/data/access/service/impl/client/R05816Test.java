package gov.ca.cwds.cms.data.access.service.impl.client;

import static gov.ca.cwds.data.legacy.cms.entity.enums.Gender.FEMALE;
import static gov.ca.cwds.data.legacy.cms.entity.enums.Gender.MALE;
import static gov.ca.cwds.data.legacy.cms.entity.enums.Gender.UNKNOWN;

import gov.ca.cwds.cms.data.access.dto.ClientEntityAwareDTO;
import gov.ca.cwds.data.legacy.cms.entity.Client;
import gov.ca.cwds.data.legacy.cms.entity.enums.Gender;
import org.junit.Test;

public class R05816Test extends BaseDocToolRulesClientImplementationTest {

  private static final String RULE_NAME = "R-05816";

  @Test
  public void testMale() throws Exception {
    checkRuleSatisfied(dto(client(MALE)), RULE_NAME);
  }

  @Test
  public void testFemale() throws Exception {
    checkRuleSatisfied(dto(client(FEMALE)), RULE_NAME);
  }

  @Test
  public void testUnknown() throws Exception {
    checkRuleViolatedOnce(dto(client(UNKNOWN)), RULE_NAME);
  }

  public static Client client(Gender gender) {
    Client client = new Client();
    client.setIdentifier("ChIlDS40FG");
    client.setGender(gender);
    return client;
  }

  public static ClientEntityAwareDTO dto(Client client) {
    ClientEntityAwareDTO dto = new ClientEntityAwareDTO();
    dto.setEntity(client);
    return dto;
  }
}
