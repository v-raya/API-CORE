package gov.ca.cwds.data.legacy.cms.dao;

import static org.junit.Assert.*;

import gov.ca.cwds.data.legacy.cms.persistence.BaseCwsCmsInMemoryPersistenceTest;
import org.junit.Before;
import org.junit.Test;

/**
 * @author CWDS TPT-3 Team
 */
public class AssignmentDaoTest extends BaseCwsCmsInMemoryPersistenceTest {

  private AssignmentDao assignmentDao;
  public static final short SIZE_OF_STATUSES = 6;

  @Before
  public void before() {
    assignmentDao = new AssignmentDao(sessionFactory);
  }

  @Test
  public void testConstructor() {
    assertEquals(sessionFactory, assignmentDao.getSessionFactory());
  }

  @Test
  public void testGetAssignmentsByStaffIds() {

  }

}