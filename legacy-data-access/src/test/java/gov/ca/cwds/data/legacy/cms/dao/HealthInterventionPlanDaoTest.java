package gov.ca.cwds.data.legacy.cms.dao;

import static org.junit.Assert.assertEquals;

import gov.ca.cwds.data.legacy.cms.entity.HealthInterventionPlan;
import gov.ca.cwds.data.legacy.cms.persistence.BaseCwsCmsInMemoryPersistenceTest;
import java.util.List;
import org.junit.Before;
import org.junit.Test;

public class HealthInterventionPlanDaoTest extends BaseCwsCmsInMemoryPersistenceTest {

  private HealthInterventionPlanDao dao = null;

  @Before
  public void before() throws Exception {
    dao = new HealthInterventionPlanDao(sessionFactory);
  }

  @Test
  public void testGetActiveHealthInterventionPlans() throws Exception {

    cleanAllAndInsert("/dbunit/HealthInterventionPlans.xml");

    List<HealthInterventionPlan> plans = dao.getActiveHealthInterventionPlans("AapJGAU04Z");
    assertEquals(1, plans.size());

  }

}
