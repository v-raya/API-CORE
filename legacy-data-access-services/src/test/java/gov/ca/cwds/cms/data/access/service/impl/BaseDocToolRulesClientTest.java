package gov.ca.cwds.cms.data.access.service.impl;

import static org.junit.Assert.fail;

import gov.ca.cwds.cms.data.access.dto.ClientEntityAwareDTO;
import gov.ca.cwds.data.legacy.cms.entity.Client;
import gov.ca.cwds.drools.DroolsException;
import gov.ca.cwds.rest.exception.BusinessValidationException;
import org.junit.Before;

public abstract class BaseDocToolRulesClientTest extends BaseDocToolRulesTest {

    protected ClientCoreServiceImpl clientCoreService;
    protected ClientEntityAwareDTO clientEntityAwareDTO;

    @Before
    public void setUp() {
        clientCoreService = new ClientCoreServiceImpl();
        clientCoreService.setDroolsService(droolsService);
        clientEntityAwareDTO = new ClientEntityAwareDTO();
    }

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

    protected void checkRuleSatisfied(ClientEntityAwareDTO dto, String ruleName) throws DroolsException {
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

    protected void checkRuleViolatedOnce(ClientEntityAwareDTO dto, String ruleName) throws DroolsException {
        try {
            runBusinessValidation(dto);
            fail();
        } catch (BusinessValidationException e) {
            assertRuleViolatedOnce(ruleName, e);
        }
    }

    protected void checkRuleViolatedOnce(Client client, String ruleName) throws DroolsException {
        clientEntityAwareDTO.setEntity(client);
        checkRuleViolatedOnce(ruleName);
    }

    protected void checkRuleSatisfied(Client client, String ruleName) throws DroolsException {
        clientEntityAwareDTO.setEntity(client);
        checkRuleSatisfied(ruleName);
    }

    private void runBusinessValidation(ClientEntityAwareDTO clientEntityAwareDTO) throws DroolsException {
        clientCoreService.runBusinessValidation(clientEntityAwareDTO, principal);
    }
}
