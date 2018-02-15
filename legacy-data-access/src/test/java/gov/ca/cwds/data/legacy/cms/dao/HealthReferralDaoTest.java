package gov.ca.cwds.data.legacy.cms.dao;

import static org.junit.Assert.assertEquals;

import gov.ca.cwds.data.legacy.cms.entity.HealthReferral;
import gov.ca.cwds.data.legacy.cms.persistence.BaseCwsCmsInMemoryPersistenceTest;
import java.util.List;
import org.junit.Before;
import org.junit.Test;

public class HealthReferralDaoTest extends BaseCwsCmsInMemoryPersistenceTest {

  public static final String CHILD_CLIENT_ID = "00xVOYD11c";

  private HealthReferralDao dao = null;

  @Before
  public void before() {
    dao = new HealthReferralDao(sessionFactory);
  }

  @Test
  public void testFindByChildClientId() throws Exception {
    cleanAllAndInsert("/dbunit/HealthReferrals.xml");

    executeInTransaction(
        sessionFactory,
        sessionFactory -> {
          List<HealthReferral> healthReferrals = dao.findByChildClientId(CHILD_CLIENT_ID);
          assertEquals(1, healthReferrals.size());
          HealthReferral healthReferral = healthReferrals.get(0);
          String childClientId = healthReferral.getChildClient().getIdentifier();
          assertEquals(CHILD_CLIENT_ID, childClientId);
        });
  }
}
