package gov.ca.cwds.cms.data.access.service.impl.placementHome;

import gov.ca.cwds.cms.data.access.service.rules.PlacementHomeDroolsConfiguration;
import gov.ca.cwds.data.legacy.cms.entity.PlacementHome;
import gov.ca.cwds.drools.DroolsException;
import org.junit.Test;

public class R00195Test extends BaseDocToolRulesPlacementHomeTest {

  @Test
  public void testFosterFamilyHomeFacilityType() throws DroolsException {
    final PlacementHome placementHome = placementHomeEntityAwareDTO.getEntity();
    placementHome.setFacilityType((short) 1416);
    placementHome.setLicensrCd(null);
    businessValidationService.runDataProcessing(
        placementHomeEntityAwareDTO,
        principal,
        PlacementHomeDroolsConfiguration.DATA_PROCESSING_INSTANCE);
    assert placementHome.getLicensrCd().equals("CT");
  }

  @Test
  public void testResourceFamilyHomeFacilityType() throws DroolsException {
    final PlacementHome placementHome = placementHomeEntityAwareDTO.getEntity();
    placementHome.setFacilityType((short) 6914);
    placementHome.setLicensrCd(null);
    businessValidationService.runDataProcessing(
        placementHomeEntityAwareDTO,
        principal,
        PlacementHomeDroolsConfiguration.DATA_PROCESSING_INSTANCE);
    assert placementHome.getLicensrCd().equals("CT");
  }

  @Test
  public void testNALicensrCd() throws DroolsException {
    final PlacementHome placementHome = placementHomeEntityAwareDTO.getEntity();
    placementHome.setFacilityType(null);
    placementHome.setLicensrCd(null);
    businessValidationService.runDataProcessing(
        placementHomeEntityAwareDTO,
        principal,
        PlacementHomeDroolsConfiguration.DATA_PROCESSING_INSTANCE);
    assert placementHome.getLicensrCd().equals("NA");
  }
}
