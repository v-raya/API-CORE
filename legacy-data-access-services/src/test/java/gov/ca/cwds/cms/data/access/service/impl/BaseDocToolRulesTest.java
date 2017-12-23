package gov.ca.cwds.cms.data.access.service.impl;

import gov.ca.cwds.cms.data.access.dto.PlacementHomeEntityAwareDTO;
import gov.ca.cwds.drools.DroolsException;
import gov.ca.cwds.drools.DroolsService;
import gov.ca.cwds.rest.exception.BusinessValidationException;
import gov.ca.cwds.security.realm.PerryAccount;
import static org.junit.Assert.fail;
import org.junit.Before;

/**
 * @author CWDS CALS API Team
 */

public abstract class BaseDocToolRulesTest {

  protected PlacementHomeServiceImpl service;
  protected PlacementHomeEntityAwareDTO placementHomeEntityAwareDTO;
  protected PerryAccount principal;

  @Before
  public void setUp() {
    service = new PlacementHomeServiceImpl();
    service.setDroolsService(new DroolsService());
    placementHomeEntityAwareDTO = HappyPathHelper
        .prepareSuccessfulPlacementHomeEntityAwareDTO();
    principal = HappyPathHelper.getHappyPathPrincipal();
  }

  protected void check(String ruleCode) {
    try {
      service.runBusinessValidation(placementHomeEntityAwareDTO, principal);
      fail();
    } catch (BusinessValidationException e) {
      assert e.getValidationDetailsList().stream().filter(issueDetails -> issueDetails.getCode().equals(ruleCode)).count() == 1;
    }
    catch (DroolsException e) {
      fail(e.getMessage());
    }
  }

}
