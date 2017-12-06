package gov.ca.cwds.cms.data.access.service.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import gov.ca.cwds.cms.data.access.dto.SCPEntityAwareDTO;
import gov.ca.cwds.cms.data.access.service.SubstituteCareProviderService;
import gov.ca.cwds.cms.data.access.utils.ParametersValidator;
import gov.ca.cwds.data.legacy.cms.entity.PhoneContactDetail;
import gov.ca.cwds.data.legacy.cms.entity.SubstituteCareProvider;
import java.util.ArrayList;
import java.util.List;
import org.junit.Test;

/**
 * @author CWDS CALS API Team
 */

public class SubstituteCareProviderServiceImplTest {

  @Test
  public void createValidateScpNotPersistent() throws Exception {
    try {
      SubstituteCareProviderService service = new SubstituteCareProviderServiceImpl();
      SubstituteCareProvider scp = new SubstituteCareProvider();
      scp.setIdentifier("1");
      SCPEntityAwareDTO parameterObject = new SCPEntityAwareDTO("1");
      parameterObject.setEntity(scp);
      service.create(parameterObject);
      fail();
    } catch (IllegalStateException e) {
      assertEquals(String.format(ParametersValidator.INSTANCE_MUST_NOT_BE_PERSISTED,
          SubstituteCareProvider.class.getSimpleName()), e.getMessage());
    }
  }

  @Test
  public void createValidatePhoneNumberNotPersisted() throws Exception {
    try {
      SubstituteCareProviderService service = new SubstituteCareProviderServiceImpl();
      SubstituteCareProvider scp = new SubstituteCareProvider();
      SCPEntityAwareDTO parameterObject = new SCPEntityAwareDTO("1");
      parameterObject.setEntity(scp);
      List<PhoneContactDetail> phoneNumbers = new ArrayList<>();
      PhoneContactDetail phoneContactDetail = new PhoneContactDetail();
      phoneContactDetail.setThirdId("1");
      phoneNumbers.add(phoneContactDetail);
      parameterObject.setPhoneNumbers(phoneNumbers);
      service.create(parameterObject);
      fail();
    } catch (IllegalStateException e) {
      assertEquals(String.format(ParametersValidator.INSTANCE_MUST_NOT_BE_PERSISTED,
          PhoneContactDetail.class.getSimpleName()), e.getMessage());
    }
  }


}