package gov.ca.cwds.cms.data.access.service.impl.placementHome;

import gov.ca.cwds.data.legacy.cms.entity.OtherAdultsInPlacementHome;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.junit.Test;

/**
 * Created by TPT2 on 12/21/2017.
 */
public class R00180Test extends BaseDocToolRulesPlacementHomeTest {

  private static final String RULE_NAME = "R-00180";

  @Test
  public void checkValidDates()  throws Exception {
    OtherAdultsInPlacementHome otherAdultsInPlacementHome = new OtherAdultsInPlacementHome();
    otherAdultsInPlacementHome.setStartDt(LocalDate.now().minus(20, ChronoUnit.YEARS));
    otherAdultsInPlacementHome.setEndDt(LocalDate.now().minus(10, ChronoUnit.YEARS));
    placementHomeEntityAwareDTO.getEntity().setOtherAdultsInPlacementHomes(Collections.singletonList(otherAdultsInPlacementHome));
    checkRuleValid(placementHomeEntityAwareDTO, RULE_NAME);
  }

  @Test
  public void checkStartMoreThenEndDateFail() throws Exception {
    OtherAdultsInPlacementHome otherAdultsInPlacementHome = new OtherAdultsInPlacementHome();
    otherAdultsInPlacementHome.setStartDt(LocalDate.now().minus(10, ChronoUnit.YEARS));
    otherAdultsInPlacementHome.setEndDt(LocalDate.now().minus(20, ChronoUnit.YEARS));
    placementHomeEntityAwareDTO.getEntity().setOtherAdultsInPlacementHomes(Collections.singletonList(otherAdultsInPlacementHome));
    checkRuleViolatedOnce(placementHomeEntityAwareDTO, RULE_NAME);
  }

  @Test
  public void checkRuleViolatedForMultipleAdults() throws Exception {
    OtherAdultsInPlacementHome otherAdultsInPlacementHome1 = new OtherAdultsInPlacementHome();
    otherAdultsInPlacementHome1.setStartDt(LocalDate.now().minus(10, ChronoUnit.YEARS));
    otherAdultsInPlacementHome1.setEndDt(LocalDate.now().minus(20, ChronoUnit.YEARS));
    otherAdultsInPlacementHome1.setIdentifier("id1");
    OtherAdultsInPlacementHome otherAdultsInPlacementHome2 = new OtherAdultsInPlacementHome();
    otherAdultsInPlacementHome2.setStartDt(LocalDate.now().minus(19, ChronoUnit.YEARS));
    otherAdultsInPlacementHome2.setEndDt(LocalDate.now().minus(20, ChronoUnit.YEARS));
    otherAdultsInPlacementHome2.setIdentifier("id2");
    List<OtherAdultsInPlacementHome> otherAdultsInPlacementHomeList = new ArrayList<>();
    otherAdultsInPlacementHomeList.add(otherAdultsInPlacementHome1);
    otherAdultsInPlacementHomeList.add(otherAdultsInPlacementHome2);
    placementHomeEntityAwareDTO.getEntity().setOtherAdultsInPlacementHomes(otherAdultsInPlacementHomeList);
    checkRuleViolated(placementHomeEntityAwareDTO, RULE_NAME, 2);
  }

  @Test
  public void checkEndDateNullValid() throws Exception {
    OtherAdultsInPlacementHome otherAdultsInPlacementHome = new OtherAdultsInPlacementHome();
    otherAdultsInPlacementHome.setStartDt(LocalDate.now().minus(10, ChronoUnit.YEARS));
    otherAdultsInPlacementHome.setEndDt(null);
    placementHomeEntityAwareDTO.getEntity().setOtherAdultsInPlacementHomes(Collections.singletonList(otherAdultsInPlacementHome));
    checkRuleValid(placementHomeEntityAwareDTO, RULE_NAME);
  }

  @Test
  public void checkStartDateNullValid() throws Exception {
    OtherAdultsInPlacementHome otherAdultsInPlacementHome = new OtherAdultsInPlacementHome();
    otherAdultsInPlacementHome.setStartDt(null);
    otherAdultsInPlacementHome.setEndDt(LocalDate.now().minus(10, ChronoUnit.YEARS));
    placementHomeEntityAwareDTO.getEntity().setOtherAdultsInPlacementHomes(Collections.singletonList(otherAdultsInPlacementHome));
    checkRuleValid(placementHomeEntityAwareDTO, RULE_NAME);
  }

  @Test
  public void checkStartAndEndDateNullValid() throws Exception {
    OtherAdultsInPlacementHome otherAdultsInPlacementHome = new OtherAdultsInPlacementHome();
    otherAdultsInPlacementHome.setStartDt(null);
    otherAdultsInPlacementHome.setEndDt(null);
    placementHomeEntityAwareDTO.getEntity().setOtherAdultsInPlacementHomes(Collections.singletonList(otherAdultsInPlacementHome));
    checkRuleValid(placementHomeEntityAwareDTO, RULE_NAME);
  }

}
