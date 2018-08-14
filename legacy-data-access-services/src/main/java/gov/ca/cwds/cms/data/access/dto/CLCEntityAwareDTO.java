package gov.ca.cwds.cms.data.access.dto;

import gov.ca.cwds.data.legacy.cms.entity.CountyLicenseCase;

/**
 * @author CWDS CALS API Team
 */
public class CLCEntityAwareDTO extends BaseEntityAwareDTO<CountyLicenseCase> {

  private String placementHomeId;

  public String getPlacementHomeId() {
    return placementHomeId;
  }

  public void setPlacementHomeId(String placementHomeId) {
    this.placementHomeId = placementHomeId;
  }

}
