package gov.ca.cwds.cms.data.access.service.impl;

import static org.junit.Assert.assertTrue;

import gov.ca.cwds.cms.data.access.dto.PlacementHomeEntityAwareDTO;
import gov.ca.cwds.drools.DroolsService;
import gov.ca.cwds.security.realm.PerryAccount;
import org.junit.Before;

/** @author CWDS CALS API Team */
public abstract class BaseDocToolRulesTest {

  protected PlacementHomeServiceImpl placementHomeService;
  protected ClientCoreServiceImpl clientCoreService;

  protected PlacementHomeEntityAwareDTO placementHomeEntityAwareDTO;
  protected PerryAccount principal;

  private DroolsService droolsService;

  @Before
  public void setUp() {
    droolsService = new DroolsService();

    placementHomeService = new PlacementHomeServiceImpl();
    placementHomeService.setDroolsService(droolsService);

    clientCoreService = new ClientCoreServiceImpl();
    clientCoreService.setDroolsService(droolsService);

    placementHomeEntityAwareDTO = EntityAwareHelper.prepareSuccessfulPlacementHomeEntityAwareDTO();
    principal = HappyPathHelper.getHappyPathPrincipal();
  }

  public static void assertRuleSatisfied(String ruleName, BusinessValidationException e) {
    assertTrue(
        e.getValidationDetailsList()
            .stream()
            .noneMatch(issueDetails -> issueDetails.getCode().equals(ruleName)));
  }

  public static void assertRuleViolated(String ruleName, BusinessValidationException e) {
    assertTrue(
        e.getValidationDetailsList()
            .stream()
            .anyMatch(issueDetails -> issueDetails.getCode().equals(ruleName)));
  }
}
