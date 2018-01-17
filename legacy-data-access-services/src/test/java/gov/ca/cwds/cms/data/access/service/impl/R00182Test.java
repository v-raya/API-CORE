package gov.ca.cwds.cms.data.access.service.impl;

import static org.junit.Assert.fail;

import gov.ca.cwds.cms.data.access.dto.OtherChildInHomeEntityAwareDTO;
import gov.ca.cwds.data.legacy.cms.entity.OtherChildrenInPlacementHome;
import gov.ca.cwds.drools.DroolsException;
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
public class R00182Test extends BaseDocToolRulesPlacementHomeTest {

  private static final Logger LOG = LoggerFactory.getLogger(R00182Test.class);

  @Before
  public void before() {
    OtherChildInHomeEntityAwareDTO otherChild1 = new OtherChildInHomeEntityAwareDTO();
    otherChild1.setEntity(new OtherChildrenInPlacementHome());
    otherChild1.getEntity().setBirthDt(LocalDate.now());

    OtherChildInHomeEntityAwareDTO otherChild2 = new OtherChildInHomeEntityAwareDTO();
    otherChild2.setEntity(new OtherChildrenInPlacementHome());
    otherChild2.getEntity().setBirthDt(LocalDate.now().minusYears(5));


    List<OtherChildInHomeEntityAwareDTO> otherChildren = new ArrayList<>(2);
    otherChildren.add(otherChild1);
    otherChildren.add(otherChild2);

    placementHomeEntityAwareDTO.setOtherChildrenInHomeParameterObjects(otherChildren);
  }

  @Test
  public void testRulePass() throws Exception {
    assertValid();
  }


  @Test
  public void testFirstChildBirthdayInFuture() throws Exception {
    assertInvalid(() -> {
      placementHomeEntityAwareDTO.getOtherChildrenInHomeParameterObjects().get(0)
          .getEntity().setBirthDt(LocalDate.now().plusYears(1));
    });
  }

  @Test
  public void testFirstAndSecondChildrenBirthdayInFuture () throws Exception {
    assertInvalid(() -> {
      placementHomeEntityAwareDTO.getOtherChildrenInHomeParameterObjects().get(0)
          .getEntity().setBirthDt(LocalDate.now().plusYears(1));
      placementHomeEntityAwareDTO.getOtherChildrenInHomeParameterObjects().get(1)
          .getEntity().setBirthDt(LocalDate.now().plusYears(2));
    });
  }


  private void assertInvalid(Runnable testCase) throws DroolsException {
    try {
      testCase.run();
      placementHomeService.runBusinessValidation(placementHomeEntityAwareDTO, principal);
      fail();
    } catch (BusinessValidationException e) {
      LOG.info(e.getValidationDetailsList().toString());
      Assert.assertEquals("R-00182", e.getValidationDetailsList().iterator().next().getCode());
    }
  }

  private void assertValid(Runnable testCase) throws DroolsException {
    testCase.run();
    assertValid();
  }


}
