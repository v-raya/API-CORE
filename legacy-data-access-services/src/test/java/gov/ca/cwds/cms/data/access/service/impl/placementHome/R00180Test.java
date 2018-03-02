package gov.ca.cwds.cms.data.access.service.impl.placementHome;

import gov.ca.cwds.cms.data.access.dto.OtherAdultInHomeEntityAwareDTO;
import gov.ca.cwds.data.legacy.cms.entity.OtherAdultsInPlacementHome;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
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
    OtherAdultInHomeEntityAwareDTO otherAdultInHomeEntityAwareDTO = new OtherAdultInHomeEntityAwareDTO();
    otherAdultInHomeEntityAwareDTO.setEntity(otherAdultsInPlacementHome);
    placementHomeEntityAwareDTO.getOtherAdultInHomeParameterObjects().add(otherAdultInHomeEntityAwareDTO);
    checkRuleValid(placementHomeEntityAwareDTO, RULE_NAME);
  }

  @Test
  public void checkStartMoreThenEndDateFail() throws Exception {
    OtherAdultsInPlacementHome otherAdultsInPlacementHome = new OtherAdultsInPlacementHome();
    otherAdultsInPlacementHome.setStartDt(LocalDate.now().minus(10, ChronoUnit.YEARS));
    otherAdultsInPlacementHome.setEndDt(LocalDate.now().minus(20, ChronoUnit.YEARS));
    OtherAdultInHomeEntityAwareDTO otherAdultInHomeEntityAwareDTO = new OtherAdultInHomeEntityAwareDTO();
    otherAdultInHomeEntityAwareDTO.setEntity(otherAdultsInPlacementHome);
    placementHomeEntityAwareDTO.getOtherAdultInHomeParameterObjects().add(otherAdultInHomeEntityAwareDTO);
    checkRuleViolatedOnce(placementHomeEntityAwareDTO, RULE_NAME);
  }

  @Test
  public void checkRuleViolatedForMultipleAdults() throws Exception {
    OtherAdultsInPlacementHome otherAdultsInPlacementHome1 = new OtherAdultsInPlacementHome();
    otherAdultsInPlacementHome1.setStartDt(LocalDate.now().minus(10, ChronoUnit.YEARS));
    otherAdultsInPlacementHome1.setEndDt(LocalDate.now().minus(20, ChronoUnit.YEARS));
    otherAdultsInPlacementHome1.setIdentifier("id1");
    OtherAdultInHomeEntityAwareDTO otherAdultInHomeEntityAwareDTO1 = new OtherAdultInHomeEntityAwareDTO();
    otherAdultInHomeEntityAwareDTO1.setEntity(otherAdultsInPlacementHome1);
    placementHomeEntityAwareDTO.getOtherAdultInHomeParameterObjects().add(otherAdultInHomeEntityAwareDTO1);

    OtherAdultsInPlacementHome otherAdultsInPlacementHome2 = new OtherAdultsInPlacementHome();
    otherAdultsInPlacementHome2.setStartDt(LocalDate.now().minus(19, ChronoUnit.YEARS));
    otherAdultsInPlacementHome2.setEndDt(LocalDate.now().minus(20, ChronoUnit.YEARS));
    otherAdultsInPlacementHome2.setIdentifier("id2");
    OtherAdultInHomeEntityAwareDTO otherAdultInHomeEntityAwareDTO2 = new OtherAdultInHomeEntityAwareDTO();
    otherAdultInHomeEntityAwareDTO2.setEntity(otherAdultsInPlacementHome2);
    placementHomeEntityAwareDTO.getOtherAdultInHomeParameterObjects().add(otherAdultInHomeEntityAwareDTO2);

    checkRuleViolated(placementHomeEntityAwareDTO, RULE_NAME, 2);
  }

  @Test
  public void checkEndDateNullValid() throws Exception {
    OtherAdultsInPlacementHome otherAdultsInPlacementHome = new OtherAdultsInPlacementHome();
    otherAdultsInPlacementHome.setStartDt(LocalDate.now().minus(10, ChronoUnit.YEARS));
    otherAdultsInPlacementHome.setEndDt(null);
    OtherAdultInHomeEntityAwareDTO otherAdultInHomeEntityAwareDTO = new OtherAdultInHomeEntityAwareDTO();
    otherAdultInHomeEntityAwareDTO.setEntity(otherAdultsInPlacementHome);
    placementHomeEntityAwareDTO.getOtherAdultInHomeParameterObjects().add(otherAdultInHomeEntityAwareDTO);
    checkRuleValid(placementHomeEntityAwareDTO, RULE_NAME);
  }

  @Test
  public void checkStartDateNullValid() throws Exception {
    OtherAdultsInPlacementHome otherAdultsInPlacementHome = new OtherAdultsInPlacementHome();
    otherAdultsInPlacementHome.setStartDt(null);
    otherAdultsInPlacementHome.setEndDt(LocalDate.now().minus(10, ChronoUnit.YEARS));
    OtherAdultInHomeEntityAwareDTO otherAdultInHomeEntityAwareDTO = new OtherAdultInHomeEntityAwareDTO();
    otherAdultInHomeEntityAwareDTO.setEntity(otherAdultsInPlacementHome);
    placementHomeEntityAwareDTO.getOtherAdultInHomeParameterObjects().add(otherAdultInHomeEntityAwareDTO);
    checkRuleValid(placementHomeEntityAwareDTO, RULE_NAME);
  }

  @Test
  public void checkStartAndEndDateNullValid() throws Exception {
    OtherAdultsInPlacementHome otherAdultsInPlacementHome = new OtherAdultsInPlacementHome();
    otherAdultsInPlacementHome.setStartDt(null);
    otherAdultsInPlacementHome.setEndDt(null);
    OtherAdultInHomeEntityAwareDTO otherAdultInHomeEntityAwareDTO = new OtherAdultInHomeEntityAwareDTO();
    otherAdultInHomeEntityAwareDTO.setEntity(otherAdultsInPlacementHome);
    placementHomeEntityAwareDTO.getOtherAdultInHomeParameterObjects().add(otherAdultInHomeEntityAwareDTO);
    checkRuleValid(placementHomeEntityAwareDTO, RULE_NAME);
  }

}
