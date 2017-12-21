package gov.ca.cwds.cms.data.access.service.impl;

import gov.ca.cwds.cms.data.access.dto.PlacementHomeEntityAwareDTO;
import gov.ca.cwds.drools.DroolsException;
import gov.ca.cwds.drools.DroolsService;
import gov.ca.cwds.rest.exception.BusinessValidationException;
import org.junit.Before;

import static org.junit.Assert.fail;

/**
 * @author CWDS CALS API Team
 */

public abstract class BaseDocToolRulesTest {

  protected PlacementHomeServiceImpl service;
  protected PlacementHomeEntityAwareDTO placementHomeEntityAwareDTO;

  @Before
  public void setUp() {
    service = new PlacementHomeServiceImpl();
    service.setDroolsService(new DroolsService());
    placementHomeEntityAwareDTO = EntityAwareHelper
        .prepareSuccessfulPlacementHomeEntityAwareDTO();

  }

  protected void check(String ruleCode) {
    try {
      service.runBusinessValidation(placementHomeEntityAwareDTO);
      fail();
    } catch (BusinessValidationException e) {
      assert e.getValidationDetailsList().stream().filter(issueDetails -> issueDetails.getCode().equals(ruleCode)).count() == 1;
    }
    catch (DroolsException e) {
      fail(e.getMessage());
    }
  }

}
