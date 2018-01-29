package gov.ca.cwds.cms.data.access.service.impl.substituteCareProvider;

import static org.junit.Assert.fail;

import gov.ca.cwds.cms.data.access.dto.ExtendedSCPEntityAwareDTO;
import gov.ca.cwds.cms.data.access.dto.SCPEntityAwareDTO;
import gov.ca.cwds.cms.data.access.service.impl.BaseDocToolRulesTest;
import gov.ca.cwds.cms.data.access.service.impl.SubstituteCareProviderServiceImpl;
import gov.ca.cwds.data.legacy.cms.entity.PlacementHomeInformation;
import gov.ca.cwds.data.legacy.cms.entity.SubstituteCareProvider;
import gov.ca.cwds.drools.DroolsException;
import gov.ca.cwds.rest.exception.BusinessValidationException;
import org.junit.Before;

/**
 * @author CWDS TPT-2 Team
 */
public abstract class BaseDocToolRulesSubstituteCareProviderTest extends BaseDocToolRulesTest {

  private SubstituteCareProviderServiceImpl scpService;

  ExtendedSCPEntityAwareDTO entityAwareDTO;

  @Before
  public void setUp() {
    SCPEntityAwareDTO scpEntityAwareDTO = new SCPEntityAwareDTO();
    entityAwareDTO = new ExtendedSCPEntityAwareDTO(scpEntityAwareDTO);

    PlacementHomeInformation placementHomeInformation = new PlacementHomeInformation();
    entityAwareDTO.setPlacementHomeInformation(placementHomeInformation);

    scpService = new SubstituteCareProviderServiceImpl();
    scpService.setDroolsService(droolsService);

    entityAwareDTO.setEntity(new SubstituteCareProvider());
  }

  void checkSuccess() {
    try {
      scpService.runDataProcessing(entityAwareDTO, principal);
      scpService.runBusinessValidation(entityAwareDTO, principal);
    } catch (BusinessValidationException e) {
      fail();
    } catch (DroolsException e) {
      fail(e.getMessage());
    }
  }

  protected void checkFail(String ruleCode) {
    try {
      scpService.runDataProcessing(entityAwareDTO, principal);
      scpService.runBusinessValidation(entityAwareDTO, principal);
      fail();
    } catch (BusinessValidationException e) {
      assert e.getValidationDetailsList()
          .stream()
          .filter(issueDetails -> issueDetails.getCode().equals(ruleCode))
          .count() == 1;
    } catch (DroolsException e) {
      fail(e.getMessage());
    }

  }

  @Override
  public String getPrivilege() {
    return "";
  }
}
