package gov.ca.cwds.cms.data.access.service.impl.placementHome;

import gov.ca.cwds.cms.data.access.service.rules.PlacementHomeDroolsConfiguration;
import org.junit.Test;

/** @author CWDS CALS API Team */
public class PlacementHomeHappyPathTest extends BaseDocToolRulesPlacementHomeTest {

  @Test
  public void testHappyPath() throws Exception {
    businessValidationService.runBusinessValidation(placementHomeEntityAwareDTO, principal,
        PlacementHomeDroolsConfiguration.INSTANCE);
  }
}
