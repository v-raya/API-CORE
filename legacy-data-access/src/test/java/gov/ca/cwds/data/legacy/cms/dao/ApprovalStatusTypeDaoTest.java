package gov.ca.cwds.data.legacy.cms.dao;

import static org.junit.Assert.*;

import gov.ca.cwds.data.legacy.cms.persistence.BaseCwsCmsInMemoryPersistenceTest;
import org.junit.Before;
import org.junit.Test;

/** @author CWDS TPT-3 Team */
public class ApprovalStatusTypeDaoTest extends BaseCwsCmsInMemoryPersistenceTest {

  private ApprovalStatusTypeDao approvalStatusTypeDao;
  public static final short SIZE_OF_STATUSES = 6;

  @Before
  public void before() {
    approvalStatusTypeDao = new ApprovalStatusTypeDao(sessionFactory);
  }

  @Test
  public void testConstructor() {
    assertEquals(sessionFactory, approvalStatusTypeDao.getSessionFactory());
  }

  @Test
  public void testFindAll() throws Exception {
    executeInTransaction(
        sessionFactory,
        (sessionFactory) -> {
          assertNotNull(approvalStatusTypeDao.findAll().size());
          assertEquals(SIZE_OF_STATUSES, approvalStatusTypeDao.findAll().size());
        });
  }
}
