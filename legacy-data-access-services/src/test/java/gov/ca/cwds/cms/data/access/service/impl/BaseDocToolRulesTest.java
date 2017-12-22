package gov.ca.cwds.cms.data.access.service.impl;

import gov.ca.cwds.cms.data.access.dto.PlacementHomeEntityAwareDTO;
import gov.ca.cwds.drools.DroolsService;
import gov.ca.cwds.security.realm.PerryAccount;
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

}
