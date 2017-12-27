package gov.ca.cwds.cms.data.access.service.impl;

import org.junit.Test;

/**
 * @author CWDS CALS API Team
 */

public class PlacementHomeHappyPathTest extends BaseDocToolRulesPlacementHomeTest {

  @Test
  public void testHappyPath() throws Exception {
    placementHomeService.runBusinessValidation(placementHomeEntityAwareDTO, principal);
  }

}
