package gov.ca.cwds.cms.data.access.service.impl;

import java.time.LocalDate;
import org.junit.Assert;
import org.junit.Test;

/**
 * Created by TPT2 on 12/11/2017.
 */
public class R07878Test extends BaseDocToolRulesSubstituteCareProviderTest {

  @Test
  public void testRulePass() {
    checkSuccess();
  }

  @Test
  public void testStartDateNull() {
    entityAwareDTO.getPlacementHomeInformation().setStartDt(null);
    checkSuccess();
    Assert.assertNotNull(entityAwareDTO.getPlacementHomeInformation().getStartDt());
  }

  @Test
  public void testStartDateNotNull() {
    LocalDate date = LocalDate.now();
    entityAwareDTO.getPlacementHomeInformation().setStartDt(date);
    checkSuccess();
    Assert.assertEquals(date, entityAwareDTO.getPlacementHomeInformation().getStartDt());
  }
  
}
