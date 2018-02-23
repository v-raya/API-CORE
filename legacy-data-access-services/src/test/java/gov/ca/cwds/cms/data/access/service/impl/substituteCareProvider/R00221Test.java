package gov.ca.cwds.cms.data.access.service.impl.substituteCareProvider;

import gov.ca.cwds.data.legacy.cms.entity.SubstituteCareProvider;
import org.junit.Test;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

/**
 * Created by TPT2 on 12/21/2017.
 */
public class R00221Test extends BaseDocToolRulesSubstituteCareProviderTest {

    @Test
    public void checkValidBirthDate() {
        SubstituteCareProvider substituteCareProvider = entityAwareDTO.getEntity();
        substituteCareProvider.setBirthDt(LocalDate.now().minus(19L, ChronoUnit.YEARS));
        checkSuccess();
    }

    @Test
    public void checkInvalidBirthDate() {
        SubstituteCareProvider substituteCareProvider = entityAwareDTO.getEntity();
        substituteCareProvider.setBirthDt(LocalDate.now().minus(17L, ChronoUnit.YEARS));
        checkFail("R-00221");
    }


}
