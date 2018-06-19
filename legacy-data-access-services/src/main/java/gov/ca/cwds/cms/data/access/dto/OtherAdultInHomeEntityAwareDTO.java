package gov.ca.cwds.cms.data.access.dto;

import gov.ca.cwds.data.legacy.cms.entity.OtherAdultsInPlacementHome;
import gov.ca.cwds.data.legacy.cms.entity.OutOfStateCheck;
import java.util.ArrayList;
import java.util.List;

/**
 * @author CWDS CALS API Team
 */

public class OtherAdultInHomeEntityAwareDTO extends
  RelationshipsAwareDTO<OtherAdultsInPlacementHome> {

  private List<OutOfStateCheck> outOfStateChecks = new ArrayList<>();

  public List<OutOfStateCheck> getOutOfStateChecks() {
    return outOfStateChecks;
  }

  public void setOutOfStateChecks(
    List<OutOfStateCheck> outOfStateChecks) {
    this.outOfStateChecks = outOfStateChecks;
  }

  public void addOutOfStateCheck(OutOfStateCheck outOfStateCheck) {
    outOfStateChecks.add(outOfStateCheck);
  }
}


