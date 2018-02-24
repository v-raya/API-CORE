package gov.ca.cwds.cms.data.access.service.impl.placementHome;

import gov.ca.cwds.data.legacy.cms.entity.SubstituteCareProvider;
import org.junit.Before;
import org.junit.Test;

public class R02974Test extends BaseDocToolRulesPlacementHomeTest {

    @Before
    public void before() {
        placementHomeEntityAwareDTO.getEntity().setNewlicUpd("N");
    }

    @Test
    public void testAbsentSCPViolation() {
        placementHomeEntityAwareDTO.getEntity().setPrimarySubstituteCareProvider(null);
        placementHomeEntityAwareDTO.getEntity().setFacilityType((short) 0);
        check("R-02974");
    }

    @Test
    public void testAbsentSCPForFacilityType6716() {
        placementHomeEntityAwareDTO.getEntity().setPrimarySubstituteCareProvider(null);
        placementHomeEntityAwareDTO.getEntity().setFacilityType((short) 6716);
        assertValid("R-02974");
    }

    @Test
    public void testSCPIsPresent() {
        placementHomeEntityAwareDTO.getEntity().setPrimarySubstituteCareProvider(new SubstituteCareProvider());
        placementHomeEntityAwareDTO.getEntity().setFacilityType((short) 0);
        assertValid("R-02974");
    }

    @Test
    public void testAbsentSCPForFacilityType6915AndTrnhsgIndIsN() {
        placementHomeEntityAwareDTO.getEntity().setPrimarySubstituteCareProvider(null);
        placementHomeEntityAwareDTO.getEntity().setFacilityType((short) 6915);
        placementHomeEntityAwareDTO.getEntity().setTrnhsgInd("N");
        assertValid("R-02974");
    }
}
