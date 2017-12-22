package gov.ca.cwds.cms.data.access.service.impl;

import gov.ca.cwds.cms.data.access.Constants.StaffPersonPrivileges;
import gov.ca.cwds.cms.data.access.dto.PlacementHomeEntityAwareDTO;
import gov.ca.cwds.data.legacy.cms.entity.PlacementHome;
import gov.ca.cwds.security.realm.PerryAccount;
import java.util.HashSet;
import java.util.Set;

/**
 * @author CWDS CALS API Team
 */

public final class HappyPathHelper {

  private HappyPathHelper() {}

  public static PlacementHomeEntityAwareDTO prepareSuccessfulPlacementHomeEntityAwareDTO() {
    PlacementHomeEntityAwareDTO placementHomeEntityAwareDTO = new PlacementHomeEntityAwareDTO();
    PlacementHome placementHome = new PlacementHome();
    placementHome.setAgeToNo((short) 23);
    placementHomeEntityAwareDTO.setEntity(placementHome);
    return placementHomeEntityAwareDTO;
  }

  public static PerryAccount getHappyPathPrincipal() {
    PerryAccount perryAccount = new PerryAccount();
    Set<String> privileges = new HashSet<>();
    privileges.add(StaffPersonPrivileges.USR_PRV_RESOURCE_MGMT_PLACEMENT_FACILITY_MAINT);
    perryAccount.setPrivileges(privileges);
    return perryAccount;
  }

}
