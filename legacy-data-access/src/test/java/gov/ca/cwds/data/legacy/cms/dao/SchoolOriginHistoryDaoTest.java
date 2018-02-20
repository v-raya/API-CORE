package gov.ca.cwds.data.legacy.cms.dao;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import gov.ca.cwds.data.legacy.cms.entity.SchoolOriginHistory;
import gov.ca.cwds.data.legacy.cms.entity.enums.YesNoUnknown;
import gov.ca.cwds.data.legacy.cms.persistence.BaseCwsCmsInMemoryPersistenceTest;
import java.time.LocalDate;
import java.util.Collection;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * @author CWDS TPT-3 Team
 */
public class SchoolOriginHistoryDaoTest extends BaseCwsCmsInMemoryPersistenceTest {

  private static final String DBUNIT_SCRIPT = "/dbunit/SchoolOriginHistory.xml";
  private SchoolOriginHistoryDao testSubject = null;

  @Before
  public void before() throws Exception {
    testSubject = new SchoolOriginHistoryDao(sessionFactory);
    cleanAllAndInsert(DBUNIT_SCRIPT);
  }

  @After
  public void after() throws Exception {
    cleanAll(DBUNIT_SCRIPT);
  }

  @Test
  public void findByClientId_success_whenMultipleRecords() {
    executeInTransaction(sessionFactory, (sessionFactory) -> {
      final Collection<SchoolOriginHistory> actualResults = testSubject.findByClientId("2fK0EyH0Wl");
      assertThat(actualResults.size(), is(2));
    });
  }

  @Test
  public void findByClientId_success_whenSingleRecord() {
    executeInTransaction(sessionFactory, (sessionFactory) -> {
      // when
      final Collection<SchoolOriginHistory> actualResults = testSubject.findByClientId("01234560Wl");

      // then
      assertThat(actualResults.size(), is(1));
      final SchoolOriginHistory actual = actualResults.iterator().next();
      assertThat(actual.getFkchldClt(), is("01234560Wl"));
      assertThat(actual.getThirdId(), is("123yPpp0Wl"));
      assertThat(actual.getSchoolDecision(), is(YesNoUnknown.YES));
      assertThat(actual.getSchoolDecisionDate(), is(LocalDate.of(2013, 2, 2)));
      assertThat(actual.getFkedPvdrt(), is("M6j2wGUq26"));
    });
  }

  @Test
  public void findByClientId_empty_whenNoRecordsFound() {
    executeInTransaction(sessionFactory, (sessionFactory) -> {
      final Collection<SchoolOriginHistory> actualResults = testSubject.findByClientId("nonExistent");
      assertThat(actualResults.size(), is(0));
    });
  }


}
