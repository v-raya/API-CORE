package gov.ca.cwds.cms.data.access.service.impl.placementHome;

import gov.ca.cwds.cms.data.access.dto.OtherAdultInHomeEntityAwareDTO;
import gov.ca.cwds.cms.data.access.dto.OtherChildInHomeEntityAwareDTO;
import gov.ca.cwds.data.legacy.cms.entity.OtherAdultsInPlacementHome;
import gov.ca.cwds.data.legacy.cms.entity.OtherChildrenInPlacementHome;
import gov.ca.cwds.data.legacy.cms.entity.PlacementHome;
import org.junit.Test;

import java.util.Collections;

public class R04966Test extends BaseDocToolRulesPlacementHomeTest {

    @Test
    public void testOtherAdultsNameValid() {
        OtherAdultsInPlacementHome otherAdultsInPlacementHome = new OtherAdultsInPlacementHome();
        otherAdultsInPlacementHome.setOthAdltnm(" A v f ");
        OtherAdultInHomeEntityAwareDTO otherAdultInHomeEntityAwareDTO = new OtherAdultInHomeEntityAwareDTO();
        otherAdultInHomeEntityAwareDTO.setEntity(otherAdultsInPlacementHome);
        placementHomeEntityAwareDTO.setOtherAdultInHomeParameterObjects(Collections.singletonList(otherAdultInHomeEntityAwareDTO));
        assertValid("R-04966");
    }

    @Test
    public void testOtherAdultsNameInvalid() {
        OtherAdultsInPlacementHome otherAdultsInPlacementHome = new OtherAdultsInPlacementHome();
        otherAdultsInPlacementHome.setOthAdltnm("Valid2 Nam31 Jr. 1");
        OtherAdultInHomeEntityAwareDTO otherAdultInHomeEntityAwareDTO = new OtherAdultInHomeEntityAwareDTO();
        otherAdultInHomeEntityAwareDTO.setEntity(otherAdultsInPlacementHome);
        placementHomeEntityAwareDTO.setOtherAdultInHomeParameterObjects(Collections.singletonList(otherAdultInHomeEntityAwareDTO));
        check("R-04966");
    }

    @Test
    public void testOtherChildrenNameValid() {
        OtherChildrenInPlacementHome otherChildrenInPlacementHome = new OtherChildrenInPlacementHome();
        otherChildrenInPlacementHome.setOthchldNm("Valid2 Nam31 Jr. 1a");
        OtherChildInHomeEntityAwareDTO otherChildInHomeEntityAwareDTO = new OtherChildInHomeEntityAwareDTO();
        otherChildInHomeEntityAwareDTO.setEntity(otherChildrenInPlacementHome);
        placementHomeEntityAwareDTO.setOtherChildrenInHomeParameterObjects(Collections.singletonList(otherChildInHomeEntityAwareDTO));
        assertValid("R-04966");
    }

    @Test
    public void testOtherChildrenNameInvalid() {
        OtherChildrenInPlacementHome otherChildrenInPlacementHome = new OtherChildrenInPlacementHome();
        otherChildrenInPlacementHome.setOthchldNm("Valid2 Nam31 Jr. 1");
        OtherChildInHomeEntityAwareDTO otherChildInHomeEntityAwareDTO = new OtherChildInHomeEntityAwareDTO();
        otherChildInHomeEntityAwareDTO.setEntity(otherChildrenInPlacementHome);
        placementHomeEntityAwareDTO.setOtherChildrenInHomeParameterObjects(Collections.singletonList(otherChildInHomeEntityAwareDTO));
        check("R-04966");
    }

    @Test
    public void testBckPersnmValid() {
        PlacementHome placementHome = new PlacementHome();
        placementHome.setBckPersnm("S 1A A Sr.");
        placementHomeEntityAwareDTO.setEntity(placementHome);
        assertValid("R-04966");
    }

    @Test
    public void testBckPersnmInvalid() {
        PlacementHome placementHome = new PlacementHome();
        placementHome.setBckPersnm("S 1 A Sr.");
        placementHomeEntityAwareDTO.setEntity(placementHome);
        check("R-04966");
    }

    @Test
    public void testPyeFstnmValid() {
        PlacementHome placementHome = new PlacementHome();
        placementHome.setPyeFstnm("A1A");
        placementHomeEntityAwareDTO.setEntity(placementHome);
        assertValid("R-04966");
    }

    @Test
    public void testPyeFstnmInvalid() {
        PlacementHome placementHome = new PlacementHome();
        placementHome.setBckPersnm("11");
        placementHomeEntityAwareDTO.setEntity(placementHome);
        check("R-04966");
    }

    @Test
    public void testPyeLstnmValid() {
        PlacementHome placementHome = new PlacementHome();
        placementHome.setPyeLstnm("A1A");
        placementHomeEntityAwareDTO.setEntity(placementHome);
        assertValid("R-04966");
    }

    @Test
    public void testPyeLstnmInvalid() {
        PlacementHome placementHome = new PlacementHome();
        placementHome.setPyeLstnm("11");
        placementHomeEntityAwareDTO.setEntity(placementHome);
        check("R-04966");
    }

    @Test
    public void testPyeMidnmValid() {
        PlacementHome placementHome = new PlacementHome();
        placementHome.setPyeMidnm("A1A");
        placementHomeEntityAwareDTO.setEntity(placementHome);
        assertValid("R-04966");
    }

    @Test
    public void testPyeMidnmInvalid() {
        PlacementHome placementHome = new PlacementHome();
        placementHome.setPyeMidnm("11");
        placementHomeEntityAwareDTO.setEntity(placementHome);
        check("R-04966");
    }


    @Test
    public void testPrmCnctnmValid() {
        PlacementHome placementHome = new PlacementHome();
        placementHome.setPrmCnctnm("S 1A A Sr.");
        placementHomeEntityAwareDTO.setEntity(placementHome);
        assertValid("R-04966");
    }

    @Test
    public void testPrmCnctnmInvalid() {
        PlacementHome placementHome = new PlacementHome();
        placementHome.setPrmCnctnm("S 1 A Sr.");
        placementHomeEntityAwareDTO.setEntity(placementHome);
        check("R-04966");
    }

    @Test
    public void testPrmSubsnmValid() {
        PlacementHome placementHome = new PlacementHome();
        placementHome.setPrmSubsnm("S 1A A Sr.");
        placementHomeEntityAwareDTO.setEntity(placementHome);
        assertValid("R-04966");
    }

    @Test
    public void testPrmSubsnmInvalid() {
        PlacementHome placementHome = new PlacementHome();
        placementHome.setPrmSubsnm("S 1 A Sr.");
        placementHomeEntityAwareDTO.setEntity(placementHome);
        check("R-04966");
    }

    @Test
    public void testLaPFstnmValid() {
        PlacementHome placementHome = new PlacementHome();
        placementHome.setLaPFstnm("A1A");
        placementHomeEntityAwareDTO.setEntity(placementHome);
        assertValid("R-04966");
    }

    @Test
    public void testLaPFstnmInvalid() {
        PlacementHome placementHome = new PlacementHome();
        placementHome.setLaPFstnm("11");
        placementHomeEntityAwareDTO.setEntity(placementHome);
        check("R-04966");
    }

    @Test
    public void testLaPLstnmValid() {
        PlacementHome placementHome = new PlacementHome();
        placementHome.setLaPLstnm("A1A");
        placementHomeEntityAwareDTO.setEntity(placementHome);
        assertValid("R-04966");
    }

    @Test
    public void testLaPLstnmInvalid() {
        PlacementHome placementHome = new PlacementHome();
        placementHome.setLaPLstnm("11");
        placementHomeEntityAwareDTO.setEntity(placementHome);
        check("R-04966");
    }

    @Test
    public void testLaPMidnmValid() {
        PlacementHome placementHome = new PlacementHome();
        placementHome.setLaPMidnm("A1A");
        placementHomeEntityAwareDTO.setEntity(placementHome);
        assertValid("R-04966");
    }

    @Test
    public void testLaPMidnmInvalid() {
        PlacementHome placementHome = new PlacementHome();
        placementHome.setLaPMidnm("11");
        placementHomeEntityAwareDTO.setEntity(placementHome);
        check("R-04966");
    }

}
