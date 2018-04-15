package gov.ca.cwds.data.legacy.cms.dao;

import gov.ca.cwds.data.legacy.cms.entity.CsecHistory;
import gov.ca.cwds.data.legacy.cms.persistence.BaseCwsCmsInMemoryPersistenceTest;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/** @author CWDS TPT-3 Team */
public class CsecHistoryDaoTest extends BaseCwsCmsInMemoryPersistenceTest {
  public static final String CHILD_CLIENT_ID = "00xVOYD11c";
  public static final String WRONG_CHILD_CLIENT_ID = "00x0000000";

  private CsecHistoryDao csecHistoryDao;

  @Before
  public void before() {
    csecHistoryDao = new CsecHistoryDao(sessionFactory);
  }

  @Test
  public void testFindByChildClientId() throws Exception {
    cleanAllAndInsert("/dbunit/CsecHistory.xml");

    executeInTransaction(
        sessionFactory,
        sessionFactory1 -> {
          List<CsecHistory> csecHistories = csecHistoryDao.findByClientId(CHILD_CLIENT_ID);
          assertNotNull(csecHistories);
          assertEquals(1, csecHistories.size());
          assertEquals(CHILD_CLIENT_ID, csecHistories.get(0).getChildClient());
        });
  }

  @Test
  public void testFindByWrongChildClientId() throws Exception {
    cleanAllAndInsert("/dbunit/CsecHistory.xml");

    executeInTransaction(
        sessionFactory,
        sessionFactory1 -> {
          List<CsecHistory> csecHistories = csecHistoryDao.findByClientId(WRONG_CHILD_CLIENT_ID);
          assertNotNull(csecHistories);
          assertEquals(0, csecHistories.size());
        });
  }
}
