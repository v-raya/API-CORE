package gov.ca.cwds.cms.data.access.service.impl;

import static org.junit.Assert.fail;

import gov.ca.cwds.drools.DroolsException;
import gov.ca.cwds.rest.exception.BusinessValidationException;
import org.junit.Assert;
import org.junit.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by TPT2 on 12/11/2017.
 */
public class R07878Test extends BaseDocToolRulesPlacementHomeTest {

  private static final Logger LOG = LoggerFactory.getLogger(R07878Test.class);

  @Before
  public void before() {
    //placementHomeEntityAwareDTO.setScpParameterObjects();
  }
 /*
  @Test
  public void testRulePass() throws Exception {
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

  */
  private void assertInvalid(Runnable testCase) throws DroolsException {
    try {
      testCase.run();
      placementHomeService.runBusinessValidation(placementHomeEntityAwareDTO, principal);
      fail();
    } catch (BusinessValidationException e) {
      LOG.info(e.getValidationDetailsList().toString());
      Assert.assertEquals("R-00181", e.getValidationDetailsList().iterator().next().getCode());
    }
  }

  private void assertValid(Runnable testCase) throws DroolsException {
    testCase.run();
    assertValid();
  }


}
