package gov.ca.cwds.cms.data.access.dto;

import gov.ca.cwds.data.legacy.cms.entity.FCEligibility;
import java.util.ArrayList;
import java.util.List;

/**
 * @author CWDS TPT-3 Team
 */
public class ChildClientEntityAwareDTO extends ClientEntityAwareDTO {
  private List<FCEligibility> fcEligibilities = new ArrayList<>();

  public List<FCEligibility> getFcEligibilities() {
    return fcEligibilities;
  }

  public void setFcEligibilities(
      List<FCEligibility> fcEligibilities) {
    this.fcEligibilities = fcEligibilities;
  }
}
