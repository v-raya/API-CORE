package gov.ca.cwds.cms.data.access.service.impl.placementHome;

import static org.junit.Assert.fail;

import gov.ca.cwds.cms.data.access.service.impl.placementHome.BaseDocToolRulesPlacementHomeTest;
import gov.ca.cwds.cms.data.access.service.rules.PlacementHomeDroolsConfiguration;
import gov.ca.cwds.data.legacy.cms.entity.EmergencyContactDetail;
import gov.ca.cwds.drools.DroolsException;
import gov.ca.cwds.rest.exception.BusinessValidationException;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/** Created by TPT2 */
public class R09753Test extends BaseDocToolRulesPlacementHomeTest {

  @Before
  public void before() {
    EmergencyContactDetail emergencyContactDetail = new EmergencyContactDetail();
    emergencyContactDetail.setStreetNm("Street Name");
    emergencyContactDetail.setStreetNo("#1");
    placementHomeEntityAwareDTO.setEmergencyContactDetail(emergencyContactDetail);
  }

  @Test
  public void testRulePass() {
    assertValid();
  }

  @Test
  public void testStreetNameIsEmpty() throws Exception {
    assertInvalid(
        () -> {
          placementHomeEntityAwareDTO.getEmergencyContactDetail().setStreetNm(" ");
        });
  }

  @Test
  public void testStreetNameIsNull() throws Exception {
    assertInvalid(
        () -> {
          placementHomeEntityAwareDTO.getEmergencyContactDetail().setStreetNm(null);
        });
  }

  @Test
  public void testStreetNameAndNoAreEmpty() {
    assertValid(
        () -> {
          placementHomeEntityAwareDTO.getEmergencyContactDetail().setStreetNm(" ");
          placementHomeEntityAwareDTO.getEmergencyContactDetail().setStreetNo(" ");
        });
  }

  @Test
  public void testStreetNameAndNoAreNull() {
    assertValid(
        () -> {
          placementHomeEntityAwareDTO.getEmergencyContactDetail().setStreetNm(null);
          placementHomeEntityAwareDTO.getEmergencyContactDetail().setStreetNo(null);
        });
  }

  private void assertInvalid(Runnable testCase) throws DroolsException {
    try {
      testCase.run();
      businessValidationService.runBusinessValidation(
          placementHomeEntityAwareDTO, principal, PlacementHomeDroolsConfiguration.INSTANCE);
      fail();
    } catch (BusinessValidationException e) {
      Assert.assertEquals("R-09753", e.getValidationDetailsList().iterator().next().getCode());
    }
  }

  private void assertValid(Runnable testCase) {
    try {
      testCase.run();
      businessValidationService.runBusinessValidation(
          placementHomeEntityAwareDTO, principal, PlacementHomeDroolsConfiguration.INSTANCE);
    } catch (Exception e) {
      e.printStackTrace();
      fail();
    }
  }
}
