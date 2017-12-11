package gov.ca.cwds.cms.data.access.service.impl;

import gov.ca.cwds.cms.data.access.dto.PlacementHomeEntityAwareDTO;
import gov.ca.cwds.data.legacy.cms.entity.PlacementHome;
import gov.ca.cwds.drools.DroolsException;
import gov.ca.cwds.drools.DroolsService;
import gov.ca.cwds.rest.exception.BusinessValidationException;
import org.junit.Before;
import org.junit.Test;

/**
 * Created by TPT2 on 12/11/2017.
 */
public class R_03960_Test {
  private PlacementHomeServiceImpl service;
  private PlacementHomeEntityAwareDTO placementHomeEntityAwareDTO;

  @Before
  public void setUp() {
    service = new PlacementHomeServiceImpl();
    service.setDroolsService(new DroolsService());
    placementHomeEntityAwareDTO = new PlacementHomeEntityAwareDTO("1");
  }

  @Test
  public void testMaxAgeInvalid() throws Exception {
    PlacementHome placementHome = new PlacementHome();
    placementHome.setAgeToNo((short) 27);
    try {
      runBusinessValidation(placementHome);
      throw new Exception("Business Validation exception must be thrown");
    } catch (BusinessValidationException e) {
      assert e.getValidationDetailsList().stream().anyMatch(issueDetails -> issueDetails.getCode().equals("R - 03855"));
    }
  }

  @Test
  public void testMaxAgeValid() throws Exception {
    PlacementHome placementHome = new PlacementHome();
    placementHome.setAgeToNo((short) 23);
    try {
      runBusinessValidation(placementHome);
    } catch (BusinessValidationException e) {
      assert e.getValidationDetailsList().stream().noneMatch(issueDetails -> issueDetails.getCode().equals("R - 03855"));
    }
  }


  private void runBusinessValidation(PlacementHome placementHome) throws DroolsException {
    placementHomeEntityAwareDTO.setEntity(placementHome);
    service.runBusinessValidation(placementHomeEntityAwareDTO);
  }
}
