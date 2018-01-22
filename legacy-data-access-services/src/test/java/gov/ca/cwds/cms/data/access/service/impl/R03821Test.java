package gov.ca.cwds.cms.data.access.service.impl;

import gov.ca.cwds.data.legacy.cms.entity.OtherAdultsInPlacementHome;
import org.junit.Test;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Collections;

/**
 * Created by TPT2 on 12/21/2017.
 */
public class R03821Test extends BaseDocToolRulesPlacementHomeTest {


  @Test
  public void checkValidStartAndBirthDate() {
    OtherAdultsInPlacementHome otherAdultsInPlacementHome = new OtherAdultsInPlacementHome();
    otherAdultsInPlacementHome.setStartDt(LocalDate.now());
    otherAdultsInPlacementHome.setBirthDt(LocalDate.now().minus(1, ChronoUnit.DAYS));
    placementHomeEntityAwareDTO.getEntity().setOtherAdultsInPlacementHomes(Collections.singletonList(otherAdultsInPlacementHome));
    assertValid();
  }

  @Test
  public void checkInvalidStartAndBirthDate() {
    OtherAdultsInPlacementHome otherAdultsInPlacementHome = new OtherAdultsInPlacementHome();
    otherAdultsInPlacementHome.setStartDt(LocalDate.now());
    otherAdultsInPlacementHome.setBirthDt(LocalDate.now().plus(1, ChronoUnit.DAYS));
    placementHomeEntityAwareDTO.getEntity().setOtherAdultsInPlacementHomes(Collections.singletonList(otherAdultsInPlacementHome));
    check("R-03821");
  }


}
