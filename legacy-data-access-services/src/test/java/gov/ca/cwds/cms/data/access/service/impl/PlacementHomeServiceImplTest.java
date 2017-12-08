package gov.ca.cwds.cms.data.access.service.impl;

import gov.ca.cwds.cms.data.access.dto.PlacementHomeEntityAwareDTO;
import gov.ca.cwds.data.legacy.cms.entity.PlacementHome;
import gov.ca.cwds.drools.DroolsException;
import gov.ca.cwds.drools.DroolsService;
import gov.ca.cwds.rest.exception.BusinessValidationException;
import org.junit.Before;
import org.junit.Test;

/**
 * @author TPT-2
 */
public class PlacementHomeServiceImplTest {

    private PlacementHomeServiceImpl service;
    private PlacementHomeEntityAwareDTO placementHomeEntityAwareDTO;

    @Before
    public void setUp() {
        service = new PlacementHomeServiceImpl();
        service.setDroolsService(new DroolsService());
        placementHomeEntityAwareDTO = new PlacementHomeEntityAwareDTO("1");
    }

    private PlacementHome getPlacementHome(int facilityType, String licenseCode, String facilityName) {
        PlacementHome placementHome = new PlacementHome();
        placementHome.setFacilityType((short)facilityType);
        placementHome.setLicensrCd(licenseCode);
        placementHome.setFacltyNm(facilityName);
        return placementHome;
    }

    @Test(expected = BusinessValidationException.class)
    public void testFosterFamilyHome() throws Exception {

        PlacementHome placementHome = getPlacementHome(1416, "CT", null);
        runBusinessValidation(placementHome);
    }

    private void runBusinessValidation(PlacementHome placementHome) throws DroolsException {
        placementHomeEntityAwareDTO.setEntity(placementHome);
        service.runBusinessValidation(placementHomeEntityAwareDTO);
    }

    @Test
    public void testFosterFamilyHomeWithName() throws Exception {
        PlacementHome placementHome = getPlacementHome(1416, "CT", "name");
        runBusinessValidation(placementHome);    }

    @Test
    public void testFosterFamilyHomeOtherLicenceCode() throws Exception {
        PlacementHome placementHome = getPlacementHome(1416, "AA", null);
        runBusinessValidation(placementHome);
    }

    @Test(expected = BusinessValidationException.class)
    public void testResourceFamilyHome() throws Exception {
        PlacementHome placementHome = getPlacementHome(6914, "CT", null);
        runBusinessValidation(placementHome);
    }

    @Test
    public void testResourceFamilyHomeWithName() throws Exception {
        PlacementHome placementHome = getPlacementHome(6914, "CT", "name");
        runBusinessValidation(placementHome);
    }

    @Test
    public void testResourceFamilyHomeOtherLicenceCode() throws Exception {
        PlacementHome placementHome = getPlacementHome(6914, "BB", null);
        runBusinessValidation(placementHome);
    }

    @Test
    public void testOtherFamilyHome() throws Exception {
        PlacementHome placementHome = getPlacementHome(-1, "CT", null);
        runBusinessValidation(placementHome);
    }

}