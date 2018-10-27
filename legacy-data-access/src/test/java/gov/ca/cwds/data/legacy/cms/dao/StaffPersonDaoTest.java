package gov.ca.cwds.data.legacy.cms.dao;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertEquals;

import gov.ca.cwds.data.legacy.cms.entity.facade.StaffBySupervisor;
import gov.ca.cwds.data.legacy.cms.persistence.BaseCwsCmsInMemoryPersistenceTest;
import java.util.Arrays;
import java.util.Collection;
import java.util.Map;
import java.util.Set;
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
  public void findClientIdsByStaffIds_success_whenHave2ResultsBy3IdsInSearch()
      throws Exception {
    // given
    cleanAllAndInsert("/dbunit/ClientCountByStaff.xml");
    executeInTransaction(
        sessionFactory,
        (sessionFactory) -> {
          // when
          final Map<String, Set<String>> actual =
              testSubject.findClientIdsByStaffIds(
                  Arrays.asList("0Ht", "0I2", "0ME"));

          // then
          assertThat(actual.size(), is(3));
          assertThat(actual.get("0Ht").size(), is(45));
          assertThat(actual.get("0I2").size(), is(0));
          assertThat(actual.get("0ME").size(), is(22));
        });
  }

  @Test
  public void findClientIdsByStaffIds_emptyListsIn2Maps_whenNoResults() {
    executeInTransaction(
        sessionFactory,
        (sessionFactory) -> {
          // when
          final Map<String, Set<String>> actual =
              testSubject.findClientIdsByStaffIds(Arrays.asList("nothing", "here"));

          // then
          assertThat(actual.size(), is(2));
          assertThat(actual.get("nothing").size(), is(0));
          assertThat(actual.get("here").size(), is(0));
        });
  }
}
