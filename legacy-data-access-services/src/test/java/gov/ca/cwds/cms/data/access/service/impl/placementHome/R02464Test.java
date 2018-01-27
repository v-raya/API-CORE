package gov.ca.cwds.cms.data.access.service.impl.placementHome;

import gov.ca.cwds.cms.data.access.service.impl.placementHome.BaseDocToolRulesPlacementHomeTest;
import gov.ca.cwds.drools.DroolsException;
import org.junit.Assert;
import org.junit.Test;

/**
 * Created by TPT2 on 12/11/2017.
 */
public class R02464Test extends BaseDocToolRulesPlacementHomeTest {

  @Test
  public void testCountyNull() throws Exception {
    checkR02464(() -> {
      placementHomeEntityAwareDTO.getEntity().setGvrEntc(null);
      principal.setCountyCode("21");
      principal.setCountyCwsCode("1088");
    });
    Assert.assertTrue(placementHomeEntityAwareDTO.getEntity().getGvrEntc() == 1088);
  }

    @Test
    public void testCountyZeroCode() throws Exception {
        checkR02464(() -> {
            placementHomeEntityAwareDTO.getEntity().setGvrEntc((short)0);
            principal.setCountyCode("22");
            principal.setCountyCwsCode("1089");
        });
        Assert.assertTrue(placementHomeEntityAwareDTO.getEntity().getGvrEntc() == 1089);
    }

    @Test
    public void testSateOfCalifornia() throws Exception {
        checkR02464(() -> {
            placementHomeEntityAwareDTO.getEntity().setGvrEntc((short)0);
            principal.setCountyCode("99");
            principal.setCountyCwsCode("1126");
        });
        Assert.assertTrue(placementHomeEntityAwareDTO.getEntity().getGvrEntc() == (short)0);
    }

    @Test
    public void testCountyNotEmpty() throws Exception {
        checkR02464(() -> {
            placementHomeEntityAwareDTO.getEntity().setGvrEntc((short)1085);
            principal.setCountyCode("22");
            principal.setCountyCwsCode("1089");
        });
        Assert.assertTrue(placementHomeEntityAwareDTO.getEntity().getGvrEntc() == (short)1085);
    }


    private void checkR02464(Runnable testCase) throws DroolsException {
      testCase.run();
      placementHomeService.runDataProcessing(placementHomeEntityAwareDTO, principal);
      placementHomeService.runBusinessValidation(placementHomeEntityAwareDTO, principal);
  }

}
