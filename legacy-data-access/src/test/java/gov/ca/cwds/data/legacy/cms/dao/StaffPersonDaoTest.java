package gov.ca.cwds.data.legacy.cms.dao;

import static org.junit.Assert.assertEquals;

import gov.ca.cwds.data.legacy.cms.entity.facade.StaffBySupervisor;
import gov.ca.cwds.data.legacy.cms.persistence.BaseCwsCmsInMemoryPersistenceTest;
import java.util.Collection;
import org.junit.Before;
import org.junit.Test;

/** @author denys.davydov */
public class StaffPersonDaoTest extends BaseCwsCmsInMemoryPersistenceTest {

  private StaffPersonDao testSubject = null;

  @Before
  public void before() {
    testSubject = new StaffPersonDao(sessionFactory);
  }

  @Test
  public void nativeFindStaffBySupervisorRacfId_success_whenSingleRecordFound() throws Exception {
    cleanAllAndInsert("/dbunit/StaffBySupervisor.xml");
    executeInTransaction(
        sessionFactory,
        (sessionFactory) -> {
          final Collection<StaffBySupervisor> staffList =
              testSubject.nativeFindStaffBySupervisorId("aab");
          assertEquals(1, staffList.size());
          final StaffBySupervisor expected =
              new StaffBySupervisor("aad", "CWDS3", "Ratnesh", "Raval", "5594871890", null);
          assertEquals(expected, staffList.iterator().next());
        });
  }

  @Test
  public void nativeFindStaffBySupervisorRacfId_returnsEmpty_whenUnknownRacfId() throws Exception {
    cleanAllAndInsert("/dbunit/StaffBySupervisor.xml");
    executeInTransaction(
        sessionFactory,
        (sessionFactory) -> {
          final Collection<StaffBySupervisor> staffList =
              testSubject.nativeFindStaffBySupervisorId("UnknownId");
          assertEquals(0, staffList.size());
        });
  }

  @Test
  public void nativeFindStaffBySupervisorRacfId_returnsEmpty_whenSupervisorHasNoSubordinates() throws Exception {
    cleanAllAndInsert("/dbunit/StaffBySupervisor.xml");
    executeInTransaction(
        sessionFactory,
        (sessionFactory) -> {
          final Collection<StaffBySupervisor> staffList =
              testSubject.nativeFindStaffBySupervisorId("0Ki");
          assertEquals(0, staffList.size());
        });
  }
}
