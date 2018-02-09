package gov.ca.cwds.data.legacy.cms.dao;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import gov.ca.cwds.data.legacy.cms.entity.DualAgencyServicesHistory;
import gov.ca.cwds.data.legacy.cms.persistence.BaseCwsCmsInMemoryPersistenceTest;
import java.time.LocalDate;
import java.util.Collection;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * @author CWDS TPT-3 Team
 */
public class DualAgencyServicesHistoryDaoTest extends BaseCwsCmsInMemoryPersistenceTest {

  private static final String DBUNIT_SCRIPT = "/dbunit/DualAgencyServicesHistory.xml";
  private DualAgencyServicesHistoryDao testSubject = null;

  @Before
  public void before() throws Exception {
    testSubject = new DualAgencyServicesHistoryDao(sessionFactory);
    cleanAllAndInsert(DBUNIT_SCRIPT);
  }

  @After
  public void after() throws Exception {
    cleanAll(DBUNIT_SCRIPT);
  }

  @Test
  public void findByClientId_success_whenMultipleRecords() {
    executeInTransaction(sessionFactory, (sessionFactory) -> {
      final Collection<DualAgencyServicesHistory> actualResults = testSubject.findByClientId("R06FKZ20X5");
      assertThat(actualResults.size(), is(2));
    });
  }

  @Test
  public void findByClientId_success_whenSingleRecord() {
    executeInTransaction(sessionFactory, (sessionFactory) -> {
      // when
      final Collection<DualAgencyServicesHistory> actualResults = testSubject.findByClientId("9HNHgeH00E");

      // then
      assertThat(actualResults.size(), is(1));
      final DualAgencyServicesHistory actual = actualResults.iterator().next();
      assertThat(actual.getFkclientT(), is("9HNHgeH00E"));
      assertThat(actual.getThirdId(), is("9vEL8AG0X5"));
      assertThat(actual.getStartDate(), is(LocalDate.of(2010, 9, 1)));
      assertThat(actual.getEndDate(), is(LocalDate.of(2018, 11, 1)));
      assertThat(actual.getProvidedByCode(), is("R"));
      assertThat(actual.getOtherDescription(), is("Description CCC"));
    });
  }

  @Test
  public void findByClientId_empty_whenNoRecordsFound() {
    executeInTransaction(sessionFactory, (sessionFactory) -> {
      final Collection<DualAgencyServicesHistory> actualResults = testSubject.findByClientId("nonExistent");
      assertThat(actualResults.size(), is(0));
    });
  }


}
