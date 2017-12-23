package gov.ca.cwds.cms.data.access.service.impl;

import com.google.inject.Inject;
import gov.ca.cwds.cms.data.access.CWSIdentifier;
import gov.ca.cwds.cms.data.access.Constants;
import gov.ca.cwds.cms.data.access.Constants.PhoneticSearchTables;
import gov.ca.cwds.cms.data.access.dao.*;
import gov.ca.cwds.cms.data.access.dto.SCPEntityAwareDTO;
import gov.ca.cwds.cms.data.access.mapper.CountyOwnershipMapper;
import gov.ca.cwds.cms.data.access.service.DataAccessServicesException;
import gov.ca.cwds.cms.data.access.service.SubstituteCareProviderService;
import gov.ca.cwds.cms.data.access.utils.ParametersValidator;
import gov.ca.cwds.data.legacy.cms.dao.SsaName3ParameterObject;
import gov.ca.cwds.data.legacy.cms.entity.*;
import gov.ca.cwds.drools.DroolsException;
import gov.ca.cwds.security.utils.PrincipalUtils;
import org.apache.commons.collections4.CollectionUtils;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Date;

import static gov.ca.cwds.cms.data.access.utils.ParametersValidator.checkNotPersisted;
import static gov.ca.cwds.security.utils.PrincipalUtils.getStaffPersonId;
import static org.apache.commons.lang3.StringUtils.upperCase;

/**
 * @author CWDS CALS API Team
 */

public class SubstituteCareProviderServiceImpl implements SubstituteCareProviderService {

  @Inject
  private SubstituteCareProviderDao substituteCareProviderDao;

  @Inject
  private SubstituteCareProviderUcDao substituteCareProviderUcDao;

  @Inject
  private CountyOwnershipDao countyOwnershipDao;

  @Inject
  private CountyOwnershipMapper countyOwnershipMapper;

  @Inject
  private PlacementHomeInformationDao placementHomeInformationDao;

  @Inject
  private PhoneContactDetailDao phoneContactDetailDao;

  @Inject
  private SsaName3Dao scpSsaName3Dao;

  @Inject
  private ClientScpEthnicityDao clientScpEthnicityDao;

  @Inject
  private OutOfStateCheckDao outOfStateCheckDao;

  @Override
  public SubstituteCareProvider create(SCPEntityAwareDTO scpEntityAwareDTO)
      throws DataAccessServicesException {
    try {
      final SubstituteCareProvider substituteCareProvider = scpEntityAwareDTO.getEntity();
      validateParameters(scpEntityAwareDTO);
      substituteCareProvider
          .setIdentifier(IdGenerator.generateId());
      runBusinessValidation(scpEntityAwareDTO, PrincipalUtils.getPrincipal());
      SubstituteCareProvider storedSubstituteCareProvider = substituteCareProviderDao
          .create(substituteCareProvider);
      storeSubstituteCareProviderUc(substituteCareProvider, scpEntityAwareDTO);
      storeCountyOwnership(substituteCareProvider.getIdentifier());
      storePlacementHomeInformation(substituteCareProvider, scpEntityAwareDTO);
      storePhoneContactDetails(storedSubstituteCareProvider, scpEntityAwareDTO);
      storeEthnicity(storedSubstituteCareProvider, scpEntityAwareDTO);
      storeOutOfStateChecks(storedSubstituteCareProvider, scpEntityAwareDTO);
      prepareSubstituteCareProviderPhoneticSearchKeywords(substituteCareProvider);
      return storedSubstituteCareProvider;
    } catch (DroolsException e) {
      throw new DataAccessServicesException(e);
    }
  }

  private void validateParameters(SCPEntityAwareDTO parameterObject) {
    checkNotPersisted(parameterObject.getEntity());
    ParametersValidator.validatePersistentObjects(parameterObject.getPhoneNumbers());
  }

  private void storeOutOfStateChecks(SubstituteCareProvider storedSubstituteCareProvider,
      SCPEntityAwareDTO parameterObject) {
    if (CollectionUtils.isNotEmpty(parameterObject.getOtherStatesOfLiving())) {
      for (CWSIdentifier stateId : parameterObject.getOtherStatesOfLiving()) {
        OutOfStateCheck outOfStateCheck = new OutOfStateCheck();
        outOfStateCheck.setStateC((short)(stateId.getCwsId()));
        outOfStateCheck.setRcpntId(storedSubstituteCareProvider.getIdentifier());
        outOfStateCheck.setRcpntCd("S");
        outOfStateCheck.setIdentifier(IdGenerator.generateId());
        outOfStateCheck.setLstUpdId(getStaffPersonId());
        outOfStateCheck.setLstUpdTs(LocalDateTime.now());
        outOfStateCheckDao.create(outOfStateCheck);
      }
    }
  }

  private void storeEthnicity(SubstituteCareProvider storedSubstituteCareProvider,
      SCPEntityAwareDTO parameterObject) {
    ClientScpEthnicity clientScpEthnicity = new ClientScpEthnicity();
    clientScpEthnicity.setEthnctyc((short)parameterObject.getEthnicity().getCwsId());
    clientScpEthnicity.setEstblshId(storedSubstituteCareProvider.getIdentifier());
    clientScpEthnicity.setEstblshCd("S");
    clientScpEthnicity.setIdentifier(IdGenerator.generateId());
    clientScpEthnicity.setLstUpdId(getStaffPersonId());
    clientScpEthnicity.setLstUpdTs(LocalDateTime.now());
    clientScpEthnicityDao.create(clientScpEthnicity);
  }

  private void prepareSubstituteCareProviderPhoneticSearchKeywords(
      SubstituteCareProvider substituteCareProvider) {
    SsaName3ParameterObject parameterObject = new SsaName3ParameterObject();
    parameterObject.setTableName(PhoneticSearchTables.SCP_PHTT);
    parameterObject.setCrudOper("I");
    parameterObject.setIdentifier(substituteCareProvider.getIdentifier());
    parameterObject.setFirstName(substituteCareProvider.getFirstNm());
    parameterObject.setMiddleName(substituteCareProvider.getMidIniNm());
    parameterObject.setLastName(substituteCareProvider.getLastNm());
    parameterObject.setStreetNumber("");
    parameterObject.setStreetName("");
    parameterObject.setGvrEntc((short) 0);
    parameterObject.setUpdateTimeStamp(new Date());
    parameterObject.setUpdateId(substituteCareProvider.getLstUpdId());
    scpSsaName3Dao.callStoredProc(parameterObject);
  }

  private void storePhoneContactDetails(SubstituteCareProvider substituteCareProvider,
      SCPEntityAwareDTO parameterObject) {
    if (CollectionUtils.isNotEmpty(parameterObject.getPhoneNumbers())) {
      parameterObject.getPhoneNumbers()
          .forEach(phoneNumber -> {
            phoneNumber.setEstblshId(substituteCareProvider.getIdentifier());
            phoneNumber.setThirdId(IdGenerator.generateId());
            phoneContactDetailDao.create(phoneNumber);
          });
    }
  }

  private void storePlacementHomeInformation(SubstituteCareProvider substituteCareProvider,
      SCPEntityAwareDTO parameterObject) {
    PlacementHomeInformation placementHomeInformation = new PlacementHomeInformation();
    placementHomeInformation.setThirdId(IdGenerator.generateId());
    placementHomeInformation.setStartDt(LocalDate.now());
    placementHomeInformation.setLicnseeCd("U");
    placementHomeInformation.setCrprvdrCd("Y");
    placementHomeInformation.setLstUpdId(getStaffPersonId());
    placementHomeInformation.setLstUpdTs(LocalDateTime.now());
    placementHomeInformation.setFksbPvdrt(substituteCareProvider.getIdentifier());
    placementHomeInformation.setFkplcHmT(parameterObject.getPlacementHomeId());
    placementHomeInformation
        .setPrprvdrCd(parameterObject.isPrimaryApplicant() ? Constants.Y : Constants.N);
    placementHomeInformation.setCdsPrsn(Constants.SPACE);
    placementHomeInformation
        .setScprvdInd(parameterObject.isPrimaryApplicant() ? Constants.N : Constants.Y);
    placementHomeInformationDao.create(placementHomeInformation);
  }

  private void storeCountyOwnership(String scpIdentifier) {
    CountyOwnership countyOwnership =
        countyOwnershipMapper.toCountyOwnership(scpIdentifier,
            "S", Collections.emptyList());
    countyOwnershipDao.create(countyOwnership);
  }

  private void storeSubstituteCareProviderUc(SubstituteCareProvider substituteCareProvider,
      SCPEntityAwareDTO parameterObject) {
    SubstituteCareProviderUc substituteCareProviderUc = new SubstituteCareProviderUc();
    substituteCareProviderUc.setPksbPvdrt(substituteCareProvider.getIdentifier());
    substituteCareProviderUc.setCaDlicNo(upperCase(substituteCareProvider.getCaDlicNo()));
    substituteCareProviderUc.setFirstNm(upperCase(substituteCareProvider.getFirstNm()));
    substituteCareProviderUc.setLastNm(upperCase(substituteCareProvider.getLastNm()));
    substituteCareProviderUc.setLstUpdId(getStaffPersonId());
    substituteCareProviderUc.setLstUpdTs(LocalDateTime.now());
    substituteCareProviderUcDao.create(substituteCareProviderUc);
  }

}
