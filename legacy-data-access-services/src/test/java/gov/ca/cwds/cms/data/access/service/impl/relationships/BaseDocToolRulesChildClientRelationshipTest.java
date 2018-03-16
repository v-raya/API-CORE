package gov.ca.cwds.cms.data.access.service.impl.relationships;

import static org.junit.Assert.fail;

import gov.ca.cwds.cms.data.access.dto.ClientRelationshipAwareDTO;
import gov.ca.cwds.cms.data.access.service.BusinessValidationService;
import gov.ca.cwds.cms.data.access.service.impl.BaseDocToolRulesTest;
import gov.ca.cwds.cms.data.access.service.impl.ClientCoreService;
import gov.ca.cwds.cms.data.access.service.impl.ClientRelationshipCoreService;
import gov.ca.cwds.cms.data.access.service.rules.ClientRelationshipDroolsConfiguration;
import gov.ca.cwds.data.legacy.cms.dao.ClientDao;
import gov.ca.cwds.drools.DroolsException;
import gov.ca.cwds.rest.exception.BusinessValidationException;
import org.junit.Before;
import org.mockito.Mock;

/**
 * @author CWDS TPT-3 Team
 */
public abstract class BaseDocToolRulesChildClientRelationshipTest extends BaseDocToolRulesTest {

  protected BusinessValidationService businessValidationService;
  protected ClientCoreService clientCoreService;
  protected ClientRelationshipAwareDTO awareDTO;

  @Mock
  private ClientDao clientDao;

  @Before
  public void setUp() {
    clientCoreService = new ClientCoreService(clientDao);
    businessValidationService = new BusinessValidationService(droolsService);
    awareDTO = new ClientRelationshipAwareDTO();
  }

  @Override
  public String getPrivilege() {
    return "";
  }

  protected void checkRuleViolatedOnce(String ruleName) throws DroolsException {
    try {
      runBusinessValidation(awareDTO);
      fail();
    } catch (BusinessValidationException e) {
      assertRuleViolatedOnce(ruleName, e);
    }
  }

  private void runBusinessValidation(ClientRelationshipAwareDTO awareDTO)
      throws DroolsException {
    businessValidationService.runBusinessValidation(
        awareDTO, principal, ClientRelationshipDroolsConfiguration.INSTANCE);
  }

  protected void checkRuleSatisfied(String ruleName) throws DroolsException {
    try {
      runBusinessValidation(awareDTO);
    } catch (BusinessValidationException e) {
      assertRuleSatisfied(ruleName, e);
    }
  }
}
