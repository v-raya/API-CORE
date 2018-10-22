package gov.ca.cwds.data.legacy.cms.dao;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.collection.IsIterableContainingInAnyOrder.containsInAnyOrder;
import static org.junit.Assert.assertEquals;

import gov.ca.cwds.data.legacy.cms.entity.facade.ClientCountByStaff;
import gov.ca.cwds.data.legacy.cms.entity.facade.StaffBySupervisor;
import gov.ca.cwds.data.legacy.cms.persistence.BaseCwsCmsInMemoryPersistenceTest;
import java.util.Arrays;
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
  public void findStaffBySupervisorRacfId_success_whenSingleRecordFound() throws Exception {
    cleanAllAndInsert("/dbunit/StaffBySupervisor.xml");
    executeInTransaction(
        sessionFactory,
        (sessionFactory) -> {
          final Collection<StaffBySupervisor> staffList =
              testSubject.findStaffBySupervisorId("aab");
          assertEquals(1, staffList.size());
          final StaffBySupervisor expected =
              new StaffBySupervisor("aad", "CWDS3", "Ratnesh", "Raval", "5594871890", null);
          assertEquals(expected, staffList.iterator().next());
        });
  }

  @Test
  public void findStaffBySupervisorRacfId_returnsEmpty_whenUnknownRacfId() throws Exception {
    cleanAllAndInsert("/dbunit/StaffBySupervisor.xml");
    executeInTransaction(
        sessionFactory,
        (sessionFactory) -> {
          final Collection<StaffBySupervisor> staffList =
              testSubject.findStaffBySupervisorId("UnknownId");
          assertEquals(0, staffList.size());
        });
  }

  @Test
  public void findStaffBySupervisorRacfId_returnsEmpty_whenSupervisorHasNoSubordinates()
      throws Exception {
    cleanAllAndInsert("/dbunit/StaffBySupervisor.xml");
    executeInTransaction(
        sessionFactory,
        (sessionFactory) -> {
          final Collection<StaffBySupervisor> staffList =
              testSubject.findStaffBySupervisorId("0Ki");
          assertEquals(0, staffList.size());
        });
  }

  @Test
  public void countClientsByStaffIds_success_whenHave2ResultsBy3IdsInSearch() throws Exception {
    // given
    cleanAllAndInsert("/dbunit/ClientCountByStaff.xml");
    executeInTransaction(
        sessionFactory,
        (sessionFactory) -> {
          // when
          final Collection<ClientCountByStaff> actual =
              testSubject.countClientsByStaffIds(Arrays.asList("0Ht", "0I2", "0ME"));

          // then
          assertThat(actual.size(), is(2));
          assertThat(
              actual,
              containsInAnyOrder(
                  new ClientCountByStaff("0Ht", 30, 22), new ClientCountByStaff("0ME", 18, 5)));
        });
  }

  @Test
  public void countClientsByStaffIds_empty_whenNoResults() {
    executeInTransaction(
        sessionFactory,
        (sessionFactory) -> {
          // when
          final Collection<ClientCountByStaff> actual =
              testSubject.countClientsByStaffIds(Arrays.asList("nothing", "here"));

          // then
          assertThat(actual.size(), is(0));
        });
  }
}
