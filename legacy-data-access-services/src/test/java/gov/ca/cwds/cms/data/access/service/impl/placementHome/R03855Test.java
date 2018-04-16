package gov.ca.cwds.cms.data.access.service.impl.placementHome;

import static org.junit.Assert.fail;

import gov.ca.cwds.cms.data.access.service.rules.PlacementHomeDroolsConfiguration;
import gov.ca.cwds.data.legacy.cms.entity.PlacementHome;
import gov.ca.cwds.rest.exception.BusinessValidationException;
import org.junit.Test;

/** Created by TPT2 on 12/11/2017. */
public class R03855Test extends BaseDocToolRulesPlacementHomeTest {

  @Test
  public void testMaxAgeInvalid() throws Exception {
    PlacementHome placementHome = new PlacementHome();
    placementHome.setAgeToNo((short) 27);
    try {
      runBusinessValidation(placementHome);
      fail();
    } catch (BusinessValidationException e) {
      assert e.getValidationDetailsList()
          .stream()
          .anyMatch(issueDetails -> issueDetails.getCode().equals("R-03855"));
    }
  }

  private void runBusinessValidation(PlacementHome placementHome) {
    placementHomeEntityAwareDTO.setEntity(placementHome);
    businessValidationService.runDataProcessing(
        placementHomeEntityAwareDTO,
        principal,
        PlacementHomeDroolsConfiguration.DATA_PROCESSING_INSTANCE);
    businessValidationService.runBusinessValidation(
        placementHomeEntityAwareDTO, principal, PlacementHomeDroolsConfiguration.INSTANCE);
  }
}
