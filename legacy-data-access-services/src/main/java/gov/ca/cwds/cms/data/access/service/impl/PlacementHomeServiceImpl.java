package gov.ca.cwds.cms.data.access.service.impl;

import static gov.ca.cwds.cms.data.access.service.impl.IdGenerator.generateId;
import static gov.ca.cwds.cms.data.access.utils.ParametersValidator.checkNotPersisted;

import com.google.inject.Inject;
import gov.ca.cwds.cms.data.access.CWSIdentifier;
import gov.ca.cwds.cms.data.access.Constants.PhoneticSearchTables;
import gov.ca.cwds.cms.data.access.dao.BackgroundCheckDao;
import gov.ca.cwds.cms.data.access.dao.CountyOwnershipDao;
import gov.ca.cwds.cms.data.access.dao.EmergencyContactDetailDao;
import gov.ca.cwds.cms.data.access.dao.ExternalInterfaceDao;
import gov.ca.cwds.cms.data.access.dao.OtherAdultsInPlacementHomeDao;
import gov.ca.cwds.cms.data.access.dao.OtherChildrenInPlacementHomeDao;
import gov.ca.cwds.cms.data.access.dao.OtherPeopleScpRelationshipDao;
import gov.ca.cwds.cms.data.access.dao.OutOfStateCheckDao;
import gov.ca.cwds.cms.data.access.dao.PlacementHomeDao;
import gov.ca.cwds.cms.data.access.dao.PlacementHomeProfileDao;
import gov.ca.cwds.cms.data.access.dao.PlacementHomeUcDao;
import gov.ca.cwds.cms.data.access.dao.SsaName3Dao;
import gov.ca.cwds.cms.data.access.mapper.CountyOwnershipMapper;
import gov.ca.cwds.cms.data.access.mapper.ExternalInterfaceMapper;
import gov.ca.cwds.cms.data.access.parameter.OtherAdultInHomeParameterObject;
import gov.ca.cwds.cms.data.access.parameter.OtherChildInHomeParameterObject;
import gov.ca.cwds.cms.data.access.parameter.PlacementHomeParameterObject;
import gov.ca.cwds.cms.data.access.parameter.SCPParameterObject;
import gov.ca.cwds.cms.data.access.service.PlacementHomeService;
import gov.ca.cwds.cms.data.access.service.SubstituteCareProviderService;
import gov.ca.cwds.cms.data.access.utils.ParametersValidator;
import gov.ca.cwds.data.legacy.cms.dao.SsaName3ParameterObject;
import gov.ca.cwds.data.legacy.cms.entity.BackgroundCheck;
import gov.ca.cwds.data.legacy.cms.entity.CountyOwnership;
import gov.ca.cwds.data.legacy.cms.entity.EmergencyContactDetail;
import gov.ca.cwds.data.legacy.cms.entity.ExternalInterface;
import gov.ca.cwds.data.legacy.cms.entity.OtherAdultsInPlacementHome;
import gov.ca.cwds.data.legacy.cms.entity.OtherChildrenInPlacementHome;
import gov.ca.cwds.data.legacy.cms.entity.OtherPeopleScpRelationship;
import gov.ca.cwds.data.legacy.cms.entity.OutOfStateCheck;
import gov.ca.cwds.data.legacy.cms.entity.PlacementHome;
import gov.ca.cwds.data.legacy.cms.entity.PlacementHomeProfile;
import gov.ca.cwds.data.legacy.cms.entity.PlacementHomeUc;
import gov.ca.cwds.data.legacy.cms.entity.SubstituteCareProvider;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Date;
import org.apache.commons.lang3.StringUtils;

/**
 * @author CWDS CALS API Team
 */

public class PlacementHomeServiceImpl implements PlacementHomeService {

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
  private SsaName3Dao ssaName3Dao;

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

  @Override
  public PlacementHome create(PlacementHomeParameterObject parameterObject) {
    final PlacementHome placementHome = parameterObject.getEntity();
    validateParameters(parameterObject);
    runBusinessValidation(placementHome);
    createPlacementHome(parameterObject);
    createPlacementHomeUc(parameterObject);
    createCountyOwnership(parameterObject);
    createExternalInterface();
    createBackgroundCheck(parameterObject);
    createEmergencyContactDetail(parameterObject);
    createPlacementHomeProfile(parameterObject);
    createSubstituteCareProviders(parameterObject);
    createOtherAdultsInHome(parameterObject);
    createOtherChildrenInHome(parameterObject);
    prepareAddressPhoneticSearchKeywords(parameterObject.getEntity());
    return parameterObject.getEntity();
  }

  private void createPlacementHome(PlacementHomeParameterObject parameterObject) {
    final PlacementHome placementHome = parameterObject.getEntity() ;
    placementHome.setIdentifier(generateId(parameterObject.getStaffPersonId()));
    placementHome.setLastUpdatedId(parameterObject.getStaffPersonId());
    placementHome.setLastUpdatedTime(LocalDateTime.now());
    placementHomeDao.create(placementHome);
  }

  private void createBackgroundCheck(
      PlacementHomeParameterObject parameterObject) {
    BackgroundCheck backgroundCheck = new BackgroundCheck();
    backgroundCheck.setIdentifier(IdGenerator.generateId(parameterObject.getStaffPersonId()));
    backgroundCheck.setBkgrchkc((short)-1);
    backgroundCheck.setBkgrchkDt(LocalDate.now());
    backgroundCheck.setLstUpdId(parameterObject.getStaffPersonId());
    backgroundCheck.setLstUpdTs(LocalDateTime.now());
    backgroundCheckDao.create(backgroundCheck);
  }

  private void createOtherChildrenInHome(PlacementHomeParameterObject parameterObject) {
    PlacementHome placementHome = parameterObject.getEntity();
    for (OtherChildInHomeParameterObject otherChildInHomeParameterObject : parameterObject
        .getOtherChildrenInHomeParameterObjects()) {
      createOtherChildInHome(placementHome, otherChildInHomeParameterObject);
      createChildRelationshipsToScp(otherChildInHomeParameterObject);
    }
  }

  private void createOtherChildInHome(PlacementHome placementHome,
      OtherChildInHomeParameterObject parameterObject) {
    OtherChildrenInPlacementHome otherChildInPlacementHome = parameterObject.getEntity();
    otherChildInPlacementHome.setLstUpdId(parameterObject.getStaffPersonId());
    otherChildInPlacementHome.setLstUpdTs(LocalDateTime.now());
    otherChildInPlacementHome.setFkplcHmT(placementHome.getIdentifier());
    otherChildInPlacementHome
        .setIdentifier(generateId(parameterObject.getStaffPersonId()));
    otherChildrenInPlacementHomeDao.create(otherChildInPlacementHome);
  }

  private void createChildRelationshipsToScp(OtherChildInHomeParameterObject parameterObject) {
    OtherChildrenInPlacementHome otherChildInPlacementHome = parameterObject.getEntity();
    for (OtherPeopleScpRelationship relationship: parameterObject.getRelationships()) {
      relationship.setIdentifier(generateId(parameterObject.getStaffPersonId()));
      relationship.setFkothKidt(otherChildInPlacementHome.getIdentifier());
      relationship.setLstUpdId(parameterObject.getStaffPersonId());
      relationship.setLstUpdTs(LocalDateTime.now());
      otherPeopleScpRelationshipDao.create(relationship);
    }
  }

  private void createOtherAdultsInHome(PlacementHomeParameterObject parameterObject) {
    final PlacementHome placementHome = parameterObject.getEntity();
    for (OtherAdultInHomeParameterObject adultInHomeParameterObject : parameterObject
        .getOtherAdultInHomeParameterObjects()) {
      createOtherAdultInHome(placementHome, adultInHomeParameterObject);
      createAdultRelationshipsToScp(adultInHomeParameterObject);
      createAdultOutOfStateChecks(adultInHomeParameterObject);
    }
  }

  private void createAdultOutOfStateChecks(OtherAdultInHomeParameterObject parameterObject) {
    OtherAdultsInPlacementHome otherAdultInPlacementHome = parameterObject.getEntity();
    for (OutOfStateCheck outOfStateCheck: parameterObject.getOutOfStateChecks()) {
      outOfStateCheck.setIdentifier(generateId(parameterObject.getStaffPersonId()));
      outOfStateCheck.setRcpntCd("O");
      outOfStateCheck.setRcpntId(otherAdultInPlacementHome.getIdentifier());
      outOfStateCheck.setLstUpdId(parameterObject.getStaffPersonId());
      outOfStateCheck.setLstUpdTs(LocalDateTime.now());
      outOfStateCheckDao.create(outOfStateCheck);
    }
  }

  private void createOtherAdultInHome(PlacementHome placementHome,
      OtherAdultInHomeParameterObject parameterObject) {
    OtherAdultsInPlacementHome otherAdultInPlacementHome = parameterObject.getEntity();
    otherAdultInPlacementHome.setLstUpdId(parameterObject.getStaffPersonId());
    otherAdultInPlacementHome.setLstUpdTs(LocalDateTime.now());
    otherAdultInPlacementHome.setFkplcHmT(placementHome.getIdentifier());
    otherAdultInPlacementHome.setIdentifier(generateId(parameterObject.getStaffPersonId()));
    otherAdultsInPlacementHomeDao.create(otherAdultInPlacementHome);
  }

  private void createAdultRelationshipsToScp(OtherAdultInHomeParameterObject parameterObject) {
    OtherAdultsInPlacementHome otherAdultInPlacementHome = parameterObject.getEntity();
    for (OtherPeopleScpRelationship relationship: parameterObject.getRelationships()) {
      relationship.setIdentifier(generateId(parameterObject.getStaffPersonId()));
      relationship.setFkothAdlt(otherAdultInPlacementHome.getIdentifier());
      relationship.setLstUpdId(parameterObject.getStaffPersonId());
      relationship.setLstUpdTs(LocalDateTime.now());
      otherPeopleScpRelationshipDao.create(relationship);
    }
  }

  private void createSubstituteCareProviders(PlacementHomeParameterObject parameterObject) {
    final PlacementHome placementHome = parameterObject.getEntity();
    for (SCPParameterObject scpParameterObject: parameterObject.getScpParameterObjects()) {
      scpParameterObject.setPlacementHomeId(placementHome.getIdentifier());
      SubstituteCareProvider substituteCareProvider = substituteCareProviderService.create(scpParameterObject);
      if (scpParameterObject.isPrimaryApplicant()) {
        placementHome.setPrimarySubstituteCareProvider(substituteCareProvider);
      }
    }
  }

  private void validateParameters(PlacementHomeParameterObject placementHomeParameterObject) {
    checkNotPersisted(placementHomeParameterObject.getEntity());
    ParametersValidator.validateParameterObjects(placementHomeParameterObject.getScpParameterObjects());
    ParametersValidator.validateParameterObjects(placementHomeParameterObject.getOtherAdultInHomeParameterObjects());
    ParametersValidator.validateParameterObjects(placementHomeParameterObject.getOtherChildrenInHomeParameterObjects());
  }

  private void createCountyOwnership(PlacementHomeParameterObject parameterObject) {
    final PlacementHome placementHome = parameterObject.getEntity();
    CountyOwnership countyOwnership =
        countyOwnershipMapper.toCountyOwnership(placementHome.getIdentifier(),
            "P", Collections.emptyList());
    countyOwnershipDao.create(countyOwnership);
  }

  private void createPlacementHomeUc(PlacementHomeParameterObject parameterObject) {
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
    placementHomeUc.setLstUpdId(parameterObject.getStaffPersonId());
    placementHomeUc.setLstUpdTs(LocalDateTime.now());
    placementHomeUcDao.create(placementHomeUc);
  }

  private void createExternalInterface() {
    ExternalInterface externalInterface = externalInterfaceMapper.toExternalInterface("");
    externalInterfaceDao.create(externalInterface);
  }

  private void createEmergencyContactDetail(PlacementHomeParameterObject parameterObject) {
    final PlacementHome placementHome = parameterObject.getEntity();
    EmergencyContactDetail emergencyContactDetail = parameterObject.getEmergencyContactDetail();
    if (emergencyContactDetail != null) {
      emergencyContactDetail.setEstblshCd("P");  //P = PLACEMENT HOME
      emergencyContactDetail.setEstblshId(placementHome.getIdentifier());
      emergencyContactDetail.setLstUpdId(parameterObject.getStaffPersonId());
      emergencyContactDetail.setLstUpdTs(LocalDateTime.now());
      emergencyContactDetail
          .setIdentifier(generateId(parameterObject.getStaffPersonId()));
      emergencyContactDetailDao.create(emergencyContactDetail);
    }
  }

  private void createPlacementHomeProfile(PlacementHomeParameterObject parameterObject) {
    final PlacementHome placementHome = parameterObject.getEntity();
    for (CWSIdentifier homeLanguage : parameterObject.getHomeLanguages()) {
      PlacementHomeProfile placementHomeProfile = new PlacementHomeProfile();
      placementHomeProfile.setThirdId(generateId(parameterObject.getStaffPersonId()));
      placementHomeProfile.setChrctrC((short) homeLanguage.getCwsId());
      placementHomeProfile.setChrctrCd("L");
      placementHomeProfile.setLstUpdId(parameterObject.getStaffPersonId());
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

}
