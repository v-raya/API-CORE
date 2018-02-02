package gov.ca.cwds.cms.data.access.dto;

import gov.ca.cwds.data.legacy.cms.entity.FCEligibility;
import gov.ca.cwds.data.legacy.cms.entity.HealthInterventionPlan;
import gov.ca.cwds.data.legacy.cms.entity.ParentalRightsTermination;
import gov.ca.cwds.data.legacy.cms.entity.MedicalEligibilityApplication;

import java.util.ArrayList;
import java.util.List;

/** @author CWDS TPT-3 Team */
public class ChildClientEntityAwareDTO extends ClientEntityAwareDTO {
  private List<FCEligibility> fcEligibilities = new ArrayList<>();
  private List<HealthInterventionPlan> activeHealthInterventionPlans = new ArrayList<>();
  private List<ParentalRightsTermination> parentalRightsTerminations = new ArrayList<>();
  private List<MedicalEligibilityApplication> medicalEligibilityApplications = new ArrayList<>();

  public List<MedicalEligibilityApplication> getMedicalEligibilityApplications() {
    return medicalEligibilityApplications;
  }

  public void setMedicalEligibilityApplications(
          List<MedicalEligibilityApplication> medicalEligibilityApplications) {
    this.medicalEligibilityApplications = medicalEligibilityApplications;
  }

  public List<FCEligibility> getFcEligibilities() {
    return fcEligibilities;
  }

  public void setFcEligibilities(List<FCEligibility> fcEligibilities) {
    this.fcEligibilities = fcEligibilities;
  }

  public List<HealthInterventionPlan> getActiveHealthInterventionPlans() {
    return activeHealthInterventionPlans;
  }

  public void setActiveHealthInterventionPlans(
      List<HealthInterventionPlan> activeHealthInterventionPlans) {
    this.activeHealthInterventionPlans = activeHealthInterventionPlans;
  }

  public List<ParentalRightsTermination> getParentalRightsTerminations() {
    return parentalRightsTerminations;
  }

  public void setParentalRightsTerminations(
      List<ParentalRightsTermination> parentalRightsTerminations) {
    this.parentalRightsTerminations = parentalRightsTerminations;
  }
}
