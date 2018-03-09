package gov.ca.cwds.cms.data.access.service.impl.placementHome;

import static gov.ca.cwds.cms.data.access.Constants.StaffPersonPrivileges.USR_PRV_RESOURCE_MGMT_PLACEMENT_FACILITY_MAINT;
import static org.junit.Assert.fail;

import gov.ca.cwds.cms.data.access.service.impl.placementHome.BaseDocToolRulesPlacementHomeTest;
import gov.ca.cwds.cms.data.access.service.rules.PlacementHomeDroolsConfiguration;
import gov.ca.cwds.rest.exception.BusinessValidationException;
import org.junit.Assert;
import org.junit.Test;

/** Created by TPT2 on 12/12/2017. */
public class R11037Test extends BaseDocToolRulesPlacementHomeTest {

  @Test
  public void testDoesNotHavePrivilege() throws Exception {
    try {
      principal.getPrivileges().remove(USR_PRV_RESOURCE_MGMT_PLACEMENT_FACILITY_MAINT);
      businessValidationService.runDataProcessing(
          placementHomeEntityAwareDTO,
          principal,
          PlacementHomeDroolsConfiguration.DATA_PROCESSING_INSTANCE);
      businessValidationService.runBusinessValidation(
          placementHomeEntityAwareDTO, principal, PlacementHomeDroolsConfiguration.INSTANCE);
      fail();
    } catch (BusinessValidationException e) {
      Assert.assertEquals("R-11037", e.getValidationDetailsList().iterator().next().getCode());
    }
  }
}
