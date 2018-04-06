package gov.ca.cwds.cms.data.access.service.impl.placementHome;

import static org.junit.Assert.fail;

import gov.ca.cwds.cms.data.access.dto.OtherChildInHomeEntityAwareDTO;
import gov.ca.cwds.cms.data.access.service.rules.PlacementHomeDroolsConfiguration;
import gov.ca.cwds.data.legacy.cms.entity.OtherChildrenInPlacementHome;
import gov.ca.cwds.rest.exception.BusinessValidationException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by TPT2
 */
public class R00181Test extends BaseDocToolRulesPlacementHomeTest {

  private static final Logger LOG = LoggerFactory.getLogger(R00181Test.class);

  @Before
  public void before() {
    OtherChildInHomeEntityAwareDTO otherChild1 = new OtherChildInHomeEntityAwareDTO();
    otherChild1.setEntity(new OtherChildrenInPlacementHome());
    otherChild1.getEntity().setBirthDt(LocalDate.now().minusYears(4));

    OtherChildInHomeEntityAwareDTO otherChild2 = new OtherChildInHomeEntityAwareDTO();
    otherChild2.setEntity(new OtherChildrenInPlacementHome());
    otherChild2.getEntity().setBirthDt(LocalDate.now().minusYears(5));


    List<OtherChildInHomeEntityAwareDTO> otherChildren = new ArrayList<>(2);
    otherChildren.add(otherChild1);
    otherChildren.add(otherChild2);

    placementHomeEntityAwareDTO.setOtherChildrenInHomeParameterObjects(otherChildren);
  }

  @Test
  public void testRulePass() {
    assertValid();
  }


  @Test
  public void testFirstChildAgeGreaterThan18() throws Exception {
    assertInvalid(() -> {
      placementHomeEntityAwareDTO.getOtherChildrenInHomeParameterObjects().get(0)
          .getEntity().setBirthDt(LocalDate.now().minusYears(20));
    });
  }

  @Test
  public void testFirstAndSecondChildAgeGreaterThan18() throws Exception {
    assertInvalid(() -> {
      placementHomeEntityAwareDTO.getOtherChildrenInHomeParameterObjects().get(0)
          .getEntity().setBirthDt(LocalDate.now().minusYears(20));
      placementHomeEntityAwareDTO.getOtherChildrenInHomeParameterObjects().get(1)
          .getEntity().setBirthDt(LocalDate.now().minusYears(20));
    });
  }


  private void assertInvalid(Runnable testCase) {
    try {
      testCase.run();
      businessValidationService.runBusinessValidation(placementHomeEntityAwareDTO, principal, PlacementHomeDroolsConfiguration.INSTANCE);
      fail();
    } catch (BusinessValidationException e) {
      LOG.info(e.getValidationDetailsList().toString());
      Assert.assertEquals("R-00181", e.getValidationDetailsList().iterator().next().getCode());
    }
  }

  private void assertValid(Runnable testCase) {
    testCase.run();
    assertValid();
  }


}
