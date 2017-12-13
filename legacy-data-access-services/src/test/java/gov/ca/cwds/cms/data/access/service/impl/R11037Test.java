package gov.ca.cwds.cms.data.access.service.impl;

import static gov.ca.cwds.cms.data.access.service.impl.EntityAwareHelper.USR_PRV_RESOURCE_MGMT_PLACEMENT_FACILITY_MAINT;
import static org.junit.Assert.fail;

import gov.ca.cwds.rest.exception.BusinessValidationException;
import org.junit.Assert;
import org.junit.Test;

/**
 * Created by TPT2 on 12/11/2017.
 */
public class R11037Test extends BaseDocToolRulesTest {

  @Test
  public void testDoesNotHavePrivilege() throws Exception {
    try {
      //In the Resource Management Application, a staff person with the privilege
      // of Resource Mgmt Placement Facility Maint can add
      // a Resource Family Home
      placementHomeEntityAwareDTO.getPerryAccount().getPrivileges().remove(
          USR_PRV_RESOURCE_MGMT_PLACEMENT_FACILITY_MAINT);
      service.runBusinessValidation(placementHomeEntityAwareDTO);
      fail();
    } catch (BusinessValidationException e) {
      Assert.assertEquals("R - 11037", e.getValidationDetailsList().iterator().next().getCode());
    }
  }

}
