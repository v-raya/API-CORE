package gov.ca.cwds.data.legacy.cms.dao;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import gov.ca.cwds.data.legacy.cms.entity.NearFatality;
import gov.ca.cwds.data.legacy.cms.persistence.BaseCwsCmsInMemoryPersistenceTest;
import java.util.List;
import org.junit.Before;
import org.junit.Test;

/** @author CWDS TPT-3 Team */
public class NearFatalityDaoTest extends BaseCwsCmsInMemoryPersistenceTest {
  public static final String CLIENT_ID = "00xVOYD00c";
  public static final String WRONG_CLIENT_ID = "00x0000000";

  private NearFatalityDao nearFatalityDao;

  @Before
  public void before() {
    nearFatalityDao = new NearFatalityDao(sessionFactory);
  }

  @Test
  public void testFindByChildClientId() throws Exception {
    cleanAllAndInsert("/dbunit/Client/update/NearFatality.xml");

    executeInTransaction(
        sessionFactory,
        sessionFactory1 -> {
          List<NearFatality> nearFatalities =
              nearFatalityDao.findNearFatalitiesByClientId(CLIENT_ID);
          assertNotNull(nearFatalities);
          assertEquals(2, nearFatalities.size());
          assertEquals(CLIENT_ID, nearFatalities.get(0).getClientId());
        });
  }

  @Test
  public void testFindByWrongChildClientId() throws Exception {
    cleanAllAndInsert("/dbunit/Client/update/NearFatality.xml");

    executeInTransaction(
        sessionFactory,
        sessionFactory1 -> {
          List<NearFatality> nearFatalities =
              nearFatalityDao.findNearFatalitiesByClientId(WRONG_CLIENT_ID);
          assertNotNull(nearFatalities);
          assertEquals(0, nearFatalities.size());
        });
  }
}
