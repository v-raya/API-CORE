package gov.ca.cwds.cms.data.access.service.impl.relationships;

import static org.junit.Assert.fail;

import gov.ca.cwds.cms.data.access.dto.ClientRelationshipAwareDTO;
import gov.ca.cwds.cms.data.access.service.BusinessValidationService;
import gov.ca.cwds.cms.data.access.service.impl.BaseDocToolRulesTest;
import gov.ca.cwds.cms.data.access.service.impl.ClientCoreService;
import gov.ca.cwds.cms.data.access.service.rules.ClientRelationshipDroolsConfiguration;
import gov.ca.cwds.data.legacy.cms.dao.ClientDao;
import gov.ca.cwds.data.legacy.cms.entity.Client;
import gov.ca.cwds.data.legacy.cms.entity.ClientRelationship;
import gov.ca.cwds.rest.exception.BusinessValidationException;
import java.time.LocalDate;
import org.junit.Before;
import org.mockito.Mock;

/**
 * @author CWDS TPT-3 Team
 */
public abstract class BaseDocToolRulesChildClientRelationshipTest extends BaseDocToolRulesTest {

  private String PRIMARY_ID = "0001112223";
  private String SECONDARY_ID = "0001112224";

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

  protected void checkRuleViolatedOnce(String ruleName) {
    try {
      runBusinessValidation(awareDTO);
      fail();
    } catch (BusinessValidationException e) {
      assertRuleViolatedOnce(ruleName, e);
    }
  }

  private void runBusinessValidation(ClientRelationshipAwareDTO awareDTO) {
    businessValidationService.runBusinessValidation(
        awareDTO, principal, ClientRelationshipDroolsConfiguration.INSTANCE);
  }

  protected void checkRuleSatisfied(String ruleName) {
    try {
      runBusinessValidation(awareDTO);
    } catch (BusinessValidationException e) {
      assertRuleSatisfied(ruleName, e);
    }
  }

  protected ClientRelationship getRelationship(LocalDate startDate, LocalDate endDate) {
    ClientRelationship clientRelationship = new ClientRelationship();
    clientRelationship.setStartDate(startDate);
    clientRelationship.setEndDate(endDate);

    Client client = new Client();
    client.setIdentifier(PRIMARY_ID);

    Client clientSecondary = new Client();
    clientSecondary.setIdentifier(SECONDARY_ID);

    clientRelationship.setPrimaryClient(client);
    clientRelationship.setSecondaryClient(clientSecondary);

    return clientRelationship;
  }
}
