package gov.ca.cwds.cms.data.access.service;

import com.google.inject.Inject;
import gov.ca.cwds.cms.data.access.dao.CountyOwnershipForPlacementHomeDao;
import gov.ca.cwds.cms.data.access.dao.PlacementHomeDao;
import gov.ca.cwds.cms.data.access.dao.PlacementHomeUcDao;
import gov.ca.cwds.cms.data.access.mapper.CountyOwnershipMapper;
import gov.ca.cwds.data.legacy.cms.entity.CountyOwnership;
import gov.ca.cwds.data.legacy.cms.entity.PlacementHome;
import gov.ca.cwds.data.legacy.cms.entity.PlacementHomeUc;
import java.time.LocalDateTime;
import java.util.Collections;
import org.apache.commons.lang3.StringUtils;

/**
 * @author CWDS CALS API Team
 */

public class PlacementHomeService implements DataAccessService<PlacementHome> {

  @Inject
  private PlacementHomeDao placementHomeDao;

  @Inject
  private PlacementHomeUcDao placementHomeUcDao;

  @Inject
  private CountyOwnershipMapper countyOwnershipMapper;

  @Inject
  private CountyOwnershipForPlacementHomeDao countyOwnershipDao;

  @Override
  public PlacementHome create(PlacementHome placementHome, String staffPersonId) {
    runBusinessRules(placementHome);
    PlacementHome storedPlacementHome = placementHomeDao.create(placementHome);
    storePlacementHomeUc(storedPlacementHome, staffPersonId);
    storeCountyOwnership(storedPlacementHome);

    return storedPlacementHome;
  }

  private void storeCountyOwnership(PlacementHome storedPlacementHome) {
    CountyOwnership countyOwnership =
        countyOwnershipMapper.toCountyOwnership(storedPlacementHome.getIdentifier(),
            "P", Collections.emptyList());
    countyOwnershipDao.create(countyOwnership);
  }

  private PlacementHomeUc storePlacementHomeUc(PlacementHome placementHome,
      String staffPersonId) {
    PlacementHomeUc placementHomeUc = new PlacementHomeUc();
    placementHomeUc.setCityNm(StringUtils.upperCase(placementHome.getCityNm()));
    placementHomeUc.setGeoRgntcd(StringUtils.upperCase(placementHome.getGeoRgntcd()));
    placementHomeUc.setLaVndrId(StringUtils.upperCase(placementHome.getLaVndrId()));
    placementHomeUc.setLicenseNo(StringUtils.upperCase(placementHome.getLicenseNo()));
    placementHomeUc.setFacltyNm(StringUtils.upperCase(placementHome.getFacltyNm()));
    placementHomeUc.setStreetNo(StringUtils.upperCase(placementHome.getStreetNo()));
    placementHomeUc.setStreetNm(StringUtils.upperCase(placementHome.getStreetNm()));
    placementHomeUc.setPkplcHmt(placementHome.getIdentifier());
    placementHomeUc.setLstUpdId(staffPersonId);
    placementHomeUc.setLstUpdTs(LocalDateTime.now());
    return placementHomeUcDao.create(placementHomeUc);
  }

}
