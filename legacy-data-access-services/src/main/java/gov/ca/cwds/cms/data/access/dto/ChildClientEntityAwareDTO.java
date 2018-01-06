package gov.ca.cwds.cms.data.access.dto;

import gov.ca.cwds.data.legacy.cms.entity.FсEligibility;
import java.util.ArrayList;
import java.util.List;

/**
 * @author CWDS TPT-3 Team
 */
public class ChildClientEntityAwareDTO extends ClientEntityAwareDTO {
  private List<FсEligibility> fcFсEligibilities = new ArrayList<>();

  public List<FсEligibility> getFcFсEligibilities() {
    return fcFсEligibilities;
  }

  public void setFcFсEligibilities(
      List<FсEligibility> fcFсEligibilities) {
    this.fcFсEligibilities = fcFсEligibilities;
  }
}
