package gov.ca.cwds.cms.data.access.service.impl;

import com.google.inject.Inject;
import gov.ca.cwds.cms.data.access.dto.ChildClientEntityAwareDTO;
import gov.ca.cwds.cms.data.access.service.ChildClientCoreService;
import gov.ca.cwds.data.legacy.cms.dao.FCEligibilityDao;
import gov.ca.cwds.data.legacy.cms.dao.HealthInterventionPlanDao;
import gov.ca.cwds.data.legacy.cms.dao.MedicalEligibilityApplicationDao;
import gov.ca.cwds.data.legacy.cms.dao.ParentalRightsTerminationDao;
import gov.ca.cwds.data.legacy.cms.dao.PaternityDetailDao;
import gov.ca.cwds.data.legacy.cms.entity.ChildClient;
import gov.ca.cwds.data.legacy.cms.entity.FCEligibility;
import gov.ca.cwds.data.legacy.cms.entity.HealthInterventionPlan;
import gov.ca.cwds.data.legacy.cms.entity.MedicalEligibilityApplication;
import gov.ca.cwds.data.legacy.cms.entity.ParentalRightsTermination;

import gov.ca.cwds.data.legacy.cms.entity.PaternityDetail;
import java.util.List;

/** @author CWDS TPT-3 Team */
public class ChildClientCoreServiceImpl extends ClientCoreServiceBase<ChildClientEntityAwareDTO>
    implements ChildClientCoreService {

  @Inject private HealthInterventionPlanDao healthInterventionPlanDao;
  @Inject private ParentalRightsTerminationDao parentalRightsTerminationDao;
  @Inject private MedicalEligibilityApplicationDao medicalEligibilityApplicationDao;
  @Inject private FCEligibilityDao fcEligibilityDao;
  @Inject private PaternityDetailDao paternityDetailDao;

  protected void enrichClientEntityAwareDto(ChildClientEntityAwareDTO clientEntityAwareDTO) {

    ChildClient childClient = (ChildClient) clientEntityAwareDTO.getEntity();
    final String childClientId = childClient.getIdentifier();

    if (childClient.getAfdcFcEligibilityIndicatorVar()) {
      List<FCEligibility> fcEligibilities = fcEligibilityDao.findByChildClientId(childClientId);
      clientEntityAwareDTO.setFcEligibilities(fcEligibilities);
    }

    List<HealthInterventionPlan> activeHealthInterventionPlans =
        healthInterventionPlanDao.findByChildClientId(childClientId);
    clientEntityAwareDTO.setActiveHealthInterventionPlans(activeHealthInterventionPlans);

    List<ParentalRightsTermination> parentalRightsTerminations =
        parentalRightsTerminationDao.findByChildClientId(childClientId);
    clientEntityAwareDTO.setParentalRightsTerminations(parentalRightsTerminations);

    List<MedicalEligibilityApplication> medicalEligibilityApplications =
        medicalEligibilityApplicationDao.findByChildClientId(childClientId);
    clientEntityAwareDTO.setMedicalEligibilityApplications(medicalEligibilityApplications);

    List<PaternityDetail> paternityDetails = paternityDetailDao.findByChildClientId(childClientId);
    clientEntityAwareDTO.setPaternityDetails(paternityDetails);
  }
}
