package gov.ca.cwds.cms.data.access.service.impl.client;

import static org.junit.Assert.fail;

import gov.ca.cwds.cms.data.access.dto.ClientEntityAwareDTO;
import gov.ca.cwds.cms.data.access.service.BusinessValidationService;
import gov.ca.cwds.cms.data.access.service.impl.ClientCoreService;
import gov.ca.cwds.cms.data.access.service.impl.BaseDocToolRulesTest;
import gov.ca.cwds.cms.data.access.service.rules.ClientDroolsConfiguration;
import gov.ca.cwds.data.legacy.cms.entity.Client;
import gov.ca.cwds.drools.DroolsException;
import gov.ca.cwds.rest.exception.BusinessValidationException;
import org.junit.Before;

public abstract class BaseDocToolRulesClientTest<T extends Client, E extends ClientEntityAwareDTO> extends
    BaseDocToolRulesTest {

  private ClientCoreService clientCoreService;
  E clientEntityAwareDTO;
  protected BusinessValidationService businessValidationService;

  @Before
  public void setUp() {
    businessValidationService = new BusinessValidationService(droolsService);
    clientCoreService = getClientCoreService();
    clientEntityAwareDTO = getAwareDTO();
  }

  protected abstract ClientCoreService getClientCoreService();

  @Override
  public String getPrivilege() {
    return "";
  }

  protected void checkRuleSatisfied(String ruleName) throws DroolsException {
    try {
      runBusinessValidation(clientEntityAwareDTO);
    } catch (BusinessValidationException e) {
      assertRuleSatisfied(ruleName, e);
    }
  }

  protected void checkRuleSatisfied(ClientEntityAwareDTO dto, String ruleName)
      throws DroolsException {
    try {
      runBusinessValidation(dto);
    } catch (BusinessValidationException e) {
      assertRuleSatisfied(ruleName, e);
    }
  }

  protected void checkRuleViolatedOnce(String ruleName) throws DroolsException {
    try {
      runBusinessValidation(clientEntityAwareDTO);
      fail();
    } catch (BusinessValidationException e) {
      assertRuleViolatedOnce(ruleName, e);
    }
  }

  protected void checkRuleViolatedOnce(ClientEntityAwareDTO dto, String ruleName)
      throws DroolsException {
    try {
      runBusinessValidation(dto);
      fail();
    } catch (BusinessValidationException e) {
      assertRuleViolatedOnce(ruleName, e);
    }
  }

  protected void checkRuleViolated(ClientEntityAwareDTO dto, String ruleName, int times)
      throws DroolsException {
    try {
      runBusinessValidation(dto);
      fail();
    } catch (BusinessValidationException e) {
      assertRuleViolated(ruleName, e, times);
    }
  }

  protected void checkRuleViolatedOnce(T client, String ruleName) throws DroolsException {
    clientEntityAwareDTO.setEntity(client);
    checkRuleViolatedOnce(ruleName);
  }

  protected void checkRuleSatisfied(T client, String ruleName) throws DroolsException {
    clientEntityAwareDTO.setEntity(client);
    checkRuleSatisfied(ruleName);
  }

  private void runBusinessValidation(ClientEntityAwareDTO clientEntityAwareDTO)
      throws DroolsException {
    businessValidationService.runBusinessValidation(clientEntityAwareDTO, principal,
        ClientDroolsConfiguration.INSTANCE);
  }

  protected abstract E getAwareDTO();
}
