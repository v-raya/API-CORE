package gov.ca.cwds.cms.data.access.service.impl;

import static org.apache.commons.lang3.StringUtils.upperCase;

import com.google.inject.Inject;
import gov.ca.cwds.cms.data.access.Constants;
import gov.ca.cwds.cms.data.access.dao.substitutecareprovider.CountyOwnershipDao;
import gov.ca.cwds.cms.data.access.dao.substitutecareprovider.PlacementHomeInformationDao;
import gov.ca.cwds.cms.data.access.dao.substitutecareprovider.SubstituteCareProviderDao;
import gov.ca.cwds.cms.data.access.dao.substitutecareprovider.SubstituteCareProviderUcDao;
import gov.ca.cwds.cms.data.access.mapper.CountyOwnershipMapper;
import gov.ca.cwds.cms.data.access.parameter.SCPParameterObject;
import gov.ca.cwds.cms.data.access.service.SubstituteCareProviderService;
import gov.ca.cwds.cms.data.access.utils.IdGenerator;
import gov.ca.cwds.data.legacy.cms.entity.CountyOwnership;
import gov.ca.cwds.data.legacy.cms.entity.PlacementHomeInformation;
import gov.ca.cwds.data.legacy.cms.entity.SubstituteCareProvider;
import gov.ca.cwds.data.legacy.cms.entity.SubstituteCareProviderUc;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collections;

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

  @Override
  public SubstituteCareProvider create(SubstituteCareProvider substituteCareProvider,
      SCPParameterObject parameterObject) {
    runBusinessRules(substituteCareProvider);
    SubstituteCareProvider storedSubstituteCareProvider = substituteCareProviderDao
        .create(substituteCareProvider);
    storeSubstituteCareProviderUc(substituteCareProvider, parameterObject);
    storeCountyOwnership(substituteCareProvider.getIdentifier());
    storePlacementHomeInformation(substituteCareProvider, parameterObject);
    return storedSubstituteCareProvider;
  }

  private void storePlacementHomeInformation(SubstituteCareProvider substituteCareProvider,
      SCPParameterObject parameterObject) {
    PlacementHomeInformation placementHomeInformation = new PlacementHomeInformation();
    placementHomeInformation.setThirdId(IdGenerator.generateId(parameterObject.getStaffPersonId()));
    placementHomeInformation.setStartDt(LocalDate.now());
    placementHomeInformation.setLicnseeCd("U");
    placementHomeInformation.setCrprvdrCd("Y");
    placementHomeInformation.setLstUpdId(parameterObject.getStaffPersonId());
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
      SCPParameterObject parameterObject) {
    SubstituteCareProviderUc substituteCareProviderUc = new SubstituteCareProviderUc();
    substituteCareProviderUc.setPksbPvdrt(substituteCareProvider.getIdentifier());
    substituteCareProviderUc.setCaDlicNo(upperCase(substituteCareProvider.getCaDlicNo()));
    substituteCareProviderUc.setFirstNm(upperCase(substituteCareProvider.getFirstNm()));
    substituteCareProviderUc.setLastNm(upperCase(substituteCareProvider.getLastNm()));
    substituteCareProviderUc.setLstUpdId(parameterObject.getStaffPersonId());
    substituteCareProviderUc.setLstUpdTs(LocalDateTime.now());
    substituteCareProviderUcDao.create(substituteCareProviderUc);
  }

}
