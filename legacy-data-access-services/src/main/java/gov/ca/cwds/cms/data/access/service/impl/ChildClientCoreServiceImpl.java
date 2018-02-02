package gov.ca.cwds.cms.data.access.service.impl;

import com.google.inject.Inject;
import gov.ca.cwds.cms.data.access.dto.ChildClientEntityAwareDTO;
import gov.ca.cwds.cms.data.access.service.ChildClientCoreService;
import gov.ca.cwds.data.legacy.cms.dao.HealthInterventionPlanDao;
import gov.ca.cwds.data.legacy.cms.dao.ParentalRightsTerminationDao;
import gov.ca.cwds.data.legacy.cms.entity.ChildClient;
import gov.ca.cwds.data.legacy.cms.entity.FCEligibility;
import gov.ca.cwds.data.legacy.cms.entity.HealthInterventionPlan;
import gov.ca.cwds.data.legacy.cms.entity.ParentalRightsTermination;
import java.util.List;

/**
 * @author CWDS TPT-3 Team
 */
public class ChildClientCoreServiceImpl extends
    ClientCoreServiceBase<ChildClientEntityAwareDTO> implements ChildClientCoreService {

  @Inject
  private HealthInterventionPlanDao healthInterventionPlanDao;
  @Inject
  private ParentalRightsTerminationDao parentalRightsTerminationDao;

  protected void enrichClientEntityAwareDto(ChildClientEntityAwareDTO clientEntityAwareDTO) {

    ChildClient childClient = (ChildClient) clientEntityAwareDTO.getEntity();
    final String childClientId = childClient.getIdentifier();

    if (childClient.getAfdcFcEligibilityIndicatorVar()) {
      List<FCEligibility> fcEligibilities =
          getFcEligibilityDao().findByChildClientId(childClientId);
      clientEntityAwareDTO.setFcEligibilities(fcEligibilities);
    }

    List<HealthInterventionPlan> activeHealthInterventionPlans =
        healthInterventionPlanDao.getActiveHealthInterventionPlans(childClientId);
    clientEntityAwareDTO.setActiveHealthInterventionPlans(activeHealthInterventionPlans);

    List<ParentalRightsTermination> parentalRightsTerminations =
        parentalRightsTerminationDao.getParentalRightsTerminationsByChildClientId(childClientId);
    clientEntityAwareDTO.setParentalRightsTerminations(parentalRightsTerminations);
  }

  public void setHealthInterventionPlanDao(
      HealthInterventionPlanDao healthInterventionPlanDao) {
    this.healthInterventionPlanDao = healthInterventionPlanDao;
  }
}
