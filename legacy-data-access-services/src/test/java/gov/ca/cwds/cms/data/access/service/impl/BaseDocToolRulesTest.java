package gov.ca.cwds.cms.data.access.service.impl;

import static org.junit.Assert.assertTrue;

import gov.ca.cwds.cms.data.access.dto.PlacementHomeEntityAwareDTO;
import gov.ca.cwds.drools.DroolsException;
import gov.ca.cwds.drools.DroolsService;
import gov.ca.cwds.rest.exception.BusinessValidationException;
import gov.ca.cwds.security.realm.PerryAccount;
import static org.junit.Assert.fail;
import org.junit.Before;

/** @author CWDS CALS API Team */
public abstract class BaseDocToolRulesTest {

  protected PlacementHomeServiceImpl placementHomeService;
  protected ClientCoreServiceImpl clientCoreService;
  protected DroolsService droolsService;

  protected PlacementHomeEntityAwareDTO placementHomeEntityAwareDTO;
  protected PerryAccount principal;

  @Before
  public void setUp() {
    droolsService = new DroolsService();

    placementHomeService = new PlacementHomeServiceImpl();
    placementHomeService.setDroolsService(droolsService);

    clientCoreService = new ClientCoreServiceImpl();
    clientCoreService.setDroolsService(droolsService);

    placementHomeEntityAwareDTO = HappyPathHelper.prepareSuccessfulPlacementHomeEntityAwareDTO();
    principal = HappyPathHelper.getPlacementFacilityHappyPathPrincipal();
  }

  protected void checkPlacementHomeService(String ruleCode) {
    try {
      placementHomeService.runBusinessValidation(placementHomeEntityAwareDTO, principal);
      fail();
    } catch (BusinessValidationException e) {
      assert e.getValidationDetailsList().stream().filter(issueDetails -> issueDetails.getCode().equals(ruleCode)).count() == 1;
    }
    catch (DroolsException e) {
      fail(e.getMessage());
    }
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
