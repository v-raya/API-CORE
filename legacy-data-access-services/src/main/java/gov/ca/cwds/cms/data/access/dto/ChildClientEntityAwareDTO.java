package gov.ca.cwds.cms.data.access.dto;

import gov.ca.cwds.data.legacy.cms.entity.CsecHistory;
import gov.ca.cwds.data.legacy.cms.entity.FCEligibility;
import gov.ca.cwds.data.legacy.cms.entity.HealthInterventionPlan;
import gov.ca.cwds.data.legacy.cms.entity.MedicalEligibilityApplication;
import gov.ca.cwds.data.legacy.cms.entity.ParentalRightsTermination;
import gov.ca.cwds.data.legacy.cms.entity.PaternityDetail;
import java.util.ArrayList;
import java.util.List;

/**
 * @author CWDS TPT-3 Team
 */
public class ChildClientEntityAwareDTO extends ClientEntityAwareDTO {

  private final List<FCEligibility> fcEligibilities = new ArrayList<>();
  private final List<HealthInterventionPlan> activeHealthInterventionPlans = new ArrayList<>();
  private final List<ParentalRightsTermination> parentalRightsTerminations = new ArrayList<>();
  private final List<MedicalEligibilityApplication> medicalEligibilityApplications =
      new ArrayList<>();
  private final List<CsecHistory> csecHistories = new ArrayList<>();
  private List<PaternityDetail> paternityDetails = new ArrayList<>();

  public List<MedicalEligibilityApplication> getMedicalEligibilityApplications() {
    return medicalEligibilityApplications;
  }

  public List<FCEligibility> getFcEligibilities() {
    return fcEligibilities;
  }

  public List<HealthInterventionPlan> getActiveHealthInterventionPlans() {
    return activeHealthInterventionPlans;
  }

  public List<ParentalRightsTermination> getParentalRightsTerminations() {
    return parentalRightsTerminations;
  }

  public List<CsecHistory> getCsecHistories() {
    return csecHistories;
  }

  public List<PaternityDetail> getPaternityDetails() {
    return paternityDetails;
  }

  public void setPaternityDetails(List<PaternityDetail> paternityDetails) {
    this.paternityDetails = paternityDetails;
  }
}
