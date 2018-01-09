package gov.ca.cwds.data.legacy.cms.dao;

import static org.junit.Assert.*;

import gov.ca.cwds.data.legacy.cms.entity.ChildClient;
import gov.ca.cwds.data.legacy.cms.entity.FCEligibility;
import gov.ca.cwds.data.legacy.cms.persistence.BaseCwsCmsInMemoryPersistenceTest;
import java.util.List;
import org.junit.Before;
import org.junit.Test;

/**
 * @author CWDS TPT-3 Team
 */
public class FCEligibilityDaoTest extends BaseCwsCmsInMemoryPersistenceTest {
  public static final String CHILD_CLIENT_ID = "00xVOYD00c";
  public static final String WRONG_CHILD_CLIENT_ID = "00x0000000";

  private FCEligibilityDao fcEligibilityDao;
  private ClientDao clientDao;

  @Before
  public void before() {
    fcEligibilityDao = new FCEligibilityDao(sessionFactory);
    clientDao = new ClientDao(sessionFactory);
  }

  @Test
  public void testFindByChildClientId() throws Exception {
    cleanAllAndInsert("/dbunit/Client/update/FCEligibility.xml");

    executeInTransaction(sessionFactory, sessionFactory1 -> {
      ChildClient childClient = (ChildClient) clientDao.find(CHILD_CLIENT_ID);
      assertNotNull(childClient);
      assertEquals(true, childClient.getAfdcFcEligibilityIndicatorVar());
      assertEquals(CHILD_CLIENT_ID, childClient.getVictimClientId());
    });

    executeInTransaction(sessionFactory, sessionFactory1 -> {
      List<FCEligibility> fcEligibilities = fcEligibilityDao.findByChildClientId(CHILD_CLIENT_ID);
      assertNotNull(fcEligibilities);
      assertEquals(1, fcEligibilities.size());
      assertEquals(CHILD_CLIENT_ID, fcEligibilities.get(0).getChildClientId());
    });
  }

  @Test
  public void testFindByWrongChildClientId() throws Exception {
    cleanAllAndInsert("/dbunit/Client/update/FCEligibility.xml");

    executeInTransaction(sessionFactory, sessionFactory1 -> {
      ChildClient childClient = (ChildClient) clientDao.find(WRONG_CHILD_CLIENT_ID);
      assertNull(childClient);
    });

    executeInTransaction(sessionFactory, sessionFactory1 -> {
      List<FCEligibility> fcEligibilities = fcEligibilityDao.findByChildClientId(WRONG_CHILD_CLIENT_ID);
      assertNotNull(fcEligibilities);
      assertEquals(0, fcEligibilities.size());
    });
  }
}