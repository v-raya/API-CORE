package gov.ca.cwds.cms.data.access.parameter;

import gov.ca.cwds.cms.data.access.CWSIdentifier;
import gov.ca.cwds.data.legacy.cms.entity.PhoneContactDetail;
import java.util.List;

/**
 * @author CWDS CALS API Team
 */

public class SCPParameterObject extends BaseParameterObject {

  private String placementHomeId;

  private boolean primaryApplicant;

  private CWSIdentifier ethnicity;

  private List<PhoneContactDetail> phoneNumbers;

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

  public List<PhoneContactDetail> getPhoneNumbers() {
    return phoneNumbers;
  }

  public void setPhoneNumbers(
      List<PhoneContactDetail> phoneNumbers) {
    this.phoneNumbers = phoneNumbers;
  }

  public CWSIdentifier getEthnicity() {
    return ethnicity;
  }

  public void setEthnicity(CWSIdentifier ethnicity) {
    this.ethnicity = ethnicity;
  }
}
