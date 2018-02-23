package gov.ca.cwds.cms.data.access.service.impl.substituteCareProvider;

import gov.ca.cwds.data.legacy.cms.entity.SubstituteCareProvider;
import org.junit.Test;

public class R04966Test extends BaseDocToolRulesSubstituteCareProviderTest {

    @Test
    public void testFirstNmValid() {
        SubstituteCareProvider substituteCareProvider = entityAwareDTO.getEntity();
        substituteCareProvider.setFirstNm("A1");
        checkSuccess("R-04966");
    }

    @Test
    public void testFirstNmInalid() {
        SubstituteCareProvider substituteCareProvider = entityAwareDTO.getEntity();
        substituteCareProvider.setFirstNm("11");
        checkFail("R-04966");
    }

    @Test
    public void testLastNmValid() {
        SubstituteCareProvider substituteCareProvider = entityAwareDTO.getEntity();
        substituteCareProvider.setLastNm("A1");
        checkSuccess("R-04966");
    }

    @Test
    public void testLastNmInalid() {
        SubstituteCareProvider substituteCareProvider = entityAwareDTO.getEntity();
        substituteCareProvider.setLastNm("11");
        checkFail("R-04966");
    }

    @Test
    public void testMidIniNmValid() {
        SubstituteCareProvider substituteCareProvider = entityAwareDTO.getEntity();
        substituteCareProvider.setMidIniNm("A1");
        checkSuccess("R-04966");
    }

    @Test
    public void testMidIniNmInalid() {
        SubstituteCareProvider substituteCareProvider = entityAwareDTO.getEntity();
        substituteCareProvider.setMidIniNm("11");
        checkFail("R-04966");
    }
}
