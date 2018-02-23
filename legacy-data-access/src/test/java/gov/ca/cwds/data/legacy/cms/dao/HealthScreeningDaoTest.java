package gov.ca.cwds.data.legacy.cms.dao;

import static org.junit.Assert.assertEquals;

import gov.ca.cwds.data.legacy.cms.entity.HealthScreening;
import gov.ca.cwds.data.legacy.cms.persistence.BaseCwsCmsInMemoryPersistenceTest;
import java.util.List;
import org.junit.Before;
import org.junit.Test;

/**
 * @author CWDS TPT-3 Team
 */
public class HealthScreeningDaoTest extends BaseCwsCmsInMemoryPersistenceTest {

  public static final String CHILD_CLIENT_ID = "Ek694Ij0Wl";

  private HealthScreeningDao dao = null;

  @Before
  public void before() {
    dao = new HealthScreeningDao(sessionFactory);
  }

  @Test
  public void testFindByChildClientId() throws Exception {
    cleanAllAndInsert("/dbunit/HealthScreenings.xml");

    executeInTransaction(
        sessionFactory,
        sessionFactory -> {
          List<HealthScreening> healthScreenings = dao.findByChildClientId(CHILD_CLIENT_ID);
          assertEquals(1, healthScreenings.size());
          HealthScreening healthScreening = healthScreenings.get(0);
          String childClientId = healthScreening.getChildClient().getIdentifier();
          assertEquals(CHILD_CLIENT_ID, childClientId);
        });
  }
}
