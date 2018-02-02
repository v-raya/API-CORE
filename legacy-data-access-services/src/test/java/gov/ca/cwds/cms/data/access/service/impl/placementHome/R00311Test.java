package gov.ca.cwds.cms.data.access.service.impl.placementHome;

import org.junit.Test;

public class R00311Test extends BaseDocToolRulesPlacementHomeTest {
    @Test
    public void testCAGovernmentEntityTypeValid() {
        placementHomeEntityAwareDTO.getEntity().setStateCode((short)1828);
        placementHomeEntityAwareDTO.getEntity().setGvrEntc((short)1);
        assertValid("R-00311");
    }

    @Test
    public void testCAGovernmentEntityTypeInvalid() {
        placementHomeEntityAwareDTO.getEntity().setStateCode((short)1828);
        placementHomeEntityAwareDTO.getEntity().setGvrEntc(null);
        check("R-00311");
    }

    @Test
    public void testNONCAGovernmentEntityTypeValid() {
        placementHomeEntityAwareDTO.getEntity().setStateCode((short)0);
        placementHomeEntityAwareDTO.getEntity().setGvrEntc(null);
        assertValid("R-00311");
    }

    @Test
    public void testNONCAGovernmentEntityTypeInvalid() {
        placementHomeEntityAwareDTO.getEntity().setStateCode((short)1);
        placementHomeEntityAwareDTO.getEntity().setGvrEntc((short)1);
        check("R-00311");
    }
}
