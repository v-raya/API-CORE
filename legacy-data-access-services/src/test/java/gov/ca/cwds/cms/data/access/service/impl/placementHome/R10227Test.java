package gov.ca.cwds.cms.data.access.service.impl.placementHome;

import gov.ca.cwds.data.legacy.cms.entity.OtherAdultsInPlacementHome;
import org.junit.Test;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Collections;

public class R10227Test extends BaseDocToolRulesPlacementHomeTest {
    @Test
    public void testIdentifiedDateValid() {
        OtherAdultsInPlacementHome otherAdultsInPlacementHome = new OtherAdultsInPlacementHome();
        otherAdultsInPlacementHome.setBirthDt(LocalDate.now().minus(20, ChronoUnit.YEARS));
        otherAdultsInPlacementHome.setIdentfdDt(LocalDate.now().minus(10, ChronoUnit.YEARS));
        placementHomeEntityAwareDTO.getEntity().setOtherAdultsInPlacementHomes(Collections.singletonList(otherAdultsInPlacementHome));
        assertValid();
    }

    @Test
    public void testIdentifiedDateInvalid() {
        OtherAdultsInPlacementHome otherAdultsInPlacementHome = new OtherAdultsInPlacementHome();
        otherAdultsInPlacementHome.setBirthDt(LocalDate.now().minus(20, ChronoUnit.YEARS));
        otherAdultsInPlacementHome.setIdentfdDt(LocalDate.now().minus(30, ChronoUnit.YEARS));
        placementHomeEntityAwareDTO.getEntity().setOtherAdultsInPlacementHomes(Collections.singletonList(otherAdultsInPlacementHome));
        check("R-10227");
    }
}
