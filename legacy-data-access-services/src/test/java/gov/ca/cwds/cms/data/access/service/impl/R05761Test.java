package gov.ca.cwds.cms.data.access.service.impl;

import static gov.ca.cwds.cms.data.access.Constants.StaffPersonPrivileges.USR_PRV_SOC158_APPLICATION;
import static org.junit.Assert.fail;

import gov.ca.cwds.drools.DroolsException;
import gov.ca.cwds.rest.exception.BusinessValidationException;
import org.junit.Assert;
import org.junit.Test;

/**
 * Created by TPT2 on 12/11/2017.
 */
public class R05761Test extends BaseDocToolRulesPlacementHomeTest {

  @Test
  public void testFacilityNameNull() throws Exception {
    checkR05761(() -> {
      placementHomeEntityAwareDTO.getEntity().setFacltyNm(null);
    });
  }

  @Test
  public void testFacilityNameEmpty() throws Exception {
    checkR05761(() -> {
      placementHomeEntityAwareDTO.getEntity().setFacltyNm("");
    });
  }

  @Test
  public void testStateCodeNull() throws Exception {
    checkR05761(() -> {
      placementHomeEntityAwareDTO.getEntity().setStateCode(null);
    });
  }

  @Test
  public void testStateCodeZero() throws Exception {
    checkR05761(() -> {
      placementHomeEntityAwareDTO.getEntity().setStateCode((short)0);
    });
  }

  private void checkR05761(Runnable testCase) throws DroolsException {
    try {
      principal.getPrivileges().add(USR_PRV_SOC158_APPLICATION);
      testCase.run();
      placementHomeService.runDataProcessing(placementHomeEntityAwareDTO, principal);
      placementHomeService.runBusinessValidation(placementHomeEntityAwareDTO, principal);
      fail();
    } catch (BusinessValidationException e) {
      Assert.assertEquals("R-05761", e.getValidationDetailsList().iterator().next().getCode());
    }
  }

}
