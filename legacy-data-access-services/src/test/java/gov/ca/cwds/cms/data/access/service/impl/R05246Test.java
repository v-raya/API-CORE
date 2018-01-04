package gov.ca.cwds.cms.data.access.service.impl;

import gov.ca.cwds.data.legacy.cms.entity.EmergencyContactDetail;
import org.junit.Test;

/**
 * Created by TPT2.
 */
public class R05246Test extends BaseDocToolRulesPlacementHomeTest {

  public static final String INVALID_PHONE_NUMBER = "0555555555";
  public static final String VALID_PHONE_NUMBER = "5555555555";

  @Test
  public void testPlacementHomeBckTelNoStartsZero() {
    placementHomeEntityAwareDTO.getEntity().setBckTelNo(INVALID_PHONE_NUMBER);
    check("R-05246");
  }

  @Test
  public void testPlacementHomePrmTelNoStartsZero() {
    placementHomeEntityAwareDTO.getEntity().setPrmTelNo(INVALID_PHONE_NUMBER);
    check("R-05246");
  }

  @Test
  public void testPlacementHomeLaPPhNoStartsZero() {
    placementHomeEntityAwareDTO.getEntity().setLaPPhNo(INVALID_PHONE_NUMBER);
    check("R-05246");
  }

  @Test
  public void testPlacementHomePyeTelNoStartsZero() {
    placementHomeEntityAwareDTO.getEntity().setPyeTelNo(INVALID_PHONE_NUMBER);
    check("R-05246");
  }


  @Test
  public void testPlacementHomeFaxNoStartsZero() {
    placementHomeEntityAwareDTO.getEntity().setFaxNo(INVALID_PHONE_NUMBER);
    check("R-05246");
  }


  @Test
  public void testEmergencyContactAltPhNoStartsZero() {
    placementHomeEntityAwareDTO.setEmergencyContactDetail(new EmergencyContactDetail());
    placementHomeEntityAwareDTO.getEmergencyContactDetail().setAltPhNo(INVALID_PHONE_NUMBER);
    check("R-05246");
  }

  @Test
  public void testEmergencyContactPriPhNoStartsZero() {
    placementHomeEntityAwareDTO.setEmergencyContactDetail(new EmergencyContactDetail());
    placementHomeEntityAwareDTO.getEmergencyContactDetail().setPriPhNo(INVALID_PHONE_NUMBER);
    check("R-05246");

  }

  @Test
  public void testValidInput() {
    placementHomeEntityAwareDTO.getEntity().setBckTelNo(VALID_PHONE_NUMBER);
    placementHomeEntityAwareDTO.getEntity().setPrmTelNo(VALID_PHONE_NUMBER);
    placementHomeEntityAwareDTO.getEntity().setLaPPhNo(VALID_PHONE_NUMBER);
    placementHomeEntityAwareDTO.getEntity().setPyeTelNo(VALID_PHONE_NUMBER);
    placementHomeEntityAwareDTO.getEntity().setFaxNo(VALID_PHONE_NUMBER);

    placementHomeEntityAwareDTO.setEmergencyContactDetail(new EmergencyContactDetail());
    placementHomeEntityAwareDTO.getEmergencyContactDetail().setAltPhNo(VALID_PHONE_NUMBER);
    placementHomeEntityAwareDTO.getEmergencyContactDetail().setPriPhNo(VALID_PHONE_NUMBER);

    assertValid();
  }


}
