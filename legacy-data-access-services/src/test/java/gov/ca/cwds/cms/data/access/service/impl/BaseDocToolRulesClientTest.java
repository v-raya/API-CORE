package gov.ca.cwds.cms.data.access.service.impl;

import static org.junit.Assert.fail;

import gov.ca.cwds.cms.data.access.dto.ClientEntityAwareDTO;
import gov.ca.cwds.data.legacy.cms.entity.Client;
import gov.ca.cwds.drools.DroolsException;
import gov.ca.cwds.rest.exception.BusinessValidationException;
import org.junit.Before;

public abstract class BaseDocToolRulesClientTest extends BaseDocToolRulesTest {

  protected ClientCoreServiceImpl clientCoreService;

  @Before
  public void setUp() {
    clientCoreService = new ClientCoreServiceImpl();
    clientCoreService.setDroolsService(droolsService);
  }

  @Override
  public String getPrivilege() {
    return "";
  }

  protected void runBusinessValidation(Client client) throws DroolsException {
    clientCoreService.runBusinessValidation(clientEntityAwareDTO(client), principal);
  }

  protected void checkRuleViolatedOnce(Client client, String ruleName) throws DroolsException {
    try {
      runBusinessValidation(client);
      fail();
    } catch (BusinessValidationException e) {
      assertRuleViolatedOnce(ruleName, e);
    }
  }

  protected void checkRuleSatisfied(Client client, String ruleName) throws DroolsException {
    try {
      runBusinessValidation(client);
    } catch (BusinessValidationException e) {
      assertRuleSatisfied(ruleName, e);
    }
  }

  private ClientEntityAwareDTO clientEntityAwareDTO(Client client) {
    ClientEntityAwareDTO clientEntityAwareDTO = new ClientEntityAwareDTO();
    clientEntityAwareDTO.setEntity(client);
    return clientEntityAwareDTO;
  }
}
