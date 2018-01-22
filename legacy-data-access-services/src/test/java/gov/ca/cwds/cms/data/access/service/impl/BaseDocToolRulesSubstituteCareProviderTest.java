package gov.ca.cwds.cms.data.access.service.impl;

import static org.junit.Assert.fail;

import gov.ca.cwds.cms.data.access.dto.ExtendedSCPEntityAwareDTO;
import gov.ca.cwds.cms.data.access.dto.SCPEntityAwareDTO;
import gov.ca.cwds.data.legacy.cms.entity.PlacementHomeInformation;
import gov.ca.cwds.drools.DroolsException;
import gov.ca.cwds.rest.exception.BusinessValidationException;
import org.junit.Before;

/**
 * @author CWDS TPT-2 Team
 */
public abstract class BaseDocToolRulesSubstituteCareProviderTest extends BaseDocToolRulesTest {

  protected SubstituteCareProviderServiceImpl scpService;

  protected ExtendedSCPEntityAwareDTO entityAwareDTO;

  @Before
  public void setUp() {
    SCPEntityAwareDTO scpEntityAwareDTO = new SCPEntityAwareDTO();
    entityAwareDTO = new ExtendedSCPEntityAwareDTO(scpEntityAwareDTO);

    PlacementHomeInformation placementHomeInformation = new PlacementHomeInformation();
    entityAwareDTO.setPlacementHomeInformation(placementHomeInformation);

    scpService = new SubstituteCareProviderServiceImpl();
    scpService.setDroolsService(droolsService);
  }

  protected void checkSuccess() {
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
  protected String getPrivilege() {
    return "";
  }
}
