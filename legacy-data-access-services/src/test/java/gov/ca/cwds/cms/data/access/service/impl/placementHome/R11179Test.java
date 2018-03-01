package gov.ca.cwds.cms.data.access.service.impl.placementHome;

import gov.ca.cwds.data.legacy.cms.entity.PlacementFacilityTypeHistory;
import gov.ca.cwds.data.legacy.cms.entity.PlacementHome;
import gov.ca.cwds.drools.DroolsException;
import org.junit.Test;

public class R11179Test extends BaseDocToolRulesPlacementHomeTest {

    @Test
    public void testPlacementFacilityTypeHistoryCreation() throws DroolsException {
        PlacementHome placementHome = placementHomeEntityAwareDTO.getEntity();
        placementHome.setFacilityType((short) 1);
        placementHomeService.runDataProcessing(placementHomeEntityAwareDTO, principal);
        assert placementHome.getPlacementFacilityTypeHistory() != null;
        assert placementHome.getPlacementFacilityTypeHistory().size() == 1;
        PlacementFacilityTypeHistory placementFacilityTypeHistory = placementHome.getPlacementFacilityTypeHistory().get(0);
        assert placementFacilityTypeHistory.getPlacementFacilityType().equals(placementHome.getFacilityType());
        assert placementFacilityTypeHistory.getCreationTimestamp() != null;
        assert placementFacilityTypeHistory.getStartTimestamp() != null;
    }
}
