package gov.ca.cwds.cms.data.access.service.impl;

import com.google.inject.Inject;
import gov.ca.cwds.cms.data.access.dto.ChildClientEntityAwareDTO;
import gov.ca.cwds.cms.data.access.service.ChildClientCoreService;
import gov.ca.cwds.data.legacy.cms.dao.HealthInterventionPlanDao;
import gov.ca.cwds.data.legacy.cms.entity.ChildClient;
import gov.ca.cwds.data.legacy.cms.entity.FCEligibility;
import gov.ca.cwds.data.legacy.cms.entity.HealthInterventionPlan;
import java.util.List;

/**
 * @author CWDS TPT-3 Team
 */
public class ChildClientCoreServiceImpl extends
    ClientCoreServiceBase<ChildClientEntityAwareDTO> implements ChildClientCoreService {

  @Inject
  private HealthInterventionPlanDao healthInterventionPlanDao;

  protected void enrichClientEntityAwareDto(ChildClientEntityAwareDTO clientEntityAwareDTO) {
    ChildClient childClient = (ChildClient) clientEntityAwareDTO.getEntity();
    if (childClient.getAfdcFcEligibilityIndicatorVar()) {
      List<FCEligibility> fcEligibilities =
          getFcEligibilityDao().findByChildClientId(childClient.getIdentifier());
      clientEntityAwareDTO.setFcEligibilities(fcEligibilities);
    }

    List<HealthInterventionPlan> activeHealthInterventionPlans =
        healthInterventionPlanDao.getActiveHealthInterventionPlans(childClient.getIdentifier());
    clientEntityAwareDTO.setActiveHealthInterventionPlans(activeHealthInterventionPlans);
  }

  public void setHealthInterventionPlanDao(
      HealthInterventionPlanDao healthInterventionPlanDao) {
    this.healthInterventionPlanDao = healthInterventionPlanDao;
  }
}
