package gov.ca.cwds.cms.data.access.service.impl;

import gov.ca.cwds.cms.data.access.service.impl.placementHome.BaseDocToolRulesPlacementHomeTest;
import gov.ca.cwds.data.legacy.cms.entity.SubstituteCareProvider;
import org.junit.Test;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

/**
 * Created by TPT2 on 12/21/2017.
 */
public class R00221Test extends BaseDocToolRulesPlacementHomeTest {


  @Test
  public void checkValidBirthDate() {
    SubstituteCareProvider substituteCareProvider = new SubstituteCareProvider();
    substituteCareProvider.setBirthDt(LocalDate.now().minus(19L, ChronoUnit.YEARS));
    placementHomeEntityAwareDTO.getEntity().setPrimarySubstituteCareProvider(substituteCareProvider);
    assertValid();
  }

  @Test
  public void checkInvalidBirthDate() {
    SubstituteCareProvider substituteCareProvider = new SubstituteCareProvider();
    substituteCareProvider.setBirthDt(LocalDate.now().minus(17L, ChronoUnit.YEARS));
    placementHomeEntityAwareDTO.getEntity().setPrimarySubstituteCareProvider(substituteCareProvider);
    check("R-00221");
  }


}
