package gov.ca.cwds.cms.data.access.parameter;

/**
 * @author CWDS CALS API Team
 */

public class SCPParameterObject extends BaseParameterObject {

  private String placementHomeId;

  private boolean primaryApplicant;

  public String getPlacementHomeId() {
    return placementHomeId;
  }

  public void setPlacementHomeId(String placementHomeId) {
    this.placementHomeId = placementHomeId;
  }

  public boolean isPrimaryApplicant() {
    return primaryApplicant;
  }

  public void setIsPrimaryApplicant(boolean primaryApplicant) {
    this.primaryApplicant = primaryApplicant;
  }

}
