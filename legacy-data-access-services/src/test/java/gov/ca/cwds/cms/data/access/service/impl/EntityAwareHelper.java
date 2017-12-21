package gov.ca.cwds.cms.data.access.service.impl;

import gov.ca.cwds.cms.data.access.Constants.StaffPersonPrivileges;
import gov.ca.cwds.cms.data.access.dto.PlacementHomeEntityAwareDTO;
import gov.ca.cwds.data.legacy.cms.entity.PlacementHome;
import gov.ca.cwds.data.legacy.cms.entity.SubstituteCareProvider;
import gov.ca.cwds.security.realm.PerryAccount;
import java.util.HashSet;
import java.util.Set;

/**
 * @author CWDS CALS API Team
 */

public final class EntityAwareHelper {

  private EntityAwareHelper() {}

  public static PlacementHomeEntityAwareDTO prepareSuccessfulPlacementHomeEntityAwareDTO() {
    PerryAccount perryAccount = new PerryAccount();
    Set<String> privileges = new HashSet<>();
    privileges.add(StaffPersonPrivileges.USR_PRV_RESOURCE_MGMT_PLACEMENT_FACILITY_MAINT);
    perryAccount.setPrivileges(privileges);
    PlacementHomeEntityAwareDTO placementHomeEntityAwareDTO = new PlacementHomeEntityAwareDTO(
        perryAccount);
    PlacementHome placementHome = new PlacementHome();
    placementHome.setAgeToNo((short) 23);
    placementHome.setZipNo("11111");
    placementHome.setLaPZipno("11111");
    placementHome.setpZipNo("11111");
    SubstituteCareProvider substituteCareProvider = new SubstituteCareProvider();
    substituteCareProvider.setZipNo("11111");
    placementHome.setPrimarySubstituteCareProvider(substituteCareProvider);
    placementHomeEntityAwareDTO.setEntity(placementHome);
    return placementHomeEntityAwareDTO;
  }

}
