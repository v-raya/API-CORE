package gov.ca.cwds.cms.data.access.dto;

import gov.ca.cwds.data.legacy.cms.entity.Client;
import gov.ca.cwds.data.legacy.cms.entity.ClientScpEthnicity;
import gov.ca.cwds.data.legacy.cms.entity.MedicalEligibilityApplication;
import gov.ca.cwds.data.legacy.cms.entity.HealthInterventionPlan;

import java.util.ArrayList;
import java.util.List;

public class ClientEntityAwareDTO extends BaseEntityAwareDTO<Client> {
    private List<ClientScpEthnicity> clientScpEthnicities = new ArrayList<>();
    private List<MedicalEligibilityApplication> medicalEligibilityApplications = new ArrayList<>();
    private List<HealthInterventionPlan> activeHealthInterventionPlans = new ArrayList<>();

    public List<ClientScpEthnicity> getClientScpEthnicities() {
        return clientScpEthnicities;
    }

    public void setClientScpEthnicities(List<ClientScpEthnicity> clientScpEthnicities) {
        this.clientScpEthnicities = clientScpEthnicities;
    }

    public List<MedicalEligibilityApplication> getMedicalEligibilityApplications() {
        return medicalEligibilityApplications;
    }

    public void setMedicalEligibilityApplications(List<MedicalEligibilityApplication> medicalEligibilityApplications) {
        this.medicalEligibilityApplications = medicalEligibilityApplications;
    }


    public List<HealthInterventionPlan> getActiveHealthInterventionPlans() {
        return activeHealthInterventionPlans;
    }

    public void setActiveHealthInterventionPlans(
            List<HealthInterventionPlan> activeHealthInterventionPlans) {
        this.activeHealthInterventionPlans = activeHealthInterventionPlans;
    }
}
