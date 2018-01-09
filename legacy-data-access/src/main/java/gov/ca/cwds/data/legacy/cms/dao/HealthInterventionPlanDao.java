package gov.ca.cwds.data.legacy.cms.dao;

import com.google.common.collect.ImmutableList;
import com.google.inject.Inject;
import gov.ca.cwds.data.BaseDaoImpl;
import gov.ca.cwds.data.legacy.cms.entity.HealthInterventionPlan;
import gov.ca.cwds.inject.CmsSessionFactory;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.SessionFactory;

public class HealthInterventionPlanDao extends BaseDaoImpl<HealthInterventionPlan> {

  @Inject
  public HealthInterventionPlanDao(@CmsSessionFactory SessionFactory sessionFactory) {
    super(sessionFactory);
  }

  public List<HealthInterventionPlan> getActiveHealthInterventionPlans(String clientId) {

    if (StringUtils.isEmpty(clientId)) {
      return new ArrayList<>();
    }

    List<HealthInterventionPlan> activePlans =
        currentSession()
            .createNamedQuery(
                HealthInterventionPlan.FIND_ACTIVE_PLANS_BY_CLIENT_ID, HealthInterventionPlan.class)
            .setParameter(HealthInterventionPlan.PARAM_CLIENT_ID, clientId)
            .list();
    return ImmutableList.<HealthInterventionPlan>builder().addAll(activePlans).build();
  }
}
