package gov.ca.cwds.cms.data.access.service.impl.client;

import gov.ca.cwds.cms.data.access.dto.ClientEntityAwareDTO;
import gov.ca.cwds.data.legacy.cms.entity.Client;
import org.junit.Test;

public class R00736Test extends BaseDocToolRulesClientImplementationTest {

  private static final String RULE_NAME = "R-00736";

  @Test
  public void testNoDriverLicenseNumberWithNonZeroStateCode() throws Exception {
    checkRuleViolatedOnce(dto(null, (short) 12), RULE_NAME);
  }

  @Test
  public void testBlankDriverLicenseNumberWithNonZeroStateCode() throws Exception {
    checkRuleViolatedOnce(dto(" ", (short)12), RULE_NAME);
  }

  @Test
  public void testDriverLicenseNumberWithZeroStateCode() throws Exception {
    checkRuleViolatedOnce(dto("123", (short)0), RULE_NAME);
  }

  @Test
  public void testNoDriverLicenseNumberWithZeroStateCode() throws Exception {
    checkRuleSatisfied(dto(null, (short) 0), RULE_NAME);
  }

  @Test
  public void testBlankDriverLicenseNumberWithZeroStateCode() throws Exception {
    checkRuleSatisfied(dto(" ", (short)0), RULE_NAME);
  }

  @Test
  public void testDriverLicenseNumberWithNonZeroStateCode() throws Exception {
    checkRuleSatisfied(dto("123", (short)12), RULE_NAME);
  }

  private static ClientEntityAwareDTO dto(String driverLicenseNumber, short driverLicenseStateCode) {
    Client client = new Client();
    client.setDriverLicenseNumber(driverLicenseNumber);
    client.setDriverLicenseStateCode(driverLicenseStateCode);
    ClientEntityAwareDTO dto = new ClientEntityAwareDTO();
    dto.setEntity(client);
    return dto;
  }
}
