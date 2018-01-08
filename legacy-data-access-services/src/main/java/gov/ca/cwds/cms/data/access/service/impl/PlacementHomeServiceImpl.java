package gov.ca.cwds.cms.data.access.service.impl;

import com.google.inject.Inject;
import gov.ca.cwds.cms.data.access.CWSIdentifier;
import gov.ca.cwds.cms.data.access.Constants.PhoneticSearchTables;
import gov.ca.cwds.cms.data.access.dao.*;
import gov.ca.cwds.cms.data.access.dto.OtherAdultInHomeEntityAwareDTO;
import gov.ca.cwds.cms.data.access.dto.OtherChildInHomeEntityAwareDTO;
import gov.ca.cwds.cms.data.access.dto.PlacementHomeEntityAwareDTO;
import gov.ca.cwds.cms.data.access.dto.SCPEntityAwareDTO;
import gov.ca.cwds.cms.data.access.mapper.CountyOwnershipMapper;
import gov.ca.cwds.cms.data.access.mapper.ExternalInterfaceMapper;
import gov.ca.cwds.cms.data.access.service.DataAccessServicesException;
import gov.ca.cwds.cms.data.access.service.PlacementHomeService;
import gov.ca.cwds.cms.data.access.service.SubstituteCareProviderService;
import gov.ca.cwds.cms.data.access.service.rules.PlacementHomeDroolsConfiguration;
import gov.ca.cwds.cms.data.access.utils.ParametersValidator;
import gov.ca.cwds.data.legacy.cms.dao.SsaName3ParameterObject;
import gov.ca.cwds.data.legacy.cms.entity.*;
import gov.ca.cwds.drools.DroolsException;
import gov.ca.cwds.drools.DroolsService;
import gov.ca.cwds.rest.exception.BusinessValidationException;
import gov.ca.cwds.rest.exception.IssueDetails;
import gov.ca.cwds.security.realm.PerryAccount;
import gov.ca.cwds.security.utils.PrincipalUtils;
import org.apache.commons.lang3.StringUtils;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Date;
import java.util.Set;

import static gov.ca.cwds.cms.data.access.service.impl.IdGenerator.generateId;
import static gov.ca.cwds.cms.data.access.utils.ParametersValidator.checkNotPersisted;
import static gov.ca.cwds.security.utils.PrincipalUtils.getStaffPersonId;

/** @author CWDS CALS API Team */
public class PlacementHomeServiceImpl implements PlacementHomeService {

  @Inject
  protected DroolsService droolsService;

  @Inject
  private PlacementHomeDao placementHomeDao;

  @Inject
  private PlacementHomeUcDao placementHomeUcDao;

  @Inject
  private CountyOwnershipMapper countyOwnershipMapper;

  @Inject
  private CountyOwnershipDao countyOwnershipDao;

  @Inject
  private ExternalInterfaceDao externalInterfaceDao;

  @Inject
  private ExternalInterfaceMapper externalInterfaceMapper;

  @Inject
  private EmergencyContactDetailDao emergencyContactDetailDao;

  @Inject
  private PlacementHomeProfileDao placementHomeProfileDao;

  @Inject
  private SubstituteCareProviderService substituteCareProviderService;

  @Inject
  private OtherChildrenInPlacementHomeDao otherChildrenInPlacementHomeDao;

  @Inject
  private OtherPeopleScpRelationshipDao otherPeopleScpRelationshipDao;

  @Inject
  private OtherAdultsInPlacementHomeDao otherAdultsInPlacementHomeDao;

  @Inject
  private OutOfStateCheckDao outOfStateCheckDao;

  @Inject
  private BackgroundCheckDao backgroundCheckDao;

  @Inject
  private SsaName3Dao ssaName3Dao;

  @Override
  public void runBusinessValidation(
      PlacementHomeEntityAwareDTO placementHomeEntityAwareDTO, PerryAccount principal)
      throws DroolsException {
      runRulesAgendaGroup(placementHomeEntityAwareDTO, PlacementHomeDroolsConfiguration.INSTANCE, principal);
  }

  @Override
  public void runDataProcessing(
          PlacementHomeEntityAwareDTO placementHomeEntityAwareDTO, PerryAccount principal)
          throws DroolsException {
      runRulesAgendaGroup(placementHomeEntityAwareDTO, PlacementHomeDroolsConfiguration.DATA_PROCESSING_INSTANCE, principal);
  }

  private void runRulesAgendaGroup(PlacementHomeEntityAwareDTO placementHomeEntityAwareDTO, PlacementHomeDroolsConfiguration dataProcessingInstance, PerryAccount principal2) throws DroolsException {
      Set<IssueDetails> detailsList =
                droolsService.performBusinessRules(
                        dataProcessingInstance, placementHomeEntityAwareDTO, principal2);
        if (!detailsList.isEmpty()) {
            throw new BusinessValidationException("Can't create Placement Home", detailsList);
      }
  }

  @Override
  public PlacementHome create(PlacementHomeEntityAwareDTO placementHomeEntityAwareDTO)
      throws DataAccessServicesException {
    try {
      validateParameters(placementHomeEntityAwareDTO);
      runDataProcessing(placementHomeEntityAwareDTO, PrincipalUtils.getPrincipal());
      runBusinessValidation(placementHomeEntityAwareDTO, PrincipalUtils.getPrincipal());
      createPlacementHome(placementHomeEntityAwareDTO);
      createPlacementHomeUc(placementHomeEntityAwareDTO);
      createCountyOwnership(placementHomeEntityAwareDTO);
      createExternalInterface();
      createBackgroundCheck();
      createEmergencyContactDetail(placementHomeEntityAwareDTO);
      createPlacementHomeProfile(placementHomeEntityAwareDTO);
      createSubstituteCareProviders(placementHomeEntityAwareDTO);
      createOtherAdultsInHome(placementHomeEntityAwareDTO);
      createOtherChildrenInHome(placementHomeEntityAwareDTO);
      prepareAddressPhoneticSearchKeywords(placementHomeEntityAwareDTO.getEntity());
      return placementHomeEntityAwareDTO.getEntity();
    } catch (DroolsException e) {
      throw new DataAccessServicesException(e);
    }
  }

  @Override
  public PlacementHome update(PlacementHomeEntityAwareDTO entityAwareDTO)
      throws DataAccessServicesException {
    throw new UnsupportedOperationException();
  }

  private void createPlacementHome(PlacementHomeEntityAwareDTO parameterObject) {
    final PlacementHome placementHome = parameterObject.getEntity();
    placementHome.setIdentifier(generateId());
    placementHome.setLastUpdateId(getStaffPersonId());
    placementHome.setLastUpdateTime(LocalDateTime.now());
    placementHomeDao.create(placementHome);
  }

  private void createBackgroundCheck() {
    BackgroundCheck backgroundCheck = new BackgroundCheck();
    backgroundCheck.setIdentifier(IdGenerator.generateId());
    backgroundCheck.setBkgrchkc((short) -1);
    backgroundCheck.setBkgrchkDt(LocalDate.now());
    backgroundCheck.setLstUpdId(getStaffPersonId());
    backgroundCheck.setLstUpdTs(LocalDateTime.now());
    backgroundCheckDao.create(backgroundCheck);
  }

  private void createOtherChildrenInHome(PlacementHomeEntityAwareDTO parameterObject) {
    PlacementHome placementHome = parameterObject.getEntity();
    for (OtherChildInHomeEntityAwareDTO otherChildInHomeParameterObject :
        parameterObject.getOtherChildrenInHomeParameterObjects()) {
      createOtherChildInHome(placementHome, otherChildInHomeParameterObject);
      createChildRelationshipsToScp(otherChildInHomeParameterObject);
    }
  }

  private void createOtherChildInHome(
      PlacementHome placementHome, OtherChildInHomeEntityAwareDTO parameterObject) {
    OtherChildrenInPlacementHome otherChildInPlacementHome = parameterObject.getEntity();
    otherChildInPlacementHome.setLstUpdId(getStaffPersonId());
    otherChildInPlacementHome.setLstUpdTs(LocalDateTime.now());
    otherChildInPlacementHome.setFkplcHmT(placementHome.getIdentifier());
    otherChildInPlacementHome.setIdentifier(generateId());
    otherChildrenInPlacementHomeDao.create(otherChildInPlacementHome);
  }

  private void createChildRelationshipsToScp(OtherChildInHomeEntityAwareDTO parameterObject) {
    OtherChildrenInPlacementHome otherChildInPlacementHome = parameterObject.getEntity();
    for (OtherPeopleScpRelationship relationship : parameterObject.getRelationships()) {
      relationship.setIdentifier(generateId());
      relationship.setFkothKidt(otherChildInPlacementHome.getIdentifier());
      relationship.setLstUpdId(getStaffPersonId());
      relationship.setLstUpdTs(LocalDateTime.now());
      otherPeopleScpRelationshipDao.create(relationship);
    }
  }

  private void createOtherAdultsInHome(PlacementHomeEntityAwareDTO parameterObject) {
    final PlacementHome placementHome = parameterObject.getEntity();
    for (OtherAdultInHomeEntityAwareDTO adultInHomeParameterObject :
        parameterObject.getOtherAdultInHomeParameterObjects()) {
      createOtherAdultInHome(placementHome, adultInHomeParameterObject);
      createAdultRelationshipsToScp(adultInHomeParameterObject);
      createAdultOutOfStateChecks(adultInHomeParameterObject);
    }
  }

  private void createAdultOutOfStateChecks(OtherAdultInHomeEntityAwareDTO parameterObject) {
    OtherAdultsInPlacementHome otherAdultInPlacementHome = parameterObject.getEntity();
    for (OutOfStateCheck outOfStateCheck : parameterObject.getOutOfStateChecks()) {
      outOfStateCheck.setIdentifier(generateId());
      outOfStateCheck.setRcpntCd("O");
      outOfStateCheck.setRcpntId(otherAdultInPlacementHome.getIdentifier());
      outOfStateCheck.setLstUpdId(getStaffPersonId());
      outOfStateCheck.setLstUpdTs(LocalDateTime.now());
      outOfStateCheckDao.create(outOfStateCheck);
    }
  }

  private void createOtherAdultInHome(
      PlacementHome placementHome, OtherAdultInHomeEntityAwareDTO parameterObject) {
    OtherAdultsInPlacementHome otherAdultInPlacementHome = parameterObject.getEntity();
    otherAdultInPlacementHome.setLstUpdId(getStaffPersonId());
    otherAdultInPlacementHome.setLstUpdTs(LocalDateTime.now());
    otherAdultInPlacementHome.setFkplcHmT(placementHome.getIdentifier());
    otherAdultInPlacementHome.setIdentifier(generateId());
    otherAdultsInPlacementHomeDao.create(otherAdultInPlacementHome);
  }

  private void createAdultRelationshipsToScp(OtherAdultInHomeEntityAwareDTO parameterObject) {
    OtherAdultsInPlacementHome otherAdultInPlacementHome = parameterObject.getEntity();
    for (OtherPeopleScpRelationship relationship : parameterObject.getRelationships()) {
      relationship.setIdentifier(generateId());
      relationship.setFkothAdlt(otherAdultInPlacementHome.getIdentifier());
      relationship.setLstUpdId(getStaffPersonId());
      relationship.setLstUpdTs(LocalDateTime.now());
      otherPeopleScpRelationshipDao.create(relationship);
    }
  }

  private void createSubstituteCareProviders(PlacementHomeEntityAwareDTO parameterObject)
      throws DataAccessServicesException {
    final PlacementHome placementHome = parameterObject.getEntity();
    for (SCPEntityAwareDTO scpParameterObject : parameterObject.getScpParameterObjects()) {
      scpParameterObject.setPlacementHomeId(placementHome.getIdentifier());
      SubstituteCareProvider substituteCareProvider =
          substituteCareProviderService.create(scpParameterObject);
      if (scpParameterObject.isPrimaryApplicant()) {
        placementHome.setPrimarySubstituteCareProvider(substituteCareProvider);
      }
    }
  }

  private void validateParameters(PlacementHomeEntityAwareDTO placementHomeParameterObject) {
    checkNotPersisted(placementHomeParameterObject.getEntity());
    ParametersValidator.validateParameterObjects(
        placementHomeParameterObject.getScpParameterObjects());
    ParametersValidator.validateParameterObjects(
        placementHomeParameterObject.getOtherAdultInHomeParameterObjects());
    ParametersValidator.validateParameterObjects(
        placementHomeParameterObject.getOtherChildrenInHomeParameterObjects());
  }

  private void createCountyOwnership(PlacementHomeEntityAwareDTO parameterObject) {
    final PlacementHome placementHome = parameterObject.getEntity();
    CountyOwnership countyOwnership =
        countyOwnershipMapper.toCountyOwnership(
            placementHome.getIdentifier(), "P", Collections.emptyList());
    countyOwnershipDao.create(countyOwnership);
  }

  private void createPlacementHomeUc(PlacementHomeEntityAwareDTO parameterObject) {
    PlacementHomeUc placementHomeUc = new PlacementHomeUc();
    final PlacementHome placementHome = parameterObject.getEntity();
    placementHomeUc.setCityNm(StringUtils.upperCase(placementHome.getCityNm()));
    placementHomeUc.setGeoRgntcd(StringUtils.upperCase(placementHome.getGeoRgntcd()));
    placementHomeUc.setLaVndrId(StringUtils.upperCase(placementHome.getLaVndrId()));
    placementHomeUc.setLicenseNo(StringUtils.upperCase(placementHome.getLicenseNo()));
    placementHomeUc.setFacltyNm(StringUtils.upperCase(placementHome.getFacltyNm()));
    placementHomeUc.setStreetNo(StringUtils.upperCase(placementHome.getStreetNo()));
    placementHomeUc.setStreetNm(StringUtils.upperCase(placementHome.getStreetNm()));
    placementHomeUc.setPkplcHmt(placementHome.getIdentifier());
    placementHomeUc.setLstUpdId(getStaffPersonId());
    placementHomeUc.setLstUpdTs(LocalDateTime.now());
    placementHomeUcDao.create(placementHomeUc);
  }

  private void createExternalInterface() {
    ExternalInterface externalInterface = externalInterfaceMapper.toExternalInterface("");
    externalInterfaceDao.create(externalInterface);
  }

  private void createEmergencyContactDetail(PlacementHomeEntityAwareDTO parameterObject) {
    final PlacementHome placementHome = parameterObject.getEntity();
    EmergencyContactDetail emergencyContactDetail = parameterObject.getEmergencyContactDetail();
    if (emergencyContactDetail != null) {
      emergencyContactDetail.setEstblshCd("P"); // P = PLACEMENT HOME
      emergencyContactDetail.setEstblshId(placementHome.getIdentifier());
      emergencyContactDetail.setLstUpdId(getStaffPersonId());
      emergencyContactDetail.setLstUpdTs(LocalDateTime.now());
      emergencyContactDetail.setIdentifier(generateId());
      emergencyContactDetailDao.create(emergencyContactDetail);
    }
  }

  private void createPlacementHomeProfile(PlacementHomeEntityAwareDTO parameterObject) {
    final PlacementHome placementHome = parameterObject.getEntity();
    for (CWSIdentifier homeLanguage : parameterObject.getHomeLanguages()) {
      PlacementHomeProfile placementHomeProfile = new PlacementHomeProfile();
      placementHomeProfile.setThirdId(generateId());
      placementHomeProfile.setChrctrC((short) homeLanguage.getCwsId());
      placementHomeProfile.setChrctrCd("L");
      placementHomeProfile.setLstUpdId(getStaffPersonId());
      placementHomeProfile.setLstUpdTs(LocalDateTime.now());
      placementHomeProfile.setFkplcHmT(placementHome.getIdentifier());
      placementHomeProfileDao.create(placementHomeProfile);
    }
  }

  private void prepareAddressPhoneticSearchKeywords(PlacementHome placementHome) {
    SsaName3ParameterObject parameterObject = new SsaName3ParameterObject();
    parameterObject.setTableName(PhoneticSearchTables.ADR_PHTT);
    parameterObject.setCrudOper("I");
    parameterObject.setIdentifier(placementHome.getIdentifier());
    parameterObject.setNameCd("P");
    parameterObject.setStreetNumber(placementHome.getStreetNo());
    parameterObject.setStreetName(placementHome.getStreetNm());
    parameterObject.setGvrEntc(placementHome.getGvrEntc());
    parameterObject.setUpdateTimeStamp(new Date());
    parameterObject.setUpdateId(placementHome.getLastUpdateId());
    ssaName3Dao.callStoredProc(parameterObject);
  }

  void setDroolsService(DroolsService droolsService) {
    this.droolsService = droolsService;
  }
}
