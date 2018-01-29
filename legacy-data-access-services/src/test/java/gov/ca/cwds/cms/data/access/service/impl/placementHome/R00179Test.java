package gov.ca.cwds.cms.data.access.service.impl.placementHome;

import gov.ca.cwds.data.legacy.cms.entity.OtherAdultsInPlacementHome;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Collections;
import org.junit.Test;

/**
 * Created by TPT2 on 12/21/2017.
 */
public class R00179Test extends BaseDocToolRulesPlacementHomeTest {

  private static final String RULE_NAME = "R-00179";

  @Test
  public void checkValidBirthDate()  throws Exception {
    OtherAdultsInPlacementHome otherAdultsInPlacementHome = new OtherAdultsInPlacementHome();
    otherAdultsInPlacementHome.setBirthDt(LocalDate.now().minus(20, ChronoUnit.YEARS));
    placementHomeEntityAwareDTO.getEntity().setOtherAdultsInPlacementHomes(Collections.singletonList(otherAdultsInPlacementHome));
    checkRuleValid(placementHomeEntityAwareDTO, RULE_NAME);
  }

  @Test
  public void checkBirthDateInFuture() throws Exception {
    OtherAdultsInPlacementHome otherAdultsInPlacementHome = new OtherAdultsInPlacementHome();
    otherAdultsInPlacementHome.setBirthDt(LocalDate.now().plus(1, ChronoUnit.DAYS));
    placementHomeEntityAwareDTO.getEntity().setOtherAdultsInPlacementHomes(Collections.singletonList(otherAdultsInPlacementHome));
    checkRuleViolatedOnce(placementHomeEntityAwareDTO, RULE_NAME);
  }

  @Test
  public void checkBirthDateLessThen18() throws Exception {
    OtherAdultsInPlacementHome otherAdultsInPlacementHome = new OtherAdultsInPlacementHome();
    otherAdultsInPlacementHome.setBirthDt(LocalDate.now().minus(17, ChronoUnit.YEARS));
    placementHomeEntityAwareDTO.getEntity().setOtherAdultsInPlacementHomes(Collections.singletonList(otherAdultsInPlacementHome));
    checkRuleViolatedOnce(placementHomeEntityAwareDTO, RULE_NAME);
  }

}
