package gov.ca.cwds.cms.data.access.service.impl;

import gov.ca.cwds.data.legacy.cms.entity.PlacementHome;
import gov.ca.cwds.rest.exception.BusinessValidationException;
import org.junit.Test;

/**
 * @author TPT-2
 */
public class R03960Test extends BaseDocToolRulesTest {


  private void preparePlacementHome(int facilityType, String licenseCode, String facilityName) {
    PlacementHome placementHome = new PlacementHome();
    placementHome.setFacilityType((short) facilityType);
    placementHome.setLicensrCd(licenseCode);
    placementHome.setFacltyNm(facilityName);
    placementHomeEntityAwareDTO.setEntity(placementHome);
  }

  @Test(expected = BusinessValidationException.class)
  public void testFosterFamilyHome() throws Exception {
    preparePlacementHome(1416, "CT", null);
    placementHomeService.runBusinessValidation(placementHomeEntityAwareDTO);
  }

  @Test
  public void testFosterFamilyHomeWithName() throws Exception {
    preparePlacementHome(1416, "CT", "name");
    placementHomeService.runBusinessValidation(placementHomeEntityAwareDTO);
  }

  @Test
  public void testFosterFamilyHomeOtherLicenceCode() throws Exception {
    preparePlacementHome(1416, "AA", null);
    placementHomeService.runBusinessValidation(placementHomeEntityAwareDTO);
  }

  @Test(expected = BusinessValidationException.class)
  public void testResourceFamilyHome() throws Exception {
    preparePlacementHome(6914, "CT", null);
    placementHomeService.runBusinessValidation(placementHomeEntityAwareDTO);
  }

  @Test
  public void testResourceFamilyHomeWithName() throws Exception {
    preparePlacementHome(6914, "CT", "name");
    placementHomeService.runBusinessValidation(placementHomeEntityAwareDTO);
  }

  @Test
  public void testResourceFamilyHomeOtherLicenceCode() throws Exception {
    preparePlacementHome(6914, "BB", null);
    placementHomeService.runBusinessValidation(placementHomeEntityAwareDTO);
  }

  @Test
  public void testOtherFamilyHome() throws Exception {
    preparePlacementHome(-1, "CT", null);
    placementHomeService.runBusinessValidation(placementHomeEntityAwareDTO);
  }
}