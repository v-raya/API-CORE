package gov.ca.cwds.cms.data.access.service.impl;

import org.junit.Test;

/**
 * @author CWDS CALS API Team
 */

public class HappyPathTest extends BaseDocToolRulesTest {

  @Test
  public void testHappyPath() throws Exception {
    placementHomeService.runBusinessValidation(placementHomeEntityAwareDTO, principal);
  }

}
