package gov.ca.cwds.cms.data.access.service.impl.placementHome;

import gov.ca.cwds.cms.data.access.service.rules.PlacementHomeDroolsConfiguration;
import org.junit.Assert;
import org.junit.Test;

/** Created by TPT2 on 12/11/2017. */
public class R04606Test extends BaseDocToolRulesPlacementHomeTest {

  @Test
  public void testAgeFromNull() throws Exception {
    checkR04606(
        () -> {
          placementHomeEntityAwareDTO.getEntity().setAgeFrmNo(null);
        });
    Assert.assertTrue(placementHomeEntityAwareDTO.getEntity().getAgeFrmNo() == 0);
  }

  @Test
  public void testAgeToNull() throws Exception {
    checkR04606(
        () -> {
          placementHomeEntityAwareDTO.getEntity().setAgeToNo(null);
        });
    Assert.assertTrue(placementHomeEntityAwareDTO.getEntity().getAgeToNo() == 18);
  }

  private void checkR04606(Runnable testCase) {
    testCase.run();
    businessValidationService.runDataProcessing(
        placementHomeEntityAwareDTO,
        principal,
        PlacementHomeDroolsConfiguration.DATA_PROCESSING_INSTANCE);
    businessValidationService.runBusinessValidation(
        placementHomeEntityAwareDTO, principal, PlacementHomeDroolsConfiguration.INSTANCE);
  }
}
