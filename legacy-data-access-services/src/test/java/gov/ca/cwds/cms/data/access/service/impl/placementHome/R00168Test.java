package gov.ca.cwds.cms.data.access.service.impl.placementHome;

import static org.junit.Assert.fail;

import gov.ca.cwds.cms.data.access.service.impl.placementHome.BaseDocToolRulesPlacementHomeTest;
import gov.ca.cwds.drools.DroolsException;
import gov.ca.cwds.rest.exception.BusinessValidationException;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * Created by TPT2 on 12/11/2017.
 */
public class R00168Test extends BaseDocToolRulesPlacementHomeTest {

  @Before
  public void before() {
    placementHomeEntityAwareDTO.getEntity().setPyeTelNo("5555555555");
    placementHomeEntityAwareDTO.getEntity().setPyeFstnm("firstName");
    placementHomeEntityAwareDTO.getEntity().setPyeLstnm("LastName");
    placementHomeEntityAwareDTO.getEntity().setPstreetNm("StreetName");
    placementHomeEntityAwareDTO.getEntity().setpCityNm("CityName");
    placementHomeEntityAwareDTO.getEntity().setpZipNo("12345");
    placementHomeEntityAwareDTO.getEntity().setPyZipSfx("1230");
    placementHomeEntityAwareDTO.getEntity().setPayeeStateCode((short)1);
  }

  @Test
  public void testRulePass() throws Exception {
    assertValid(() -> {});
  }

  @Test
  public void testFirstNameNull() throws Exception {
    assertInvalid(() -> {
      placementHomeEntityAwareDTO.getEntity().setPyeFstnm(null);
    });
  }

  @Test
  public void testLastNameNull() throws Exception {
    assertInvalid(() -> {
      placementHomeEntityAwareDTO.getEntity().setPyeLstnm(null);
    });
  }

  @Test
  public void testStreetNameNull() throws Exception {
    assertInvalid(() -> {
      placementHomeEntityAwareDTO.getEntity().setPstreetNm(null);
    });
  }

  @Test
  public void testCityNameNull() throws Exception {
    assertInvalid(() -> {
      placementHomeEntityAwareDTO.getEntity().setpCityNm(null);
    });
  }

  @Test
  public void testStateCodeNullWhenZipPresent() throws Exception {
    assertInvalid(() -> {
      placementHomeEntityAwareDTO.getEntity().setPayeeStateCode(null);
    });
  }

  private void assertInvalid(Runnable testCase) throws DroolsException {
    try {
      testCase.run();
      placementHomeService.runBusinessValidation(placementHomeEntityAwareDTO, principal);
      fail();
    } catch (BusinessValidationException e) {
      Assert.assertEquals("R-00168", e.getValidationDetailsList().iterator().next().getCode());
    }
  }

  private void assertValid(Runnable testCase) {
    try {
      testCase.run();
      placementHomeService.runBusinessValidation(placementHomeEntityAwareDTO, principal);
    } catch (Exception e) {
      e.printStackTrace();
      fail();
    }
  }

}
