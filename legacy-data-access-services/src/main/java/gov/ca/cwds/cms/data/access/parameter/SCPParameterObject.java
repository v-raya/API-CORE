package gov.ca.cwds.cms.data.access.parameter;

import gov.ca.cwds.cms.data.access.domain.PhoneNumber;
import java.util.List;

/**
 * @author CWDS CALS API Team
 */

public class SCPParameterObject extends BaseParameterObject {

  private String placementHomeId;

  private boolean primaryApplicant;

  private List<PhoneNumber> phoneNumbers;

  public String getPlacementHomeId() {
    return placementHomeId;
  }

  public void setPlacementHomeId(String placementHomeId) {
    this.placementHomeId = placementHomeId;
  }

  public boolean isPrimaryApplicant() {
    return primaryApplicant;
  }

  public void setPrimaryApplicant(boolean primaryApplicant) {
    this.primaryApplicant = primaryApplicant;
  }

  public List<PhoneNumber> getPhoneNumbers() {
    return phoneNumbers;
  }

  public void setPhoneNumbers(
      List<PhoneNumber> phoneNumbers) {
    this.phoneNumbers = phoneNumbers;
  }
}
