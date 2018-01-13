package gov.ca.cwds.cms.data.access.service.impl;

import static org.junit.Assert.fail;

import gov.ca.cwds.data.legacy.cms.entity.EmergencyContactDetail;
import gov.ca.cwds.drools.DroolsException;
import gov.ca.cwds.rest.exception.BusinessValidationException;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * Created by TPT2
 */
public class R09753Test extends BaseDocToolRulesPlacementHomeTest {

  @Before
  public void before() {
    EmergencyContactDetail emergencyContactDetail = new EmergencyContactDetail();
    emergencyContactDetail.setStreetNm("Street Name");
    emergencyContactDetail.setStreetNo("#1");
    placementHomeEntityAwareDTO.setEmergencyContactDetail(emergencyContactDetail);
  }

  @Test
  public void testRulePass() throws Exception {
    assertValid();
  }

  @Test
  public void testStreetNameIsEmpty() throws Exception {
    assertInvalid(() -> {
       placementHomeEntityAwareDTO.getEmergencyContactDetail().setStreetNm(" ");
    });
  }

  @Test
  public void testStreetNameIsNull() throws Exception {
    assertInvalid(() -> {
      placementHomeEntityAwareDTO.getEmergencyContactDetail().setStreetNm(null);
    });
  }

  @Test
  public void testStreetNameAndNoAreEmpty() throws Exception {
    assertValid(() -> {
      placementHomeEntityAwareDTO.getEmergencyContactDetail().setStreetNm(" ");
      placementHomeEntityAwareDTO.getEmergencyContactDetail().setStreetNo(" ");
    });
  }

  @Test
  public void testStreetNameAndNoAreNull() throws Exception {
    assertValid(() -> {
      placementHomeEntityAwareDTO.getEmergencyContactDetail().setStreetNm(null);
      placementHomeEntityAwareDTO.getEmergencyContactDetail().setStreetNo(null);
    });
  }

  private void assertInvalid(Runnable testCase) throws DroolsException {
    try {
      testCase.run();
      placementHomeService.runBusinessValidation(placementHomeEntityAwareDTO, principal);
      fail();
    } catch (BusinessValidationException e) {
      Assert.assertEquals("R-09753", e.getValidationDetailsList().iterator().next().getCode());
    }
  }

  private void assertValid(Runnable testCase) throws DroolsException {
    try {
      testCase.run();
      placementHomeService.runBusinessValidation(placementHomeEntityAwareDTO, principal);
    } catch (Exception e) {
      e.printStackTrace();
      fail();
    }
  }

}
