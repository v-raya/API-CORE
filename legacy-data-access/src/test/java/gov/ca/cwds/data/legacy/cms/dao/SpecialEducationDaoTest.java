package gov.ca.cwds.data.legacy.cms.dao;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import gov.ca.cwds.data.legacy.cms.entity.SpecialEducation;
import gov.ca.cwds.data.legacy.cms.persistence.BaseCwsCmsInMemoryPersistenceTest;
import java.util.List;
import org.junit.Before;
import org.junit.Test;

/**
 * @author CWDS TPT-3 Team
 */
public class SpecialEducationDaoTest extends BaseCwsCmsInMemoryPersistenceTest {

  public static final String CHILD_CLIENT_ID = "00xVOYD11c";
  public static final String WRONG_CHILD_CLIENT_ID = "00x0000000";

  private SpecialEducationDao creditReportHistoryDao;

  @Before
  public void before() {
    creditReportHistoryDao = new SpecialEducationDao(sessionFactory);
  }

  @Test
  public void testFindByChildClientId() throws Exception {
    cleanAllAndInsert("/dbunit/SpecialEducation.xml");

    executeInTransaction(sessionFactory, sessionFactory1 -> {
      List<SpecialEducation> specialEducations = creditReportHistoryDao.findByClientId(CHILD_CLIENT_ID);
      assertNotNull(specialEducations);
      assertEquals(2, specialEducations.size());
      assertEquals(CHILD_CLIENT_ID, specialEducations.get(0).getChildClientId());
      assertEquals(CHILD_CLIENT_ID, specialEducations.get(1).getChildClientId());
    });
  }

  @Test
  public void testFindByWrongChildClientId() throws Exception {
    cleanAllAndInsert("/dbunit/CreditReportHistory.xml");

    executeInTransaction(sessionFactory, sessionFactory1 -> {
      List<SpecialEducation> specialEducations = creditReportHistoryDao.findByClientId(WRONG_CHILD_CLIENT_ID);
      assertNotNull(specialEducations);
      assertEquals(0, specialEducations.size());
    });
  }
}