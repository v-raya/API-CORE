package gov.ca.cwds.cms.data.access.service.impl.placementHome;

import static org.junit.Assert.fail;

import gov.ca.cwds.cms.data.access.Constants.StaffPersonPrivileges;
import gov.ca.cwds.cms.data.access.dto.PlacementHomeEntityAwareDTO;
import gov.ca.cwds.cms.data.access.service.BusinessValidationService;
import gov.ca.cwds.cms.data.access.service.impl.BaseDocToolRulesTest;
import gov.ca.cwds.cms.data.access.service.impl.PlacementHomeServiceImpl;
import gov.ca.cwds.cms.data.access.service.rules.PlacementHomeDroolsConfiguration;
import gov.ca.cwds.data.legacy.cms.entity.PlacementHome;
import gov.ca.cwds.data.legacy.cms.entity.SubstituteCareProvider;
import gov.ca.cwds.drools.DroolsException;
import gov.ca.cwds.rest.exception.BusinessValidationException;
import org.junit.Before;

/** @author CWDS CALS API Team */
public abstract class BaseDocToolRulesPlacementHomeTest extends BaseDocToolRulesTest {

  protected PlacementHomeServiceImpl placementHomeService;
  protected BusinessValidationService businessValidationService;
  protected PlacementHomeEntityAwareDTO placementHomeEntityAwareDTO;

  @Before
  public void setUp() {
    businessValidationService = new BusinessValidationService(droolsService);
    placementHomeService = new PlacementHomeServiceImpl();
    placementHomeEntityAwareDTO = prepareSuccessfulPlacementHomeEntityAwareDTO();
  }

  protected void checkRuleViolatedOnce(
      PlacementHomeEntityAwareDTO placementHomeEntityAwareDTO, String ruleName)
      throws DroolsException {
    try {
      runBusinessValidation(placementHomeEntityAwareDTO);
      fail();
    } catch (BusinessValidationException e) {
      assertRuleViolatedOnce(ruleName, e);
    }
  }

  void checkRuleViolated(
      PlacementHomeEntityAwareDTO placementHomeEntityAwareDTO, String ruleName, int count)
      throws DroolsException {
    try {
      runBusinessValidation(placementHomeEntityAwareDTO);
      fail();
    } catch (BusinessValidationException e) {
      assertRuleViolated(ruleName, e, count);
    }
  }

  void checkRuleValid(PlacementHomeEntityAwareDTO placementHomeEntityAwareDTO, String ruleName)
      throws DroolsException {
    try {
      runBusinessValidation(placementHomeEntityAwareDTO);
    } catch (BusinessValidationException e) {
      assertRuleValid(ruleName, e);
    }
  }

  private void runBusinessValidation(PlacementHomeEntityAwareDTO placementHomeEntityAwareDTO)
      throws DroolsException {
    businessValidationService.runBusinessValidation(
        placementHomeEntityAwareDTO, principal, PlacementHomeDroolsConfiguration.INSTANCE);
  }

  @Override
  public String getPrivilege() {
    return StaffPersonPrivileges.USR_PRV_RESOURCE_MGMT_PLACEMENT_FACILITY_MAINT;
  }

  protected void check(String ruleCode) {
    try {
      businessValidationService.runDataProcessing(
          placementHomeEntityAwareDTO,
          principal,
          PlacementHomeDroolsConfiguration.DATA_PROCESSING_INSTANCE);
      businessValidationService.runBusinessValidation(
          placementHomeEntityAwareDTO, principal, PlacementHomeDroolsConfiguration.INSTANCE);
      fail();
    } catch (BusinessValidationException e) {
      assert e.getValidationDetailsList()
              .stream()
              .filter(issueDetails -> issueDetails.getCode().equals(ruleCode))
              .count()
          == 1;
    } catch (DroolsException e) {
      fail(e.getMessage());
    }
  }

  protected void assertValid(String ruleCode) {
    try {
      businessValidationService.runDataProcessing(
          placementHomeEntityAwareDTO,
          principal,
          PlacementHomeDroolsConfiguration.DATA_PROCESSING_INSTANCE);
      businessValidationService.runBusinessValidation(
          placementHomeEntityAwareDTO, principal, PlacementHomeDroolsConfiguration.INSTANCE);
    } catch (BusinessValidationException e) {
      assert e.getValidationDetailsList()
          .stream()
          .noneMatch(issueDetails -> issueDetails.getCode().equals(ruleCode));
    } catch (Exception e) {
      fail(e.getMessage());
    }
  }

  protected void assertValid() {
    try {
      businessValidationService.runBusinessValidation(
          placementHomeEntityAwareDTO, principal, PlacementHomeDroolsConfiguration.INSTANCE);
    } catch (Exception e) {
      fail(e.getMessage());
    }
  }

  private PlacementHomeEntityAwareDTO prepareSuccessfulPlacementHomeEntityAwareDTO() {
    PlacementHomeEntityAwareDTO placementHomeEntityAwareDTO = new PlacementHomeEntityAwareDTO();
    PlacementHome placementHome = new PlacementHome();
    placementHome.setAgeToNo((short) 23);
    placementHome.setZipNo("11111");
    placementHome.setLaPZipno("11111");

    placementHome.setpZipNo("11111");
    placementHome.setPyeFstnm("PyeFstnm");
    placementHome.setPyeLstnm("PyeLstnm");
    placementHome.setPstreetNm("PstreetNm");
    placementHome.setpCityNm("pCityNm");
    placementHome.setPayeeStateCode((short) 1);

    SubstituteCareProvider substituteCareProvider = new SubstituteCareProvider();
    substituteCareProvider.setZipNo("11111");
    placementHome.setPrimarySubstituteCareProvider(substituteCareProvider);
    placementHomeEntityAwareDTO.setEntity(placementHome);
    return placementHomeEntityAwareDTO;
  }
}
