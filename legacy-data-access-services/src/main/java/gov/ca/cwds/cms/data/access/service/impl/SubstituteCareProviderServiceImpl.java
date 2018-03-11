package gov.ca.cwds.cms.data.access.service.impl;

import static gov.ca.cwds.cms.data.access.utils.ParametersValidator.checkNotPersisted;
import static gov.ca.cwds.security.utils.PrincipalUtils.getStaffPersonId;
import static org.apache.commons.lang3.StringUtils.upperCase;

import com.google.inject.Inject;
import gov.ca.cwds.cms.data.access.CWSIdentifier;
import gov.ca.cwds.cms.data.access.Constants;
import gov.ca.cwds.cms.data.access.Constants.PhoneticSearchTables;
import gov.ca.cwds.cms.data.access.dao.ScpOtherEthnicityDao;
import gov.ca.cwds.cms.data.access.dao.CountyOwnershipDao;
import gov.ca.cwds.cms.data.access.dao.OutOfStateCheckDao;
import gov.ca.cwds.cms.data.access.dao.PhoneContactDetailDao;
import gov.ca.cwds.cms.data.access.dao.PlacementHomeInformationDao;
import gov.ca.cwds.cms.data.access.dao.SsaName3Dao;
import gov.ca.cwds.cms.data.access.dao.SubstituteCareProviderDao;
import gov.ca.cwds.cms.data.access.dao.SubstituteCareProviderUcDao;
import gov.ca.cwds.cms.data.access.dto.ExtendedSCPEntityAwareDTO;
import gov.ca.cwds.cms.data.access.dto.SCPEntityAwareDTO;
import gov.ca.cwds.cms.data.access.mapper.CountyOwnershipMapper;
import gov.ca.cwds.cms.data.access.service.DataAccessServicesException;
import gov.ca.cwds.cms.data.access.service.SubstituteCareProviderService;
import gov.ca.cwds.cms.data.access.service.rules.SubstituteCareProviderDroolsConfiguration;
import gov.ca.cwds.cms.data.access.utils.ParametersValidator;
import gov.ca.cwds.data.legacy.cms.dao.SsaName3ParameterObject;
import gov.ca.cwds.data.legacy.cms.entity.CountyOwnership;
import gov.ca.cwds.data.legacy.cms.entity.OutOfStateCheck;
import gov.ca.cwds.data.legacy.cms.entity.PhoneContactDetail;
import gov.ca.cwds.data.legacy.cms.entity.PlacementHomeInformation;
import gov.ca.cwds.data.legacy.cms.entity.ScpOtherEthnicity;
import gov.ca.cwds.data.legacy.cms.entity.SubstituteCareProvider;
import gov.ca.cwds.data.legacy.cms.entity.SubstituteCareProviderUc;
import gov.ca.cwds.drools.DroolsException;
import gov.ca.cwds.drools.DroolsService;
import gov.ca.cwds.rest.exception.BusinessValidationException;
import gov.ca.cwds.rest.exception.IssueDetails;
import gov.ca.cwds.security.annotations.Authorize;
import gov.ca.cwds.security.realm.PerryAccount;
import gov.ca.cwds.security.utils.PrincipalUtils;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import org.apache.commons.collections4.CollectionUtils;

/**
 * @author CWDS CALS API Team
 */

public class SubstituteCareProviderServiceImpl implements SubstituteCareProviderService {

  @Inject
  private DroolsService droolsService;

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
  private ScpOtherEthnicityDao scpOtherEthnicityDao;

  @Inject
  private OutOfStateCheckDao outOfStateCheckDao;


  @Override
  public void runBusinessValidation(SCPEntityAwareDTO entityAwareDTO, PerryAccount principal)
      throws DroolsException {
    // Perform validation rules
    Set<IssueDetails> detailsList = droolsService.performBusinessRules(
        SubstituteCareProviderDroolsConfiguration.INSTANCE, entityAwareDTO, principal);

    if (!detailsList.isEmpty()) {
      throw new BusinessValidationException(
          "Business rules validation is failed for SubstituteCareProvider", detailsList);
    }
  }

  @Override
  public void runDataProcessing(SCPEntityAwareDTO entityAwareDTO, PerryAccount principal)
      throws DroolsException {
    // Perform data processing roles
    droolsService.performBusinessRules(
        SubstituteCareProviderDroolsConfiguration.DATA_PROCESSING_INSTANCE, entityAwareDTO,
        principal);
  }


  /**
   *
   * Creates SubstituteCareProvider and dependent entities
   *
   * @param scpEntityAwareDTO
   * @return a substitute care provider
   * @throws DataAccessServicesException
   */
  @Override
  public SubstituteCareProvider create(@Authorize("substituteCareProvider:create:scpEntityAwareDTO.entity") SCPEntityAwareDTO scpEntityAwareDTO)
      throws DataAccessServicesException {
    try {
      validateParameters(scpEntityAwareDTO);
      ExtendedSCPEntityAwareDTO extScpEntityAwareDTO = enrichSCPEntityAwareDTO(scpEntityAwareDTO);
      final SubstituteCareProvider substituteCareProvider = extScpEntityAwareDTO.getEntity();
      applyBusinessRules(extScpEntityAwareDTO);
      SubstituteCareProvider storedSubstituteCareProvider =
          substituteCareProviderDao.create(substituteCareProvider);
      substituteCareProviderUcDao.create(extScpEntityAwareDTO.getSubstituteCareProviderUc());
      placementHomeInformationDao.create(extScpEntityAwareDTO.getPlacementHomeInformation());

      storeEthnicities(extScpEntityAwareDTO);
      storeCountyOwnership(substituteCareProvider.getIdentifier());
      storePhoneContactDetails(extScpEntityAwareDTO.getPhoneNumbers());
      storeOutOfStateChecks(extScpEntityAwareDTO);

      prepareSubstituteCareProviderPhoneticSearchKeywords(substituteCareProvider);
      return storedSubstituteCareProvider;
    } catch (DroolsException e) {
      throw new DataAccessServicesException(e);
    }
  }

  /**
   * DocTool rule R - 07045 implemented in this method
   *
   * Rule Txt: For each ethnicity selected by the user in the Ethnicity dialog, create a
   * CLIENT_SCP_ETHNICITY row.
   *
   * Logic: Create CLIENT_SCP_ETHNICITY. Set .Established_For_Code = S, and set .Established_For_Id
   * = SUBSTITUTE_CARE_PROVIDER.Id. Set .Ethnicity_Type = (user selected) Ethnicity Type.
   *
   * @param extScpEntityAwareDTO
   */
  private void storeEthnicities(ExtendedSCPEntityAwareDTO extScpEntityAwareDTO) {
    extScpEntityAwareDTO.getClientScpEthnicities().forEach(clientScpEthnicity -> {
      scpOtherEthnicityDao.create(clientScpEthnicity);
    });
  }

  private void applyBusinessRules(ExtendedSCPEntityAwareDTO extScpEntityAwareDTO)
      throws DroolsException {
    PerryAccount principal = PrincipalUtils.getPrincipal();
    runDataProcessing(extScpEntityAwareDTO, principal);
    runBusinessValidation(extScpEntityAwareDTO, principal);
  }

  /**
   * Build all entities which are required for creating SubstituteCareProvider entity
   */
  private ExtendedSCPEntityAwareDTO enrichSCPEntityAwareDTO(SCPEntityAwareDTO scpEntityAwareDTO) {
    ExtendedSCPEntityAwareDTO dto = new ExtendedSCPEntityAwareDTO(scpEntityAwareDTO);
    final SubstituteCareProvider substituteCareProvider = dto.getEntity();
    substituteCareProvider.setIdentifier(IdGenerator.generateId());
    dto.setSubstituteCareProviderUc(buildSubstituteCareProviderUc(dto.getEntity()));
    dto.setPlacementHomeInformation(buildPlacementHomeInformation(dto.getEntity(), dto));
    enrichPhoneContactDetails(dto);

    List<ScpOtherEthnicity> clientScpEthnicities = dto.getEthnicityList().stream()
        .map(ethnicity -> buildScpOtherEthnicity(ethnicity, dto.getEntity().getIdentifier()))
        .collect(Collectors.toList());

    dto.setClientScpEthnicities(clientScpEthnicities);
    enrichOutOfStateChecks(dto);
    return dto;
  }

  private SubstituteCareProviderUc buildSubstituteCareProviderUc(
      SubstituteCareProvider substituteCareProvider) {
    SubstituteCareProviderUc substituteCareProviderUc = new SubstituteCareProviderUc();
    substituteCareProviderUc.setPksbPvdrt(substituteCareProvider.getIdentifier());
    substituteCareProviderUc.setCaDlicNo(upperCase(substituteCareProvider.getCaDlicNo()));
    substituteCareProviderUc.setFirstNm(upperCase(substituteCareProvider.getFirstNm()));
    substituteCareProviderUc.setLastNm(upperCase(substituteCareProvider.getLastNm()));
    substituteCareProviderUc.setLstUpdId(getStaffPersonId());
    substituteCareProviderUc.setLstUpdTs(LocalDateTime.now());
    return substituteCareProviderUc;
  }

  private PlacementHomeInformation buildPlacementHomeInformation(
      SubstituteCareProvider substituteCareProvider, SCPEntityAwareDTO scpEntityAwareDTO) {
    PlacementHomeInformation placementHomeInformation = new PlacementHomeInformation();
    placementHomeInformation.setThirdId(IdGenerator.generateId());
    placementHomeInformation.setStartDt(LocalDate.now());
    placementHomeInformation.setLicnseeCd("U");
    placementHomeInformation.setCrprvdrCd("Y");
    placementHomeInformation.setLstUpdId(getStaffPersonId());
    placementHomeInformation.setLstUpdTs(LocalDateTime.now());
    placementHomeInformation.setFksbPvdrt(substituteCareProvider.getIdentifier());
    placementHomeInformation.setFkplcHmT(scpEntityAwareDTO.getPlacementHomeId());
    placementHomeInformation
        .setPrprvdrCd(scpEntityAwareDTO.isPrimaryApplicant() ? Constants.Y : Constants.N);
    placementHomeInformation.setCdsPrsn(Constants.SPACE);
    placementHomeInformation
        .setScprvdInd(scpEntityAwareDTO.isPrimaryApplicant() ? Constants.N : Constants.Y);

    return placementHomeInformation;
  }

  private void enrichPhoneContactDetails(ExtendedSCPEntityAwareDTO scpDto) {
    if (CollectionUtils.isNotEmpty(scpDto.getPhoneNumbers())) {
      scpDto.getPhoneNumbers().forEach(phoneNumber -> {
        phoneNumber.setEstblshId(scpDto.getEntity().getIdentifier());
        phoneNumber.setThirdId(IdGenerator.generateId());
      });
    }
  }

  private ScpOtherEthnicity buildScpOtherEthnicity(CWSIdentifier ethnicity, String scpId) {
    ScpOtherEthnicity clientScpEthnicity = new ScpOtherEthnicity();
    clientScpEthnicity.setEthnicityCode((short) ethnicity.getCwsId());
    clientScpEthnicity.setSubstituteCareProviderId(scpId);
    clientScpEthnicity.setId(IdGenerator.generateId());
    clientScpEthnicity.setLastUpdateId(getStaffPersonId());
    clientScpEthnicity.setLastUpdateTime(LocalDateTime.now());
    return clientScpEthnicity;
  }

  private void enrichOutOfStateChecks(ExtendedSCPEntityAwareDTO dto) {
    if (CollectionUtils.isNotEmpty(dto.getOtherStatesOfLiving())) {
      List<OutOfStateCheck> outOfStateChecks = new ArrayList<>(dto.getOtherStatesOfLiving().size());
      for (CWSIdentifier stateId : dto.getOtherStatesOfLiving()) {
        OutOfStateCheck outOfStateCheck = new OutOfStateCheck();
        outOfStateCheck.setStateC((short) (stateId.getCwsId()));
        outOfStateCheck.setRcpntId(dto.getEntity().getIdentifier());
        outOfStateCheck.setRcpntCd("S");
        outOfStateCheck.setIdentifier(IdGenerator.generateId());
        outOfStateCheck.setLstUpdId(getStaffPersonId());
        outOfStateCheck.setLstUpdTs(LocalDateTime.now());
        outOfStateChecks.add(outOfStateCheck);
      }
      dto.setOutOfStateChecks(outOfStateChecks);
    }
  }

  @Override
  public SubstituteCareProvider update(SCPEntityAwareDTO entityAwareDTO)
      throws DataAccessServicesException {
    throw new UnsupportedOperationException();
  }

  private void validateParameters(SCPEntityAwareDTO parameterObject) {
    checkNotPersisted(parameterObject.getEntity());
    ParametersValidator.validatePersistentObjects(parameterObject.getPhoneNumbers());
  }

  private void storeOutOfStateChecks(ExtendedSCPEntityAwareDTO dto) {
    if (CollectionUtils.isNotEmpty(dto.getOutOfStateChecks())) {
      dto.getOutOfStateChecks()
          .forEach(outOfStateCheck -> outOfStateCheckDao.create(outOfStateCheck));
    }
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

  private void storePhoneContactDetails(List<PhoneContactDetail> phoneContactDetails) {
    if (CollectionUtils.isNotEmpty(phoneContactDetails)) {
      phoneContactDetails.forEach(phoneNumber -> phoneContactDetailDao.create(phoneNumber));
    }
  }

  private void storeCountyOwnership(String scpIdentifier) {
    CountyOwnership countyOwnership =
        countyOwnershipMapper.toCountyOwnership(scpIdentifier, "S", Collections.emptyList());
    countyOwnershipDao.create(countyOwnership);
  }

  public void setSubstituteCareProviderDao(SubstituteCareProviderDao substituteCareProviderDao) {
    this.substituteCareProviderDao = substituteCareProviderDao;
  }

  public void setSubstituteCareProviderUcDao(
      SubstituteCareProviderUcDao substituteCareProviderUcDao) {
    this.substituteCareProviderUcDao = substituteCareProviderUcDao;
  }

  public void setCountyOwnershipDao(CountyOwnershipDao countyOwnershipDao) {
    this.countyOwnershipDao = countyOwnershipDao;
  }

  public void setCountyOwnershipMapper(CountyOwnershipMapper countyOwnershipMapper) {
    this.countyOwnershipMapper = countyOwnershipMapper;
  }

  public void setPlacementHomeInformationDao(
      PlacementHomeInformationDao placementHomeInformationDao) {
    this.placementHomeInformationDao = placementHomeInformationDao;
  }

  public void setPhoneContactDetailDao(PhoneContactDetailDao phoneContactDetailDao) {
    this.phoneContactDetailDao = phoneContactDetailDao;
  }

  public void setScpSsaName3Dao(SsaName3Dao scpSsaName3Dao) {
    this.scpSsaName3Dao = scpSsaName3Dao;
  }

  public void setScpOtherEthnicityDao(ScpOtherEthnicityDao scpOtherEthnicityDao) {
    this.scpOtherEthnicityDao = scpOtherEthnicityDao;
  }

  public void setOutOfStateCheckDao(OutOfStateCheckDao outOfStateCheckDao) {
    this.outOfStateCheckDao = outOfStateCheckDao;
  }

  public void setDroolsService(DroolsService droolsService) {
    this.droolsService = droolsService;
  }
}
