package gov.ca.cwds.cms.data.access.service.impl;

import gov.ca.cwds.cms.data.access.dto.PlacementHomeEntityAwareDTO;
import gov.ca.cwds.data.legacy.cms.entity.PlacementHome;
import gov.ca.cwds.security.realm.PerryAccount;
import java.util.HashSet;
import java.util.Set;

/**
 * @author CWDS CALS API Team
 */

public final class EntityAwareHelper {

  public static final String USR_PRV_RESOURCE_MGMT_PLACEMENT_FACILITY_MAINT = "Resource Mgmt Placement Facility Maint";

  private EntityAwareHelper() {}

  public static PlacementHomeEntityAwareDTO prepareSuccessfulPlacementHomeEntityAwareDTO() {
    PerryAccount perryAccount = new PerryAccount();
    Set<String> privileges = new HashSet<>();
    privileges.add(USR_PRV_RESOURCE_MGMT_PLACEMENT_FACILITY_MAINT);
    perryAccount.setPrivileges(privileges);
    PlacementHomeEntityAwareDTO placementHomeEntityAwareDTO = new PlacementHomeEntityAwareDTO(
        perryAccount);
    placementHomeEntityAwareDTO.setEntity(new PlacementHome());
    return placementHomeEntityAwareDTO;
  }

}
