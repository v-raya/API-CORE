package gov.ca.cwds.cms.data.access.service.impl;

import static gov.ca.cwds.cms.data.access.service.impl.EntityAwareHelper.clientEntityAwareDTO;
import static org.junit.Assert.fail;

import gov.ca.cwds.data.legacy.cms.entity.Client;
import gov.ca.cwds.rest.exception.BusinessValidationException;
import org.junit.Test;

public class R00741Test extends BaseDocToolRulesTest {

  @Test
  public void testLanguagesAreDifferent() throws Exception {
    Client client = new Client();
    client.setPrimaryLanguageCode((short) 1);
    client.setSecondaryLanguageCode((short) 2);

    try {
      clientCoreService.runBusinessValidation(clientEntityAwareDTO(client));
    } catch (BusinessValidationException e) {
      assertRuleSatisfied("R-R00741", e);
    }
  }

  @Test
  public void testOneLanguagesIsUnknown() throws Exception {
    Client client = new Client();
    client.setPrimaryLanguageCode((short) 1);
    client.setSecondaryLanguageCode((short) 0);

    try {
      clientCoreService.runBusinessValidation(clientEntityAwareDTO(client));
    } catch (BusinessValidationException e) {
      assertRuleSatisfied("R-R00741", e);
    }
  }

  @Test
  public void testLanguagesAreTheSame() throws Exception {
    Client client = new Client();
    client.setPrimaryLanguageCode((short) 1);
    client.setSecondaryLanguageCode((short) 1);

    try {
      clientCoreService.runBusinessValidation(clientEntityAwareDTO(client));
      fail();
    } catch (BusinessValidationException e) {
      assertRuleViolated("R-00741", e);
    }
  }

  @Test
  public void testLanguagesAreUnknown() throws Exception {
    Client client = new Client();
    client.setPrimaryLanguageCode((short) 0);
    client.setSecondaryLanguageCode((short) 0);

    try {
      clientCoreService.runBusinessValidation(clientEntityAwareDTO(client));
    } catch (BusinessValidationException e) {
      assertRuleSatisfied("R-R00741", e);
    }
  }
}
